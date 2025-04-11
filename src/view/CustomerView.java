package view;

import controller.CustomerController;
import model.Customers;
import utils.CommonView;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionListener;

public class CustomerView extends JPanel {

    private JTextField textFieldTotalCustomers;
    private DefaultTableModel tableModel;
    private JTable table;
    private JButton btnExportExcel;
    private JButton btnImportExcel;
    private JTextField textFieldCustomerId = new JTextField();
    private JRadioButton radioPositionNam, radioPositionNu, radioNamSearch, radioNuSearch;
    private JTextField textFieldPhone;
    private JTextField textFieldEmail, textFieldDob;
    private JButton calendarButton;
    private JButton btnAdd= CommonView.createButton("THÊM", new Color(0, 153, 0));
    private JButton btnUpdate = CommonView.createButton("SỬA", new Color(128, 128, 128));
    private JButton btnDelete = CommonView.createButton("XÓA", new Color(255, 0, 0));
    private JButton btnReset = CommonView.createButton("LÀM MỚI", new Color(255, 153, 0));
    private JButton btnSearch;
    private JComboBox<String> checkcombobox;
    private JTextField textFieldSearch;
    private JTextField textFieldLastName;
    private JTextField textFieldFirstName;
    private JTextField textFieldNote;


    public JPanel initCustomerView() {
        Font font = new Font("Tahoma", Font.BOLD, 15);
        JPanel panelContent = new JPanel();
        panelContent.setLayout(null);
        panelContent.setBackground(new Color(157, 239, 227));



        // Tiêu đề của panel
        JLabel labelTitle = new JLabel("THÔNG TIN KHÁCH HÀNG");
        labelTitle.setBounds(500, 10, 300, 30);
        labelTitle.setFont(new Font("Tahoma", Font.BOLD, 30));
        panelContent.add(labelTitle);

        // Nhãn hiển thị tổng số khách hàng
        JLabel labelTotalCustomers = new JLabel("Số lượng khách hàng: ");
        labelTotalCustomers.setFont(font);
        labelTotalCustomers.setBounds(50, 50, 500, 30);
        panelContent.add(labelTotalCustomers);

        textFieldTotalCustomers = new JTextField("0");
        textFieldTotalCustomers.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textFieldTotalCustomers.setBounds(250, 50, 50, 30);
        //textFieldTotalCustomers.setEditable(false); // Không cho phép chỉnh sửa
        panelContent.add(textFieldTotalCustomers);

        // Bảng dữ liệu khách hàng
        String[] columnNames = {"Mã KH", "Họ", "Tên", "Giới tính", "Số ĐT", "Email","Ngày sinh", "Tổng chi tiêu", "Ngày lập thẻ","Note"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);

        // Thiết lập font và chiều cao dòng cho bảng
        table.setFont(new Font("Tahoma", Font.PLAIN, 12));
        table.setRowHeight(20);

        CustomerController action = new CustomerController(this);

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
        scrollPane.setBounds(50, 90, 1200, 350);
        panelContent.add(scrollPane);


        // Nút xuất và nhập Excel (giống trong hình ảnh)
        btnExportExcel = CommonView.createButton("Xuất Excel", new Color(255, 204, 102));
        btnExportExcel.setBounds(1100, 50, 120, 30);
        panelContent.add(btnExportExcel);

        btnImportExcel = CommonView.createButton("Nhập Excel", new Color(102, 204, 102));
        btnImportExcel.setBounds(950, 50, 120, 30);
        panelContent.add(btnImportExcel);

        // Phần nhập thông tin khách hàng
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(null);
        inputPanel.setBackground(new Color(255, 255, 255));
        inputPanel.setBounds(0, 500, 1500, 800);
        panelContent.add(inputPanel);

        JLabel labelInputTitle = new JLabel("NHẬP THÔNG TIN");
        labelInputTitle.setFont(new Font("Tahoma", Font.BOLD, 17));
        labelInputTitle.setBounds(50, 10, 200, 30);
        inputPanel.add(labelInputTitle);

        // Mã khách hàng
        JLabel labelCustomerId = new JLabel("Mã KH");
        labelCustomerId.setFont(font);
        labelCustomerId.setBounds(50, 40, 70, 30);
        inputPanel.add(labelCustomerId);


        textFieldCustomerId.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textFieldCustomerId.setBounds(120, 40, 150, 30);
        inputPanel.add(textFieldCustomerId);

        // Vị trí (Giới tính)
        JLabel labelGender = new JLabel("Giới tính");
        labelGender.setFont(font);
        labelGender.setBounds(290, 40, 70, 30);
        inputPanel.add(labelGender);

        radioPositionNam = new JRadioButton("Nam");
        radioPositionNam.setFont(new Font("Tahoma", Font.PLAIN, 15));
        radioPositionNam.setBounds(360, 40, 60, 30);
        radioPositionNam.setBackground(Color.WHITE);
        inputPanel.add(radioPositionNam);

        radioPositionNu = new JRadioButton("Nữ");
        radioPositionNu.setFont(new Font("Tahoma", Font.PLAIN, 15));
        radioPositionNu.setBounds(420, 40, 60, 30);
        radioPositionNu.setBackground(Color.WHITE);
        inputPanel.add(radioPositionNu);

        ButtonGroup positionGroup = new ButtonGroup();
        positionGroup.add(radioPositionNam);
        positionGroup.add(radioPositionNu);

        // Họ tên
        JLabel labelLastName = new JLabel("Họ");
        labelLastName.setFont(font);
        labelLastName.setBounds(50, 70, 70, 30);
        inputPanel.add(labelLastName);

        textFieldLastName = new JTextField();
        textFieldLastName.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textFieldLastName.setBounds(120, 70, 150, 30);
        inputPanel.add(textFieldLastName);

        JLabel labelFirstName = new JLabel("Tên");
        labelFirstName.setFont(font);
        labelFirstName.setBounds(50, 100, 70, 30);
        inputPanel.add(labelFirstName);

        textFieldFirstName = new JTextField();
        textFieldFirstName.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textFieldFirstName.setBounds(120, 100, 150, 30);
        inputPanel.add(textFieldFirstName);


        // Số điện thoại
        JLabel labelPhone = new JLabel("Số ĐT");
        labelPhone.setFont(font);
        labelPhone.setBounds(50, 130, 70, 30);
        inputPanel.add(labelPhone);

        textFieldPhone = new JTextField();
        textFieldPhone.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textFieldPhone.setBounds(120, 130, 150, 30);
        inputPanel.add(textFieldPhone);

        // Email
        JLabel labelEmail = new JLabel("Email");
        labelEmail.setFont(font);
        labelEmail.setBounds(290, 100, 70, 30);
        inputPanel.add(labelEmail);

        textFieldEmail = new JTextField();
        textFieldEmail.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textFieldEmail.setBounds(380, 100, 150, 30);
        inputPanel.add(textFieldEmail);


        // Ngày sinh
        JLabel labelDob = new JLabel("Ngày sinh");
        labelDob.setFont(font);
        labelDob.setBounds(290, 70, 80, 30);
        inputPanel.add(labelDob);


        textFieldDob = new JTextField();
        textFieldDob.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textFieldDob.setBounds(380, 70, 150, 30);
        inputPanel.add(textFieldDob);

        calendarButton = new JButton();
        calendarButton.setBounds(540, 70, 30, 30);
        ImageIcon icon7 = CommonView.scaleImage("images/icon7.png", 30, 30);
        calendarButton.setIcon(icon7);
        inputPanel.add(calendarButton);

        calendarButton.addActionListener(action);


        // Note
        JLabel labelNote = new JLabel("Note");
        labelNote.setFont(font);
        labelNote.setBounds(290, 130, 70, 30);
        inputPanel.add(labelNote);

        textFieldNote = new JTextField();
        textFieldNote.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textFieldNote.setBounds(380, 130, 200, 30);
        inputPanel.add(textFieldNote);

        // Các nút chức năng
        btnAdd.setBounds(50, 180, 100, 40);
        inputPanel.add(btnAdd);
        btnAdd.addActionListener(action);

        btnDelete.setBounds(160, 180, 100, 40);
        inputPanel.add(btnDelete);
        btnDelete.addActionListener(action);

        btnUpdate.setBounds(270, 180, 100, 40);
        inputPanel.add(btnUpdate);
        btnUpdate.addActionListener(action);


        btnReset.setBounds(380, 180, 150, 40);
        inputPanel.add(btnReset);
        btnReset.addActionListener(action);

        // Phần tìm kiếm
        JLabel labelSearchTitle = new JLabel("TÌM KIẾM");
        labelSearchTitle.setFont(new Font("Tahoma", Font.BOLD, 17));
        labelSearchTitle.setBounds(720, 10, 200, 30);
        inputPanel.add(labelSearchTitle);

        JLabel labelSearchCustomerId = new JLabel("Tìm Kiếm Theo");
        labelSearchCustomerId.setFont(font);
        labelSearchCustomerId.setBounds(720, 40, 120, 30);
        inputPanel.add(labelSearchCustomerId);

        String[] check = {"Mã KH", "Họ KH", "Tên KH", "Số ĐT", "Email"};
        checkcombobox = new JComboBox<>(check);
        checkcombobox.setFont(new Font("Tahoma", Font.PLAIN, 15));
        checkcombobox.setBounds(850, 40, 120, 30);
        inputPanel.add(checkcombobox);


        // Vị trí (Giới tính)
        JLabel labelGenderSearch = new JLabel("Giới tính");
        labelGenderSearch.setFont(font);
        labelGenderSearch.setBounds(720, 70, 70, 30);
        inputPanel.add(labelGenderSearch);

        radioNamSearch = new JRadioButton("Nam");
        radioNamSearch.setFont(new Font("Tahoma", Font.PLAIN, 15));
        radioNamSearch.setBounds(850, 70, 60, 30);
        radioNamSearch.setBackground(Color.WHITE);
        inputPanel.add(radioNamSearch);

        radioNuSearch = new JRadioButton("Nữ");
        radioNuSearch.setFont(new Font("Tahoma", Font.PLAIN, 15));
        radioNuSearch.setBounds(910, 70, 60, 30);
        radioNuSearch.setBackground(Color.WHITE);
        inputPanel.add(radioNuSearch);

        ButtonGroup GroupSearch = new ButtonGroup();
        GroupSearch.add(radioNamSearch);
        GroupSearch.add(radioNuSearch);

        textFieldSearch = new JTextField();
        textFieldSearch.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textFieldSearch.setBounds(720, 100, 260, 40);
        inputPanel.add(textFieldSearch);

        btnSearch = CommonView.createButton("Tìm kiếm", new Color(102, 204, 102));
        btnSearch.setBounds(1000, 100, 150, 40);
        inputPanel.add(btnSearch);
        btnSearch.addActionListener(action);

        ImageIcon icon8 = CommonView.scaleImage("images/icon8.png", 400, 200);
        JLabel lblIcon = new JLabel(icon8);
        lblIcon.setBounds(700, 130, 400, 250);
        inputPanel.add(lblIcon);

        return panelContent;

    }

