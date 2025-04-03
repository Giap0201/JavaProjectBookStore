package view;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.image.BufferedImage;

public class CustomerInvoiceView {
    public JPanel initCustomerInvoiceView() {
        JPanel panelContent = new JPanel();
        panelContent.setLayout(null);
        panelContent.setBackground(new Color(157, 239, 227));

        // Thêm panelHeader và panelDetails vào panelContent
        JPanel panelHeader = createHeaderPanel();
        JPanel panelDetails = createDetailsPanel();

        panelContent.add(panelHeader);
        panelContent.add(panelDetails);

        return panelContent;
    }

    private JPanel createHeaderPanel() {
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
        labelInvoiceId.setBounds(10, 50, 100, 20);
        panelHeader.add(labelInvoiceId);

        JTextField textFieldInvoiceId = new JTextField("11");
        textFieldInvoiceId.setFont(font1);
        textFieldInvoiceId.setBounds(130, 50, 100, 20);
        panelHeader.add(textFieldInvoiceId);

        // Employee ID
        JLabel labelEmployeeId = new JLabel("Mã nhân viên:");
        labelEmployeeId.setFont(font);
        labelEmployeeId.setBounds(250, 50, 120, 20);
        panelHeader.add(labelEmployeeId);

        JTextField textFieldEmployeeId = new JTextField("NV1");
        textFieldEmployeeId.setFont(font1);
        textFieldEmployeeId.setBounds(380, 50, 100, 20);
        panelHeader.add(textFieldEmployeeId);

        // Customer ID
        JLabel labelCustomerId = new JLabel("Mã khách hàng:");
        labelCustomerId.setFont(font);
        labelCustomerId.setBounds(500, 50, 120, 20);
        panelHeader.add(labelCustomerId);

        JTextField textFieldCustomerId = new JTextField("KH01");
        textFieldCustomerId.setFont(font1);
        textFieldCustomerId.setBounds(630, 50, 100, 20);
        panelHeader.add(textFieldCustomerId);

        // Delivery ID
        JLabel labelDeliveryId = new JLabel("Mã giao dịch:");
        labelDeliveryId.setFont(font);
        labelDeliveryId.setBounds(750, 50, 120, 20);
        panelHeader.add(labelDeliveryId);

        JTextField textFieldDeliveryId = new JTextField("GG1");
        textFieldDeliveryId.setFont(font1);
        textFieldDeliveryId.setBounds(880, 50, 100, 20);
        panelHeader.add(textFieldDeliveryId);

        // Date
        JLabel labelDate = new JLabel("Ngày lập:");
        labelDate.setFont(font);
        labelDate.setBounds(10, 90, 100, 20);
        panelHeader.add(labelDate);

        JTextField textFieldDate = new JTextField("2023-01-24");
        textFieldDate.setFont(font1);
        textFieldDate.setBounds(130, 90, 150, 20);
        panelHeader.add(textFieldDate);

        // Create Invoice Button
        JButton btnCreateInvoice = new JButton("TẠO HÓA ĐƠN");
        btnCreateInvoice.setFont(font);
        btnCreateInvoice.setBackground(new Color(34, 139, 34)); // Green color
        btnCreateInvoice.setForeground(new Color(255, 255, 255));
        btnCreateInvoice.setHorizontalAlignment(JButton.CENTER);
        btnCreateInvoice.setBounds(830, 90, 150, 40);
        panelHeader.add(btnCreateInvoice);

        ImageIcon saleIcon = scaleImage("images/icon11.png", 150, 150); // Replace with actual path
        JLabel labelBookImage = new JLabel(saleIcon);
        labelBookImage.setBounds(1050, 20, 150, 150);
        panelHeader.add(labelBookImage);


        return panelHeader;
    }

