package controller;

import dao.GetDiscountByBookId;
import model.Books;
import model.Customers;
import model.OrderDetails;
import model.Orders;
import service.BookService;
import service.CustomerService;
import service.OrderService;
import utils.CommonView;
import utils.ImageUtils;
import view.CreateInvoiceView;
import view.CustomerView;
import view.SelectBookForOrderView;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderController implements ActionListener {
    private CreateInvoiceView orderView ;
    private OrderService orderService ;
    private CustomerService customerService ;
    private SelectBookForOrderView selectBookForOrderView ;
    private BookService bookService ;
    private Books currentSelectedBook = null; // Lưu sách đang được chọn
    private GetDiscountByBookId getDiscountByBookId; // Để lấy discount



    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^0\\d{9,10}$");
    // Định dạng ngày tháng bạn muốn sử dụng (ví dụ)
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    private static final SimpleDateFormat DATE_FORMAT_DISPLAY = new SimpleDateFormat("dd/MM/yyyy"); // Dùng để hiển thị ngày
    private NumberFormat currencyFormatter; // Để định dạng tiền tệ


    public OrderController(CreateInvoiceView orderView) {
        this.orderView = orderView ;
        this.orderService = new OrderService() ;
        this.customerService = new CustomerService() ;
        this.bookService = new BookService() ;
        this.currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        this.getDiscountByBookId = new GetDiscountByBookId();
        addTableSelectionListener();
        start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == orderView.getBtnSearchCustmer()) {
            filterCustomer();
        } else if (source == orderView.getBtnSearchBook()) {
            openSelectBook(); // Đổi tên hàm cho rõ ràng
        } else if (source == orderView.getBtnAdd()) {
            handleAddItemToTable(); // Thay đổi hành động nút Add
        } else if (source == orderView.getBtnEdit()) {
            handleEditItemInTable();
        } else if (source == orderView.getBtnDelete()) {
            handleDeleteItemFromTable();
        } else if (source == orderView.getBtnReset()) {
            handleReset();
        } else if (source == orderView.getBtnConfirm()) { // Sử dụng nút Confirm để lưu
            handleConfirmInvoice();
        } else if (source == orderView.getBtnCancel()) {
            handleCancel();
        }
    }

    public void addTableSelectionListener() {
        orderView.getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectRow = orderView.getTable().getSelectedRow();
                if (!e.getValueIsAdjusting() && selectRow != -1 ) {

                    if (selectRow >= 0) {
                        DefaultTableModel model = orderView.getTableModel();
                        // Lấy dữ liệu từ model, xử lý null an toàn
                        String bookId = model.getValueAt(selectRow, 0).toString();
                        String bookName = model.getValueAt(selectRow, 1).toString();
                        int quantity = Integer.parseInt(model.getValueAt(selectRow, 2).toString());
                        double priceBook = Double.parseDouble(model.getValueAt(selectRow, 3).toString());

                        currentSelectedBook = bookService.getBookByID(bookId);
                        if (currentSelectedBook != null) {
                            orderView.getTextFieldBookId().setText(currentSelectedBook.getBookID());
                            orderView.getTextFieldBookName().setText(currentSelectedBook.getBookName());
                            orderView.getTextFieldUnitPrice().setText(currencyFormatter.format(currentSelectedBook.getPrice())); // Định dạng tiền tệ
                            displayBookImage(currentSelectedBook.getUrlImage()); // Hiển thị ảnh
                        } else {
                            clearItemInputFields();
                            CommonView.showErrorMessage(null, "Không tìm thấy thông tin sách với ID: " + bookId);
                        }


                        // --- Cập nhật trạng thái nút ---
                        orderView.getBtnAdd().setEnabled(false);
                        orderView.getBtnEdit().setEnabled(true);
                        orderView.getBtnDelete().setEnabled(true);
                        orderView.getBtnReset().setEnabled(true);
                    } else {
                        // Nếu không có hàng nào được chọn
                        resetInputFieldsState();
                    }
                }
            }
        });
    }



    private void start() {
        // --- Trạng thái nút ban đầu ---
        // Header
        orderView.getTextFieldInvoiceId().setText(""); // Có thể để trống hoặc tự sinh sau
        orderView.getTextFieldCustomerId().setText("");
        orderView.getTextFieldCustomerName().setText("");
        orderView.getTextFieldPhoneNumber().setText("");

        // Item Details
        clearItemInputFields();
        resetItemButtonStates(); // Reset nút Thêm/Sửa/Xóa item

        // Footer/Totals
        orderView.getTextFieldTotalAmount().setText(currencyFormatter.format(0));
        orderView.getTextFieldDiscount().setText("0");
        orderView.getTextFieldTotalInvoiceAmount().setText(currencyFormatter.format(0));

        // Buttons Footer
        orderView.getBtnConfirm().setEnabled(true); // Bật nút xác nhận
        orderView.getBtnCancel().setEnabled(true); // Bật nút hủy

        // Table
        orderView.getTableModel().setRowCount(0); // Xóa bảng

        // Set ngày hiện tại
        Date currentDate = new Date();
        orderView.getTextFieldDate().setText(DATE_FORMAT_DISPLAY.format(currentDate));
        orderView.getTextFieldDate().setEditable(false); // Không cho sửa ngày

        // Vô hiệu hóa nút không cần thiết ban đầu
        // orderView.getBtnCreateInvoice().setEnabled(false); // Nếu không dùng nút này
    }
    // Xóa các trường nhập thông tin sách
    private void clearItemInputFields() {
        orderView.getTextFieldBookId().setText("");
        orderView.getTextFieldBookName().setText("");
        orderView.getTextFieldQuantity().setText("1"); // Reset số lượng về 1
        orderView.getTextFieldUnitPrice().setText("");
        orderView.setPreviewImage(null); // Xóa ảnh xem trước
        currentSelectedBook = null; // Reset sách đang chọn
        orderView.getTable().clearSelection(); // Bỏ chọn trên bảng
    }

    // Reset trạng thái các nút Thêm/Sửa/Xóa Item
    private void resetItemButtonStates() {
        orderView.getBtnAdd().setEnabled(false); // Chỉ bật Add khi đã chọn sách
        orderView.getBtnEdit().setEnabled(false);
        orderView.getBtnDelete().setEnabled(false);
        orderView.getBtnReset().setEnabled(true); // Nút reset luôn bật
    }

    private void filterCustomer() {
        String phoneNumber = orderView.getTextFieldPhoneNumber().getText().trim();
        if (phoneNumber.isEmpty()) {
            CommonView.showErrorMessage(null, "Vui lòng nhập Số điện thoại để tìm khách hàng.");
            return;
        }

        Matcher phoneMatcher = PHONE_NUMBER_PATTERN.matcher(phoneNumber);
        if (!phoneMatcher.matches()) {
            CommonView.showErrorMessage(null, "Số điện thoại không hợp lệ (cần 10-11 số, bắt đầu bằng 0).");
            return; // Dừng lại nếu SĐT không hợp lệ
        }

        Customers customer = customerService.getCustomerByPhoneNumber(phoneNumber);
        if (customer != null) {
            orderView.getTextFieldCustomerId().setText(customer.getCustomerID());
            orderView.getTextFieldCustomerName().setText(customer.getLastName() + " " + customer.getFirstName());
            // Giữ nguyên SĐT đã nhập để người dùng thấy
        } else {
            int confirmation = JOptionPane.showConfirmDialog(
                    orderView, // Parent component
                    "Không tìm thấy khách hàng với SĐT '" + phoneNumber + "'.\nBạn có muốn thêm khách hàng mới không?",
                    "Xác nhận thêm khách hàng",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (confirmation == JOptionPane.YES_OPTION) {
                showAddCustomerPopup(phoneNumber);
            } else {
                // Người dùng chọn NO, xóa các trường liên quan đến khách hàng
                orderView.getTextFieldCustomerId().setText("");
                orderView.getTextFieldCustomerName().setText("");
                // Giữ lại số điện thoại để người dùng có thể sửa
            }
        }
    }

    //khi click vao tim kiem thi chon khach hang
    private void openSelectBook() {
        // Nếu chưa có cửa sổ chọn sách, tạo mới
        // if (selectBookForOrderView == null || !selectBookForOrderView.isDisplayable()) {
        selectBookForOrderView = new SelectBookForOrderView();
        // }

        selectBookForOrderView.setVisible(true); // Hiển thị cửa sổ chọn sách

        // Lấy sách được chọn từ cửa sổ đó (SelectBookForOrderView cần có phương thức trả về sách đã chọn)
        Books selectedBook = selectBookForOrderView.getBook(); // Giả sử có phương thức getBook()

        if (selectedBook != null) {
            currentSelectedBook = selectedBook; // Lưu lại sách vừa chọn
            orderView.getTextFieldBookId().setText(selectedBook.getBookID());
            orderView.getTextFieldBookName().setText(selectedBook.getBookName());
            orderView.getTextFieldUnitPrice().setText(currencyFormatter.format(selectedBook.getPrice())); // Định dạng tiền tệ
            orderView.getTextFieldQuantity().setText("1"); // Reset số lượng về 1

            // Hiển thị ảnh sách
            displayBookImage(selectedBook.getUrlImage());

            // Bật nút "THÊM" sau khi đã chọn sách
            orderView.getBtnAdd().setEnabled(true);
            orderView.getBtnEdit().setEnabled(false); // Tắt sửa/xóa khi vừa chọn sách mới
            orderView.getBtnDelete().setEnabled(false);

        } else {
            // Nếu người dùng đóng cửa sổ mà không chọn sách
             orderView.getBtnAdd().setEnabled(false); // Tắt nút Add nếu không chọn được sách
        }
    }

    // Hiển thị ảnh sách từ URL
    private void displayBookImage(String imageUrl) {
        ImageIcon bookIcon = null;
        if (imageUrl != null && !imageUrl.trim().isEmpty()) {
            // Cố gắng lấy đường dẫn tuyệt đối (quan trọng nếu ảnh nằm ngoài resources)
            String absolutePath = ImageUtils.getAbsolutePathFromRelative(imageUrl);
            if (absolutePath != null && Files.exists(Paths.get(absolutePath))) {
                // Scale ảnh từ file
                bookIcon = ImageUtils.scaleImageFromFile(absolutePath, ImageUtils.DEFAULT_IMAGE_WIDTH, ImageUtils.DEFAULT_IMAGE_HEIGHT);
            } else {
                // Thử load từ resources nếu đường dẫn tương đối và không tìm thấy file
                bookIcon = ImageUtils.scaleImageResource(imageUrl, ImageUtils.DEFAULT_IMAGE_WIDTH, ImageUtils.DEFAULT_IMAGE_HEIGHT);
                if (bookIcon == null) {
                    System.err.println("Ảnh không tìm thấy tại file: " + absolutePath + " hoặc resource: " + imageUrl);
                }
            }
        } else {
            System.err.println("URL ảnh không hợp lệ hoặc trống.");
        }
        // Gọi hàm setPreviewImage của View để hiển thị ảnh (hoặc ảnh mặc định)
        orderView.setPreviewImage(bookIcon);
    }


    // Xử lý khi nhấn nút "THÊM" (Thêm item vào bảng tạm)
    private void handleAddItemToTable() {
        // 1. Validate đã chọn sách chưa?
        if (currentSelectedBook == null || orderView.getTextFieldBookId().getText().trim().isEmpty()) {
            CommonView.showErrorMessage(null, "Vui lòng chọn một quyển sách trước khi thêm.");
            openSelectBook(); // Mở lại cửa sổ chọn sách
            return;
        }

        // 2. Validate số lượng
        String quantityStr = orderView.getTextFieldQuantity().getText().trim();
        int quantity;
        try {
            quantity = Integer.parseInt(quantityStr);
            if (quantity <= 0) {
                CommonView.showErrorMessage(null, "Số lượng phải là số nguyên dương.");
                orderView.getTextFieldQuantity().requestFocus(); // Focus vào ô số lượng
                return;
            }
            // Kiểm tra số lượng tồn kho
            if (quantity > currentSelectedBook.getQuantity()) {
                CommonView.showErrorMessage(null, "Số lượng tồn kho không đủ (Còn lại: " + currentSelectedBook.getQuantity() + ").");
                orderView.getTextFieldQuantity().requestFocus();
                return;
            }
        } catch (NumberFormatException ex) {
            CommonView.showErrorMessage(null, "Số lượng phải là một con số.");
            orderView.getTextFieldQuantity().requestFocus();
            return;
        }

        // 3. Kiểm tra sách đã có trong bảng chưa
        DefaultTableModel model = orderView.getTableModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            if (model.getValueAt(i, 0).equals(currentSelectedBook.getBookID())) {
                // Sách đã tồn tại, hỏi người dùng muốn cộng dồn hay thay thế
                int choice = JOptionPane.showConfirmDialog(orderView,
                        "Sách '" + currentSelectedBook.getBookName() + "' đã có trong hóa đơn.\n" +
                                "Bạn có muốn cộng thêm số lượng " + quantity + " không?\n" +
                                "(Chọn NO để thay thế số lượng cũ bằng số lượng mới)",
                        "Sách đã tồn tại", JOptionPane.YES_NO_CANCEL_OPTION);

                if (choice == JOptionPane.YES_OPTION) {
                    int oldQuantity = (int) model.getValueAt(i, 2);
                    int newQuantity = oldQuantity + quantity;
                    // Kiểm tra lại tồn kho với số lượng mới
                    if (newQuantity > currentSelectedBook.getQuantity()) {
                        CommonView.showErrorMessage(null, "Tổng số lượng vượt quá tồn kho (Còn lại: " + currentSelectedBook.getQuantity() + ").");
                        return;
                    }
                    model.setValueAt(newQuantity, i, 2); // Cập nhật số lượng
                    // Tính lại thành tiền cho dòng đó
                    OrderDetails tempDetail = new OrderDetails();
                    tempDetail.setBook(currentSelectedBook);
                    tempDetail.setQuantity(newQuantity);
                    tempDetail.setDiscount(getDiscountByBookId.getDiscountByBookId(currentSelectedBook.getBookID())); // Lấy lại discount
                    model.setValueAt(tempDetail.getTotal(), i, 5); // Cập nhật thành tiền
                    updateInvoiceTotals(); // Cập nhật tổng hóa đơn
                    clearItemInputFields(); // Xóa input
                    resetItemButtonStates();
                    return; // Kết thúc sau khi cập nhật
                } else if (choice == JOptionPane.NO_OPTION) {
                    // Thay thế số lượng cũ bằng số lượng mới (validate lại tồn kho)
                    if (quantity > currentSelectedBook.getQuantity()) {
                        CommonView.showErrorMessage(null, "Số lượng tồn kho không đủ (Còn lại: " + currentSelectedBook.getQuantity() + ").");
                        return;
                    }
                    model.setValueAt(quantity, i, 2); // Cập nhật số lượng mới
                    // Tính lại thành tiền
                    OrderDetails tempDetail = new OrderDetails();
                    tempDetail.setBook(currentSelectedBook);
                    tempDetail.setQuantity(quantity);
                    tempDetail.setDiscount(getDiscountByBookId.getDiscountByBookId(currentSelectedBook.getBookID()));
                    model.setValueAt(tempDetail.getTotal(), i, 5);
                    updateInvoiceTotals();
                    clearItemInputFields();
                    resetItemButtonStates();
                    return;
                } else {
                    // Người dùng chọn Cancel hoặc đóng dialog
                    return; // Không làm gì cả
                }
            }
        }


        // 4. Lấy % giảm giá cho sách này (nếu có)
        float itemDiscountPercent = getDiscountByBookId.getDiscountByBookId(currentSelectedBook.getBookID());

        // 5. Tạo đối tượng OrderDetails tạm để tính toán
        OrderDetails newItem = new OrderDetails();
        newItem.setBook(currentSelectedBook);
        newItem.setQuantity(quantity);
        newItem.setDiscount(itemDiscountPercent);

        // 6. Thêm dòng mới vào bảng
        model.addRow(newItem.toTableRow());

        // 7. Cập nhật tổng tiền hóa đơn
        updateInvoiceTotals();

        // 8. Xóa các trường nhập liệu item và reset nút
        clearItemInputFields();
        resetItemButtonStates();
    }


    // Xử lý khi nhấn nút "SỬA"
    private void handleEditItemInTable() {
        int selectedRow = orderView.getTable().getSelectedRow();
        if (selectedRow < 0) {
            CommonView.showErrorMessage(null, "Vui lòng chọn một dòng trong bảng để sửa.");
            return;
        }

        // Validate số lượng mới
        String quantityStr = orderView.getTextFieldQuantity().getText().trim();
        int newQuantity;
        try {
            newQuantity = Integer.parseInt(quantityStr);
            if (newQuantity <= 0) {
                CommonView.showErrorMessage(null, "Số lượng phải là số nguyên dương.");
                return;
            }
            // Lấy lại BookID từ bảng để kiểm tra tồn kho
            DefaultTableModel model = orderView.getTableModel();
            String bookId = model.getValueAt(selectedRow, 0).toString();
            Books bookInRow = bookService.getBookByID(bookId);
            if (bookInRow != null && newQuantity > bookInRow.getQuantity()) {
                CommonView.showErrorMessage(null, "Số lượng tồn kho không đủ (Còn lại: " + bookInRow.getQuantity() + ").");
                return;
            }
            if(bookInRow == null){
                CommonView.showErrorMessage(null, "Không tìm thấy sách để kiểm tra tồn kho.");
                return;
            }

        } catch (NumberFormatException ex) {
            CommonView.showErrorMessage(null, "Số lượng phải là một con số.");
            return;
        }

        // Cập nhật số lượng trong bảng
        DefaultTableModel model = orderView.getTableModel();
        model.setValueAt(newQuantity, selectedRow, 2);

        // Tính lại thành tiền cho dòng đó
        Books book = bookService.getBookByID(model.getValueAt(selectedRow, 0).toString());
        float discount = (float) model.getValueAt(selectedRow, 4); // Lấy lại discount từ bảng
        OrderDetails tempDetail = new OrderDetails();
        tempDetail.setBook(book);
        tempDetail.setQuantity(newQuantity);
        tempDetail.setDiscount(discount);
        model.setValueAt(tempDetail.getTotal(), selectedRow, 5); // Cập nhật thành tiền

        // Cập nhật tổng hóa đơn
        updateInvoiceTotals();

        // Xóa input và reset nút
        clearItemInputFields();
        resetItemButtonStates();

        CommonView.showInfoMessage(null, "Đã cập nhật số lượng sản phẩm.");
    }

    // Xử lý khi nhấn nút "XÓA"
    private void handleDeleteItemFromTable() {
        int selectedRow = orderView.getTable().getSelectedRow();
        if (selectedRow < 0) {
            CommonView.showErrorMessage(null, "Vui lòng chọn một dòng trong bảng để xóa.");
            return;
        }

        int confirmation = JOptionPane.showConfirmDialog(orderView,
                "Bạn có chắc chắn muốn xóa sản phẩm này khỏi hóa đơn?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirmation == JOptionPane.YES_OPTION) {
            DefaultTableModel model = orderView.getTableModel();
            model.removeRow(selectedRow);

            // Cập nhật tổng hóa đơn
            updateInvoiceTotals();

            // Xóa input và reset nút
            clearItemInputFields();
            resetItemButtonStates();

            CommonView.showInfoMessage(null, "Đã xóa sản phẩm khỏi hóa đơn.");
        }
    }
    // Tính toán và cập nhật các trường tổng tiền
    public void updateInvoiceTotals() {
        DefaultTableModel model = orderView.getTableModel();
        double totalAmount = 0.0; // Tổng tiền hàng (trước giảm giá HĐ)

        for (int i = 0; i < model.getRowCount(); i++) {
            // Lấy giá trị từ cột "Thành tiền" (cột 5)
            Object value = model.getValueAt(i, 5);
            if (value instanceof Number) {
                totalAmount += ((Number) value).doubleValue();
            }
        }

//        double totalSpending = 0.0;
//
//        for (int i = 0; i < model.getRowCount(); i++) {
//            // Lấy giá trị từ cột "Thành tiền" (cột 5)
//            Object value = model.getValueAt(i, 5);
//            if (value instanceof Number) {
//                totalSpending += ((Number) value).doubleValue();
//            }
//        }

        // Hiển thị tổng tiền hàng (đã định dạng)
        orderView.setFormattedCurrency(orderView.getTextFieldTotalAmount(), totalAmount);

        // Lấy % giảm giá tổng hóa đơn
        double discountPercent = 0.0;
        try {
            String discountStr = orderView.getTextFieldDiscount().getText().trim();
            if (!discountStr.isEmpty()) {
                discountPercent = Double.parseDouble(discountStr);
            }
            if (discountPercent < 0 || discountPercent > 100) {
                // Có thể hiển thị lỗi nhẹ hoặc giới hạn lại giá trị
                // CommonView.showWarningMessage(orderView, "Giảm giá hóa đơn phải từ 0 đến 100%.");
                discountPercent = Math.max(0, Math.min(100, discountPercent)); // Giới hạn trong khoảng 0-100
                orderView.getTextFieldDiscount().setText(String.valueOf(discountPercent)); // Cập nhật lại ô input
            }
        } catch (NumberFormatException e) {
            // Nếu nhập không phải số, coi như giảm giá là 0
            // CommonView.showWarningMessage(orderView,"Giảm giá hóa đơn không hợp lệ, đặt về 0.");
            orderView.getTextFieldDiscount().setText("0"); // Reset về 0 nếu nhập lỗi
            discountPercent = 0.0;
        }

        // Tính tổng tiền thanh toán cuối cùng
        double finalInvoiceAmount = totalAmount * (1 - (discountPercent / 100.0));

        // Hiển thị tổng tiền thanh toán (đã định dạng)
        orderView.setFormattedCurrency(orderView.getTextFieldTotalInvoiceAmount(), finalInvoiceAmount);
    }

    // Xử lý khi nhấn nút "LÀM MỚI" (Reset)
    private void handleReset() {
        int confirmation = JOptionPane.showConfirmDialog(orderView,
                "Bạn có chắc chắn muốn làm mới toàn bộ hóa đơn?\nMọi thông tin chưa lưu sẽ bị mất.",
                "Xác nhận làm mới",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirmation == JOptionPane.YES_OPTION) {
            start(); // Gọi lại hàm khởi tạo để reset mọi thứ về ban đầu
        }
    }

    // Xử lý khi nhấn nút "HỦY BỎ"
    private void handleCancel() {
        int confirmation = JOptionPane.showConfirmDialog(orderView,
                "Bạn có muốn hủy bỏ việc tạo hóa đơn này không?",
                "Xác nhận hủy",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirmation == JOptionPane.YES_OPTION) {
            // Tùy chọn: Đóng cửa sổ/panel hoặc chỉ đơn giản là reset
            start(); // Reset về trạng thái ban đầu
            // Nếu đây là Dialog, có thể gọi dispose()
            // if (orderView.getParent() instanceof JDialog) { ((JDialog) orderView.getParent()).dispose(); }
        }
    }
    // Xử lý khi nhấn nút "XÁC NHẬN & LƯU" (Confirm)
    private void handleConfirmInvoice() {
        // 1. Validate Header Info
        String invoiceId = orderView.getTextFieldInvoiceId().getText().trim();
        String customerId = orderView.getTextFieldCustomerId().getText().trim();
        String dateStr = orderView.getTextFieldDate().getText().trim();

        if (invoiceId.isEmpty()) {
            CommonView.showErrorMessage(null, "Mã hóa đơn không được để trống.");
            orderView.getTextFieldInvoiceId().requestFocus();
            return;
        }
        // TODO: Kiểm tra xem Mã hóa đơn đã tồn tại chưa (nếu cần)
        // boolean exists = orderService.checkInvoiceIdExists(invoiceId);
        // if (exists) { ... }

        if (customerId.isEmpty()) {
            CommonView.showErrorMessage(null, "Vui lòng chọn hoặc thêm khách hàng.");
            orderView.getTextFieldPhoneNumber().requestFocus();
            return;
        }

        // 2. Validate Table Items
        DefaultTableModel model = orderView.getTableModel();
        if (model.getRowCount() == 0) {
            CommonView.showErrorMessage(null, "Hóa đơn phải có ít nhất một sản phẩm.");
            return;
        }

        // 3. Parse Date
        Date orderDate;
        try {
            orderDate = DATE_FORMAT_DISPLAY.parse(dateStr);
        } catch (ParseException e) {
            CommonView.showErrorMessage(null, "Định dạng ngày không hợp lệ.");
            return;
        }

        // 4. Get Customer Object
        Customers customer = customerService.getCustomerByCustomerId(customerId);
        if (customer == null) {
            CommonView.showErrorMessage(null, "Không tìm thấy thông tin khách hàng hợp lệ. Vui lòng kiểm tra lại.");
            return;
        }

        // 5. Create Orders Object (Header)
        Orders newOrder = new Orders();
        newOrder.setOrderId(invoiceId);
        newOrder.setCustomer(customer);
        newOrder.setOrderDate(orderDate);
        newOrder.setStatus("Chưa xử lý"); // Hoặc trạng thái mặc định khác

        // 6. Create List<OrderDetails> from Table
        ArrayList<OrderDetails> orderDetailsList = new ArrayList<>();
        for (int i = 0; i < model.getRowCount(); i++) {
            String bookId = model.getValueAt(i, 0).toString();
            int quantity = (int) model.getValueAt(i, 2);
            float discount = (float) model.getValueAt(i, 4);


            Books book = bookService.getBookByID(bookId);
            if (book == null) {
                CommonView.showErrorMessage(null, "Lỗi: Không tìm thấy sách với ID '" + bookId + "' trong cơ sở dữ liệu.");
                return; // Dừng lại nếu có lỗi dữ liệu
            }
            // Kiểm tra lại tồn kho lần cuối trước khi lưu
            if (quantity > book.getQuantity()) {
                CommonView.showErrorMessage(null, "Lỗi: Số lượng tồn kho sách '" + book.getBookName() + "' không đủ để hoàn tất đơn hàng.");
                return;
            }

            OrderDetails detail = new OrderDetails();
            detail.setOrderId(invoiceId); // Gán ID hóa đơn cho chi tiết
            detail.setBook(book);
            detail.setQuantity(quantity);
            detail.setDiscount(discount);

            orderDetailsList.add(detail);
        }

        // 7. Call Service to Save Order and Details (Cần sửa Service/DAO)
        try {
            // Giả sử có phương thức saveOrderWithDetails trong OrderService
            // Phương thức này nên xử lý transaction để lưu Orders, OrderDetails và cập nhật tồn kho Books
            boolean success = orderService.saveOrderWithDetails(newOrder, orderDetailsList);
            double total = 0;
            try {
                String priceStr = orderView.getTextFieldTotalAmount().getText().trim();
                System.out.println(priceStr);
                priceStr = priceStr.replaceAll("[^\\d.,]", "").replace(".", "").replace(",", ".").replace("₫","").replace(" ","");
                System.out.println(priceStr);

                total = Double.parseDouble(priceStr);
                // tiếp tục xử lý
            } catch (NumberFormatException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Giá trị nhập không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }


            boolean checkUpdateTotalMoney = customerService.updateSpending(newOrder.getCustomer().getCustomerID(),total);


            if (success && checkUpdateTotalMoney) {
                CommonView.showInfoMessage(null, "Tạo và lưu hóa đơn thành công!");
                start(); // Reset form sau khi lưu thành công
            } else {
                CommonView.showErrorMessage(null, "Lưu hóa đơn thất bại. Có lỗi xảy ra trong quá trình xử lý. Có the ma HĐ bi trung");
            }
        } catch (Exception ex) {
            CommonView.showErrorMessage(null, "Lỗi hệ thống khi lưu hóa đơn: " + ex.getMessage());
            ex.printStackTrace(); // In lỗi ra console để debug
        }
    }


    private void showAddCustomerPopup(String phoneNumber) {
        // Tạo thể hiện CustomerView tạm thời để lấy panel
        CustomerView tempCustomerView = new CustomerView();
        JPanel inputPanel = tempCustomerView.inputPanel(); // Gọi hàm tạo panel

        inputPanel.setPreferredSize(new Dimension(650, 250));

        // Điền sẵn số điện thoại vào form thêm mới
        tempCustomerView.getTextFieldPhone().setText(phoneNumber);
        tempCustomerView.getTextFieldPhone().setEditable(false); // Không cho sửa SĐT ở form này

        // Tạo các nút cho dialog
        String[] options = {"Lưu", "Hủy"};

        int result = JOptionPane.showOptionDialog(
                orderView, // Parent component
                inputPanel, // Panel cần hiển thị
                "Thêm khách hàng mới", // Tiêu đề dialog
                JOptionPane.OK_CANCEL_OPTION, // Loại Option (ảnh hưởng icon mặc định và giá trị trả về)
                JOptionPane.PLAIN_MESSAGE,    // Loại Message (không hiển thị icon chuẩn)
                null,       // Icon tùy chỉnh (null là không dùng)
                options,    // Text cho các nút
                options[0]  // Nút mặc định được chọn
        );

        if (result == JOptionPane.OK_OPTION) { // Người dùng nhấn "Lưu" (index 0)
            try {
                // Lấy dữ liệu từ các trường trong tempCustomerView
                String customerId = tempCustomerView.getTextFieldCustomerId().getText().trim();
                String lastName = tempCustomerView.getTextFieldLastName().getText().trim();
                String firstName = tempCustomerView.getTextFieldFirstName().getText().trim();
                String email = tempCustomerView.getTextFieldEmail().getText().trim();
                String dobString = tempCustomerView.getTextFieldDob().getText().trim();
                String note = tempCustomerView.getTextFieldNote().getText().trim();
                String gender = null;
                if (tempCustomerView.getRadioPositionNam().isSelected()) {
                    gender = "Nam"; // Hoặc giá trị phù hợp với DB/Model của bạn
                } else if (tempCustomerView.getRadioPositionNu().isSelected()) {
                    gender = "Nữ"; // Hoặc giá trị phù hợp với DB/Model của bạn
                }

                // Validate dữ liệu cơ bản (thêm các validate khác nếu cần)
                if (lastName.isEmpty() || firstName.isEmpty()) {
                    JOptionPane.showMessageDialog(orderView, "Vui lòng nhập đầy đủ Họ và Tên.", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
                    // Có thể gọi lại showAddCustomerPopup hoặc yêu cầu người dùng thử lại
                    return;
                }
                if (gender == null) {
                    JOptionPane.showMessageDialog(orderView, "Vui lòng chọn Giới tính.", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // Thêm validate cho Email, Ngày sinh nếu cần

                // Tạo đối tượng Customers mới
                Customers newCustomer = new Customers();
                newCustomer.setCustomerID(customerId); // ID thường do DB tự tạo hoặc bạn cần cơ chế tạo ID mới
                newCustomer.setLastName(lastName);
                newCustomer.setFirstName(firstName);
                newCustomer.setPhoneNumber(phoneNumber); // Dùng SĐT đã validate ban đầu
                newCustomer.setGender(gender); // Giả sử model có setGender(String)
                newCustomer.setEmail(email);
                newCustomer.setNote(note);

                Date currentDate = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                String formattedDate = formatter.format(currentDate);
                newCustomer.setCreationDate(currentDate);

                orderView.getTextFieldDate().setText(formattedDate);

                // Xử lý ngày sinh (cần try-catch vì parse có thể lỗi)
                if (!dobString.isEmpty()) {
                    try {
                        Date dob = DATE_FORMAT.parse(dobString);
                        newCustomer.setDateOfBirth(new java.sql.Date(dob.getTime())); // Giả sử model có setDob(java.sql.Date)
                    } catch (java.text.ParseException pe) {
                        JOptionPane.showMessageDialog(orderView, "Định dạng Ngày sinh không hợp lệ (ví dụ: dd/MM/yyyy).", "Lỗi định dạng", JOptionPane.ERROR_MESSAGE);
                        return; // Dừng lại nếu ngày sinh sai
                    }
                }

                // Gọi service để thêm khách hàng mới
                boolean success = customerService.insertCustomer(newCustomer); // Cần tạo hàm này trong CustomerService

                if (success) {
                    JOptionPane.showMessageDialog(orderView, "Thêm khách hàng mới thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);

                    // Tùy chọn: Lấy lại thông tin khách hàng vừa thêm (để có ID) và cập nhật UI
                    Customers addedCustomer = customerService.getCustomerByPhoneNumber(phoneNumber);
                    if (addedCustomer != null) {
                        orderView.getTextFieldCustomerId().setText(addedCustomer.getCustomerID());
                        orderView.getTextFieldCustomerName().setText(addedCustomer.getLastName() + " " + addedCustomer.getFirstName());
                        orderView.getTextFieldPhoneNumber().setText(addedCustomer.getPhoneNumber());
                        orderView.getTextFieldCustomerId().setEditable(false);
                        orderView.getTextFieldCustomerName().setEditable(false);
                    }
                } else {
                    JOptionPane.showMessageDialog(orderView, "Thêm khách hàng thất bại. Vui lòng thử lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(orderView, "Đã xảy ra lỗi khi lưu khách hàng: " + ex.getMessage(), "Lỗi hệ thống", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace(); // In lỗi ra console để debug
            }
        } else {
            // Người dùng nhấn "Hủy" hoặc đóng dialog
            // Có thể không cần làm gì hoặc xóa các trường
            orderView.getTextFieldCustomerId().setText("");
            orderView.getTextFieldCustomerName().setText("");
            }
    }

    private void resetInputFieldsState() {
        clear(); // Gọi hàm clear của View (đã bao gồm cả Note)
        orderView.getBtnAdd().setEnabled(true);
        orderView.getBtnEdit().setEnabled(false);
        orderView.getBtnDelete().setEnabled(false);
        // Xóa lựa chọn trên bảng
        orderView.getTable().clearSelection();
        // Clear RadioButton nhập liệu (đã làm trong customerView.clear())
    }

    private void clear(){
        orderView.getTextFieldBookId().setText("");
        orderView.getTextFieldBookName().setText("");
        orderView.getTextFieldQuantity().setText("1");
        orderView.getTextFieldUnitPrice().setText("");

        orderView.setPreviewImage(null);
    }


}





