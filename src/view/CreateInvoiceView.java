package view;

import controller.CategoryController;
import controller.OrderController;
import utils.CommonView;
import utils.ImageUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class CreateInvoiceView extends Component {
    private JTextField textFieldBookId,textFieldUnitPrice;
    private JTextField textFieldBookName,textFieldQuantity;
    private JTextField textFieldInvoiceId;
    //private JTextField textFieldEmployeeId;
    //private JTextField textFieldDeliveryId;
    private JTextField textFieldDate;
    private JTextField textFieldTotalAmount;
    private JTextField textFieldDiscount;
    private JTextField textFieldTotalInvoiceAmount;
   // private CategoryController categoryController;
    private JButton btnAdd,btnEdit,btnDelete;
    private JButton btnReset;
    private JButton btnCreateInvoice;
    private JButton btnCancel;
    private JButton btnConfirm;
    private JButton btnSearchBook;
    //private JButton btnSearchEmployee;
    private JButton btnSearchCustmer;
    private JTextField textFieldCustomerId;
    private ImageIcon bookIcon;
    private JLabel labelBookImage;
    private JTextField textFieldPhoneNumber;
    private JTextField textFieldCustomerName;
    private DefaultTableModel tableModel;
    private JTable table;

    public JPanel initCustomerInvoiceView() {
        JPanel panelContent = new JPanel();
        panelContent.setLayout(null);
        panelContent.setBackground(new Color(157, 239, 227));

        Font font = new Font("Tahoma", Font.BOLD, 15);
        Font font1 = new Font("Tahoma", Font.PLAIN, 15);
        JPanel panelHeader = new JPanel();
        panelHeader.setLayout(null);
        panelHeader.setBackground(new Color(157, 239, 227));
        panelHeader.setBounds(0, 0, 1450, 170); // Điều chỉnh kích thước phù hợp

        // Title
        JLabel labelTitle = new JLabel("TẠO HÓA ĐƠN BÁN HÀNG");
        labelTitle.setBounds(600, 10, 300, 30);
        labelTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        panelHeader.add(labelTitle);

        // Invoice ID
        JLabel labelInvoiceId = new JLabel("Mã hóa đơn :");
        labelInvoiceId.setFont(font);
        labelInvoiceId.setBounds(10, 50, 100, 30);
        panelHeader.add(labelInvoiceId);

        textFieldInvoiceId = new JTextField("");
        textFieldInvoiceId.setFont(font1);
        textFieldInvoiceId.setBounds(130, 50, 100, 30);
        panelHeader.add(textFieldInvoiceId);

        // Employee ID
//        JLabel labelEmployeeId = new JLabel("Mã nhân viên:");
//        labelEmployeeId.setFont(font);
//        labelEmployeeId.setBounds(250, 50, 120, 30);
//        panelHeader.add(labelEmployeeId);
//
//        textFieldEmployeeId = new JTextField("");
//        textFieldEmployeeId.setFont(font1);
//        textFieldEmployeeId.setBounds(380, 50, 80, 30);
//        panelHeader.add(textFieldEmployeeId);
//
//        btnSearchEmployee = CommonView.createButton("...", new Color(255, 225, 29));
//        btnSearchEmployee.setBounds(470, 50, 20, 30);
//        panelHeader.add(btnSearchEmployee);

        // Customer ID
        JLabel labelCustomerId = new JLabel("Mã khách hàng:");
        labelCustomerId.setFont(font);
        labelCustomerId.setBounds(250+50, 50, 120, 30);
        panelHeader.add(labelCustomerId);

        textFieldCustomerId = new JTextField("");
        textFieldCustomerId.setFont(font1);
        textFieldCustomerId.setBounds(380+50, 50, 100, 30);
        textFieldCustomerId.setBackground(new Color(192, 192, 192));
        textFieldCustomerId.setEditable(false);
        panelHeader.add(textFieldCustomerId);

        JLabel labelCustomerName = new JLabel("Ten khách hàng:");
        labelCustomerName.setFont(font);
        labelCustomerName.setBounds(550, 50, 130, 30);
        panelHeader.add(labelCustomerName);

        textFieldCustomerName = new JTextField("");
        textFieldCustomerName.setFont(font1);
        textFieldCustomerName.setBounds(690, 50, 150, 30);
        textFieldCustomerName.setEditable(false);
        textFieldCustomerName.setBackground(new Color(192, 192, 192));
        panelHeader.add(textFieldCustomerName);

        JLabel labelPhoneCustomer = new JLabel("SĐT :");
        labelPhoneCustomer.setFont(font);
        labelPhoneCustomer.setBounds(250+50, 90, 50, 30);
        panelHeader.add(labelPhoneCustomer);

        textFieldPhoneNumber = new JTextField("");
        textFieldPhoneNumber.setFont(font1);
        textFieldPhoneNumber.setBounds(380+50, 90, 150, 30);
        panelHeader.add(textFieldPhoneNumber);

        btnSearchCustmer = CommonView.createButton("Lọc", new Color(207, 181, 38));
        btnSearchCustmer.setBounds(530, 130, 100, 30);
        panelHeader.add(btnSearchCustmer);

        // Delivery ID
//        JLabel labelDeliveryId = new JLabel("Mã giao dịch:");
//        labelDeliveryId.setFont(font);
//        labelDeliveryId.setBounds(750, 50, 120, 30);
//        panelHeader.add(labelDeliveryId);
//
//        textFieldDeliveryId = new JTextField("");
//        textFieldDeliveryId.setFont(font1);
//        textFieldDeliveryId.setBounds(880, 50, 100, 30);
//        panelHeader.add(textFieldDeliveryId);

        // Date
        JLabel labelDate = new JLabel("Ngày lập:");
        labelDate.setFont(font);
        labelDate.setBounds(10, 90, 100, 30);
        panelHeader.add(labelDate);

        textFieldDate = new JTextField("");
        textFieldDate.setFont(font1);
        textFieldDate.setBounds(130, 90, 150, 30);
        textFieldDate.setBackground(new Color(192, 192, 192));
        textFieldDate.setEditable(false);
        panelHeader.add(textFieldDate);

        // Create Invoice Button
        btnCreateInvoice = CommonView.createButton("TẠO HÓA ĐƠN", new Color(34, 139, 34));
        btnCreateInvoice.setBounds(830, 90, 150, 40);
        panelHeader.add(btnCreateInvoice);

        ImageIcon saleIcon = CommonView.scaleImage("images/icon11.png", 150, 150); // Replace with actual path
        JLabel labelIconImage = new JLabel(saleIcon);
        labelIconImage.setBounds(1050, 20, 150, 150);
        panelHeader.add(labelIconImage);


        JPanel panelDetails = new JPanel();
        panelDetails.setLayout(null);
        panelDetails.setBackground(new Color(157, 239, 227));
        panelDetails.setBounds(0, 170, 1450, 800); // Điều chỉnh vị trí và kích thước

//        JButton btnChooseBook = CommonView.createButton("Chọn sách",new Color(207, 181, 38));
//        btnChooseBook.setFont(font1);
//        btnChooseBook.setBounds(90,10,150,30);
//        panelDetails.add(btnChooseBook);

        // Book Image (Placeholder for the book cover)
//        bookIcon = CommonView.scaleImage("images/pictureX.png", 200, 200); // Replace with actual path
//        labelBookImage = new JLabel(bookIcon);
//        labelBookImage.setBounds(80, 10 + 40, 200, 200);
//        labelBookImage.setBorder(BorderFactory.createLineBorder(Color.BLACK));
//        panelDetails.add(labelBookImage);
        // Khởi tạo JLabel để hiển thị ảnh (thay thế labelImage6 cũ)
        labelBookImage = new JLabel();
        labelBookImage.setBounds(80, 10 ,200, 200); // Vị trí và kích thước như cũ
        labelBookImage.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Thêm đường viền để dễ thấy
        labelBookImage.setHorizontalAlignment(JLabel.CENTER); // Căn giữa ảnh
        labelBookImage.setText("Chưa có ảnh"); // Text mặc định
        // Load ảnh mặc định ban đầu (nếu có)
        // displayBookImage("images/icon6.png"); // Gọi hàm hiển thị ảnh mặc định (nếu cần)
        // Hoặc set icon mặc định nếu icon6 là resource nội bộ
        ImageIcon defaultIcon = ImageUtils.getDefaultScaledIcon("images/pictureX.png",ImageUtils.DEFAULT_IMAGE_WIDTH,
                ImageUtils.DEFAULT_IMAGE_HEIGHT);
        //ImageIcon defaultIcon = scaleImageResource("images/icon6.png", 200, 200); // Dùng hàm riêng cho resource
        if (defaultIcon != null) {
            labelBookImage.setIcon(defaultIcon);
            labelBookImage.setText(null); // Xóa text khi có icon
        }else{
            labelBookImage.setIcon(null);
            labelBookImage.setText("Chưa có ảnh");
        }
        panelDetails.add(labelBookImage);

        // Book ID
        JLabel labelBookId = new JLabel("MÃ SÁCH:");
        labelBookId.setFont(font);
        labelBookId.setBounds(50, 230, 100, 30);
        panelDetails.add(labelBookId);

        textFieldBookId = new JTextField("");
        textFieldBookId.setFont(font1);
        textFieldBookId.setBounds(180, 230 , 100, 30);
        textFieldBookId.setEditable(false);
        textFieldBookId.setBackground(new Color(192, 192, 192));
        panelDetails.add(textFieldBookId);

        btnSearchBook = CommonView.createButton("...", new Color(255, 225, 29));
        btnSearchBook.setBounds(290, 230, 30, 30);
        panelDetails.add(btnSearchBook);

        // Book Name
        JLabel labelBookName = new JLabel("TÊN SÁCH:");
        labelBookName.setFont(font);
        labelBookName.setBounds(50, 270, 100, 30);
        panelDetails.add(labelBookName);

        textFieldBookName = new JTextField("");
        textFieldBookName.setFont(font1);
        textFieldBookName.setBounds(180, 270, 150, 30);
        textFieldBookName.setBackground(new Color(192, 192, 192));
        textFieldBookName.setEditable(false);
        panelDetails.add(textFieldBookName);

        // Quantity
        JLabel labelQuantity = new JLabel("SỐ LƯỢNG:");
        labelQuantity.setFont(font);
        labelQuantity.setBounds(50, 310, 100, 30);
        panelDetails.add(labelQuantity);

        textFieldQuantity = new JTextField("1");
        textFieldQuantity.setFont(font1);
        textFieldQuantity.setBounds(180, 310, 150, 30);
        panelDetails.add(textFieldQuantity);

        // Unit Price
        JLabel labelUnitPrice = new JLabel("ĐƠN GIÁ:");
        labelUnitPrice.setFont(font);
        labelUnitPrice.setBounds(50, 350, 100, 30);
        panelDetails.add(labelUnitPrice);

        textFieldUnitPrice = new JTextField("");
        textFieldUnitPrice.setFont(font1);
        textFieldUnitPrice.setBounds(180, 350, 150, 30);
        textFieldUnitPrice.setBackground(new Color(192, 192, 192));
        textFieldUnitPrice.setEditable(false);
        panelDetails.add(textFieldUnitPrice);



        // Add, Edit, Delete Buttons
        btnAdd = CommonView.createButton("THÊM", new Color(32, 204, 35));
        btnAdd.setBounds(50+20, 400 + 40, 120, 40);
        panelDetails.add(btnAdd);

        btnEdit = CommonView.createButton("SỬA", new Color(255, 105, 180));
        btnEdit.setBounds(180+20, 400 + 40, 120, 40);
        panelDetails.add(btnEdit);

        btnDelete = CommonView.createButton("XÓA", new Color(250, 4, 4));
        btnDelete.setBounds(180+20, 440 + 50, 120, 40);
        panelDetails.add(btnDelete);

        btnReset = CommonView.createButton("LÀM MỚI", new Color(213, 72, 30));
        btnReset.setBounds(50+20, 440 + 50, 120, 40);
        panelDetails.add(btnReset);


        // Total Amount
        JLabel labelTotalAmount = new JLabel("THÀNH TIỀN:");
        labelTotalAmount.setFont(font);
        labelTotalAmount.setBounds(440, 470, 120, 30);
        panelDetails.add(labelTotalAmount);

        textFieldTotalAmount = new JTextField("");
        textFieldTotalAmount.setFont(font1);
        textFieldTotalAmount.setBounds(570, 470, 130, 30);
        textFieldTotalAmount.setBackground(new Color(192, 192, 192));
        panelDetails.add(textFieldTotalAmount);

        // Discount
        JLabel labelDiscount = new JLabel("GIẢM GIÁ:");
        labelDiscount.setFont(font);
        labelDiscount.setBounds(730, 470, 120, 30);
        panelDetails.add(labelDiscount);

        textFieldDiscount = new JTextField("0");
        textFieldDiscount.setFont(font1);
        textFieldDiscount.setBounds(840, 470, 130, 30);
        panelDetails.add(textFieldDiscount);

        // Total Invoice Amount
        JLabel labelTotalInvoiceAmount = new JLabel("TỔNG TIỀN:");
        labelTotalInvoiceAmount.setFont(font);
        labelTotalInvoiceAmount.setBounds(1000, 470, 100, 30);
        panelDetails.add(labelTotalInvoiceAmount);

        textFieldTotalInvoiceAmount = new JTextField("");
        textFieldTotalInvoiceAmount.setFont(font1);
        textFieldTotalInvoiceAmount.setBounds(1120, 470, 130, 30);
        textFieldTotalInvoiceAmount.setBackground(new Color(192, 192, 192));
        panelDetails.add(textFieldTotalInvoiceAmount);

        // Share and Cancel Buttons
        btnCancel = CommonView.createButton("HỦY", new Color(0, 0, 255));
        btnCancel.setBounds(950, 520, 140, 40);
        panelDetails.add(btnCancel);

        btnConfirm = CommonView.createButton("XÁC NHẬN", new Color(255, 165, 0));
        btnConfirm.setBounds(1100, 520, 150, 40);
        panelDetails.add(btnConfirm);


        // Table for Book Details
        String[] columnNames = {"Mã sách", "Tên sách", "Số lượng", "Đơn giá", "Giảm giá", "Thành tiền"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);

        table.setFont(new Font("Tahoma", Font.PLAIN, 12));
        table.setRowHeight(20);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Tahoma", Font.BOLD, 14));
        header.setBackground(Color.LIGHT_GRAY);
        header.setForeground(Color.BLACK);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(350, 10, 900, 400);
        panelDetails.add(scrollPane);

        panelContent.add(panelHeader);
        panelContent.add(panelDetails);

        OrderController orderController = new OrderController(this);
        btnSearchCustmer.addActionListener(orderController);
        btnSearchBook.addActionListener(orderController);
        btnAdd.addActionListener(orderController);
        btnEdit.addActionListener(orderController);
        btnDelete.addActionListener(orderController);
        btnReset.addActionListener(orderController);
        btnCreateInvoice.addActionListener(orderController);

        return panelContent;
    }
    public void setPreviewImage(ImageIcon icon) {
        if (icon != null) {
            labelBookImage.setIcon(icon);
            labelBookImage.setText(null); // Xóa text nếu có ảnh
        } else {
            // Nếu icon là null, thử hiển thị icon mặc định
            ImageIcon defaultIcon = ImageUtils.getDefaultScaledIcon("images/icon6.png",
                    ImageUtils.DEFAULT_IMAGE_WIDTH,
                    ImageUtils.DEFAULT_IMAGE_HEIGHT
            );
            if (defaultIcon != null) {
                labelBookImage.setIcon(defaultIcon);
                labelBookImage.setText(null);
            } else {
                // Dự phòng nếu cả icon mặc định/placeholder cũng lỗi
                labelBookImage.setIcon(null);
                labelBookImage.setText("Lỗi hiển thị ảnh");
            }
        }
    }

    public JTextField getTextFieldBookId() {
        return textFieldBookId;
    }

    public void setTextFieldBookId(JTextField textFieldBookId) {
        this.textFieldBookId = textFieldBookId;
    }

    public JTextField getTextFieldUnitPrice() {
        return textFieldUnitPrice;
    }

    public void setTextFieldUnitPrice(JTextField textFieldUnitPrice) {
        this.textFieldUnitPrice = textFieldUnitPrice;
    }

    public JTextField getTextFieldBookName() {
        return textFieldBookName;
    }

    public void setTextFieldBookName(JTextField textFieldBookName) {
        this.textFieldBookName = textFieldBookName;
    }

    public JTextField getTextFieldQuantity() {
        return textFieldQuantity;
    }

    public void setTextFieldQuantity(JTextField textFieldQuantity) {
        this.textFieldQuantity = textFieldQuantity;
    }

    public JTextField getTextFieldDate() {
        return textFieldDate;
    }

    public void setTextFieldDate(JTextField textFieldDate) {
        this.textFieldDate = textFieldDate;
    }

    public JTextField getTextFieldInvoiceId() {
        return textFieldInvoiceId;
    }

    public void setTextFieldInvoiceId(JTextField textFieldInvoiceId) {
        this.textFieldInvoiceId = textFieldInvoiceId;
    }

    public JTextField getTextFieldTotalAmount() {
        return textFieldTotalAmount;
    }

    public void setTextFieldTotalAmount(JTextField textFieldTotalAmount) {
        this.textFieldTotalAmount = textFieldTotalAmount;
    }

    public JTextField getTextFieldDiscount() {
        return textFieldDiscount;
    }

    public void setTextFieldDiscount(JTextField textFieldDiscount) {
        this.textFieldDiscount = textFieldDiscount;
    }

    public JTextField getTextFieldTotalInvoiceAmount() {
        return textFieldTotalInvoiceAmount;
    }

    public void setTextFieldTotalInvoiceAmount(JTextField textFieldTotalInvoiceAmount) {
        this.textFieldTotalInvoiceAmount = textFieldTotalInvoiceAmount;
    }

    public JButton getBtnAdd() {
        return btnAdd;
    }

    public void setBtnAdd(JButton btnAdd) {
        this.btnAdd = btnAdd;
    }

    public JButton getBtnEdit() {
        return btnEdit;
    }

    public void setBtnEdit(JButton btnEdit) {
        this.btnEdit = btnEdit;
    }

    public JButton getBtnDelete() {
        return btnDelete;
    }

    public void setBtnDelete(JButton btnDelete) {
        this.btnDelete = btnDelete;
    }

    public JButton getBtnReset() {
        return btnReset;
    }

    public void setBtnReset(JButton btnReset) {
        this.btnReset = btnReset;
    }

    public JButton getBtnCreateInvoice() {
        return btnCreateInvoice;
    }

    public void setBtnCreateInvoice(JButton btnCreateInvoice) {
        this.btnCreateInvoice = btnCreateInvoice;
    }

    public JButton getBtnCancel() {
        return btnCancel;
    }

    public void setBtnCancel(JButton btnCancel) {
        this.btnCancel = btnCancel;
    }

    public JButton getBtnConfirm() {
        return btnConfirm;
    }

    public void setBtnConfirm(JButton btnConfirm) {
        this.btnConfirm = btnConfirm;
    }

    public JButton getBtnSearchBook() {
        return btnSearchBook;
    }

    public void setBtnSearchBook(JButton btnSearchBook) {
        this.btnSearchBook = btnSearchBook;
    }

    public JButton getBtnSearchCustmer() {
        return btnSearchCustmer;
    }

    public void setBtnSearchCustmer(JButton btnSearchCustmer) {
        this.btnSearchCustmer = btnSearchCustmer;
    }

    public JTextField getTextFieldCustomerId() {
        return textFieldCustomerId;
    }

    public void setTextFieldCustomerId(JTextField textFieldCustomerId) {
        this.textFieldCustomerId = textFieldCustomerId;
    }

    public ImageIcon getBookIcon() {
        return bookIcon;
    }

    public void setBookIcon(ImageIcon bookIcon) {
        this.bookIcon = bookIcon;
    }

    public JLabel getLabelBookImage() {
        return labelBookImage;
    }

    public void setLabelBookImage(JLabel labelBookImage) {
        this.labelBookImage = labelBookImage;
    }

    public JTextField getTextFieldPhoneNumber() {
        return textFieldPhoneNumber;
    }

    public void setTextFieldPhoneNumber(JTextField textFieldPhoneNumber) {
        this.textFieldPhoneNumber = textFieldPhoneNumber;
    }

    public JTextField getTextFieldCustomerName() {
        return textFieldCustomerName;
    }

    public void setTextFieldCustomerName(JTextField textFieldCustomerName) {
        this.textFieldCustomerName = textFieldCustomerName;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public void setTableModel(DefaultTableModel tableModel) {
        this.tableModel = tableModel;
    }

    public JTable getTable() {
        return table;
    }

    public void setTable(JTable table) {
        this.table = table;
    }
}
