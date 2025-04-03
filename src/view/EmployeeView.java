package view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.image.BufferedImage;

public class EmployeeView extends JPanel {

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

        // Mã nhân viên
        JLabel labelEmployeeId = new JLabel("Mã Nhân Viên:");
        labelEmployeeId.setFont(font);
        labelEmployeeId.setBounds(10, 10, 120, 20);
        inputPanel.add(labelEmployeeId);

        JTextField textFieldEmployeeId = new JTextField();
        textFieldEmployeeId.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textFieldEmployeeId.setBounds(130, 10, 150, 20);
        inputPanel.add(textFieldEmployeeId);

        // Họ
        JLabel labelLastName = new JLabel("Họ:");
        labelLastName.setFont(font);
        labelLastName.setBounds(10, 40, 120, 20);
        inputPanel.add(labelLastName);

        JTextField textFieldLastName = new JTextField();
        textFieldLastName.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textFieldLastName.setBounds(130, 40, 150, 20);
        inputPanel.add(textFieldLastName);

        // Tên
        JLabel labelFirstName = new JLabel("Tên:");
        labelFirstName.setFont(font);
        labelFirstName.setBounds(10, 70, 120, 20);
        inputPanel.add(labelFirstName);

        JTextField textFieldFirstName = new JTextField();
        textFieldFirstName.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textFieldFirstName.setBounds(130, 70, 150, 20);
        inputPanel.add(textFieldFirstName);

        // Số điện thoại
        JLabel labelPhone = new JLabel("SĐT:");
        labelPhone.setFont(font);
        labelPhone.setBounds(10, 100, 120, 20);
        inputPanel.add(labelPhone);

        JTextField textFieldPhone = new JTextField();
        textFieldPhone.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textFieldPhone.setBounds(130, 100, 150, 20);
        inputPanel.add(textFieldPhone);

        // Email
        JLabel labelEmail = new JLabel("Email:");
        labelEmail.setFont(font);
        labelEmail.setBounds(10, 130, 120, 20);
        inputPanel.add(labelEmail);

        JTextField textFieldEmail = new JTextField();
        textFieldEmail.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textFieldEmail.setBounds(130, 130, 150, 20);
        inputPanel.add(textFieldEmail);

        // Chức vụ
        JLabel labelPosition = new JLabel("Chức Vụ:");
        labelPosition.setFont(font);
        labelPosition.setBounds(10, 160, 120, 20);
        inputPanel.add(labelPosition);

        JTextField textFieldPosition = new JTextField();
        textFieldPosition.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textFieldPosition.setBounds(130, 160, 150, 20);
        inputPanel.add(textFieldPosition);

        // Lương
        JLabel labelSalary = new JLabel("Lương:");
        labelSalary.setFont(font);
        labelSalary.setBounds(10, 190, 120, 20);
        inputPanel.add(labelSalary);

        JTextField textFieldSalary = new JTextField();
        textFieldSalary.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textFieldSalary.setBounds(130, 190, 150, 20);
        inputPanel.add(textFieldSalary);

        // Vị trí (Giới tính)
        JLabel labelGender = new JLabel("Giới tính");
        labelGender.setFont(font);
        labelGender.setBounds(10, 220, 70, 20);
        inputPanel.add(labelGender);

        JRadioButton radioPositionNam = new JRadioButton("Nam");
        radioPositionNam.setFont(new Font("Tahoma", Font.PLAIN, 15));
        radioPositionNam.setBounds(130, 220, 60, 20);
        radioPositionNam.setBackground(Color.WHITE);
        inputPanel.add(radioPositionNam);

        JRadioButton radioPositionNu = new JRadioButton("Nữ");
        radioPositionNu.setFont(new Font("Tahoma", Font.PLAIN, 15));
        radioPositionNu.setBounds(190, 220, 60, 20);
        radioPositionNu.setBackground(Color.WHITE);
        inputPanel.add(radioPositionNu);

        ButtonGroup positionGroup = new ButtonGroup();
        positionGroup.add(radioPositionNam);
        positionGroup.add(radioPositionNu);

        // Ngày sinh
        JLabel labelDob = new JLabel("Ngày Sinh:");
        labelDob.setFont(font);
        labelDob.setBounds(10, 250, 120, 20);
        inputPanel.add(labelDob);


        JTextField textFieldDob = new JTextField();
        textFieldDob.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textFieldDob.setBounds(130, 250, 150, 20);
        inputPanel.add(textFieldDob);

        JButton calendarButton = new JButton();
        calendarButton.setBounds(280,250, 20, 20);
        ImageIcon icon7=scaleImage("images/icon7.png",20,20);
        calendarButton.setIcon(icon7);
        inputPanel.add(calendarButton);

        // Các nút chức năng
        JButton btnAdd = new JButton("THÊM");
        btnAdd.setFont(font);
        btnAdd.setBackground(new Color(0, 153, 0)); // Màu xanh lá
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setBounds(30, 380, 100, 40);
        panelContent.add(btnAdd);

