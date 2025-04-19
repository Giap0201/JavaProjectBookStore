package controller;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderController implements ActionListener {
    private CreateInvoiceView orderView ;
    private OrderService orderService ;
    private CustomerService customerService ;
    private SelectBookForOrderView selectBookForOrderView ;
    private BookService bookService ;

    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^0\\d{9,10}$");
    // Định dạng ngày tháng bạn muốn sử dụng (ví dụ)
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    private model.Books Books;

    public OrderController(CreateInvoiceView orderView) {
        this.orderView = orderView ;
        this.orderService = new OrderService() ;
        this.customerService = new CustomerService() ;
        this.bookService = new BookService() ;
        addTableSelectionListener();
        start();
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
                        //float discount = Float.parseFloat(model.getValueAt(selectRow, 4).toString());
                        // double totalMoney = getDoubleValueFromModel(model, selectRow, 7); // Lấy nếu cần hiển thị
                        // String creationDateStr = getStringValueFromModel(model, selectRow, 8); // Lấy nếu cần

                        orderView.getTextFieldBookId().setText(bookId);
                        orderView.getTextFieldBookName().setText(bookName);
                        orderView.getTextFieldQuantity().setText(String.valueOf(quantity));
                        orderView.getTextFieldUnitPrice().setText(String.valueOf(priceBook));

//                        if ("Nam".equalsIgnoreCase(gender)) {
//                            customerView.getRadioPositionNam().setSelected(true);
//                        } else if ("Nữ".equalsIgnoreCase(gender)) {
//                            customerView.getRadioPositionNu().setSelected(true);
//                        } else {
//                            // Clear selection nếu giới tính không xác định
//                            customerView.getRadioPositionNam().setSelected(false);
//                            customerView.getRadioPositionNu().setSelected(false);
//                            // Nếu có getter cho ButtonGroup: customerView.getPositionGroup().clearSelection();
//                        }

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

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == orderView.getBtnSearchCustmer()){
            filterCustomer();
        }else if(e.getSource() == orderView.getBtnSearchBook()){
            openSelectCustomer();
        }else if(e.getSource() == orderView.getBtnAdd()){
            handleAddOrderBook();
        }else if (e.getSource() == orderView.getBtnCreateInvoice()){
            handleAddOrder();
        }
    }

    private void start(){
        orderView.getBtnAdd().setEnabled(false);
        orderView.getBtnEdit().setEnabled(false);
        orderView.getBtnDelete().setEnabled(false);
        orderView.getBtnReset().setEnabled(true);
        Date currentDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = formatter.format(currentDate);
        orderView.getTextFieldDate().setText(formattedDate);
    }

    private void filterCustomer(){
        String phoneNumber = orderView.getTextFieldPhoneNumber().getText().trim();
        if(phoneNumber.isEmpty()){
            CommonView.showErrorMessage(null,"Vui lòng nhap SĐT ");
            return;
        }
        // 2. Validate định dạng Số điện thoại (dùng Regex)
        Matcher phoneMatcher = PHONE_NUMBER_PATTERN.matcher(phoneNumber);
        if (!phoneMatcher.matches()) {
            CommonView.showErrorMessage(null,"Số điện thoại không hợp lệ (cần 10-11 số, bắt đầu bằng 0).");
            throw new IllegalArgumentException("Số điện thoại không hợp lệ (cần 10-11 số, bắt đầu bằng 0).");
        }
        Customers customer = customerService.getCustomerByPhoneNumber(phoneNumber);
        if(customer != null){
            orderView.getTextFieldCustomerId().setText(customer.getCustomerID());
            orderView.getTextFieldCustomerName().setText(customer.getLastName()+" "+customer.getFirstName());
            orderView.getTextFieldPhoneNumber().setText(customer.getPhoneNumber());
        }else{
            int confirmation = JOptionPane.showConfirmDialog(
                orderView,
                "Khách hàng với số điện thoại '" + phoneNumber + "' không tồn tại. Bạn có muốn thêm mới?",
                "Xác nhận thêm khách hàng",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );

            if (confirmation == JOptionPane.YES_OPTION) {
                showAddCustomerPopup(phoneNumber);
            } else {
                // Người dùng chọn NO, có thể xóa các trường khác nếu muốn
                orderView.getTextFieldCustomerId().setText("");
                orderView.getTextFieldCustomerName().setText("");
                // Giữ lại số điện thoại đã nhập để người dùng có thể sửa và tìm lại
            }
        }
    }

    //khi click vao tim kiem thi chon khach hang
    private void openSelectCustomer() {
        selectBookForOrderView = new SelectBookForOrderView();
        selectBookForOrderView.setVisible(true);
        Books bookResult = selectBookForOrderView.getBook();
//        System.out.println(customersResult.getCustomerID());
        if (bookResult != null) {
            orderView.getTextFieldBookId().setText(bookResult.getBookID());
            orderView.getTextFieldBookName().setText(bookResult.getBookName());
            orderView.getTextFieldUnitPrice().setText(String.valueOf(bookResult.getPrice()));

            String imageUrl = bookResult.getUrlImage();
            ImageIcon bookIcon = null;
            if (imageUrl != null && !imageUrl.trim().isEmpty()) {
                String absolutePath = ImageUtils.getAbsolutePathFromRelative(imageUrl);
                if (absolutePath != null && Files.exists(Paths.get(absolutePath))) {
                    bookIcon = ImageUtils.scaleImageFromFile(absolutePath,ImageUtils.DEFAULT_IMAGE_WIDTH,ImageUtils.DEFAULT_IMAGE_HEIGHT);
                } else {
                    System.err.println("Ảnh không tìm thấy tại: " + absolutePath);
                }
            }else {
                System.err.println("URL ảnh không hợp lệ hoặc không thể chuyển đổi: " + imageUrl);
                // URL không hợp lệ -> bookIcon vẫn là null
            }
            orderView.setPreviewImage(bookIcon);
        }
        orderView.getBtnAdd().setEnabled(true);
    }

    private void handleAddOrder(){
        try{
            Orders order = getOrderFromInput();
            if(orderService.insertOrder(order)){
                CommonView.showInfoMessage(null,"Them don hang thanh cong");
                //orderView.getTextFieldInvoiceId().setEditable(false);
            }else{
                CommonView.showErrorMessage(null,"Them that bai");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleAddOrderBook() {
        try{
            OrderDetails oderDetails = getOrderDetailFromInput();
            if(orderService.addOrder(oderDetails)){
                updateOrderTable();
                CommonView.showErrorMessage(null,"Them sach thanh cong");
                resetInputFieldsState();
            }else{
                CommonView.showErrorMessage(null,"Them khach hang that bai ");
            }

        } catch (IllegalArgumentException | ParseException ex) {
            ex.printStackTrace();
            CommonView.showErrorMessage(null,"Loi Nhap Lieu");
        }catch (Exception ex) {
            CommonView.showErrorMessage(null,ex.getMessage());
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
        orderView.getTextFieldQuantity().setText("");
        orderView.getTextFieldUnitPrice().setText("");

        orderView.setPreviewImage(null);
    }

    public Orders getOrderFromInput() throws ParseException {
        String orderID = orderView.getTextFieldInvoiceId().getText().trim();
        String dateStr = orderView.getTextFieldDate().getText().trim();
        String customerID = orderView.getTextFieldCustomerId().getText().trim();
        Customers customer = customerService.getCustomerByCustomerId(customerID);
        // 1. Kiểm tra các trường bắt buộc
        if (orderID.isEmpty()) throw new IllegalArgumentException("Ma Sach không được để trống.");
        if (customerID.isEmpty()) throw new IllegalArgumentException("Ma khach hang không được để trống.");
        Date creationDate = null;
        SimpleDateFormat parseDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        if (!dateStr.isEmpty()) {
            creationDate = parseDateFormat.parse(dateStr);
        }
        return new Orders(orderID,customer,creationDate,"");
    }

    public OrderDetails getOrderDetailFromInput() throws ParseException {
        String orderID = orderView.getTextFieldInvoiceId().getText().trim();
        String bookID = orderView.getTextFieldBookId().getText().trim();
        int quantity = Integer.parseInt(orderView.getTextFieldQuantity().getText().trim());

        // 1. Kiểm tra các trường bắt buộc
        if (orderID.isEmpty()) throw new IllegalArgumentException("Ma order không được để trống.");
        if (bookID.isEmpty()) throw new IllegalArgumentException("Ma Sach không được để trống.");
        if (quantity == 0 || quantity < 0) throw new IllegalArgumentException("So luong la so tu nhien");
        Books book = bookService.getBookByID(bookID);
        if (book == null) {
            CommonView.showErrorMessage(null,"Vui long chon sach");
        }
        if(quantity > book.getQuantity()){
            throw new IllegalArgumentException("So luong sach khong du");
        }

        return new OrderDetails(orderID,0,book,quantity);

    }

    public void updateOrderTable(){
        ArrayList<OrderDetails> orderDetailsList = orderService.getAllOrderDetails();
        if (orderView == null || orderView.getTableModel() == null) {
            System.err.println("Lỗi: CustomerView hoặc TableModel chưa được khởi tạo.");
            return;
        }

        DefaultTableModel model = orderView.getTableModel();
        model.setRowCount(0);

        if (orderDetailsList != null) {
            for (OrderDetails orderDetail : orderDetailsList) {
                Books book = orderDetail.getBook();
                int quantity = orderDetail.getQuantity();
                float discount = orderDetail.getDiscount();
                double totalPrice = orderDetail.getTotal();
                //String[] columnNames = {"Mã sách", "Tên sách", "Số lượng", "Đơn giá", "Giảm giá %", "Thành tiền"};

                model.addRow(new Object[]{
                        book.getBookID(),
                        book.getBookName(),
                        quantity,
                        book.getPrice(),
                        discount,
                        totalPrice
                });
            }
        }

    }

}





