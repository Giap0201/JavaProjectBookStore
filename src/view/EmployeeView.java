package view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

import controller.EmployeeController;
import utils.CommonView;

public class EmployeeView extends JPanel {

    private JTextField textFieldEmployeeId = new JTextField();
    private JTextField textFieldLastName;
    private JTextField textFieldFirstName;
    private JTextField textFieldPhone,textFieldEmail,textFieldPosition,textFieldSalary,textFieldDob;
    private JRadioButton radioPositionNam,radioPositionNu;
    private JButton calendarButton, btnReset = CommonView.createButton("LÀM MỚI",new Color(255, 248, 29)),
            btnDelete = CommonView.createButton("XÓA",new Color(255, 0, 0)),
            btnUpdate = CommonView.createButton("SỬA",new Color(255, 153, 0)),
            btnAdd = CommonView.createButton("THÊM",new Color(0, 153, 0));;
    private JTextField textFieldSearch,textFieldSearchSalaryFrom,textFieldSearchSalaryTo;
    private JComboBox<String> comboBoxSearch;
    private JButton btnSearchAll,btnExportExcel;
    private DefaultTableModel tableModel;
    private JTable table;


    public JPanel initEmployeeView() {
        Font font = new Font("Tahoma", Font.BOLD, 15);
        JPanel panelContent = new JPanel();
        panelContent.setLayout(null);
        panelContent.setBackground(new Color(157, 239, 227));

        // Tiêu đề của panel
        JLabel labelTitle = new JLabel("QUẢN LÝ NHÂN VIÊN");
        labelTitle.setBounds(600, 10, 300, 30);
        labelTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        panelContent.add(labelTitle);

        // Phần nhập thông tin nhân viên
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(null);
        inputPanel.setBackground(new Color(255, 255, 255));
        inputPanel.setBounds(10, 50, 320, 300);

        panelContent.add(inputPanel);
        // Bảng dữ liệu nhân viên
        String[] columnNames = {"Mã NV", "Họ", "Tên" , "Chức vụ", "Email", "SĐT", "Lương", "Phái", "Ngày sinh"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);

        EmployeeController action = new EmployeeController(this);

        // Mã nhân viên
        JLabel labelEmployeeId = new JLabel("Mã Nhân Viên:");
        labelEmployeeId.setFont(font);
        labelEmployeeId.setBounds(10, 10, 120, 30);
        inputPanel.add(labelEmployeeId);

        textFieldEmployeeId.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textFieldEmployeeId.setBounds(130, 10, 150, 30);
        inputPanel.add(textFieldEmployeeId);

        // Họ
        JLabel labelLastName = new JLabel("Họ:");
        labelLastName.setFont(font);
        labelLastName.setBounds(10, 40, 120, 30);
        inputPanel.add(labelLastName);

        textFieldLastName = new JTextField();
        textFieldLastName.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textFieldLastName.setBounds(130, 40, 150, 30);
        inputPanel.add(textFieldLastName);

        // Tên
        JLabel labelFirstName = new JLabel("Tên:");
        labelFirstName.setFont(font);
        labelFirstName.setBounds(10, 70, 120, 30);
        inputPanel.add(labelFirstName);

        textFieldFirstName = new JTextField();
        textFieldFirstName.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textFieldFirstName.setBounds(130, 70, 150, 30);
        inputPanel.add(textFieldFirstName);

        // Số điện thoại
        JLabel labelPhone = new JLabel("SĐT:");
        labelPhone.setFont(font);
        labelPhone.setBounds(10, 100, 120, 30);
        inputPanel.add(labelPhone);

        textFieldPhone = new JTextField();
        textFieldPhone.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textFieldPhone.setBounds(130, 100, 150, 30);
        inputPanel.add(textFieldPhone);

        // Email
        JLabel labelEmail = new JLabel("Email:");
        labelEmail.setFont(font);
        labelEmail.setBounds(10, 130, 120, 30);
        inputPanel.add(labelEmail);

        textFieldEmail = new JTextField();
        textFieldEmail.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textFieldEmail.setBounds(130, 130, 150, 30);
        inputPanel.add(textFieldEmail);

        // Chức vụ
        JLabel labelPosition = new JLabel("Chức Vụ:");
        labelPosition.setFont(font);
        labelPosition.setBounds(10, 160, 120, 30);
        inputPanel.add(labelPosition);

        textFieldPosition = new JTextField();
        textFieldPosition.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textFieldPosition.setBounds(130, 160, 150, 30);
        inputPanel.add(textFieldPosition);

        // Lương
        JLabel labelSalary = new JLabel("Lương:");
        labelSalary.setFont(font);
        labelSalary.setBounds(10, 190, 120, 30);
        inputPanel.add(labelSalary);

        textFieldSalary = new JTextField();
        textFieldSalary.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textFieldSalary.setBounds(130, 190, 150, 30);
        inputPanel.add(textFieldSalary);

        // Vị trí (Giới tính)
        JLabel labelGender = new JLabel("Giới tính");
        labelGender.setFont(font);
        labelGender.setBounds(10, 220, 70, 30);
        inputPanel.add(labelGender);

        radioPositionNam = new JRadioButton("Nam");
        radioPositionNam.setFont(new Font("Tahoma", Font.PLAIN, 15));
        radioPositionNam.setBounds(130, 220, 60, 30);
        radioPositionNam.setBackground(Color.WHITE);
        inputPanel.add(radioPositionNam);

        radioPositionNu = new JRadioButton("Nữ");
        radioPositionNu.setFont(new Font("Tahoma", Font.PLAIN, 15));
        radioPositionNu.setBounds(190, 220, 60, 30);
        radioPositionNu.setBackground(Color.WHITE);
        inputPanel.add(radioPositionNu);

        ButtonGroup positionGroup = new ButtonGroup();
        positionGroup.add(radioPositionNam);
        positionGroup.add(radioPositionNu);

        // Ngày sinh
        JLabel labelDob = new JLabel("Ngày Sinh:");
        labelDob.setFont(font);
        labelDob.setBounds(10, 250, 120, 30);
        inputPanel.add(labelDob);


        textFieldDob = new JTextField();
        textFieldDob.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textFieldDob.setBounds(130, 250, 150, 30);
        inputPanel.add(textFieldDob);

        calendarButton = new JButton();
        calendarButton.setBounds(280,250, 30, 30);
        ImageIcon icon7=CommonView.scaleImage("images/icon7.png",30,30);
        calendarButton.setIcon(icon7);
        inputPanel.add(calendarButton);
        calendarButton.addActionListener(action);

        // Các nút chức năng
        btnAdd.setBounds(30, 380, 100, 40);
        panelContent.add(btnAdd);
        btnAdd.addActionListener(action);

        btnUpdate.setBounds(170, 380, 100, 40);
        panelContent.add(btnUpdate);
        btnUpdate.addActionListener(action);

        btnDelete.setBounds(30, 440, 100, 40);
        panelContent.add(btnDelete);
        btnDelete.addActionListener(action);

        btnReset.setBounds(170, 440, 120, 40);
        panelContent.add(btnReset);
        btnReset.addActionListener(action);

        ImageIcon icon9=CommonView.scaleImage("images/icon10.png",300,200);
        JLabel lblicon9 = new JLabel(icon9);
        lblicon9.setBounds(10, 540, 300, 200);
        panelContent.add(lblicon9);

        ImageIcon imageBackground = CommonView.scaleImage("images/image.jpg",900,200);
        JLabel imageLabel = new JLabel(imageBackground);
        imageLabel.setBounds(350, 50, imageBackground.getIconWidth(), imageBackground.getIconHeight());
        panelContent.add(imageLabel);


        // Phần tìm kiếm
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(null);
        searchPanel.setBackground(new Color(255, 255, 255));
        searchPanel.setBounds(350, 260, 900, 100);
        panelContent.add(searchPanel);

        JLabel labelSearchTitle = new JLabel("TÌM KIẾM NHÂN VIÊN");
        labelSearchTitle.setFont(new Font("Tahoma", Font.BOLD, 17));
        labelSearchTitle.setBounds(10, 10, 200, 20);
        searchPanel.add(labelSearchTitle);

        comboBoxSearch = new JComboBox<>(new String[]{"Mã NV", "Họ", "Tên","SĐT","Chức vụ"});
        comboBoxSearch.setFont(new Font("Tahoma", Font.PLAIN, 15));
        comboBoxSearch.setBounds(80, 40, 150, 30);
        searchPanel.add(comboBoxSearch);

        textFieldSearch = new JTextField();
        textFieldSearch.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textFieldSearch.setBounds(270, 40, 160, 30);
        searchPanel.add(textFieldSearch);


        JLabel labelSearchSalaryFrom = new JLabel("Lương Từ ");
        labelSearchSalaryFrom.setFont(font);
        labelSearchSalaryFrom.setBounds(80, 70, 100, 30);
        searchPanel.add(labelSearchSalaryFrom);

        textFieldSearchSalaryFrom = new JTextField();
        textFieldSearchSalaryFrom.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textFieldSearchSalaryFrom.setBounds(160, 70, 100, 30);
        searchPanel.add(textFieldSearchSalaryFrom);

        JLabel labelSearchSalaryTo = new JLabel("đến");
        labelSearchSalaryTo.setFont(font);
        labelSearchSalaryTo.setBounds(270, 70, 100, 30);
        searchPanel.add(labelSearchSalaryTo);

        textFieldSearchSalaryTo = new JTextField();
        textFieldSearchSalaryTo.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textFieldSearchSalaryTo.setBounds(310, 70, 100, 30);
        searchPanel.add(textFieldSearchSalaryTo);

        btnSearchAll = CommonView.createButton("Tìm Kiếm",Color.BLACK);
        btnSearchAll.setBounds(550, 50, 120, 40);
        searchPanel.add(btnSearchAll);
        btnSearchAll.addActionListener(action);



        // Thiết lập font và chiều cao dòng cho bảng
        table.setFont(new Font("Tahoma", Font.PLAIN, 12));
        table.setRowHeight(20);

        // Tùy chỉnh tiêu đề bảng
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Tahoma", Font.BOLD, 14));
        header.setBackground(Color.LIGHT_GRAY);
        header.setForeground(Color.BLACK);

        // Căn giữa nội dung bảng
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Thêm bảng vào thanh cuộn
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(350, 380, 900, 420);
        panelContent.add(scrollPane);


        btnExportExcel = CommonView.createButton("Xuất Excel",new Color(237, 16, 159));
        btnExportExcel.setBounds(1130, 830, 120, 40);
        panelContent.add(btnExportExcel);

        return panelContent;
    }

