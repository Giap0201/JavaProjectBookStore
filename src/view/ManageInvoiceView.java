package view;

import com.toedter.calendar.JDateChooser;
import utils.CommonView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ManageInvoiceView extends JPanel {
    private static final Font font1 = new Font("Tahoma", Font.BOLD, 15);
    private static final Font font2 = new Font("Tahoma", Font.PLAIN, 15);
    private JButton btnDelete2, btnChange2, btnSearch, btnLoad, btnSave, btnChange, btnDelete;
    private JDateChooser date;
    private DefaultTableModel tableModelInvoice, tableModelDetails;
    private JTable tableInvoice, tabelDetails;
    private JLabel labeltotal, labelMonney, lblTotalItemsValue, lblTotalPriceValue;
    private JTextField textFieldInvoiceId, textFieldCustomerId, textFieldEmployee;
    private JComboBox<String> jComboBoxTT;

    public JPanel createPanel1() {
        JPanel panelContent1 = new JPanel();
        panelContent1.setLayout(null);

        // Mã hóa đơn
        JLabel labelInvoiceId = createLabel("Mã HĐ", font1);
        labelInvoiceId.setBounds(20, 15, 70, 25);
        panelContent1.add(labelInvoiceId);

        textFieldInvoiceId = createTextField(font2);
        textFieldInvoiceId.setBounds(100, 15, 270, 25); // Rộng hơn
        panelContent1.add(textFieldInvoiceId);

        // Mã khách hàng
        JLabel labelCustomerId = createLabel("Mã KH", font1);
        labelCustomerId.setBounds(20, 55, 70, 25);
        panelContent1.add(labelCustomerId);

        textFieldCustomerId = createTextField(font2);
        textFieldCustomerId.setBounds(100, 55, 190, 25); // Rộng hơn
        panelContent1.add(textFieldCustomerId);

        JButton btnSearchCustomer = CommonView.createButton("Tìm", new Color(56, 46, 211));
        btnSearchCustomer.setBounds(300, 55, 70, 25);
        // btnSearchCustomer.addActionListener(e -> openCustomerSelectionDialog());
        panelContent1.add(btnSearchCustomer);

        // Mã nhân viên
        JLabel labelEmployee = createLabel("Mã NV", font1);
        labelEmployee.setBounds(20, 95, 70, 25);
        panelContent1.add(labelEmployee);

        textFieldEmployee = createTextField(font2);
        textFieldEmployee.setBounds(100, 95, 190, 25); // Rộng hơn
        panelContent1.add(textFieldEmployee);

        JButton btnSearchEmployee = CommonView.createButton("Tìm", new Color(56, 46, 211));
        btnSearchEmployee.setBounds(300, 95, 70, 25);
        // btnSearchEmployee.addActionListener(e -> openEmployeeSelectionDialog());
        panelContent1.add(btnSearchEmployee);

        // Ngày lập
        JLabel labelDay = createLabel("Ngày lập", font1);
        labelDay.setBounds(20, 135, 70, 25);
        panelContent1.add(labelDay);

        date = new JDateChooser();
        date.setFont(font2);
        date.setBounds(100, 135, 270, 25); // Rộng hơn
        panelContent1.add(date);

        // Trạng thái
        JLabel labelTT = createLabel("Trạng thái", font1);
        labelTT.setBounds(20, 175, 90, 25);
        panelContent1.add(labelTT);

        jComboBoxTT = new JComboBox<>();
        jComboBoxTT.setFont(font2);
        jComboBoxTT.addItem("Chọn trạng thái");
        jComboBoxTT.addItem("Đã thanh toán");
        jComboBoxTT.addItem("Chưa thanh toán");
        jComboBoxTT.setBounds(100, 175, 270, 25); // Rộng hơn
        panelContent1.add(jComboBoxTT);

        // Các nút chức năng (2 hàng, mỗi hàng 3 nút)
        btnDelete = CommonView.createButton("Xoá", new Color(246, 4, 60));
        btnDelete.setBounds(20, 210, 100, 30);
        panelContent1.add(btnDelete);

        btnChange = CommonView.createButton("Sửa", new Color(1, 131, 85));
        btnChange.setBounds(140, 210, 100, 30);
        panelContent1.add(btnChange);

        btnSave = CommonView.createButton("Xuất", new Color(60, 197, 71));
        btnSave.setBounds(260, 210, 100, 30);
        panelContent1.add(btnSave);

        btnLoad = CommonView.createButton("Làm mới", new Color(211, 99, 167));
        btnLoad.setBounds(20, 250, 100, 30);
        panelContent1.add(btnLoad);

        btnSearch = CommonView.createButton("Tìm kiếm", new Color(56, 46, 211));
        btnSearch.setBounds(140, 250, 100, 30);
        panelContent1.add(btnSearch);

        JButton btnClear = CommonView.createButton("Xóa trắng", new Color(255, 165, 0));
        btnClear.setBounds(260, 250, 100, 30);
        panelContent1.add(btnClear);

        return panelContent1;
    }

    private JPanel createPanel2() {
        JPanel panelContent2 = new JPanel();
        panelContent2.setLayout(null);

        String[] columns = {"Mã HĐ", "Mã KH", "Mã NV", "Ngày lập", "Tổng tiền", "Trạng thái"};
        tableModelInvoice = new DefaultTableModel();
        tableInvoice = CommonView.createTable(tableModelInvoice, columns);
        JScrollPane scrollPane = new JScrollPane(tableInvoice);
        scrollPane.setBounds(10, 10, 780, 200); // Mở rộng bảng
        panelContent2.add(scrollPane);

        JLabel labelTotalInvoices = createLabel("Số lượng hoá đơn: ", font1);
        labelTotalInvoices.setBounds(10, 220, 150, 20);
        panelContent2.add(labelTotalInvoices);

        labeltotal = createLabel("0", font1);
        labeltotal.setBounds(160, 220, 150, 20);
        panelContent2.add(labeltotal);

        JLabel labelTotalMoney = createLabel("Tổng tiền: ", font1);
        labelTotalMoney.setBounds(300, 220, 100, 20);
        panelContent2.add(labelTotalMoney);

        labelMonney = createLabel("0 VNĐ", font1);
        labelMonney.setBounds(400, 220, 150, 20);
        panelContent2.add(labelMonney);

        return panelContent2;
    }

    private JPanel createPanel3() {
        JPanel panelContent3 = new JPanel();
        panelContent3.setLayout(null);

        // Tiêu đề chi tiết hóa đơn
        JLabel labelTitleDetails = new JLabel("CHI TIẾT HOÁ ĐƠN");
        labelTitleDetails.setFont(new Font("Tahoma", Font.BOLD, 16));
        labelTitleDetails.setForeground(new Color(2, 62, 150));
        labelTitleDetails.setBounds(550, 5, 200, 25); // Căn giữa tiêu đề
        panelContent3.add(labelTitleDetails);

        // Bảng chi tiết hóa đơn
        String[] columnDetails = {"Mã HĐ", "Mã Sách", "Tên Sách", "Số lượng", "Đơn Giá", "Thành tiền"};
        tableModelDetails = new DefaultTableModel();
        tabelDetails = CommonView.createTable(tableModelDetails, columnDetails);

        JScrollPane scrollPaneDetails = new JScrollPane(tabelDetails);
        scrollPaneDetails.setBounds(10, 40, 1190, 250); // Kích thước bảng
        panelContent3.add(scrollPaneDetails);

        // Label thống kê (bên trái)
        JLabel lblTotalItems = createLabel("Tổng số sản phẩm:", font1);
        lblTotalItems.setBounds(10, 300, 200, 25);
        panelContent3.add(lblTotalItems);

        lblTotalItemsValue = createLabel("0", font1);
        lblTotalItemsValue.setBounds(220, 300, 100, 25);
        panelContent3.add(lblTotalItemsValue);

        JLabel lblTotalPrice = createLabel("Tổng tiền:", font1);
        lblTotalPrice.setBounds(10, 330, 200, 25);
        panelContent3.add(lblTotalPrice);

        lblTotalPriceValue = createLabel("0 VNĐ", font1);
        lblTotalPriceValue.setBounds(220, 330, 150, 25);
        panelContent3.add(lblTotalPriceValue);

        // Các nút chức năng (bên phải)
        btnChange2 = CommonView.createButton("Sửa", new Color(44, 134, 3));
        btnChange2.setBounds(1000, 300, 100, 30);
        panelContent3.add(btnChange2);

        btnDelete2 = CommonView.createButton("Xoá", new Color(203, 13, 13));
        btnDelete2.setBounds(1110, 300, 100, 30);
        panelContent3.add(btnDelete2);

        JButton btnExport = CommonView.createButton("Xuất Excel", new Color(60, 197, 71));
        btnExport.setBounds(1000, 340, 210, 30);
        panelContent3.add(btnExport);

        return panelContent3;
    }

    public JPanel initManageInvoiceView() {
        JPanel panelContent = new JPanel();
        panelContent.setLayout(null);
        panelContent.setBackground(new Color(245, 248, 214));

        // Tiêu đề chính
        JLabel labelTitle = new JLabel("QUẢN LÍ HOÁ ĐƠN");
        labelTitle.setBounds(500, 10, 250, 30); // Căn giữa
        labelTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        panelContent.add(labelTitle);

        // Panel 1: Thông tin nhập liệu (bên trái)
        JPanel panelContent1 = createPanel1();
        panelContent1.setLayout(null);
        panelContent1.setBounds(10, 50, 400, 280); // Bên trái
        panelContent1.setBackground(new Color(245, 248, 214));
        panelContent.add(panelContent1);

        // Panel 2: Bảng hóa đơn (bên phải)
        JPanel panelContent2 = createPanel2();
        panelContent2.setLayout(null);
        panelContent2.setBounds(420, 50, 800, 280); // Bên phải, mở rộng chiều rộng
        panelContent2.setBackground(new Color(245, 248, 214));
        panelContent.add(panelContent2);

        // Panel 3: Chi tiết hóa đơn (bên dưới)
        JPanel panelContent3 = createPanel3();
        panelContent3.setLayout(null);
        panelContent3.setBounds(10, 370, 1210, 370); // Chiếm toàn bộ chiều ngang
        panelContent3.setBackground(new Color(245, 248, 214));
        panelContent.add(panelContent3);

        return panelContent;
    }

    public JTextField createTextField(Font font) {
        JTextField textField = new JTextField();
        textField.setColumns(10);
        textField.setFont(font);
        return textField;
    }

    public JLabel createLabel(String title, Font font) {
        JLabel label = new JLabel(title);
        label.setFont(font);
        return label;
    }

    public JButton getBtnDelete2() {
        return btnDelete2;
    }

    public JButton getBtnChange2() {
        return btnChange2;
    }

    public JButton getBtnSearch() {
        return btnSearch;
    }

    public JButton getBtnLoad() {
        return btnLoad;
    }

    public JButton getBtnSave() {
        return btnSave;
    }

    public JButton getBtnChange() {
        return btnChange;
    }

    public JButton getBtnDelete() {
        return btnDelete;
    }

    public JDateChooser getDate() {
        return date;
    }

    public DefaultTableModel getTableModelInvoice() {
        return tableModelInvoice;
    }

    public DefaultTableModel getTableModelDetails() {
        return tableModelDetails;
    }

    public JTable getTableInvoice() {
        return tableInvoice;
    }

    public JTable getTabelDetails() {
        return tabelDetails;
    }

    public JLabel getLabeltotal() {
        return labeltotal;
    }

    public JLabel getLabelMonney() {
        return labelMonney;
    }

    public JLabel getLblTotalItemsValue() {
        return lblTotalItemsValue;
    }

    public JLabel getLblTotalPriceValue() {
        return lblTotalPriceValue;
    }

    public JTextField getTextFieldInvoiceId() {
        return textFieldInvoiceId;
    }

    public JTextField getTextFieldCustomerId() {
        return textFieldCustomerId;
    }

    public JTextField getTextFieldEmployee() {
        return textFieldEmployee;
    }

    public JComboBox<String> getjComboBoxTT() {
        return jComboBoxTT;
    }
}