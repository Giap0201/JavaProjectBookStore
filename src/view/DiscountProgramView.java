package view;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.image.BufferedImage;

public class DiscountProgramView {
    public JPanel initDiscountProgramView() {
        Font font = new Font("Tahoma", Font.BOLD, 15);
        Font font1 = new Font("Tahoma", Font.PLAIN, 15);
        JPanel panelContent = new JPanel();
        panelContent.setLayout(null);
        panelContent.setBackground(new Color(157, 239, 227));

        // Tiêu đề
        JLabel labelTitle = new JLabel("CHƯƠNG TRÌNH GIẢM GIÁ");
        labelTitle.setBounds(500, 10, 300, 30);
        labelTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        panelContent.add(labelTitle);

        // Panel đầu vào
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(null);
        inputPanel.setBackground(new Color(157, 239, 227));
        inputPanel.setBounds(0, 50, 1500, 450); // Tăng chiều cao để chứa bảng
        panelContent.add(inputPanel);

        ImageIcon discount = scaleImage("images/discount2.png",300,150);
        JLabel lblDiscount = new JLabel(discount);
        lblDiscount.setBounds(80,0,discount.getIconWidth(),discount.getIconHeight());
        inputPanel.add(lblDiscount);

        // Bảng hiển thị trong inputPanel
        String[] columnNamesTop = {"Mã", "Chương trình", "Loại Khuyến mãi", "Phần trăm", "Ngày BD", "Ngày KT"};
        DefaultTableModel tableModelTop = new DefaultTableModel(columnNamesTop, 0);
        JTable tableTop = new JTable(tableModelTop);

        tableTop.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tableTop.setRowHeight(20);

        JTableHeader headerTop = tableTop.getTableHeader();
        headerTop.setFont(new Font("Tahoma", Font.BOLD, 14));
        headerTop.setBackground(Color.LIGHT_GRAY);
        headerTop.setForeground(Color.BLACK);

        DefaultTableCellRenderer centerRendererTop = new DefaultTableCellRenderer();
        centerRendererTop.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tableTop.getColumnCount(); i++) {
            tableTop.getColumnModel().getColumn(i).setCellRenderer(centerRendererTop);
        }

        JScrollPane scrollPaneTop = new JScrollPane(tableTop);
        scrollPaneTop.setBounds(450, 10, 800, 350); // Đặt ở góc bên phải trong inputPanel
        inputPanel.add(scrollPaneTop);

        // Thêm một dòng mẫu vào bảng trên
        Object[] newRowTop = {"A02", "", "", "", "2021/05/01", "2021/05/08"};
        Object[] newRowTop2 = {"A04", "", "", "", "2021/05/01", "2021/05/02"};
        tableModelTop.addRow(newRowTop);
        tableModelTop.addRow(newRowTop2);

        // Mã giảm giá
        JLabel labelDiscountId = new JLabel("Mã giảm giá:");
        labelDiscountId.setFont(font);
        labelDiscountId.setBounds(50, 170, 100, 30); // Điều chỉnh y để nhường chỗ cho bảng
        inputPanel.add(labelDiscountId);

        JTextField textFieldDiscountId = new JTextField("A04");
        textFieldDiscountId.setFont(font1);
        textFieldDiscountId.setBounds(190, 170, 180, 30);
        inputPanel.add(textFieldDiscountId);

        // Tên chương trình
        JLabel labelProgramName = new JLabel("Tên chương trình:");
        labelProgramName.setFont(font);
        labelProgramName.setBounds(50, 210, 150, 30);
        inputPanel.add(labelProgramName);

        JTextField textFieldProgramName = new JTextField("");
        textFieldProgramName.setFont(font1);
        textFieldProgramName.setBounds(190, 210, 180, 30);
        inputPanel.add(textFieldProgramName);

        // Loại chương trình
        JLabel labelProgramType = new JLabel("Loại chương trình:");
        labelProgramType.setFont(font);
        labelProgramType.setBounds(50, 250, 150, 30);
        inputPanel.add(labelProgramType);

        JTextField textFieldProgramType = new JTextField("");
        textFieldProgramType.setFont(font1);
        textFieldProgramType.setBounds(190, 250, 180, 30);
        inputPanel.add(textFieldProgramType);

        // Ngày bắt đầu
        JLabel labelStartDate = new JLabel("Ngày bắt đầu:");
        labelStartDate.setFont(font);
        labelStartDate.setBounds(50, 290, 150, 30);
        inputPanel.add(labelStartDate);

        JTextField textFieldStartDate = new JTextField("");
        textFieldStartDate.setFont(font1);
        textFieldStartDate.setBounds(190, 290, 180, 30);
        inputPanel.add(textFieldStartDate);