    public JTextField getTextFieldEmployeeId() {
        return textFieldEmployeeId;
    }

    public void setTextFieldEmployeeId(JTextField textFieldEmployeeId) {
        this.textFieldEmployeeId = textFieldEmployeeId;
    }

    public JTextField getTextFieldLastName() {
        return textFieldLastName;
    }

    public void setTextFieldLastName(JTextField textFieldLastName) {
        this.textFieldLastName = textFieldLastName;
    }

    public JTextField getTextFieldFirstName() {
        return textFieldFirstName;
    }

    public void setTextFieldFirstName(JTextField textFieldFirstName) {
        this.textFieldFirstName = textFieldFirstName;
    }

    public JTextField getTextFieldPhone() {
        return textFieldPhone;
    }

    public void setTextFieldPhone(JTextField textFieldPhone) {
        this.textFieldPhone = textFieldPhone;
    }

    public JTextField getTextFieldEmail() {
        return textFieldEmail;
    }

    public void setTextFieldEmail(JTextField textFieldEmail) {
        this.textFieldEmail = textFieldEmail;
    }

    public JTextField getTextFieldPosition() {
        return textFieldPosition;
    }

    public void setTextFieldPosition(JTextField textFieldPosition) {
        this.textFieldPosition = textFieldPosition;
    }

