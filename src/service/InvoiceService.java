package service;

import dao.InvoiceDAO;
import model.Books;
import model.DiscountDetails;
import model.Invoice;
import model.InvoiceDetails;
import utils.Search;

import java.util.ArrayList;

public class InvoiceService {
    private InvoiceDAO invoiceDAO;

    public InvoiceService() {
        this.invoiceDAO = new InvoiceDAO();
    }

    public InvoiceService(InvoiceDAO invoiceDAO) {
        this.invoiceDAO = invoiceDAO;
    }

    public boolean deleteInvoice(String invoiceId) {
        return invoiceDAO.deleteInvoice(invoiceId) > 0;
    }

    public ArrayList<Invoice> getAllInvoice() {
        return invoiceDAO.getAllInvoice();
    }

    public boolean updateInvoice(Invoice invoice) {
        return invoiceDAO.updateInvoice(invoice) > 0;
    }

    public Invoice getInvoiceByID(String invoiceID) {
        if (invoiceID != null && !invoiceID.isEmpty()) {
            return invoiceDAO.getInvoiceByID(invoiceID);
        }
        return null;
    }

    public ArrayList<Invoice> getInvoiceSearch(Invoice invoice) {
        return invoiceDAO.listSearchInvoice(invoice);
    }

    public ArrayList<InvoiceDetails> getInvoiceDetailByID(String invoiceID) {
        //lay ra toan bo chi tiet hoa don
        ArrayList<InvoiceDetails> invoices = invoiceDAO.listInvoiceDetails(invoiceID);
        //trong chi tiet hoa don thi chi co 1 ma hoa don va co nhieu sach
        //lay ra id cua sach
        ArrayList<String> bookIDs = new ArrayList<>();
        for (InvoiceDetails invoiceDetail : invoices) {
            bookIDs.add(invoiceDetail.getBookID());
        }
        //so sanh voi toan bo sach trong cua hang de lay ra doi tuong sach
        ArrayList<Books> listBooks = Search.listBooks(bookIDs);

        //kiem tra sach do co dang duoc giam gia hay khong
        ArrayList<DiscountDetails> listBookDiscount = new DiscountDetailService().getAllDiscountDetails();
        //kiem tra xem ngay cua hoa don co nam trong khoang thoi gian giam gia khong

        return invoices;
    }
}