        JButton calendarButton = new JButton();
        calendarButton.setBounds(380,290, 30, 30);
        ImageIcon icon7=scaleImage("images/icon7.png",30,30);
        calendarButton.setIcon(icon7);
        inputPanel.add(calendarButton);

        // Ngày kết thúc
        JLabel labelEndDate = new JLabel("Ngày kết thúc:");
        labelEndDate.setFont(font);
        labelEndDate.setBounds(50, 330, 150, 30);
        inputPanel.add(labelEndDate);

        JTextField textFieldEndDate = new JTextField("");
        textFieldEndDate.setFont(font1);
        textFieldEndDate.setBounds(190, 330, 180, 30);
        inputPanel.add(textFieldEndDate);

        JButton endDateButton = new JButton();
        endDateButton.setBounds(380,330, 30, 30);
        ImageIcon endDateImage=scaleImage("images/icon7.png",30,30);
        endDateButton.setIcon(endDateImage);
        inputPanel.add(endDateButton);

        // Nút Thêm, Sửa, Xóa, Tắt Cả
        JButton btnAdd = new JButton("THÊM");
        btnAdd.setFont(font);
        btnAdd.setBackground(new Color(32, 204, 35)); // Màu xanh lá
        btnAdd.setForeground(new Color(255, 255, 255));
        btnAdd.setHorizontalAlignment(JButton.CENTER);
        btnAdd.setBounds(50, 380, 100, 40); // Điều chỉnh y để nhường chỗ cho bảng
        inputPanel.add(btnAdd);

        JButton btnEdit = new JButton("SỬA");
        btnEdit.setFont(font);
        btnEdit.setBackground(new Color(0, 0, 255)); // Màu xanh dương
        btnEdit.setForeground(new Color(255, 255, 255));
        btnEdit.setHorizontalAlignment(JButton.CENTER);
        btnEdit.setBounds(160, 380, 100, 40);
        inputPanel.add(btnEdit);

        JButton btnDelete = new JButton("XÓA");
        btnDelete.setFont(font);
        btnDelete.setBackground(new Color(255, 0, 0)); // Màu đỏ
        btnDelete.setForeground(new Color(255, 255, 255));
        btnDelete.setHorizontalAlignment(JButton.CENTER);
        btnDelete.setBounds(270, 380, 100, 40);
        inputPanel.add(btnDelete);

        JButton btnDisableAll = new JButton("TẤT CẢ");
        btnDisableAll.setFont(font);
        btnDisableAll.setBackground(new Color(0, 0, 0)); // Màu đen
        btnDisableAll.setForeground(new Color(255, 255, 255));
        btnDisableAll.setHorizontalAlignment(JButton.CENTER);
        btnDisableAll.setBounds(380, 380, 100, 40);
        inputPanel.add(btnDisableAll);

        // Panel tìm kiếm
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(null);
        searchPanel.setBackground(new Color(157, 239, 227));
        searchPanel.setBounds(50, 500, 900, 80); // Đặt ngay dưới inputPanel
        searchPanel.setBorder(BorderFactory.createLineBorder(new Color(228, 13, 13)));
        panelContent.add(searchPanel);

        JLabel labelSearch = new JLabel("Tìm kiếm");
        labelSearch.setFont(new Font("Tahoma", Font.BOLD, 24));
        labelSearch.setBounds(50, 10, 500, 20);
        searchPanel.add(labelSearch);

        JLabel labelSearchStartDate = new JLabel("Ngày bắt đầu:");
        labelSearchStartDate.setFont(font);
        labelSearchStartDate.setBounds(170, 40, 110, 30);
        searchPanel.add(labelSearchStartDate);

        JTextField textFieldSearchStartDate = new JTextField();
        textFieldSearchStartDate.setFont(font1);
        textFieldSearchStartDate.setBounds(280, 40, 130, 30);
        searchPanel.add(textFieldSearchStartDate);

        JButton startDateButtonSearch = new JButton();
        startDateButtonSearch.setBounds(420,40, 30, 30);
        ImageIcon startDateImageSearch=scaleImage("images/icon7.png",30,30);
        startDateButtonSearch.setIcon(startDateImageSearch);
        searchPanel.add(startDateButtonSearch);

        JLabel labelSearchEndDate = new JLabel("Ngày kết thúc:");
        labelSearchEndDate.setFont(font);
        labelSearchEndDate.setBounds(500, 40, 150, 30);
        searchPanel.add(labelSearchEndDate);

        JTextField textFieldSearchEndDate = new JTextField();
        textFieldSearchEndDate.setFont(font1);
        textFieldSearchEndDate.setBounds(630, 40, 130, 30);
        searchPanel.add(textFieldSearchEndDate);