    public JTextField getTextFieldSalary() {
        return textFieldSalary;
    }

    public void setTextFieldSalary(JTextField textFieldSalary) {
        this.textFieldSalary = textFieldSalary;
    }

    public JTextField getTextFieldDob() {
        return textFieldDob;
    }

    public void setTextFieldDob(JTextField textFieldDob) {
        this.textFieldDob = textFieldDob;
    }

    public JRadioButton getRadioPositionNam() {
        return radioPositionNam;
    }

    public void setRadioPositionNam(JRadioButton radioPositionNam) {
        this.radioPositionNam = radioPositionNam;
    }

    public JRadioButton getRadioPositionNu() {
        return radioPositionNu;
    }

    public void setRadioPositionNu(JRadioButton radioPositionNu) {
        this.radioPositionNu = radioPositionNu;
    }

    public JButton getCalendarButton() {
        return calendarButton;
    }

    public void setCalendarButton(JButton calendarButton) {
        this.calendarButton = calendarButton;
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

    public JButton getBtnUpdate() {
        return btnUpdate;
    }

    public void setBtnUpdate(JButton btnUpdate) {
        this.btnUpdate = btnUpdate;
    }

    public JButton getBtnAdd() {
        return btnAdd;
    }

    public void setBtnAdd(JButton btnAdd) {
        this.btnAdd = btnAdd;
    }

    public JTextField getTextFieldSearch() {
        return textFieldSearch;
    }

    public void setTextFieldSearch(JTextField textFieldSearch) {
        this.textFieldSearch = textFieldSearch;
    }

    public JTextField getTextFieldSearchSalaryFrom() {
        return textFieldSearchSalaryFrom;
    }

    public void setTextFieldSearchSalaryFrom(JTextField textFieldSearchSalaryFrom) {
        this.textFieldSearchSalaryFrom = textFieldSearchSalaryFrom;
    }

    public JTextField getTextFieldSearchSalaryTo() {
        return textFieldSearchSalaryTo;
    }

    public void setTextFieldSearchSalaryTo(JTextField textFieldSearchSalaryTo) {
        this.textFieldSearchSalaryTo = textFieldSearchSalaryTo;
    }

    public JComboBox<String> getComboBoxSearch() {
        return comboBoxSearch;
    }

    public void setComboBoxSearch(JComboBox<String> comboBoxSearch) {
        this.comboBoxSearch = comboBoxSearch;
    }

    public JButton getBtnSearchAll() {
        return btnSearchAll;
    }

    public void setBtnSearchAll(JButton btnSearchAll) {
        this.btnSearchAll = btnSearchAll;
    }

    public JButton getBtnExportExcel() {
        return btnExportExcel;
    }

    public void setBtnExportExcel(JButton btnExportExcel) {
        this.btnExportExcel = btnExportExcel;
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
    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

}