        JButton btnUpdate = new JButton("SỬA");
        btnUpdate.setFont(font);
        btnUpdate.setBackground(new Color(255, 153, 0)); // Màu cam
        btnUpdate.setForeground(Color.WHITE);
        btnUpdate.setBounds(170, 380, 100, 40);
        panelContent.add(btnUpdate);

        JButton btnDelete = new JButton("XÓA");
        btnDelete.setFont(font);
        btnDelete.setBackground(new Color(255, 0, 0)); // Màu đỏ
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setBounds(170, 440, 100, 40);
        panelContent.add(btnDelete);

        ImageIcon icon9=scaleImage("images/icon10.png",300,200);
        JLabel lblicon9 = new JLabel(icon9);
        lblicon9.setBounds(10, 540, 300, 200);
        panelContent.add(lblicon9);

        ImageIcon imageBackground = scaleImage("images/image.jpg",900,200);
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

        JComboBox<String> comboBoxSearch = new JComboBox<>(new String[]{"Mã NV", "Họ", "Tên","SĐT","Chức vụ"});
        comboBoxSearch.setFont(new Font("Tahoma", Font.PLAIN, 15));
        comboBoxSearch.setBounds(80, 40, 150, 20);
        searchPanel.add(comboBoxSearch);

        JTextField textFieldSearch = new JTextField();
        textFieldSearch.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textFieldSearch.setBounds(270, 40, 160, 20);
        searchPanel.add(textFieldSearch);


        JButton btnSearchAll = new JButton("Tìm Kiếm");
        btnSearchAll.setFont(font);
        btnSearchAll.setBackground(Color.BLACK);
        btnSearchAll.setForeground(Color.WHITE);
        btnSearchAll.setBounds(600, 40, 120, 25);
        searchPanel.add(btnSearchAll);

        JButton btnSearchSpecific = new JButton("Tất Cả");
        btnSearchSpecific.setFont(font);
        btnSearchSpecific.setBackground(new Color(0, 153, 0)); // Màu xanh lá
        btnSearchSpecific.setForeground(Color.WHITE);
        btnSearchSpecific.setBounds(730, 40, 120, 25);
        searchPanel.add(btnSearchSpecific);

        JLabel labelSearchSalaryFrom = new JLabel("Lương Từ ");
        labelSearchSalaryFrom.setFont(font);
        labelSearchSalaryFrom.setBounds(80, 70, 100, 20);
        searchPanel.add(labelSearchSalaryFrom);

        JTextField textFieldSearchSalaryFrom = new JTextField();
        textFieldSearchSalaryFrom.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textFieldSearchSalaryFrom.setBounds(160, 70, 100, 20);
        searchPanel.add(textFieldSearchSalaryFrom);

        JLabel labelSearchSalaryTo = new JLabel("đến");
        labelSearchSalaryTo.setFont(font);
        labelSearchSalaryTo.setBounds(270, 70, 100, 20);
        searchPanel.add(labelSearchSalaryTo);

        JTextField textFieldSearchSalaryTo = new JTextField();
        textFieldSearchSalaryTo.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textFieldSearchSalaryTo.setBounds(310, 70, 100, 20);
        searchPanel.add(textFieldSearchSalaryTo);

        JButton btnSearchSalary = new JButton("Tìm Kiếm");
        btnSearchSalary.setFont(font);
        btnSearchSalary.setBackground(Color.BLACK);
        btnSearchSalary.setForeground(Color.WHITE);
        btnSearchSalary.setBounds(600, 70, 120, 25);
        searchPanel.add(btnSearchSalary);

        // Bảng dữ liệu nhân viên
        String[] columnNames = {"Mã NV", "Họ", "Tên", "Số điện thoại", "Email", "Phái", "Chức vụ", "Lương", "Ngày sinh"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);

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

        // Dữ liệu mẫu cho bảng (dựa trên hình ảnh)
        Object[] row1 = {"MT", "Nguyen Duc Minh", "Trung", "0708240367", "minhtrung4376@", "Nam", "Tổng Giám Đốc", "1.0E7", "2001-10-01"};
        Object[] row2 = {"PK", "Pham Thi", "Khue", "0113114115", "", "Nữ", "", "1.0E7", "2021-04-25"};
        Object[] row3 = {"NV", "Trinh Thanh", "Tung", "0888549433", "", "Nam", "", "1.0E7", "2021-04-25"};
        Object[] row4 = {"NV", "Nguyen Tran Vu", "Tao", "0999999999", "", "Nam", "", "1.0E7", "2021-04-25"};
        tableModel.addRow(row1);
        tableModel.addRow(row2);
        tableModel.addRow(row3);
        tableModel.addRow(row4);

        JButton btnExportExcel = new JButton("Xuất Excel");
        btnExportExcel.setFont(font);
        btnExportExcel.setBackground(new Color(237, 16, 159)); // Màu xanh lam
        btnExportExcel.setForeground(Color.WHITE);
        btnExportExcel.setBounds(1130, 830, 120, 40);
        panelContent.add(btnExportExcel);

        return panelContent;
    }
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
        EmployeeView a = new EmployeeView();
        JPanel panel = a.initEmployeeView();
        JFrame app = new App();
        app.add(panel, BorderLayout.CENTER);
    }

}