        JButton endDateButtonSearch = new JButton();
        endDateButtonSearch.setBounds(770,40, 30, 30);
        ImageIcon endDateImageSearch=scaleImage("images/icon7.png",30,30);
        endDateButtonSearch.setIcon(endDateImageSearch);
        searchPanel.add(endDateButtonSearch);

        JButton dateSearch = new JButton("TÌM KIẾM");
        dateSearch.setFont(font);
        dateSearch.setBackground(new Color(0, 0, 0)); // Màu đen
        dateSearch.setForeground(new Color(255, 255, 255));
        dateSearch.setHorizontalAlignment(JButton.CENTER);
        dateSearch.setBounds(950, 40, 130, 30);
        searchPanel.add(dateSearch);

        // Panel chi tiết giảm giá
        JPanel discountDetailsPanel = new JPanel();
        discountDetailsPanel.setLayout(null);
        discountDetailsPanel.setBackground(new Color(157, 239, 227));
        discountDetailsPanel.setBounds(0, 580, 1500, 250); // Đặt ngay dưới searchPanel
        panelContent.add(discountDetailsPanel);

        JLabel labelDiscountDetails = new JLabel("CHI TIẾT CHƯƠNG TRÌNH GIẢM GIÁ");
        labelDiscountDetails.setFont(new Font("Tahoma", Font.BOLD, 17));
        labelDiscountDetails.setBounds(50, 10, 500, 20);
        discountDetailsPanel.add(labelDiscountDetails);

        // Bảng chi tiết chương trình giảm giá
        String[] columnNames = {"Mã GG", "% Giảm giá", "Mã sách"};
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
        scrollPane.setBounds(50, 40, 500, 200);
        discountDetailsPanel.add(scrollPane);

        // Thêm một dòng mẫu vào bảng
        Object[] newRow = {"A04", "15.0", "KD04"};
        tableModel.addRow(newRow);

        // Mã sách (phần chi tiết)
        JLabel labelBookId = new JLabel("Mã sách:");
        labelBookId.setFont(new Font("Tahoma", Font.BOLD, 18));
        labelBookId.setBounds(600, 40, 150, 40);
        discountDetailsPanel.add(labelBookId);

        JTextField textFieldBookId = new JTextField("KD04");
        textFieldBookId.setFont(font1);
        textFieldBookId.setBounds(700, 40, 200, 40);
        discountDetailsPanel.add(textFieldBookId);

        // % Giảm giá
        JLabel labelDiscountPercent = new JLabel("% GIẢM:");
        labelDiscountPercent.setFont(font);
        labelDiscountPercent.setBounds(600, 100, 150, 40);
        discountDetailsPanel.add(labelDiscountPercent);

        JTextField textFieldDiscountPercent = new JTextField();
        textFieldDiscountPercent.setFont(font1);
        textFieldDiscountPercent.setBounds(700, 100, 200, 40);
        discountDetailsPanel.add(textFieldDiscountPercent);

        // Nút Thêm, Xóa (phần chi tiết)
        JButton btnAddDetail = new JButton("THÊM");
        btnAddDetail.setFont(font);
        btnAddDetail.setBackground(new Color(32, 204, 35)); // Màu xanh lá
        btnAddDetail.setForeground(new Color(255, 255, 255));
        btnAddDetail.setHorizontalAlignment(JButton.CENTER);
        btnAddDetail.setBounds(650, 160, 100, 40);
        discountDetailsPanel.add(btnAddDetail);

        JButton btnDeleteDetail = new JButton("XÓA");
        btnDeleteDetail.setFont(font);
        btnDeleteDetail.setBackground(new Color(255, 0, 0)); // Màu đỏ
        btnDeleteDetail.setForeground(new Color(255, 255, 255));
        btnDeleteDetail.setHorizontalAlignment(JButton.CENTER);
        btnDeleteDetail.setBounds(770, 160, 100, 40);
        discountDetailsPanel.add(btnDeleteDetail);

        ImageIcon discount2 = scaleImage("images/discount.png",300,200);
        JLabel lblDiscount2 = new JLabel(discount2);
        lblDiscount2.setBounds(930,40,discount2.getIconWidth(),discount2.getIconHeight());
        discountDetailsPanel.add(lblDiscount2);

        return panelContent;
    }

    /**
     * Hàm hỗ trợ scale ảnh (tái sử dụng từ code của bạn)
     */
    private ImageIcon scaleImage(String path, int width, int height) {
        try {
            ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource(path));
            Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception e) {
            System.err.println("Không tìm thấy ảnh: " + path);
            return new ImageIcon(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)); // Trả về ảnh trống
        }
    }

    public static void main(String[] args) {
        DiscountProgramView a = new DiscountProgramView();
        JPanel panel = a.initDiscountProgramView();
        JFrame app = new App();
        app.add(panel, BorderLayout.CENTER);
    }
}