    public JTextField getTextFieldTotalCustomers() {
        return textFieldTotalCustomers;
    }

    public void setTextFieldTotalCustomers(JTextField textFieldTotalCustomers) {
        this.textFieldTotalCustomers = textFieldTotalCustomers;
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

    public JButton getBtnExportExcel() {
        return btnExportExcel;
    }

    public void setBtnExportExcel(JButton btnExportExcel) {
        this.btnExportExcel = btnExportExcel;
    }

    public JButton getBtnImportExcel() {
        return btnImportExcel;
    }

    public void setBtnImportExcel(JButton btnImportExcel) {
        this.btnImportExcel = btnImportExcel;
    }

    public JTextField getTextFieldCustomerId() {
        return textFieldCustomerId;
    }

    public void setTextFieldCustomerId(JTextField textFieldCustomerId) {
        this.textFieldCustomerId = textFieldCustomerId;
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

    public JRadioButton getRadioNamSearch() {
        return radioNamSearch;
    }

    public void setRadioNamSearch(JRadioButton radioNamSearch) {
        this.radioNamSearch = radioNamSearch;
    }

    public JRadioButton getRadioNuSearch() {
        return radioNuSearch;
    }

    public void setRadioNuSearch(JRadioButton radioNuSearch) {
        this.radioNuSearch = radioNuSearch;
    }

    public JTextField gettextFieldLastName() {
        return textFieldLastName;
    }

    public void settextFieldLastName(JTextField textFieldLastName) {
        this.textFieldLastName = textFieldLastName;
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

    public JTextField getTextFieldDob() {
        return textFieldDob;
    }

    public void setTextFieldDob(JTextField textFieldDob) {
        this.textFieldDob = textFieldDob;
    }

    public JButton getCalendarButton() {
        return calendarButton;
    }

    public void setCalendarButton(JButton calendarButton) {
        this.calendarButton = calendarButton;
    }

    public JButton getBtnAdd() {
        return btnAdd;
    }

    public void setBtnAdd(JButton btnAdd) {
        this.btnAdd = btnAdd;
    }

    public JButton getBtnUpdate() {
        return btnUpdate;
    }

    public void setBtnUpdate(JButton btnUpdate) {
        this.btnUpdate = btnUpdate;
    }

    public JButton getBtnDelete() {
        return btnDelete;
    }

    public void setBtnDelete(JButton btnDelete) {
        this.btnDelete = btnDelete;
    }

    public JButton getBtnSearch() {
        return btnSearch;
    }

    public void setBtnSearch(JButton btnSearch) {
        this.btnSearch = btnSearch;
    }

    public JButton getBtnReset() {
        return btnReset;
    }

    public void setBtnReset(JButton btnReset) {
        this.btnReset = btnReset;
    }

    public JComboBox<String> getCheckcombobox() {
        return checkcombobox;
    }

    public void setCheckcombobox(JComboBox<String> checkcombobox) {
        this.checkcombobox = checkcombobox;
    }

    public JTextField getTextFieldSearch() {
        return textFieldSearch;
    }

    public void setTextFieldSearch(JTextField textFieldSearch) {
        this.textFieldSearch = textFieldSearch;
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

    public JTextField getTextFieldNote() {
        return textFieldNote;
    }

    public void setTextFieldNote(JTextField textFieldNote) {
        this.textFieldNote = textFieldNote;
    }

    public void clear() {
        JTextField[] fields = {
                textFieldCustomerId,
                textFieldLastName,
                textFieldFirstName,
                textFieldPhone,
                textFieldEmail,
                textFieldDob,
                textFieldNote
        };
        for (JTextField field : fields) {
            field.setText("");
        }
    }

    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

}