package view;

import controller.BookController;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BookView extends JPanel {

    private JTextField textFieldBookId, textFieldBookId1;
    private JTextField textFieldCategory, textFieldCategory1;
    private JTextField textFieldBookName, textFieldBookName1;
    private JTextField textFieldAuthor, textFieldAuthor1;
    private JTextField textFieldYearPublished, textFieldYearPublished1;
    private JTextField textFieldQuantity, textFieldQuantity1;
    private JTextField textFieldPrice, textFieldPrice1;
    private JButton btnAdd, btnChange, btnDelete, btnReset, btnSaveFile, btnView, btnSearch;

    public JPanel initBookView() {
        Font font = new Font("Tahoma", Font.BOLD, 15);
        JPanel panelContent = new JPanel();
        panelContent.setLayout(null);
        panelContent.setBackground(new Color(157, 239, 227));

        JLabel labeltitle = new JLabel("QUẢN LÍ SÁCH");
        labeltitle.setBounds(600, 10, 250, 30);
        labeltitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        panelContent.add(labeltitle);

        JLabel labelBookId = new JLabel("Mã sách");
        labelBookId.setFont(font);
        labelBookId.setBounds(50, 50, 100, 20);
        panelContent.add(labelBookId);

        Font font1 = new Font("Tahoma", Font.PLAIN, 15);
        textFieldBookId = new JTextField();
        textFieldBookId.setFont(font1);
        textFieldBookId.setBounds(170, 50, 180, 20);
        panelContent.add(textFieldBookId);

        JLabel labelCategogy = new JLabel("Thể loại");
        labelCategogy.setFont(font);
        labelCategogy.setBounds(50, 90, 100, 20);
        panelContent.add(labelCategogy);

        textFieldCategory = new JTextField();
        textFieldCategory.setFont(font1);
        textFieldCategory.setBounds(170, 90, 180, 20);
        panelContent.add(textFieldCategory);

        JLabel labelBookName = new JLabel("Tên sách");
        labelBookName.setFont(font);
        labelBookName.setBounds(50, 130, 100, 20);
        panelContent.add(labelBookName);

        textFieldBookName = new JTextField();
        textFieldBookName.setFont(font1);
        textFieldBookName.setBounds(170, 130, 180, 20);
        panelContent.add(textFieldBookName);

        JLabel labelAuthor = new JLabel("Tác giả");
        labelAuthor.setFont(font);
        labelAuthor.setBounds(50, 170, 100, 20);
        panelContent.add(labelAuthor);

        textFieldAuthor = new JTextField();
        textFieldAuthor.setFont(font1);
        textFieldAuthor.setBounds(170, 170, 180, 20);
        panelContent.add(textFieldAuthor);

        JLabel labelYearPublished = new JLabel("Năm xuất bản");
        labelYearPublished.setFont(font);
        labelYearPublished.setBounds(50, 210, 130, 20);
        panelContent.add(labelYearPublished);

        textFieldYearPublished = new JTextField();
        textFieldYearPublished.setFont(font1);
        textFieldYearPublished.setBounds(170, 210, 180, 20);
        panelContent.add(textFieldYearPublished);

        JLabel labelQuantity = new JLabel("Số lượng");
        labelQuantity.setFont(font);
        labelQuantity.setBounds(50, 250, 100, 20);
        panelContent.add(labelQuantity);

        textFieldQuantity = new JTextField();
        textFieldQuantity.setFont(font1);
        textFieldQuantity.setBounds(170, 250, 180, 20);
        panelContent.add(textFieldQuantity);

        JLabel labelPrice = new JLabel("Giá");
        labelPrice.setFont(font);
        labelPrice.setBounds(50, 290, 100, 20);
        panelContent.add(labelPrice);

        textFieldPrice = new JTextField();
        textFieldPrice.setFont(font1);
        textFieldPrice.setBounds(170, 290, 180, 20);
        panelContent.add(textFieldPrice);
        //Su kien bam vao buttoon
        BookController bookController = new BookController(this);
        //cac nut button
        btnAdd = new JButton("Thêm");
        btnAdd.setFont(font);
        btnAdd.setBackground(new Color(14, 110, 166));
        btnAdd.setForeground(new Color(255, 255, 255));
        btnAdd.setHorizontalAlignment(JButton.CENTER);
        btnAdd.setBounds(450, 100, 100, 30);
        panelContent.add(btnAdd);

        btnChange = new JButton("Sửa");
        btnChange.setFont(font);
        btnChange.setBackground(new Color(147, 32, 204));
        btnChange.setForeground(new Color(255, 255, 255));
        btnChange.setHorizontalAlignment(JButton.CENTER);
        btnChange.setBounds(450, 160, 100, 30);
        panelContent.add(btnChange);

        btnDelete = new JButton("Xóa");
        btnDelete.setFont(font);
        btnDelete.setBackground(new Color(246, 4, 60));
        btnDelete.setForeground(new Color(255, 255, 255));
        btnDelete.setHorizontalAlignment(JButton.CENTER);
        btnDelete.setBounds(450, 220, 100, 30);
        panelContent.add(btnDelete);

        btnReset = new JButton("Làm mới");
        btnReset.setFont(font);
        btnReset.setBackground(new Color(222, 99, 1));
        btnReset.setForeground(new Color(255, 255, 255));
        btnReset.setHorizontalAlignment(JButton.CENTER);
        btnReset.setBounds(600, 100, 100, 30);
        panelContent.add(btnReset);

        btnSaveFile = new JButton("Xuất file");
        btnSaveFile.setFont(font);
        btnSaveFile.setBackground(new Color(32, 204, 35));
        btnSaveFile.setForeground(new Color(255, 255, 255));
        btnSaveFile.setHorizontalAlignment(JButton.CENTER);
        btnSaveFile.setBounds(600, 160, 100, 30);
        panelContent.add(btnSaveFile);

        btnView = new JButton("Lưu");
        btnView.setFont(font);
        btnView.setBackground(new Color(16, 120, 133));
        btnView.setForeground(new Color(255, 255, 255));
        btnView.setHorizontalAlignment(JButton.CENTER);
        btnView.setBounds(600, 220, 100, 30);
        panelContent.add(btnView);

        ImageIcon icon6 = scaleImage("images/icon6.png", 400, 200);
        JLabel labelImage6 = new JLabel(icon6);
        labelImage6.setBounds(700, 30, 500, 250);
        panelContent.add(labelImage6);


        //Phan tim kiem loc san pham
        JLabel labelSearch = new JLabel("Tìm Kiếm");
        labelSearch.setFont(new Font("Tahoma", Font.BOLD, 17));
        labelSearch.setBounds(630, 330, 500, 20);
        panelContent.add(labelSearch);

        //tao ra 1 panel moi chua tat ca phan tim kiem
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(null);
        searchPanel.setBackground(new Color(255, 255, 255));
        searchPanel.setBounds(0, 330, 1500, 800);
        panelContent.add(searchPanel);
        JLabel labelBookId1 = new JLabel("Mã sách");
        labelBookId1.setFont(font);
        labelBookId1.setBounds(170, 30, 70, 20);
        searchPanel.add(labelBookId1);

        textFieldBookId1 = new JTextField();
        textFieldBookId1.setFont(font1);
        textFieldBookId1.setBounds(280, 30, 120, 20);
        searchPanel.add(textFieldBookId1);

        JLabel labelCategogy1 = new JLabel("Thể loại");
        labelCategogy1.setFont(font);
        labelCategogy1.setBounds(420, 30, 70, 20);
        searchPanel.add(labelCategogy1);

        textFieldCategory1 = new JTextField();
        textFieldCategory1.setFont(font1);
        textFieldCategory1.setBounds(500, 30, 120, 20);
        searchPanel.add(textFieldCategory1);

        JLabel labelBookName1 = new JLabel("Tên sách");
        labelBookName1.setFont(font);
        labelBookName1.setBounds(640, 30, 70, 20);
        searchPanel.add(labelBookName1);

        textFieldBookName1 = new JTextField();
        textFieldBookName1.setFont(font1);
        textFieldBookName1.setBounds(720, 30, 120, 20);
        searchPanel.add(textFieldBookName1);

        JLabel labelAuthor1 = new JLabel("Tác giả");
        labelAuthor1.setFont(font);
        labelAuthor1.setBounds(860, 30, 70, 20);
        searchPanel.add(labelAuthor1);

        textFieldAuthor1 = new JTextField();
        textFieldAuthor1.setFont(font1);
        textFieldAuthor1.setBounds(940, 30, 120, 20);
        searchPanel.add(textFieldAuthor1);

        JLabel labelYearPublished1 = new JLabel("Năm xuất bản");
        labelYearPublished1.setFont(font);
        labelYearPublished1.setBounds(170, 60, 110, 20);
        searchPanel.add(labelYearPublished1);

        textFieldYearPublished1 = new JTextField();
        textFieldYearPublished1.setFont(font1);
        textFieldYearPublished1.setBounds(280, 60, 120, 20);
        searchPanel.add(textFieldYearPublished1);

        JLabel labelQuantity1 = new JLabel("Số lượng");
        labelQuantity1.setFont(font);
        labelQuantity1.setBounds(420, 60, 80, 20);
        searchPanel.add(labelQuantity1);

        textFieldQuantity1 = new JTextField();
        textFieldQuantity1.setFont(font1);
        textFieldQuantity1.setBounds(500, 60, 120, 20);
        searchPanel.add(textFieldQuantity1);

        JLabel labelPrice1 = new JLabel("Giá");
        labelPrice1.setFont(font);
        labelPrice1.setBounds(640, 60, 30, 20);
        searchPanel.add(labelPrice1);

        textFieldPrice1 = new JTextField();
        textFieldPrice1.setFont(font1);
        textFieldPrice1.setBounds(720, 60, 120, 20);
        searchPanel.add(textFieldPrice1);

        btnSearch = new JButton("Tìm Kiếm");
        btnSearch.setFont(font);
        btnSearch.setBackground(new Color(38, 55, 114));
        btnSearch.setForeground(new Color(255, 255, 255));
        btnSearch.setHorizontalAlignment(JButton.CENTER);
        btnSearch.setBounds(940, 60, 120, 25);
        searchPanel.add(btnSearch);

        //tao bang du lieu de hien thi ket qua
        String[] columnNames = {"Mã sách", "Thể loại", "Tên sách", "Tác giả", "Năm xuất bản", "Số lượng", "Giá"};

        // Tao model cho JTable
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);

        // Dat font chu va do cao cua dong trong bang
        table.setFont(new Font("Tahoma", Font.PLAIN, 12));
        table.setRowHeight(20);

        // Lay phan tieu de cua bang
        JTableHeader header = table.getTableHeader();

        // Dat font chu cho tieu de cot
        header.setFont(new Font("Tahoma", Font.BOLD, 14)); // Chu dam
        header.setBackground(Color.LIGHT_GRAY); // Dat mau nen cho tieu de
        header.setForeground(Color.BLACK); // Dat mau chu cho tieu de

        // Tao doi tuong can giua noi dung
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        // Duyet qua tung cot de dat can giua noi dung
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Tao JScrollPane de chua JTable
        JScrollPane scrollPane = new JScrollPane(table);

        // Dat vi tri va kich thuoc cho JScrollPane
        scrollPane.setBounds(50, 100, 1200, 300);

        // Them JScrollPane vao panel chinh
        panelContent.add(scrollPane);

        // Tao mot dong du lieu va them vao bang
        Object[] newRow = {"B001", "Van hoc", "Chi Pheo", "Nam Cao", "1941", "10", "50000"};
        tableModel.addRow(newRow);
        searchPanel.add(scrollPane);
        //thao tac them su kien cho cac nut bam
        btnAdd.addActionListener(bookController);
        btnChange.addActionListener(bookController);
        btnView.addActionListener(bookController);
        btnDelete.addActionListener(bookController);
        btnReset.addActionListener(bookController);
        btnSaveFile.addActionListener(bookController);
        btnSearch.addActionListener(bookController);

        return panelContent;

    }
    /**
     * Hàm hỗ trợ scale ảnh
     */
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
        BookView a = new BookView();
        JPanel panel = a.initBookView();
        JFrame app = new App();
        app.add(panel, BorderLayout.CENTER);
    }

    public JTextField getTextFieldBookId() {
        return textFieldBookId;
    }

    public void setTextFieldBookId(JTextField textFieldBookId) {
        this.textFieldBookId = textFieldBookId;
    }

    public JTextField getTextFieldBookId1() {
        return textFieldBookId1;
    }

    public void setTextFieldBookId1(JTextField textFieldBookId1) {
        this.textFieldBookId1 = textFieldBookId1;
    }

    public JTextField getTextFieldCategory() {
        return textFieldCategory;
    }

    public void setTextFieldCategory(JTextField textFieldCategory) {
        this.textFieldCategory = textFieldCategory;
    }

    public JTextField getTextFieldCategory1() {
        return textFieldCategory1;
    }

    public void setTextFieldCategory1(JTextField textFieldCategory1) {
        this.textFieldCategory1 = textFieldCategory1;
    }

    public JTextField getTextFieldBookName() {
        return textFieldBookName;
    }

    public void setTextFieldBookName(JTextField textFieldBookName) {
        this.textFieldBookName = textFieldBookName;
    }

    public JTextField getTextFieldBookName1() {
        return textFieldBookName1;
    }

    public void setTextFieldBookName1(JTextField textFieldBookName1) {
        this.textFieldBookName1 = textFieldBookName1;
    }

    public JTextField getTextFieldAuthor() {
        return textFieldAuthor;
    }

    public void setTextFieldAuthor(JTextField textFieldAuthor) {
        this.textFieldAuthor = textFieldAuthor;
    }

    public JTextField getTextFieldAuthor1() {
        return textFieldAuthor1;
    }

    public void setTextFieldAuthor1(JTextField textFieldAuthor1) {
        this.textFieldAuthor1 = textFieldAuthor1;
    }

    public JTextField getTextFieldYearPublished() {
        return textFieldYearPublished;
    }

    public void setTextFieldYearPublished(JTextField textFieldYearPublished) {
        this.textFieldYearPublished = textFieldYearPublished;
    }

    public JTextField getTextFieldYearPublished1() {
        return textFieldYearPublished1;
    }

    public void setTextFieldYearPublished1(JTextField textFieldYearPublished1) {
        this.textFieldYearPublished1 = textFieldYearPublished1;
    }

    public JTextField getTextFieldQuantity() {
        return textFieldQuantity;
    }

    public void setTextFieldQuantity(JTextField textFieldQuantity) {
        this.textFieldQuantity = textFieldQuantity;
    }

    public JTextField getTextFieldQuantity1() {
        return textFieldQuantity1;
    }

    public void setTextFieldQuantity1(JTextField textFieldQuantity1) {
        this.textFieldQuantity1 = textFieldQuantity1;
    }

    public JTextField getTextFieldPrice() {
        return textFieldPrice;
    }

    public void setTextFieldPrice(JTextField textFieldPrice) {
        this.textFieldPrice = textFieldPrice;
    }

    public JTextField getTextFieldPrice1() {
        return textFieldPrice1;
    }

    public void setTextFieldPrice1(JTextField textFieldPrice1) {
        this.textFieldPrice1 = textFieldPrice1;
    }

    public JButton getBtnAdd() {
        return btnAdd;
    }

    public void setBtnAdd(JButton btnAdd) {
        this.btnAdd = btnAdd;
    }

    public JButton getBtnChange() {
        return btnChange;
    }

    public void setBtnChange(JButton btnChange) {
        this.btnChange = btnChange;
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

    public JButton getBtnSaveFile() {
        return btnSaveFile;
    }

    public void setBtnSaveFile(JButton btnSaveFile) {
        this.btnSaveFile = btnSaveFile;
    }

    public JButton getBtnView() {
        return btnView;
    }

    public void setBtnView(JButton btnView) {
        this.btnView = btnView;
    }

    public JButton getBtnSearch() {
        return btnSearch;
    }

    public void setBtnSearch(JButton btnSearch) {
        this.btnSearch = btnSearch;
    }
}