    private JPanel createDetailsPanel() {
        Font font = new Font("Tahoma", Font.BOLD, 15);
        Font font1 = new Font("Tahoma", Font.PLAIN, 15);
        JPanel panelDetails = new JPanel();
        panelDetails.setLayout(null);
        panelDetails.setBackground(new Color(157, 239, 227));
        panelDetails.setBounds(0, 170, 1450, 800); // Điều chỉnh vị trí và kích thước

        // Book Image (Placeholder for the book cover)
        ImageIcon bookIcon = scaleImage("images/book1.jpg", 200, 200); // Replace with actual path
        JLabel labelBookImage = new JLabel(bookIcon);
        labelBookImage.setBounds(80, 10, 200, 200);
        labelBookImage.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelDetails.add(labelBookImage);

        // Book ID
        JLabel labelBookId = new JLabel("MÃ SÁCH:");
        labelBookId.setFont(font);
        labelBookId.setBounds(50, 230, 100, 30);
        panelDetails.add(labelBookId);

        JTextField textFieldBookId = new JTextField("KD03");
        textFieldBookId.setFont(font1);
        textFieldBookId.setBounds(180, 230, 150, 30);
        textFieldBookId.setBackground(new Color(192, 192, 192));
        panelDetails.add(textFieldBookId);

        // Book Name
        JLabel labelBookName = new JLabel("TÊN SÁCH:");
        labelBookName.setFont(font);
        labelBookName.setBounds(50, 270, 100, 30);
        panelDetails.add(labelBookName);

        JTextField textFieldBookName = new JTextField("Đừng lựa chọn an nhàn khi còn trẻ");
        textFieldBookName.setFont(font1);
        textFieldBookName.setBounds(180, 270, 150, 30);
        textFieldBookName.setBackground(new Color(192, 192, 192));
        panelDetails.add(textFieldBookName);

        // Quantity
        JLabel labelQuantity = new JLabel("SỐ LƯỢNG:");
        labelQuantity.setFont(font);
        labelQuantity.setBounds(50, 310, 100, 20);
        panelDetails.add(labelQuantity);

        JTextField textFieldQuantity = new JTextField("0");
        textFieldQuantity.setFont(font1);
        textFieldQuantity.setBounds(180, 310, 150, 30);
        panelDetails.add(textFieldQuantity);

        // Unit Price
        JLabel labelUnitPrice = new JLabel("ĐƠN GIÁ:");
        labelUnitPrice.setFont(font);
        labelUnitPrice.setBounds(50, 350, 100, 30);
        panelDetails.add(labelUnitPrice);

        JTextField textFieldUnitPrice = new JTextField("75000.0");
        textFieldUnitPrice.setFont(font1);
        textFieldUnitPrice.setBounds(180, 350, 150, 30);
        textFieldUnitPrice.setBackground(new Color(192, 192, 192));
        panelDetails.add(textFieldUnitPrice);

        // Add, Edit, Delete Buttons
        JButton btnAdd = new JButton("THÊM");
        btnAdd.setFont(font);
        btnAdd.setBackground(new Color(32, 204, 35)); // Green color
        btnAdd.setForeground(new Color(255, 255, 255));
        btnAdd.setHorizontalAlignment(JButton.CENTER);
        btnAdd.setBounds(50, 400, 100, 30);
        panelDetails.add(btnAdd);

        JButton btnEdit = new JButton("SỬA");
        btnEdit.setFont(font);
        btnEdit.setBackground(new Color(255, 105, 180)); // Pink color
        btnEdit.setForeground(new Color(255, 255, 255));
        btnEdit.setHorizontalAlignment(JButton.CENTER);
        btnEdit.setBounds(180, 400, 100, 30);
        panelDetails.add(btnEdit);

        JButton btnDelete = new JButton("XÓA");
        btnDelete.setFont(font);
        btnDelete.setBackground(new Color(250, 4, 4)); // Light blue color
        btnDelete.setForeground(new Color(255, 255, 255));
        btnDelete.setHorizontalAlignment(JButton.CENTER);
        btnDelete.setBounds(180, 440, 100, 30);
        panelDetails.add(btnDelete);


        // Total Amount
        JLabel labelTotalAmount = new JLabel("THÀNH TIỀN:");
        labelTotalAmount.setFont(font);
        labelTotalAmount.setBounds(440, 470, 120, 30);
        panelDetails.add(labelTotalAmount);

        JTextField textFieldTotalAmount = new JTextField("150000");
        textFieldTotalAmount.setFont(font1);
        textFieldTotalAmount.setBounds(570, 470, 130, 30);
        textFieldTotalAmount.setBackground(new Color(192, 192, 192));
        panelDetails.add(textFieldTotalAmount);

        // Discount
        JLabel labelDiscount = new JLabel("GIẢM GIÁ:");
        labelDiscount.setFont(font);
        labelDiscount.setBounds(730, 470, 120, 30);
        panelDetails.add(labelDiscount);

        JTextField textFieldDiscount = new JTextField("0");
        textFieldDiscount.setFont(font1);
        textFieldDiscount.setBounds(840, 470, 130, 30);
        panelDetails.add(textFieldDiscount);

        // Total Invoice Amount
        JLabel labelTotalInvoiceAmount = new JLabel("TỔNG TIỀN:");
        labelTotalInvoiceAmount.setFont(font);
        labelTotalInvoiceAmount.setBounds(1000, 470, 100, 30);
        panelDetails.add(labelTotalInvoiceAmount);

        JTextField textFieldTotalInvoiceAmount = new JTextField("150000");
        textFieldTotalInvoiceAmount.setFont(font1);
        textFieldTotalInvoiceAmount.setBounds(1120, 470, 130, 30);
        textFieldTotalInvoiceAmount.setBackground(new Color(192, 192, 192));
        panelDetails.add(textFieldTotalInvoiceAmount);

        // Share and Cancel Buttons
        JButton btnCancel = new JButton("HỦY");
        btnCancel.setFont(font);
        btnCancel.setBackground(new Color(0, 0, 255)); // Blue color
        btnCancel.setForeground(new Color(255, 255, 255));
        btnCancel.setHorizontalAlignment(JButton.CENTER);
        btnCancel.setBounds(950, 520, 140, 40);
        panelDetails.add(btnCancel);

        JButton btnShare = new JButton("XÁC NHẬN");
        btnShare.setFont(font);
        btnShare.setBackground(new Color(255, 165, 0)); // Orange color
        btnShare.setForeground(new Color(255, 255, 255));
        btnShare.setHorizontalAlignment(JButton.CENTER);
        btnShare.setBounds(1100, 520, 150, 40);
        panelDetails.add(btnShare);


        // Table for Book Details
        String[] columnNames = {"Mã sách", "Tên sách", "Số lượng", "Đơn giá", "Giảm giá", "Thành tiền"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);

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

        // Add a sample row to the table
        Object[] newRow = {"KD03", "Chó tđi vé tùi lz", "2", "75000.0", "0.0", "150000.0"};
        tableModel.addRow(newRow);

        return panelDetails;
    }

    /**
     * Hàm hỗ trợ scale ảnh (reused from your code)
     */
    private ImageIcon scaleImage(String path, int width, int height) {
        try {
            ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource(path));
            Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception e) {
            System.err.println("No Image: " + path);
            return new ImageIcon(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)); // Trả về ảnh trống
        }
    }
    public static void main(String[] args) {
        CustomerInvoiceView a = new CustomerInvoiceView();
        JPanel panel = a.initCustomerInvoiceView();
        JFrame app = new App();
        app.add(panel, BorderLayout.CENTER);
    }
}
