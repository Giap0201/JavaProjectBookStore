package view;

import com.toedter.calendar.JDateChooser;
import utils.CommonView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ManageInvoiceView extends JPanel {
    private static final Font font1 = new Font("Tahoma", Font.BOLD, 15);
    private static final Font font2 = new Font("Tahoma", Font.PLAIN, 15);
    private JButton btnDelete2,btnChange2,btnSearch,btnLoad,btnSave,btnChange,btnDelete;
    private JDateChooser date;
    private DefaultTableModel tableModelInvoice,tableModelDetails;
    private JTable tableInvoice,tabelDetails;
    private JLabel labeltotal,labelMonney,lblTotalItemsValue,lblTotalPriceValue;
    private JTextField textFieldInvoiceId,textFieldCustomerId,textFieldEmployee;
    private JComboBox<String> jComboBoxTT;

    public JPanel createPanel1() {
        JPanel panelContent1 = new JPanel();
        panelContent1.setLayout(null);
        JLabel labelInvoiceId = createLabel("Mã HĐ", font1);
        labelInvoiceId.setBounds(50, 15, 70, 25);
        panelContent1.add(labelInvoiceId);

        textFieldInvoiceId = createTextField(font2);
        textFieldInvoiceId.setBounds(130, 15, 120, 25);
        panelContent1.add(textFieldInvoiceId);

        JLabel labelCustomerId = createLabel("Mã KH", font1);
        labelCustomerId.setBounds(280, 15, 70, 25);
        panelContent1.add(labelCustomerId);

        textFieldCustomerId = createTextField(font2);
        textFieldCustomerId.setBounds(355, 15, 120, 25);
        panelContent1.add(textFieldCustomerId);

        JLabel labelEmployee = createLabel("Mã NV", font1);
        labelEmployee.setBounds(510, 15, 70, 25);
        panelContent1.add(labelEmployee);

        textFieldEmployee = createTextField(font2);
        textFieldEmployee.setBounds(585, 15, 120, 25);
        panelContent1.add(textFieldEmployee);

        JLabel labelDay = createLabel("Ngày lập", font1);
        labelDay.setBounds(730, 15, 70, 25);
        panelContent1.add(labelDay);

        date = new JDateChooser();
        date.setFont(font2);
        date.setBounds(820, 15, 130, 25);
        panelContent1.add(date);

        JLabel labelTT = createLabel("Trạng thái", font1);
        labelTT.setBounds(970, 15, 90, 25);
        panelContent1.add(labelTT);

        jComboBoxTT = new JComboBox<>();
        jComboBoxTT.setFont(font2);
        jComboBoxTT.addItem("Chọn trạng thái");
        jComboBoxTT.addItem("Đã thanh toán");
        jComboBoxTT.addItem("Chưa thanh toán");
        jComboBoxTT.setBounds(1080,15,140,25);
        panelContent1.add(jComboBoxTT);

        btnDelete = CommonView.createButton("Xoá", new Color(246, 4, 60));
        btnDelete.setBounds(300, 50, 100, 30);
        panelContent1.add(btnDelete);

        btnChange = CommonView.createButton("Sửa", new Color(1, 131, 85));
        btnChange.setBounds(450, 50, 100, 30);
        panelContent1.add(btnChange);

        btnSave = CommonView.createButton("Xuất", new Color(60, 197, 71));
        btnSave.setBounds(600, 50, 100, 30);
        panelContent1.add(btnSave);

        btnLoad = CommonView.createButton("Làm mới", new Color(211, 99, 167));
        btnLoad.setBounds(750, 50, 100, 30);
        panelContent1.add(btnLoad);

        btnSearch = CommonView.createButton("Tìm kiếm", new Color(56, 46, 211));
        btnSearch.setBounds(900, 50, 120, 30);
        panelContent1.setBackground(new Color(245, 248, 214));
        panelContent1.add(btnSearch);
        panelContent1.setBorder(BorderFactory.createLineBorder(new Color(3, 30, 117)));
        return panelContent1;
    }

    private JPanel createPanel2() {
        JPanel panelContent2 = new JPanel();
        String[] columns = {"Mã HĐ", "Mã KH", "Mã NV", "Mã GD", "Ngày lập", "Tổng tiền", "Giảm giá", "Thành tiền"};
        tableModelInvoice = new DefaultTableModel();
        tableInvoice = CommonView.createTable(tableModelInvoice, columns);
        JScrollPane scrollPane = new JScrollPane(tableInvoice);
        scrollPane.setBounds(40, 10, 1200, 280);
        panelContent2.add(scrollPane);
        JLabel labeltotal1 = createLabel("Số lượng hoá đơn: ", font1);
        labeltotal1.setBounds(50, 300, 150, 20);
        panelContent2.add(labeltotal1);

        labeltotal = createLabel("0", font1);
        labeltotal.setBounds(200, 300, 150, 20);
        panelContent2.add(labeltotal);

        JLabel labelMonney1 = createLabel("Tổng tiền: ", font1);
        labelMonney1.setBounds(400, 300, 100, 20);
        panelContent2.add(labelMonney1);

        labelMonney = createLabel("0 VNĐ", font1);
        labelMonney.setBounds(500, 300, 150, 20);
        panelContent2.add(labelMonney);
        return panelContent2;
    }

    private JPanel createPanel3() {
        JPanel panelContent3 = new JPanel();
        panelContent3.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.BLUE));
        panelContent3.setLayout(null);
        JLabel labeltitle3 = new JLabel("CHI TIẾT HOÁ ĐƠN");
        labeltitle3.setFont(new Font("Tahoma", Font.BOLD, 16));
        labeltitle3.setForeground(new Color(2, 62, 150));
        labeltitle3.setBounds(620, 5, 200, 25);
        panelContent3.add(labeltitle3);

        String[] columnTabel2 = {"Mã HĐ", "Mã Sách", "Tên Sách", "Số lượng", "Đơn Giá", "Giảm Giá", "Thành tiền"};
        tableModelDetails = new DefaultTableModel();
        tabelDetails = CommonView.createTable(tableModelDetails, columnTabel2);

        JScrollPane scrollPane2 = new JScrollPane(tabelDetails);
        scrollPane2.setBounds(40, 40, 1000, 260);
        panelContent3.add(scrollPane2);

