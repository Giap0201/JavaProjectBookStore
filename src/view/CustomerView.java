package view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.image.BufferedImage;

public class CustomerView extends JPanel {

    public JPanel initCustomerView() {
        Font font = new Font("Tahoma", Font.BOLD, 15);
        JPanel panelContent = new JPanel();
        panelContent.setLayout(null);
        panelContent.setBackground(new Color(157, 239, 227));

        // Tiêu đề của panel
        JLabel labelTitle = new JLabel("THÔNG TIN KHÁCH HÀNG");
        labelTitle.setBounds(500, 10, 300, 30);
        labelTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        panelContent.add(labelTitle);

        // Nhãn hiển thị tổng số khách hàng
        JLabel labelTotalCustomers = new JLabel("Số lượng khách hàng: ");
        labelTotalCustomers.setFont(font);
        labelTotalCustomers.setBounds(50, 50, 500, 20);
        panelContent.add(labelTotalCustomers);

        JTextField textFieldTotalCustomers = new JTextField("0");
        textFieldTotalCustomers.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textFieldTotalCustomers.setBounds(250, 50, 50, 20);
        //textFieldTotalCustomers.setEditable(false); // Không cho phép chỉnh sửa
        panelContent.add(textFieldTotalCustomers);

        // Bảng dữ liệu khách hàng
        String[] columnNames = {"Mã KH", "Họ", "Phái", "Tên", "Số ĐT", "Email", "Tổng chi tiêu", "Ngày lập thẻ","Ghi chú"};
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
        scrollPane.setBounds(50, 90, 1200, 350);
        panelContent.add(scrollPane);

        // Dữ liệu mẫu cho bảng (dựa trên hình ảnh)
        Object[] row1 = {"K01", "Nguyen Tran V", "Phu", "Tên", "0908301831", "varvus12@gmail.com", "1000", "2001-3-18","hehe"};
        tableModel.addRow(row1);

        // Nút xuất và nhập Excel (giống trong hình ảnh)
        JButton btnExportExcel = new JButton("Xuất Excel");
        btnExportExcel.setFont(font);
        btnExportExcel.setBackground(new Color(255, 204, 102)); // Màu cam nhạt
        btnExportExcel.setForeground(Color.BLACK);
        btnExportExcel.setBounds(1100, 50, 120, 30);
        panelContent.add(btnExportExcel);

        JButton btnImportExcel = new JButton("Nhập Excel");
        btnImportExcel.setFont(font);
        btnImportExcel.setBackground(new Color(102, 204, 102)); // Màu xanh nhạt
        btnImportExcel.setForeground(Color.BLACK);
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
        labelInputTitle.setBounds(50, 10, 200, 20);
        inputPanel.add(labelInputTitle);

        // Mã khách hàng
        JLabel labelCustomerId = new JLabel("Mã KH");
        labelCustomerId.setFont(font);
        labelCustomerId.setBounds(50, 40, 70, 20);
        inputPanel.add(labelCustomerId);

        JTextField textFieldCustomerId = new JTextField();
        textFieldCustomerId.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textFieldCustomerId.setBounds(120, 40, 150, 20);
        inputPanel.add(textFieldCustomerId);

        // Vị trí (Giới tính)
        JLabel labelGender = new JLabel("Giới tính");
        labelGender.setFont(font);
        labelGender.setBounds(290, 40, 70, 20);
        inputPanel.add(labelGender);

        JRadioButton radioPositionNam = new JRadioButton("Nam");
        radioPositionNam.setFont(new Font("Tahoma", Font.PLAIN, 15));
        radioPositionNam.setBounds(360, 40, 60, 20);
        radioPositionNam.setBackground(Color.WHITE);
        inputPanel.add(radioPositionNam);

        JRadioButton radioPositionNu = new JRadioButton("Nữ");
        radioPositionNu.setFont(new Font("Tahoma", Font.PLAIN, 15));
        radioPositionNu.setBounds(420, 40, 60, 20);
        radioPositionNu.setBackground(Color.WHITE);
        inputPanel.add(radioPositionNu);

        ButtonGroup positionGroup = new ButtonGroup();
        positionGroup.add(radioPositionNam);
        positionGroup.add(radioPositionNu);

        // Họ tên
        JLabel labelFullName = new JLabel("Họ tên");
        labelFullName.setFont(font);
        labelFullName.setBounds(50, 70, 70, 20);
        inputPanel.add(labelFullName);

        JTextField textFieldFullName = new JTextField();
        textFieldFullName.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textFieldFullName.setBounds(120, 70, 150, 20);
        inputPanel.add(textFieldFullName);

        // Số điện thoại
        JLabel labelPhone = new JLabel("Số ĐT");
        labelPhone.setFont(font);
        labelPhone.setBounds(50, 100, 70, 20);
        inputPanel.add(labelPhone);

        JTextField textFieldPhone = new JTextField();
        textFieldPhone.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textFieldPhone.setBounds(120, 100, 150, 20);
        inputPanel.add(textFieldPhone);

        // Email
        JLabel labelEmail = new JLabel("Email");
        labelEmail.setFont(font);
        labelEmail.setBounds(50, 130, 70, 20);
        inputPanel.add(labelEmail);

        JTextField textFieldEmail = new JTextField();
        textFieldEmail.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textFieldEmail.setBounds(120, 130, 150, 20);
        inputPanel.add(textFieldEmail);

        // Ngày sinh
        JLabel labelDob = new JLabel("Ngày sinh");
        labelDob.setFont(font);
        labelDob.setBounds(290, 70, 80, 20);
        inputPanel.add(labelDob);


        JTextField textFieldDob = new JTextField();
        textFieldDob.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textFieldDob.setBounds(380, 70, 150, 20);
        inputPanel.add(textFieldDob);

        JButton calendarButton = new JButton();
        calendarButton.setBounds(540, 70, 20, 20);
        ImageIcon icon7=scaleImage("images/icon7.png",20,20);
        calendarButton.setIcon(icon7);
        inputPanel.add(calendarButton);

        // Các nút chức năng
        JButton btnAdd = new JButton("THÊM");
        btnAdd.setFont(font);
        btnAdd.setBackground(new Color(0, 153, 0)); // Màu xanh lá
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setBounds(50, 180, 100, 40);
        inputPanel.add(btnAdd);

        JButton btnDelete = new JButton("XÓA");
        btnDelete.setFont(font);
        btnDelete.setBackground(new Color(255, 0, 0)); // Màu đỏ
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setBounds(160, 180, 100, 40);
        inputPanel.add(btnDelete);

        JButton btnUpdate = new JButton("SỬA");
        btnUpdate.setFont(font);
        btnUpdate.setBackground(new Color(128, 128, 128)); // Màu xám
        btnUpdate.setForeground(Color.WHITE);
        btnUpdate.setBounds(270, 180, 100, 40);
        inputPanel.add(btnUpdate);

        JButton btnReset = new JButton("LÀM MỚI");
        btnReset.setFont(font);
        btnReset.setBackground(new Color(255, 153, 0)); // Màu cam
        btnReset.setForeground(Color.WHITE);
        btnReset.setBounds(380, 180, 150, 40);
        inputPanel.add(btnReset);

        // Phần tìm kiếm
        JLabel labelSearchTitle = new JLabel("TÌM KIẾM");
        labelSearchTitle.setFont(new Font("Tahoma", Font.BOLD, 17));
        labelSearchTitle.setBounds(720, 10, 200, 20);
        inputPanel.add(labelSearchTitle);

        JLabel labelSearchCustomerId = new JLabel("Tìm Kiếm Theo");
        labelSearchCustomerId.setFont(font);
        labelSearchCustomerId.setBounds(720, 40, 120, 20);
        inputPanel.add(labelSearchCustomerId);

        String[] check = {"Mã KH", "Họ tên KH", "Tên KH", "Số ĐT", "Email"};
        JComboBox<String> checkcombobox = new JComboBox<>(check);
        checkcombobox.setFont(new Font("Tahoma", Font.PLAIN, 15));
        checkcombobox.setBounds(850, 40, 120, 30);
        inputPanel.add(checkcombobox);


        // Vị trí (Giới tính)
        JLabel labelGenderSearch = new JLabel("Giới tính");
        labelGenderSearch.setFont(font);
        labelGenderSearch.setBounds(720, 70, 70, 20);
        inputPanel.add(labelGenderSearch);

        JRadioButton radioNamSearch = new JRadioButton("Nam");
        radioNamSearch.setFont(new Font("Tahoma", Font.PLAIN, 15));
        radioNamSearch.setBounds(850, 70, 60, 20);
        radioNamSearch.setBackground(Color.WHITE);
        inputPanel.add(radioNamSearch);

        JRadioButton radioNuSearch = new JRadioButton("Nữ");
        radioNuSearch.setFont(new Font("Tahoma", Font.PLAIN, 15));
        radioNuSearch.setBounds(910, 70, 60, 20);
        radioNuSearch.setBackground(Color.WHITE);
        inputPanel.add(radioNuSearch);

        ButtonGroup GroupSearch = new ButtonGroup();
        GroupSearch.add(radioNamSearch);
        GroupSearch.add(radioNuSearch);

        JTextField textFieldSearch = new JTextField();
        textFieldSearch.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textFieldSearch.setBounds(720, 100, 260, 40);
        inputPanel.add(textFieldSearch);

        JButton btnSearch = new JButton("Tìm kiếm");
        btnSearch.setFont(font);
        btnSearch.setBackground(new Color(102, 204, 102)); // Màu xanh nhạt
        btnSearch.setForeground(Color.BLACK);
        btnSearch.setBounds(1000, 100, 150, 40);
        inputPanel.add(btnSearch);

        ImageIcon icon8 = scaleImage("images/icon8.png",400,200);
        JLabel lblIcon = new JLabel(icon8);
        lblIcon.setBounds(700,130,400,250);
        inputPanel.add(lblIcon);

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
        CustomerView a = new CustomerView();
        JPanel panel = a.initCustomerView();
        JFrame app = new App();
        app.add(panel, BorderLayout.CENTER);
    }


}
