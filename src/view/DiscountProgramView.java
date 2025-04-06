package view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class DiscountProgramView {
    public JPanel initDiscountProgramView() {
        Font font = new Font("Tahoma", Font.BOLD, 15);
        Font font1 = new Font("Tahoma", Font.PLAIN, 15);
        JPanel panelContent = new JPanel();
        panelContent.setLayout(null);
        panelContent.setBackground(new Color(157, 239, 227));

        // Tiêu đề
        JLabel labelTitle = new JLabel("CHƯƠNG TRÌNH GIẢM GIÁ");
        labelTitle.setBounds(50, 10, 300, 30);
        labelTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        panelContent.add(labelTitle);

        // Panel đầu vào
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(null);
        inputPanel.setBackground(new Color(157, 239, 227));
        inputPanel.setBounds(0, 50, 1500, 350); // Tăng chiều cao để chứa bảng
        panelContent.add(inputPanel);

        // Bảng hiển thị trong inputPanel
        String[] columnNamesTop = {"Mã", "Khái Trương Cửa Hàng", "Khuyến Mãi 30%", "40%", "Ngày BD", "Ngày KT"};
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
        scrollPaneTop.setBounds(600, 10, 650, 100); // Đặt ở góc bên phải trong inputPanel
        inputPanel.add(scrollPaneTop);

        // Thêm một dòng mẫu vào bảng trên
        Object[] newRowTop = {"A02", "", "", "", "2021/05/01", "2021/05/08"};
        Object[] newRowTop2 = {"A04", "", "", "", "2021/05/01", "2021/05/02"};
        tableModelTop.addRow(newRowTop);
        tableModelTop.addRow(newRowTop2);

        // Mã giảm giá
        JLabel labelDiscountId = new JLabel("Mã giảm giá:");
        labelDiscountId.setFont(font);
        labelDiscountId.setBounds(50, 120, 100, 20); // Điều chỉnh y để nhường chỗ cho bảng
        inputPanel.add(labelDiscountId);

        JTextField textFieldDiscountId = new JTextField("A04");
        textFieldDiscountId.setFont(font1);
        textFieldDiscountId.setBounds(170, 120, 180, 20);
        inputPanel.add(textFieldDiscountId);

        // Tên chương trình
        JLabel labelProgramName = new JLabel("Tên chương trình:");
        labelProgramName.setFont(font);
        labelProgramName.setBounds(50, 160, 130, 20);
        inputPanel.add(labelProgramName);

        JTextField textFieldProgramName = new JTextField("Lê Quốc Tế Lào Đông");
        textFieldProgramName.setFont(font1);
        textFieldProgramName.setBounds(170, 160, 180, 20);
        inputPanel.add(textFieldProgramName);

        // Loại chương trình
        JLabel labelProgramType = new JLabel("Loại chương trình:");
        labelProgramType.setFont(font);
        labelProgramType.setBounds(50, 200, 130, 20);
        inputPanel.add(labelProgramType);

        JTextField textFieldProgramType = new JTextField("Khuyến Mãi");
        textFieldProgramType.setFont(font1);
        textFieldProgramType.setBounds(170, 200, 180, 20);
        inputPanel.add(textFieldProgramType);

        // Ngày bắt đầu
        JLabel labelStartDate = new JLabel("Ngày bắt đầu:");
        labelStartDate.setFont(font);
        labelStartDate.setBounds(50, 240, 100, 20);
        inputPanel.add(labelStartDate);

        JTextField textFieldStartDate = new JTextField("2021/05/01");
        textFieldStartDate.setFont(font1);
        textFieldStartDate.setBounds(170, 240, 180, 20);
        inputPanel.add(textFieldStartDate);

        // Ngày kết thúc
        JLabel labelEndDate = new JLabel("Ngày kết thúc:");
        labelEndDate.setFont(font);
        labelEndDate.setBounds(50, 280, 100, 20);
        inputPanel.add(labelEndDate);

        JTextField textFieldEndDate = new JTextField("2021/05/08");
        textFieldEndDate.setFont(font1);
        textFieldEndDate.setBounds(170, 280, 180, 20);
        inputPanel.add(textFieldEndDate);

        // Nút Thêm, Sửa, Xóa, Tắt Cả
        JButton btnAdd = new JButton("THÊM");
        btnAdd.setFont(font);
        btnAdd.setBackground(new Color(32, 204, 35)); // Màu xanh lá
        btnAdd.setForeground(new Color(255, 255, 255));
        btnAdd.setHorizontalAlignment(JButton.CENTER);
        btnAdd.setBounds(450, 120, 100, 30); // Điều chỉnh y để nhường chỗ cho bảng
        inputPanel.add(btnAdd);

        JButton btnEdit = new JButton("SỬA");
        btnEdit.setFont(font);
        btnEdit.setBackground(new Color(0, 0, 255)); // Màu xanh dương
        btnEdit.setForeground(new Color(255, 255, 255));
        btnEdit.setHorizontalAlignment(JButton.CENTER);
        btnEdit.setBounds(450, 180, 100, 30);
        inputPanel.add(btnEdit);

        JButton btnDelete = new JButton("XÓA");
        btnDelete.setFont(font);
        btnDelete.setBackground(new Color(255, 0, 0)); // Màu đỏ
        btnDelete.setForeground(new Color(255, 255, 255));
        btnDelete.setHorizontalAlignment(JButton.CENTER);
        btnDelete.setBounds(450, 240, 100, 30);
        inputPanel.add(btnDelete);

        JButton btnDisableAll = new JButton("TẮT CẢ");
        btnDisableAll.setFont(font);
        btnDisableAll.setBackground(new Color(0, 0, 0)); // Màu đen
        btnDisableAll.setForeground(new Color(255, 255, 255));
        btnDisableAll.setHorizontalAlignment(JButton.CENTER);
        btnDisableAll.setBounds(450, 300, 100, 30);
        inputPanel.add(btnDisableAll);

        // Panel tìm kiếm
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(null);
        searchPanel.setBackground(new Color(157, 239, 227));
        searchPanel.setBounds(0, 410, 1500, 80); // Đặt ngay dưới inputPanel
        panelContent.add(searchPanel);

        JLabel labelSearch = new JLabel("Tìm kiếm");
        labelSearch.setFont(new Font("Tahoma", Font.BOLD, 17));
        labelSearch.setBounds(50, 10, 500, 20);
        searchPanel.add(labelSearch);

        JLabel labelSearchStartDate = new JLabel("Ngày bắt đầu:");
        labelSearchStartDate.setFont(font);
        labelSearchStartDate.setBounds(170, 40, 110, 20);
        searchPanel.add(labelSearchStartDate);

        JTextField textFieldSearchStartDate = new JTextField();
        textFieldSearchStartDate.setFont(font1);
        textFieldSearchStartDate.setBounds(280, 40, 120, 20);
        searchPanel.add(textFieldSearchStartDate);

        JLabel labelSearchEndDate = new JLabel("Ngày kết thúc:");
        labelSearchEndDate.setFont(font);
        labelSearchEndDate.setBounds(420, 40, 110, 20);
        searchPanel.add(labelSearchEndDate);

        JTextField textFieldSearchEndDate = new JTextField();
        textFieldSearchEndDate.setFont(font1);
        textFieldSearchEndDate.setBounds(530, 40, 120, 20);
        searchPanel.add(textFieldSearchEndDate);

        // Panel chi tiết giảm giá
        JPanel discountDetailsPanel = new JPanel();
        discountDetailsPanel.setLayout(null);
        discountDetailsPanel.setBackground(new Color(157, 239, 227));
        discountDetailsPanel.setBounds(0, 500, 1500, 250); // Đặt ngay dưới searchPanel
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
        scrollPane.setBounds(50, 40, 1200, 150);
        discountDetailsPanel.add(scrollPane);

        // Thêm một dòng mẫu vào bảng
        Object[] newRow = {"A04", "15.0", "KD04"};
        tableModel.addRow(newRow);

        // Mã sách (phần chi tiết)
        JLabel labelBookId = new JLabel("Mã sách:");
        labelBookId.setFont(font);
        labelBookId.setBounds(50, 200, 100, 20);
        discountDetailsPanel.add(labelBookId);

        JTextField textFieldBookId = new JTextField("KD04");
        textFieldBookId.setFont(font1);
        textFieldBookId.setBounds(170, 200, 180, 20);
        discountDetailsPanel.add(textFieldBookId);

        // % Giảm giá
        JLabel labelDiscountPercent = new JLabel("% GIẢM:");
        labelDiscountPercent.setFont(font);
        labelDiscountPercent.setBounds(400, 200, 100, 20);
        discountDetailsPanel.add(labelDiscountPercent);

        JTextField textFieldDiscountPercent = new JTextField();
        textFieldDiscountPercent.setFont(font1);
        textFieldDiscountPercent.setBounds(500, 200, 120, 20);
        discountDetailsPanel.add(textFieldDiscountPercent);

        // Nút Thêm, Xóa (phần chi tiết)
        JButton btnAddDetail = new JButton("THÊM");
        btnAddDetail.setFont(font);
        btnAddDetail.setBackground(new Color(32, 204, 35)); // Màu xanh lá
        btnAddDetail.setForeground(new Color(255, 255, 255));
        btnAddDetail.setHorizontalAlignment(JButton.CENTER);
        btnAddDetail.setBounds(650, 200, 100, 30);
        discountDetailsPanel.add(btnAddDetail);

        JButton btnDeleteDetail = new JButton("XÓA");
        btnDeleteDetail.setFont(font);
        btnDeleteDetail.setBackground(new Color(255, 0, 0)); // Màu đỏ
        btnDeleteDetail.setForeground(new Color(255, 255, 255));
        btnDeleteDetail.setHorizontalAlignment(JButton.CENTER);
        btnDeleteDetail.setBounds(770, 200, 100, 30);
        discountDetailsPanel.add(btnDeleteDetail);

        return panelContent;
    }

    public static void main(String[] args) {
        DiscountProgramView a = new DiscountProgramView();
        JPanel panel = a.initDiscountProgramView();
        JFrame app = new App();
        app.add(panel, BorderLayout.CENTER);
    }
}