// Nhãn hiển thị tiêu đề tổng số sản phẩm
        JLabel lblTotalItems = createLabel("Tổng số sản phẩm:", font1);
        lblTotalItems.setBounds(1060, 30, 150, 25);
        panelContent3.add(lblTotalItems);

// Nhãn hiển thị giá trị tổng số sản phẩm (mặc định là 0)
        lblTotalItemsValue = createLabel("0", font1);
        lblTotalItemsValue.setBounds(1220, 30, 100, 25);
        panelContent3.add(lblTotalItemsValue);

// Nhãn hiển thị tiêu đề tổng tiền
        JLabel lblTotalPrice = createLabel("Tổng tiền:", font1);
        lblTotalPrice.setBounds(1060, 70, 150, 25);
        panelContent3.add(lblTotalPrice);

// Nhãn hiển thị giá trị tổng tiền (mặc định là 0 VNĐ)
        lblTotalPriceValue = createLabel("0 VNĐ", font1);
        lblTotalPriceValue.setBounds(1220, 70, 120, 25);
        panelContent3.add(lblTotalPriceValue);

        btnChange2 = CommonView.createButton("Sửa", new Color(44, 134, 3));
        btnChange2.setBounds(1110, 110, 120, 30);
        panelContent3.add(btnChange2);

        btnDelete2 = CommonView.createButton("Xoá", new Color(203, 13, 13));
        btnDelete2.setBounds(1110, 150, 120, 30);
        panelContent3.add(btnDelete2);
        return panelContent3;
    }

    public JPanel initManageInvoiceView() {
        JPanel panelContent = new JPanel();
        panelContent.setLayout(null);
        JLabel labeltitle = new JLabel("QUẢN LÍ HOÁ ĐƠN");
        labeltitle.setBounds(600, 10, 250, 30);
        labeltitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        panelContent.add(labeltitle);

        //phan chua sua xoa xuat lam moi
        JPanel panelContent1 = createPanel1();
        panelContent1.setLayout(null);
        panelContent1.setBounds(30, 45, 1230, 100);

        JPanel panelContent2 = createPanel2();
        panelContent2.setLayout(null);
        panelContent2.setBounds(0, 150, 1500, 330);
        JPanel panelContent3 = createPanel3();
        panelContent3.setLayout(null);
        panelContent3.setBounds(0, 480, 1500, 320);

        //them vao panel chinh
        panelContent.add(panelContent1);
        panelContent.setBackground(new Color(245, 248, 214));
        panelContent1.setBackground(new Color(245, 248, 214));
        panelContent2.setBackground(new Color(245, 248, 214));
        panelContent3.setBackground(new Color(245, 248, 214));

        panelContent.add(panelContent2);
        panelContent.add(panelContent3);
        return panelContent;
    }

    //ham tao hang loat textFile
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