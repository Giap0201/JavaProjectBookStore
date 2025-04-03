package view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class ManageInvoiceView extends JPanel {
    public JPanel initManageInvoiceView() {
        Font font = new Font("Tahoma", Font.BOLD, 15);
        JPanel panelContent = new JPanel();
        panelContent.setLayout(null);
        panelContent.setBackground(new Color(157, 239, 227));

        JLabel labeltitle = new JLabel("QUẢN LÍ HOÁ ĐƠN");
        labeltitle.setBounds(600, 10, 250, 30);
        labeltitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        panelContent.add(labeltitle);

        //phan chua sua xoa xuat lam moi
        JPanel panelContent1  = new JPanel();
        panelContent1.setLayout(null);
        panelContent1.setBounds(0,45,1500,85);

        //them vao panel chinh
        panelContent.add(panelContent1);

        Font font1 = new Font("Tahoma", Font.BOLD, 15);
        Font font2 = new Font("Tahoma", Font.PLAIN, 15);
        JLabel labelInvoiceId = createLabel("Mã HĐ",font1);
        labelInvoiceId.setBounds(50,15, 70, 25);
        panelContent1.add(labelInvoiceId);

        JTextField textFieldInvoiceId = createTextField(font2);
        textFieldInvoiceId.setBounds(130,15, 120, 25);
        panelContent1.add(textFieldInvoiceId);

        JLabel labelCustomerId = createLabel("Mã KH",font1);
        labelCustomerId.setBounds(280,15,70,25);
        panelContent1.add(labelCustomerId);

        JTextField textFieldCustomerId = createTextField(font2);
        textFieldCustomerId.setBounds(355,15, 120, 25);
        panelContent1.add(textFieldCustomerId);

        JLabel labelEmployee = createLabel("Mã NV",font1);
        labelEmployee.setBounds(510,15, 70, 25);
        panelContent1.add(labelEmployee);

        JTextField textFieldEmployee = createTextField(font2);
        textFieldEmployee.setBounds(585,15, 120, 25);
        panelContent1.add(textFieldEmployee);

        JLabel labelDay = createLabel("Ngày lập",font1);
        labelDay.setBounds(730,15, 70, 25);
        panelContent1.add(labelDay);

        JTextField textFieldDay = createTextField(font2);
        textFieldDay.setBounds(820,15, 130, 25);
        panelContent1.add(textFieldDay);

        JLabel labelGD = createLabel("Mã GD",font1);
        labelGD.setBounds(50,50, 70, 25);
        panelContent1.add(labelGD);

        JTextField textFieldGD = createTextField(font2);
        textFieldGD.setBounds(130,50, 120, 25);
        panelContent1.add(textFieldGD);

        JLabel labelTong = createLabel("Tổng tiền",font1);
        labelTong.setBounds(270,50, 80, 25);
        panelContent1.add(labelTong);

        JTextField textFieldTong = createTextField(font2);
        textFieldTong.setBounds(355,50, 120, 25);
        panelContent1.add(textFieldTong);

        JLabel labelSales = createLabel("Giảm giá",font1);
        labelSales.setBounds(510,50, 70, 25);
        panelContent1.add(labelSales);

        JTextField textFieldSales = createTextField(font2);
        textFieldSales.setBounds(585,50, 120, 25);
        panelContent1.add(textFieldSales);

        JLabel labelThanhTien = createLabel("Thành tiền",font1);
        labelThanhTien.setBounds(730,50, 100, 25);
        panelContent1.add(labelThanhTien);

        JTextField textFieldThanhTien = createTextField(font2);
        textFieldThanhTien.setBounds(820,50, 130, 25);
        panelContent1.add(textFieldThanhTien);

        JButton btnDelete = createButton("Xoá",font1);
        btnDelete.setBackground(new Color(211, 99, 167));
        btnDelete.setBounds(1000,15,110,25);
        panelContent1.add(btnDelete);

        JButton btnChange = createButton("Sửa",font1);
        btnChange.setBackground(new Color(211, 99, 167));
        btnChange.setBounds(1130,15,110,25);
        panelContent1.add(btnChange);

        JButton btnSave = createButton("Xuất",font1);
        btnSave.setBackground(new Color(211, 99, 167));
        btnSave.setBounds(1000,50,110,25);
        panelContent1.add(btnSave);

        JButton btnLoad = createButton("Làm mới",font1);
        btnLoad.setBackground(new Color(211, 99, 167));
        btnLoad.setBounds(1130,50,110,25);
        panelContent1.add(btnLoad);

        //content2
        JPanel panelConten2  = new JPanel();
        panelConten2.setLayout(null);
        panelConten2.setBounds(0,130,1500,340);
        panelConten2.setBackground(new Color(245, 248, 214));

        JLabel title2 = new JLabel("TÌM KIẾM HOÁ ĐƠN");
        title2.setFont(new Font("Tahoma", Font.BOLD, 16));
        title2.setForeground(new Color(41, 41, 201));
        title2.setBounds(610, 0, 250, 30);
        panelConten2.add(title2);

        JLabel labelSearchID = createLabel("Mã HĐ", font1);
        labelSearchID.setBounds(220, 30, 80, 25);
        panelConten2.add(labelSearchID);

        JTextField textFieldSearchID = createTextField(font2);
        textFieldSearchID.setBounds(300, 30, 150, 25);
        panelConten2.add(textFieldSearchID);

        JLabel labelSearchEID = createLabel("Mã NV", font1);
        labelSearchEID.setBounds(470, 30, 80, 25);
        panelConten2.add(labelSearchEID);

        JTextField textFieldSearchEID = createTextField(font2);
        textFieldSearchEID.setBounds(550, 30, 150, 25);
        panelConten2.add(textFieldSearchEID);

        JLabel labelSearchCID = createLabel("Mã KH", font1);
        labelSearchCID.setBounds(720, 30, 80, 25);
        panelConten2.add(labelSearchCID);

        JTextField textFieldSearchCID = createTextField(font2);
        textFieldSearchCID.setBounds(800, 30, 150, 25);
        panelConten2.add(textFieldSearchCID);

        JButton btnSearch = createButton("Tìm Kiếm", font1);
        btnSearch.setBackground(new Color(211, 99, 167));
        btnSearch.setBounds(1010, 30, 150, 25);
        panelConten2.add(btnSearch);

        JLabel labelDayStart = createLabel("Thời gian từ", font1);
        labelDayStart.setBounds(180, 60, 130, 25);
        panelConten2.add(labelDayStart);

        JTextField textFieldDayStart = createTextField(font2);
        textFieldDayStart.setBounds(300, 60, 150, 25);
        panelConten2.add(textFieldDayStart);

        JLabel labelDayEnd = createLabel("đến", font1);
        labelDayEnd.setBounds(490, 60, 50, 25);
        panelConten2.add(labelDayEnd);

        JTextField textFieldDayEnd = createTextField(font2);
        textFieldDayEnd.setBounds(550, 60, 150, 25);
        panelConten2.add(textFieldDayEnd);

        JLabel labelMoneyStart = createLabel("Giá tiền từ", font1);
        labelMoneyStart.setBounds(710, 60, 100, 25);
        panelConten2.add(labelMoneyStart);

        JTextField textFieldMoneyStart = createTextField(font2);
        textFieldMoneyStart.setBounds(800, 60, 150, 25);
        panelConten2.add(textFieldMoneyStart);

        JLabel labelMonneyEnd = createLabel("đến", font1);
        labelMonneyEnd.setBounds(960, 60, 50, 25);
        panelConten2.add(labelMonneyEnd);

        JTextField textFieldMonneyEnd = createTextField(font2);
        textFieldMonneyEnd.setBounds(1010, 60, 150, 25);
        panelConten2.add(textFieldMonneyEnd);

        //bang du dieu
        String[] column = {"Mã HĐ","Mã KH","Mã NV","Mã GD","Ngày lập","Tổng tiền","Giảm giá","Thành tiền"};
        DefaultTableModel tableModel = new DefaultTableModel(column, 0);
        JTable table = new JTable(tableModel);
        table.setFont(new Font("Tahoma", Font.PLAIN, 12));
        table.setRowHeight(15);

        JTableHeader tableHeader = table.getTableHeader();

        //dat font va tieu de cot
        tableHeader.setFont(new Font("Tahoma", Font.BOLD, 13));
        tableHeader.setBackground(new Color(172, 172, 182));

        //tao doi tuong can giua noi dung
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        //duyet qua tung cot tao can giua noi dung
        for (int i = 0; i<table.getColumnCount(); i++){
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }


        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(40,95,1200,220);
        panelConten2.add(scrollPane);

        JLabel labeltotal = createLabel("Số lượng hoá đơn: ",font1);
        labeltotal.setBounds(50,317,150,20);
        panelConten2.add(labeltotal);

        JLabel textFieldTotal = createLabel("0",font1);
        textFieldTotal.setBounds(200, 317, 150, 20);
        panelConten2.add(textFieldTotal);

        JLabel labelmonney = createLabel("Tổng tiền: ",font1);
        labelmonney.setBounds(400,317,100,20);
        panelConten2.add(labelmonney);

        JLabel textFieldMonney = createLabel("0 VNĐ",font1);
        textFieldMonney.setBounds(500, 317, 150, 20);
        panelConten2.add(textFieldMonney);


        //content3
        JPanel panelConten3 = new JPanel();
        panelConten3.setLayout(null);
        panelConten3.setBounds(0,466,1500,320);

        JLabel labeltitle3 = new JLabel("CHI TIẾT HOÁ ĐƠN");
        labeltitle3.setFont(new Font("Tahoma", Font.BOLD, 16));
        labeltitle3.setForeground(new Color(2, 62, 150));
        labeltitle3.setBounds(620, 5, 200, 25);
        panelConten3.add(labeltitle3);

        String[] columnTabel2 = {"Mã HĐ","Mã Sách","Tên Sách","Số lượng","Đơn Giá","Giảm Giá","Thành tiền"};
        DefaultTableModel tableModel2 = new DefaultTableModel(columnTabel2, 0);
        JTable tabel2 = new JTable(tableModel2);
        tabel2.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tabel2.setRowHeight(20);
        JTableHeader tableHeader2 = tabel2.getTableHeader();
        tableHeader2.setFont(new Font("Tahoma", Font.BOLD, 13));
        tableHeader2.setBackground(new Color(172, 172, 182));

// Căn giữa nội dung bảng
        DefaultTableCellRenderer tableCellRenderer2 = new DefaultTableCellRenderer();
        tableCellRenderer2.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < tabel2.getColumnCount(); i++) {
            tabel2.getColumnModel().getColumn(i).setCellRenderer(tableCellRenderer2);
        }

// Thêm bảng vào JScrollPane
        JScrollPane scrollPane2 = new JScrollPane(tabel2);
        scrollPane2.setBounds(40, 40, 1000, 260);
        panelConten3.add(scrollPane2);

// Nhãn hiển thị tiêu đề tổng số sản phẩm
        JLabel lblTotalItems = createLabel("Tổng số sản phẩm:", font1);
        lblTotalItems.setBounds(1060, 30, 150, 25);
        panelConten3.add(lblTotalItems);

// Nhãn hiển thị giá trị tổng số sản phẩm (mặc định là 0)
        JLabel lblTotalItemsValue = createLabel("0", font1);
        lblTotalItemsValue.setBounds(1220, 30, 100, 25);
        panelConten3.add(lblTotalItemsValue);

// Nhãn hiển thị tiêu đề tổng tiền
        JLabel lblTotalPrice = createLabel("Tổng tiền:", font1);
        lblTotalPrice.setBounds(1060, 70, 150, 25);
        panelConten3.add(lblTotalPrice);

// Nhãn hiển thị giá trị tổng tiền (mặc định là 0 VNĐ)
        JLabel lblTotalPriceValue = createLabel("0 VNĐ", font1);
        lblTotalPriceValue.setBounds(1220, 70, 120, 25);
        panelConten3.add(lblTotalPriceValue);

// Thêm nút "Sửa"
        JButton btnChange2 = createButton("Sửa", font1);
        btnChange2.setBackground(new Color(44, 134, 3));
        btnChange2.setBounds(1110, 110, 120, 30);
        panelConten3.add(btnChange2);

// Thêm nút "Xoá"
        JButton btnDelete2 = createButton("Xoá", font1);
        btnDelete2.setBackground(new Color(203, 13, 13));
        btnDelete2.setBounds(1110, 150, 120, 30);
        panelConten3.add(btnDelete2);

        panelContent.add(panelConten2);
        panelContent.add(panelConten3);
        return panelContent;
    }
    //ham tao hang loat textFile
    public JTextField createTextField(Font font) {
        JTextField textField = new JTextField();
        textField.setColumns(10);
        textField.setFont(font);
        return textField;
    }
    public JLabel createLabel(String title,Font font) {
        JLabel label = new JLabel(title);
        label.setFont(font);
        return label;
    }
    public JButton createButton(String title,Font font) {
        JButton button = new JButton(title);
        button.setFont(font);
        return button;
    }

    public static void main(String[] args) {
        ManageInvoiceView a = new ManageInvoiceView();
        JPanel panel = a.initManageInvoiceView();
        JFrame app = new App();
        app.add(panel, BorderLayout.CENTER);
    }
}
