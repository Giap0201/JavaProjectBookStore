package view;

import com.toedter.calendar.JDateChooser;
import controller.InvoiceController;
import utils.CommonView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ManageInvoiceView extends JPanel {
    private App app;
    private static final Font font1 = new Font("Tahoma", Font.BOLD, 15);
    private static final Font font2 = new Font("Tahoma", Font.PLAIN, 15);
    private JButton btnAdd, btnDelete, btnChange, btnSearch, btnLoad, btnSave, btnSearchCustomer, btnSearchEmployee;
    private JDateChooser date;
    private DefaultTableModel tableModelInvoice, tableModelDetails;
    private JTable tableInvoice, tableDetails;
    private JLabel labeltotal, labelMonney, lblTotalItemsValue, lblTotalPriceValue;
    private JTextField textFieldInvoiceId, textFieldCustomerId, textFieldEmployee;
    private JComboBox<String> jComboBoxTT;
    private JLabel lblOrderIdValue, lblDayOfEstablishmentValue, lblStatusValue;
    private JLabel lblCustomerIdValue, lblCustomerNameValue, lblCustomerPhoneValue, lblCustomerAddressValue;
    private JLabel lblEmployeeIdValue, lblEmployeeNameValue;

    public ManageInvoiceView(App app) {
        this.app = app;
    }

    public JPanel createPanel1() {
        JPanel panelContent1 = new JPanel();
        panelContent1.setLayout(null);

        // Mã hóa đơn
        JLabel labelInvoiceId = createLabel("Mã HĐ", font1);
        labelInvoiceId.setBounds(20, 15, 70, 25);
        panelContent1.add(labelInvoiceId);

        textFieldInvoiceId = createTextField(font2);
        textFieldInvoiceId.setBounds(100, 15, 270, 25);
        panelContent1.add(textFieldInvoiceId);

        // Mã khách hàng
        JLabel labelCustomerId = createLabel("Mã KH", font1);
        labelCustomerId.setBounds(20, 55, 70, 25);
        panelContent1.add(labelCustomerId);

        textFieldCustomerId = createTextField(font2);
        textFieldCustomerId.setBounds(100, 55, 190, 25);
        panelContent1.add(textFieldCustomerId);

        btnSearchCustomer = CommonView.createButton("Tìm", new Color(56, 46, 211));
        btnSearchCustomer.setBounds(300, 55, 70, 25);
        panelContent1.add(btnSearchCustomer);

        // Mã nhân viên
        JLabel labelEmployee = createLabel("Mã NV", font1);
        labelEmployee.setBounds(20, 95, 70, 25);
        panelContent1.add(labelEmployee);

        textFieldEmployee = createTextField(font2);
        textFieldEmployee.setBounds(100, 95, 190, 25);
        panelContent1.add(textFieldEmployee);

        btnSearchEmployee = CommonView.createButton("Tìm", new Color(56, 46, 211));
        btnSearchEmployee.setBounds(300, 95, 70, 25);
        panelContent1.add(btnSearchEmployee);

        // Ngày lập
        JLabel labelDay = createLabel("Ngày lập", font1);
        labelDay.setBounds(20, 135, 70, 25);
        panelContent1.add(labelDay);

        date = new JDateChooser();
        date.setFont(font2);
        date.setBounds(100, 135, 270, 25);
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
        jComboBoxTT.addItem("Đã huỷ");
        jComboBoxTT.setBounds(100, 175, 270, 25);
        panelContent1.add(jComboBoxTT);

        btnDelete = CommonView.createButton("Xoá", new Color(246, 4, 60));
        btnDelete.setBounds(20, 210, 100, 30);
        panelContent1.add(btnDelete);

        btnChange = CommonView.createButton("Sửa", new Color(1, 131, 85));
        btnChange.setBounds(140, 210, 100, 30);
        panelContent1.add(btnChange);

        btnSave = CommonView.createButton("Lưu Excel", new Color(60, 197, 71));
        btnSave.setBounds(260, 210, 110, 30);
        panelContent1.add(btnSave);

        btnLoad = CommonView.createButton("Làm mới", new Color(211, 99, 167));
        btnLoad.setBounds(20, 250, 100, 30);
        panelContent1.add(btnLoad);

        btnSearch = CommonView.createButton("Tìm", new Color(56, 46, 211));
        btnSearch.setBounds(140, 250, 100, 30);
        panelContent1.add(btnSearch);

        btnAdd = CommonView.createButton("Thêm mới", new Color(255, 165, 0));
        btnAdd.setBounds(260, 250, 110, 30);
        panelContent1.add(btnAdd);

        return panelContent1;
    }

    public JPanel createPanel2() {
        JPanel panelContent2 = new JPanel();
        panelContent2.setLayout(null);

        String[] columns = {"Mã HĐ", "Tên KH", "SDT", "Mã NV", "Tên NV", "Ngày lập", "Trạng thái"};
        tableModelInvoice = new DefaultTableModel();
        tableInvoice = CommonView.createTable(tableModelInvoice, columns);
        JScrollPane scrollPane = new JScrollPane(tableInvoice);
        scrollPane.setBounds(10, 10, 820, 240);
        panelContent2.add(scrollPane);

        JLabel labelTotalInvoices = createLabel("Số lượng hoá đơn: ", font1);
        labelTotalInvoices.setBounds(10, 260, 150, 20);
        panelContent2.add(labelTotalInvoices);

        labeltotal = createLabel("0", font1);
        labeltotal.setBounds(160, 260, 100, 20);
        panelContent2.add(labeltotal);

        JLabel labelTotalMoney = createLabel("Số lượng khách hàng: ", font1);
        labelTotalMoney.setBounds(360, 260, 170, 20);
        panelContent2.add(labelTotalMoney);

        labelMonney = createLabel("0", font1);
        labelMonney.setBounds(530, 260, 100, 20);
        panelContent2.add(labelMonney);

        return panelContent2;
    }

    private JPanel createPanel3() {
        JPanel panelContent3 = new JPanel();
        panelContent3.setLayout(new BorderLayout());
        panelContent3.setBackground(new Color(157, 239, 227));

        // Tiêu đề
        JLabel labelTitleDetails = new JLabel("CHI TIẾT HOÁ ĐƠN", SwingConstants.CENTER);
        labelTitleDetails.setFont(new Font("Tahoma", Font.BOLD, 18));
        labelTitleDetails.setForeground(new Color(2, 62, 150));
        labelTitleDetails.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panelContent3.add(labelTitleDetails, BorderLayout.NORTH);

        // Thông tin hóa đơn, khách hàng, nhân viên (bên trái, cột dọc)
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(10, 2, 10, 5));
        infoPanel.setBackground(new Color(157, 239, 227));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Thông tin hóa đơn"));
        infoPanel.setPreferredSize(new Dimension(400, 0));

        // Thông tin hóa đơn
        infoPanel.add(createLabel("Mã Hóa Đơn:", font1));
        lblOrderIdValue = createLabel("", font2);
        lblOrderIdValue.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        infoPanel.add(lblOrderIdValue);

        infoPanel.add(createLabel("Ngày Lập:", font1));
        lblDayOfEstablishmentValue = createLabel("", font2);
        lblDayOfEstablishmentValue.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        infoPanel.add(lblDayOfEstablishmentValue);

        infoPanel.add(createLabel("Trạng Thái:", font1));
        lblStatusValue = createLabel("", font2);
        lblStatusValue.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        infoPanel.add(lblStatusValue);

        // Thông tin khách hàng
        infoPanel.add(createLabel("Mã Khách Hàng:", font1));
        lblCustomerIdValue = createLabel("", font2);
        lblCustomerIdValue.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        infoPanel.add(lblCustomerIdValue);

        infoPanel.add(createLabel("Tên Khách Hàng:", font1));
        lblCustomerNameValue = createLabel("", font2);
        lblCustomerNameValue.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        infoPanel.add(lblCustomerNameValue);

        infoPanel.add(createLabel("Số Điện Thoại:", font1));
        lblCustomerPhoneValue = createLabel("", font2);
        lblCustomerPhoneValue.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        infoPanel.add(lblCustomerPhoneValue);

        infoPanel.add(createLabel("Địa Chỉ:", font1));
        lblCustomerAddressValue = createLabel("", font2);
        lblCustomerAddressValue.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        infoPanel.add(lblCustomerAddressValue);

        // Thông tin nhân viên
        infoPanel.add(createLabel("Mã Nhân Viên:", font1));
        lblEmployeeIdValue = createLabel("", font2);
        lblEmployeeIdValue.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        infoPanel.add(lblEmployeeIdValue);

        infoPanel.add(createLabel("Tên Nhân Viên:", font1));
        lblEmployeeNameValue = createLabel("", font2);
        lblEmployeeNameValue.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        infoPanel.add(lblEmployeeNameValue);

        panelContent3.add(infoPanel, BorderLayout.WEST);

        // Danh sách sách (bên phải, dạng bảng)
        JPanel booksPanel = new JPanel();
        booksPanel.setLayout(new BorderLayout());
        booksPanel.setBackground(new Color(157, 239, 227));
        booksPanel.setBorder(BorderFactory.createTitledBorder("Danh sách sách"));

        // Tạo bảng
        String[] columns = {"Mã Sách", "Tên Sách", "Số lượng", "Đơn Giá", "Giảm giá", "Thành tiền"};
        tableModelDetails = new DefaultTableModel();
        tableDetails = CommonView.createTable(tableModelDetails, columns);
        tableDetails.setRowHeight(25);

        JScrollPane scrollPaneDetails = new JScrollPane(tableDetails);
        scrollPaneDetails.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        booksPanel.add(scrollPaneDetails, BorderLayout.CENTER);

        panelContent3.add(booksPanel, BorderLayout.CENTER);

        // Thống kê
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(null);
        statsPanel.setPreferredSize(new Dimension(0, 60));
        statsPanel.setBackground(new Color(200, 240, 227));
        statsPanel.setBorder(BorderFactory.createTitledBorder("Thống kê"));

        JLabel lblTotalItems = createLabel("Tổng số sản phẩm:", font1);
        lblTotalItems.setBounds(20, 20, 150, 25);
        statsPanel.add(lblTotalItems);

        lblTotalItemsValue = createLabel("0", font1);
        lblTotalItemsValue.setBounds(180, 20, 100, 25);
        statsPanel.add(lblTotalItemsValue);

        JLabel lblTotalPrice = createLabel("Tổng tiền:", font1);
        lblTotalPrice.setBounds(300, 20, 100, 25);
        statsPanel.add(lblTotalPrice);

        lblTotalPriceValue = createLabel("0 VNĐ", font1);
        lblTotalPriceValue.setBounds(400, 20, 150, 25);
        statsPanel.add(lblTotalPriceValue);

        panelContent3.add(statsPanel, BorderLayout.SOUTH);

        return panelContent3;
    }
    public JPanel initManageInvoiceView() {
        JPanel panelContent = new JPanel();
        panelContent.setLayout(null);
        panelContent.setBackground(new Color(157, 239, 227));

        JLabel labelTitle = new JLabel("QUẢN LÍ HOÁ ĐƠN");
        labelTitle.setBounds(530, 10, 250, 30);
        labelTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        panelContent.add(labelTitle);

        JPanel panelContent1 = createPanel1();
        panelContent1.setBounds(10, 50, 400, 280);
        panelContent1.setBackground(new Color(157, 239, 227));
        panelContent.add(panelContent1);

        JPanel panelContent2 = createPanel2();
        panelContent2.setBounds(420, 50, 850, 280);
        panelContent2.setBackground(new Color(157, 239, 227));
        panelContent.add(panelContent2);

        JPanel panelContent3 = createPanel3();
        panelContent3.setBounds(10, 340, 1210, 460);
        panelContent3.setBackground(new Color(157, 239, 227));
        panelContent.add(panelContent3);

        InvoiceController controller = new InvoiceController(app, this);

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

    // Getter cho các thành phần
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

    public JTable getTableInvoice() {
        return tableInvoice;
    }

    public DefaultTableModel getTableModelDetails() {
        return tableModelDetails;
    }

    public JTable getTableDetails() {
        return tableDetails;
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

    public JButton getBtnSearchCustomer() {
        return btnSearchCustomer;
    }

    public JButton getBtnSearchEmployee() {
        return btnSearchEmployee;
    }

    public JButton getBtnAdd() {
        return btnAdd;
    }

    public App getApp() {
        return app;
    }

    public JLabel getLblOrderIdValue() {
        return lblOrderIdValue;
    }

    public JLabel getLblDayOfEstablishmentValue() {
        return lblDayOfEstablishmentValue;
    }

    public JLabel getLblStatusValue() {
        return lblStatusValue;
    }

    public JLabel getLblCustomerIdValue() {
        return lblCustomerIdValue;
    }

    public JLabel getLblCustomerNameValue() {
        return lblCustomerNameValue;
    }

    public JLabel getLblCustomerPhoneValue() {
        return lblCustomerPhoneValue;
    }

    public JLabel getLblCustomerAddressValue() {
        return lblCustomerAddressValue;
    }

    public JLabel getLblEmployeeIdValue() {
        return lblEmployeeIdValue;
    }

    public JLabel getLblEmployeeNameValue() {
        return lblEmployeeNameValue;
    }
}