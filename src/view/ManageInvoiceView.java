package view;

import com.toedter.calendar.JDateChooser;
import controller.InvoiceController;
import utils.CommonView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class ManageInvoiceView extends JPanel {
    private App app;
    private static final Font font1 = new Font("Tahoma", Font.BOLD, 15);
    private static final Font font2 = new Font("Tahoma", Font.PLAIN, 15);
    private JButton btnAdd,btnDelete2, btnChange2, btnSearch, btnLoad, btnSave, btnChange, btnDelete, btnSearchCustomer, btnSearchEmployee;
    private JButton btnAddDetails, btnRefreshDetails, btnSearchDetails;
    private JDateChooser date;
    private DefaultTableModel tableModelInvoice, tableModelDetails;
    private JTable tableInvoice, tabelDetails;
    private JLabel labeltotal, labelMonney, lblTotalItemsValue, lblTotalPriceValue;
    private JTextField textFieldInvoiceId, textFieldCustomerId, textFieldEmployee, textFieldSearchDetails;
    private JLabel lblBookIdValue, lblBookNameValue, lblQuantityValue, lblUnitPriceValue, lblDiscountValue, lblSubtotalValue;
    private JComboBox<String> jComboBoxTT;

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
        panelContent3.setLayout(null);
        panelContent3.setBackground(new Color(157, 239, 227));

        // Tiêu đề
        JLabel labelTitleDetails = new JLabel("CHI TIẾT HOÁ ĐƠN", SwingConstants.CENTER);
        labelTitleDetails.setFont(new Font("Tahoma", Font.BOLD, 18));
        labelTitleDetails.setForeground(new Color(2, 62, 150));
        labelTitleDetails.setBounds(0, 10, 1190, 30);
        panelContent3.add(labelTitleDetails);

        // Bên trái: Bảng chi tiết hóa đơn và chức năng
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(null);
        tablePanel.setBounds(10, 50, 800, 340); // Rộng hơn
        tablePanel.setBackground(new Color(157, 239, 227));

        // Tìm kiếm
        JLabel lblSearch = createLabel("Tìm kiếm:", font1);
        lblSearch.setBounds(0, 0, 80, 30);
        tablePanel.add(lblSearch);

        textFieldSearchDetails = createTextField(font2);
        textFieldSearchDetails.setBounds(80, 0, 620, 30);
        textFieldSearchDetails.setToolTipText("Nhập mã sách hoặc tên sách để tìm kiếm");
        tablePanel.add(textFieldSearchDetails);

        btnSearchDetails = CommonView.createButton("Tìm", new Color(56, 46, 211));
        btnSearchDetails.setBounds(700, 0, 100, 30);
        btnSearchDetails.setToolTipText("Tìm kiếm chi tiết hóa đơn");
        tablePanel.add(btnSearchDetails);

        // Bảng
        String[] columnDetails = {"Mã HĐ", "Mã Sách", "Tên Sách", "Số lượng", "Đơn Giá", "Giảm giá", "Thành tiền"};
        tableModelDetails = new DefaultTableModel();
        tabelDetails = CommonView.createTable(tableModelDetails, columnDetails);
        tabelDetails.setRowHeight(25);
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModelDetails);
        tabelDetails.setRowSorter(sorter);

        JScrollPane scrollPaneDetails = new JScrollPane(tabelDetails);
        scrollPaneDetails.setBounds(0, 40, 800, 260);
        scrollPaneDetails.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        tablePanel.add(scrollPaneDetails);

        // Chức năng
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttonPanel.setBounds(0, 310, 800, 30);
        buttonPanel.setBackground(new Color(157, 239, 227));

        btnAddDetails = CommonView.createButton("Thêm", new Color(60, 197, 71));
        btnAddDetails.setPreferredSize(new Dimension(100, 30));
        btnAddDetails.setToolTipText("Thêm chi tiết hóa đơn mới");
        buttonPanel.add(btnAddDetails);

        btnChange2 = CommonView.createButton("Sửa", new Color(1, 131, 85));
        btnChange2.setPreferredSize(new Dimension(100, 30));
        btnChange2.setToolTipText("Cập nhật chi tiết hóa đơn");
        buttonPanel.add(btnChange2);

        btnDelete2 = CommonView.createButton("Xóa", new Color(246, 4, 60));
        btnDelete2.setPreferredSize(new Dimension(100, 30));
        btnDelete2.setToolTipText("Xóa chi tiết hóa đơn được chọn");
        buttonPanel.add(btnDelete2);

        btnRefreshDetails = CommonView.createButton("Làm mới", new Color(211, 99, 167));
        btnRefreshDetails.setPreferredSize(new Dimension(100, 30));
        btnRefreshDetails.setToolTipText("Tải lại chi tiết hóa đơn");
        buttonPanel.add(btnRefreshDetails);

        JButton btnExport = CommonView.createButton("Xuất Excel", new Color(56, 46, 211));
        btnExport.setPreferredSize(new Dimension(120, 30));
        btnExport.setToolTipText("Xuất chi tiết hóa đơn ra Excel");
        buttonPanel.add(btnExport);

        tablePanel.add(buttonPanel);

        panelContent3.add(tablePanel);

        // Bên phải: Thông tin chi tiết (chỉ label)
        JPanel detailPanel = new JPanel();
        detailPanel.setLayout(null);
        detailPanel.setBounds(820, 50, 380, 340); // Hẹp hơn
        detailPanel.setBackground(new Color(157, 239, 227));
        detailPanel.setBorder(BorderFactory.createTitledBorder("Thông tin chi tiết"));

        // Mã sách
        JLabel lblBookId = createLabel("Mã Sách:", font1);
        lblBookId.setBounds(20, 20, 80, 25);
        detailPanel.add(lblBookId);

        lblBookIdValue = createLabel("", font2);
        lblBookIdValue.setBounds(100, 20, 260, 25);
        lblBookIdValue.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        detailPanel.add(lblBookIdValue);

        // Tên sách
        JLabel lblBookName = createLabel("Tên Sách:", font1);
        lblBookName.setBounds(20, 60, 80, 25);
        detailPanel.add(lblBookName);

        lblBookNameValue = createLabel("", font2);
        lblBookNameValue.setBounds(100, 60, 260, 25);
        lblBookNameValue.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        detailPanel.add(lblBookNameValue);

        // Số lượng
        JLabel lblQuantity = createLabel("Số lượng:", font1);
        lblQuantity.setBounds(20, 100, 80, 25);
        detailPanel.add(lblQuantity);

        lblQuantityValue = createLabel("", font2);
        lblQuantityValue.setBounds(100, 100, 260, 25);
        lblQuantityValue.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        detailPanel.add(lblQuantityValue);

        // Đơn giá
        JLabel lblUnitPrice = createLabel("Đơn Giá:", font1);
        lblUnitPrice.setBounds(20, 140, 80, 25);
        detailPanel.add(lblUnitPrice);

        lblUnitPriceValue = createLabel("", font2);
        lblUnitPriceValue.setBounds(100, 140, 260, 25);
        lblUnitPriceValue.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        detailPanel.add(lblUnitPriceValue);

        // Giảm giá
        JLabel lblDiscount = createLabel("Giảm giá:", font1);
        lblDiscount.setBounds(20, 180, 80, 25);
        detailPanel.add(lblDiscount);

        lblDiscountValue = createLabel("", font2);
        lblDiscountValue.setBounds(100, 180, 260, 25);
        lblDiscountValue.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        detailPanel.add(lblDiscountValue);

        // Thành tiền
        JLabel lblSubtotal = createLabel("Thành tiền:", font1);
        lblSubtotal.setBounds(20, 220, 80, 25);
        detailPanel.add(lblSubtotal);

        lblSubtotalValue = createLabel("", font2);
        lblSubtotalValue.setBounds(100, 220, 260, 25);
        lblSubtotalValue.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        detailPanel.add(lblSubtotalValue);

        panelContent3.add(detailPanel);

        // Thống kê
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(null);
        statsPanel.setBounds(10, 400, 1190, 60);
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

        panelContent3.add(statsPanel);

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
        panelContent3.setBounds(10, 340, 1210, 460); // Điều chỉnh vị trí do panel2 rộng hơn
        panelContent3.setBackground(new Color(157, 239, 227));
        panelContent.add(panelContent3);

        InvoiceController controller  = new InvoiceController(app,this);

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

    // Getter cho các thành phần mới
    public JButton getBtnAddDetails() {
        return btnAddDetails;
    }

    public JButton getBtnRefreshDetails() {
        return btnRefreshDetails;
    }

    public JButton getBtnSearchDetails() {
        return btnSearchDetails;
    }

    public JTextField getTextFieldSearchDetails() {
        return textFieldSearchDetails;
    }

    public JLabel getLblBookIdValue() {
        return lblBookIdValue;
    }

    public JLabel getLblBookNameValue() {
        return lblBookNameValue;
    }

    public JLabel getLblQuantityValue() {
        return lblQuantityValue;
    }

    public JLabel getLblUnitPriceValue() {
        return lblUnitPriceValue;
    }

    public JLabel getLblDiscountValue() {
        return lblDiscountValue;
    }

    public JLabel getLblSubtotalValue() {
        return lblSubtotalValue;
    }

    // Getter cho các thành phần hiện có
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

    public JButton getBtnSearchCustomer() {
        return btnSearchCustomer;
    }

    public JButton getBtnSearchEmployee() {
        return btnSearchEmployee;
    }

    // Xóa getter không còn sử dụng
    public JButton getBtnSearchBook() {
        return null; // Không còn nút tìm sách
    }

    public JTextField getTextFieldBookId() {
        return null; // Không còn text field
    }

    public JTextField getTextFieldBookName() {
        return null;
    }

    public JTextField getTextFieldQuantity() {
        return null;
    }

    public JTextField getTextFieldUnitPrice() {
        return null;
    }

    public JTextField getTextFieldDiscount() {
        return null;
    }

    public JTextField getTextFieldSubtotal() {
        return null;
    }

    public JButton getBtnAdd() {
        return btnAdd;
    }
}