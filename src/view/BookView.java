package view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BookView extends JFrame {
    private JTextField textFieldBookId, textFieldCategory, textFieldBookName, textFieldAuthor, textFieldYearPublished, textFieldQuantity, textFieldPrice;
    private JButton btnAdd, btnChange, btnDelete, btnReset, btnSaveFile, btnSearch;
    private JTable table;
    private DefaultTableModel tableModel;

    public BookView() {
        setLayout(new BorderLayout());
        setBackground(new Color(157, 239, 227));

        add(initTitlePanel(), BorderLayout.NORTH);
        add(initFormPanel(), BorderLayout.WEST);
        add(initButtonPanel(), BorderLayout.CENTER);
        add(initSearchPanel(), BorderLayout.SOUTH);
        add(initTablePanel(), BorderLayout.EAST);

        setVisible(true);
    }

    /**
     * Tạo tiêu đề
     */
    private JPanel initTitlePanel() {
        JPanel panel = new JPanel();
        JLabel labelTitle = new JLabel("QUẢN LÍ SÁCH");
        labelTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        panel.add(labelTitle);
        return panel;
    }

    /**
     * Form nhập dữ liệu
     */
    private JPanel initFormPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2, 5, 5));

        Font font = new Font("Tahoma", Font.BOLD, 15);
        Font font1 = new Font("Tahoma", Font.PLAIN, 15);

        panel.add(createLabel("Mã sách", font));
        textFieldBookId = createTextField(font1);
        panel.add(textFieldBookId);

        panel.add(createLabel("Thể loại", font));
        textFieldCategory = createTextField(font1);
        panel.add(textFieldCategory);

        panel.add(createLabel("Tên sách", font));
        textFieldBookName = createTextField(font1);
        panel.add(textFieldBookName);

        panel.add(createLabel("Tác giả", font));
        textFieldAuthor = createTextField(font1);
        panel.add(textFieldAuthor);

        panel.add(createLabel("Năm xuất bản", font));
        textFieldYearPublished = createTextField(font1);
        panel.add(textFieldYearPublished);

        panel.add(createLabel("Số lượng", font));
        textFieldQuantity = createTextField(font1);
        panel.add(textFieldQuantity);

        panel.add(createLabel("Giá", font));
        textFieldPrice = createTextField(font1);
        panel.add(textFieldPrice);

        return panel;
    }

    /**
     * Tạo panel chứa các button chức năng
     */
    private JPanel initButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 3, 5, 5));

        btnAdd = createButton("Thêm", new Color(14, 110, 166));
        panel.add(btnAdd);

        btnChange = createButton("Sửa", new Color(147, 32, 204));
        panel.add(btnChange);

        btnDelete = createButton("Xóa", new Color(246, 4, 60));
        panel.add(btnDelete);

        btnReset = createButton("Làm mới", new Color(222, 99, 1));
        panel.add(btnReset);

        btnSaveFile = createButton("Xuất file", new Color(32, 204, 35));
        panel.add(btnSaveFile);

        return panel;
    }

    /**
     * Tạo panel tìm kiếm
     */
    private JPanel initSearchPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2, 5, 5));

        textFieldBookId = createTextField(new Font("Tahoma", Font.PLAIN, 14));
        panel.add(textFieldBookId);

        btnSearch = createButton("Tìm Kiếm", new Color(38, 55, 114));
        panel.add(btnSearch);

        return panel;
    }

    /**
     * Tạo panel chứa bảng dữ liệu
     */
    private JPanel initTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] columnNames = {"Mã sách", "Thể loại", "Tên sách", "Tác giả", "Năm xuất bản", "Số lượng", "Giá"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);

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
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Hỗ trợ scale ảnh
     */
    private ImageIcon scaleImage(String path, int width, int height) {
        try {
            ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource(path));
            Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception e) {
            System.err.println("No Image: " + path);
            return new ImageIcon(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB));
        }
    }

    /**
     * Hỗ trợ tạo JLabel
     */
    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        return label;
    }

    /**
     * Hỗ trợ tạo JTextField
     */
    private JTextField createTextField(Font font) {
        JTextField textField = new JTextField();
        textField.setFont(font);
        return textField;
    }

    /**
     * Hỗ trợ tạo JButton
     */
    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Tahoma", Font.BOLD, 15));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        return button;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Book Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(new BookView());
        frame.setVisible(true);
    }
}
