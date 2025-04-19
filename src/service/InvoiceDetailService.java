package service;

import dao.InvoiceDAO;
import model.*;
import utils.Search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InvoiceDetailService {
    private final InvoiceDAO invoiceDAO;
    private final DiscountDetailService discountDetailService;
    public InvoiceDetailService(InvoiceDAO invoiceDAO, DiscountDetailService discountDetailService) {
        this.invoiceDAO = invoiceDAO;
        this.discountDetailService = discountDetailService;
    }
    public InvoiceDetailService() {
        this.invoiceDAO = new InvoiceDAO();
        this.discountDetailService = new DiscountDetailService();
    }

    // Phương thức chính: Lấy chi tiết hóa đơn theo invoiceID
    public ArrayList<InvoiceDetails> getInvoiceDetailByID(String invoiceID) {
        // Lấy danh sách chi tiết hóa đơn và sách, da su li
        ArrayList<InvoiceDetails> invoices = fetchInvoiceDetailsWithBooks(invoiceID);
        if (invoices.isEmpty()) {
            return invoices; // Trả về danh sách rỗng nếu không tìm thấy chi tiết hóa đơn
        }

        // Lấy ngày hóa đơn để kiểm tra giảm giá
        java.util.Date dayOfEstablishment = getInvoiceDate(invoiceID);
        if (dayOfEstablishment == null) {
            // Nếu không lấy được ngày, gán discount = 0 và tính totalMoney
            for (InvoiceDetails invoiceDetail : invoices) {
                invoiceDetail.setDiscount(0);
                invoiceDetail.setTotalMoney(invoiceDetail.getBooks().getPrice() * invoiceDetail.getQuantity());
            }
            return invoices;
        }

        // Kiểm tra và áp dụng giảm giá
        applyDiscounts(invoices, dayOfEstablishment);

        return invoices;
    }

    // Lấy chi tiết hóa đơn và thông tin sách
    private ArrayList<InvoiceDetails> fetchInvoiceDetailsWithBooks(String invoiceID) {
        ArrayList<InvoiceDetails> invoices = new ArrayList<>();
        ArrayList<String> bookIDs = new ArrayList<>();

        // Lấy danh sách chi tiết hóa đơn
        invoices = invoiceDAO.listInvoiceDetails(invoiceID);
        if (invoices.isEmpty()) {
            return invoices;
        }

        // Thu thập bookIDs
        for (InvoiceDetails invoiceDetail : invoices) {
            bookIDs.add(invoiceDetail.getBookID());
        }

        // Lấy thông tin sách đầy đủ từ Search
        ArrayList<Books> listBooks = Search.listBooks(bookIDs);

        // Gán Books vào InvoiceDetails
        for (InvoiceDetails invoiceDetail : invoices) {
            Books book = listBooks.stream()
                    .filter(b -> b.getBookID().equals(invoiceDetail.getBookID()))
                    .findFirst()
                    .orElse(null);
            invoiceDetail.setBooks(book);
        }

        return invoices;
    }

    // Hàm phụ: Lấy ngày hóa đơn (dayOfEstablishment)
    private java.util.Date getInvoiceDate(String invoiceID) {
        Invoice invoice = invoiceDAO.getInvoiceByID(invoiceID);
        return invoice != null ? invoice.getDate() : null;
    }

    // Hàm phụ: Kiểm tra và áp dụng giảm giá
    private void applyDiscounts(ArrayList<InvoiceDetails> invoices, java.util.Date dayOfEstablishment) {
        // Lấy danh sách bookIDs
        ArrayList<String> bookIDs = new ArrayList<>();
        for (InvoiceDetails invoiceDetail : invoices) {
            bookIDs.add(invoiceDetail.getBookID());
        }

        // Lấy danh sách giảm giá
        ArrayList<DiscountDetails> listBookDiscount = discountDetailService.getAllDiscountDetails();

        // Lọc các chương trình giảm giá hợp lệ và chọn percent cao nhất
        Map<String, Double> bestDiscounts = new HashMap<>();
        for (DiscountDetails discountDetail : listBookDiscount) {
            String bookID = discountDetail.getBook().getBookID();
            Discount discount = discountDetail.getDiscount();
            java.util.Date startDate = discount.getStartDate();
            java.util.Date endDate = discount.getEndDate();

            // Kiểm tra xem ngày hóa đơn có nằm trong khoảng thời gian giảm giá không
            if (startDate != null && endDate != null && dayOfEstablishment != null &&
                    !dayOfEstablishment.before(startDate) && !dayOfEstablishment.after(endDate)) {
                double percent = discountDetail.getPercent();
                // Cập nhật percent cao nhất cho bookID
                bestDiscounts.compute(bookID, (key, oldPercent) ->
                        oldPercent == null || percent > oldPercent ? percent : oldPercent);
            }
        }

        // Áp dụng giảm giá và tính totalMoney
        for (InvoiceDetails invoiceDetail : invoices) {
            String bookID = invoiceDetail.getBookID();
            Double percent = bestDiscounts.get(bookID);

            if (percent != null) {
                // Tính discount (giá trị giảm giá)
                invoiceDetail.setDiscount(percent);

                // Tính totalMoney (tổng tiền sau giảm giá)
                double discountValue = invoiceDetail.getBooks().getPrice() * invoiceDetail.getQuantity() * (percent / 100);
                double total = invoiceDetail.getBooks().getPrice() * invoiceDetail.getQuantity() - discountValue;
                invoiceDetail.setTotalMoney(total);
            } else {
                // Nếu không có giảm giá
                invoiceDetail.setDiscount(0);
                invoiceDetail.setTotalMoney(invoiceDetail.getBooks().getPrice() * invoiceDetail.getQuantity());
            }
        }
    }
}