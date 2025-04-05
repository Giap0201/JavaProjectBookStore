package view;

import controller.BookController;
import controller.CategoryController;
import model.Books;
import model.Category;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class BookView extends JPanel {
    private CategoryController categoryController;
    private JTextField textFieldBookId, textFieldBookId1;
    private JComboBox<String> comboBoxCategory, comboBoxCategory_search;
    private JTextField textFieldBookName, textFieldBookName1;
    private JTextField textFieldAuthor, textFieldAuthor1;
    private JTextField textFieldYearPublished, textFieldYearPublished1;
    private JTextField textFieldQuantity, textFieldQuantity1;
    private JTextField textFieldPrice, textFieldPrice1;
    private JButton btnAdd, btnChange, btnDelete, btnReset, btnSaveFile, btnView, btnSearch;
    private DefaultTableModel tableModel;
    private JTable table;

    public BookView() {
        this.categoryController = new CategoryController();
    }

    public JPanel initBookView() {
        Font font = new Font("Tahoma", Font.BOLD, 15);
        JPanel panelContent = new JPanel();
        panelContent.setLayout(null);
        panelContent.setBackground(new Color(157, 239, 227));

        JLabel labeltitle = new JLabel("QUẢN LÍ SÁCH");
        labeltitle.setBounds(600, 10, 250, 30);
        labeltitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        panelContent.add(labeltitle);
        Font font1 = new Font("Tahoma", Font.PLAIN, 15);

        JLabel labelBookId = new JLabel("Mã sách");
        labelBookId.setFont(font);
        labelBookId.setBounds(50, 50, 100, 20);
        panelContent.add(labelBookId);

        textFieldBookId = new JTextField();
        textFieldBookId.setFont(font1);
        textFieldBookId.setBounds(170, 50, 230, 30);
        panelContent.add(textFieldBookId);

        JLabel labelCategory = new JLabel("Thể loại");
        labelCategory.setFont(font);
        labelCategory.setBounds(50, 90, 100, 20);
        panelContent.add(labelCategory);

        comboBoxCategory = new JComboBox<>();
        //tai len tu ham duoi db
        loadCategories(comboBoxCategory);
        comboBoxCategory.setFont(font1);
        comboBoxCategory.setBounds(170, 90, 230, 30);
        panelContent.add(comboBoxCategory);

        JLabel labelBookName = new JLabel("Tên sách");
        labelBookName.setFont(font);
        labelBookName.setBounds(50, 130, 100, 20);
        panelContent.add(labelBookName);

        textFieldBookName = new JTextField();
        textFieldBookName.setFont(font1);
        textFieldBookName.setBounds(170, 130, 230, 30);
        panelContent.add(textFieldBookName);

        JLabel labelAuthor = new JLabel("Tác giả");
        labelAuthor.setFont(font);
        labelAuthor.setBounds(50, 170, 100, 20);
        panelContent.add(labelAuthor);

        textFieldAuthor = new JTextField();
        textFieldAuthor.setFont(font1);
        textFieldAuthor.setBounds(170, 170, 230, 30);
        panelContent.add(textFieldAuthor);

        JLabel labelYearPublished = new JLabel("Năm xuất bản");
        labelYearPublished.setFont(font);
        labelYearPublished.setBounds(450, 50, 130, 20);
        panelContent.add(labelYearPublished);

        textFieldYearPublished = new JTextField();
        textFieldYearPublished.setFont(font1);
        textFieldYearPublished.setBounds(580, 50, 230, 30);
        panelContent.add(textFieldYearPublished);

        JLabel labelQuantity = new JLabel("Số lượng");
        labelQuantity.setFont(font);
        labelQuantity.setBounds(450, 90, 100, 20);
        panelContent.add(labelQuantity);

        textFieldQuantity = new JTextField();
        textFieldQuantity.setFont(font1);
        textFieldQuantity.setBounds(580, 90, 230, 30);
        panelContent.add(textFieldQuantity);

        JLabel labelPrice = new JLabel("Giá");
        labelPrice.setFont(font);
        labelPrice.setBounds(450, 130, 100, 20);
        panelContent.add(labelPrice);

        textFieldPrice = new JTextField();
        textFieldPrice.setFont(font1);
        textFieldPrice.setBounds(580, 130, 230, 30);
        panelContent.add(textFieldPrice);

        // Các nút button
        btnAdd = createButton("Thêm", new Color(14, 110, 166));
        btnAdd.setBounds(50 + 40, 220, 100, 30);
        panelContent.add(btnAdd);

        btnChange = createButton("Sửa", new Color(147, 32, 204));
        btnChange.setBounds(160 + 40, 220, 100, 30);
        panelContent.add(btnChange);

        btnDelete = createButton("Xóa", new Color(246, 4, 60));
        btnDelete.setBounds(270 + 40, 220, 100, 30);
        panelContent.add(btnDelete);

        btnReset = createButton("Làm mới", new Color(222, 99, 1));
        btnReset.setBounds(380 + 40, 220, 100, 30);
        panelContent.add(btnReset);

        btnSaveFile = createButton("Xuất file", new Color(32, 204, 35));
        btnSaveFile.setBounds(490 + 40, 220, 100, 30);
        panelContent.add(btnSaveFile);

        btnView = createButton("Lưu", new Color(16, 120, 133));
        btnView.setBounds(600 + 40, 220, 100, 30);
        panelContent.add(btnView);


        ImageIcon icon6 = scaleImage("images/icon6.png", 400, 200);
        JLabel labelImage6 = new JLabel(icon6);
        labelImage6.setBounds(820, 30, 500, 250);
        panelContent.add(labelImage6);

        //tao ra 1 panel moi chua tat ca phan tim kiem
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(null);
        searchPanel.setBackground(new Color(255, 255, 255));
        searchPanel.setBounds(0, 270, 1500, 400);
        panelContent.add(searchPanel);
        JLabel labelSearch = new JLabel("Tìm Kiếm");
        labelSearch.setFont(new Font("Tahoma", Font.BOLD, 16));
        labelSearch.setBounds(630, 10, 500, 20);
        labelSearch.setForeground(new Color(44, 44, 171));
        searchPanel.add(labelSearch);

        JLabel labelBookId1 = new JLabel("Mã sách");
        labelBookId1.setFont(font);
        labelBookId1.setBounds(170, 30 + 10, 70, 20);
        searchPanel.add(labelBookId1);
        Font font11 = new Font("Tahoma", Font.PLAIN, 13);
        textFieldBookId1 = new JTextField();
        textFieldBookId1.setFont(font11);
        textFieldBookId1.setBounds(280, 30 + 10, 120, 20);
        searchPanel.add(textFieldBookId1);

        JLabel labelCategogy1 = new JLabel("Thể loại");
        labelCategogy1.setFont(font);
        labelCategogy1.setBounds(420, 30 + 10, 70, 20);
        searchPanel.add(labelCategogy1);

        comboBoxCategory_search = new JComboBox<>();
        loadCategories(comboBoxCategory_search);
        comboBoxCategory_search.setFont(font11);
        comboBoxCategory_search.setBounds(500, 30 + 10, 120, 20);
        searchPanel.add(comboBoxCategory_search);

        JLabel labelBookName1 = new JLabel("Tên sách");
        labelBookName1.setFont(font);
        labelBookName1.setBounds(640, 30 + 10, 70, 20);
        searchPanel.add(labelBookName1);

        textFieldBookName1 = new JTextField();
        textFieldBookName1.setFont(font11);
        textFieldBookName1.setBounds(720, 30 + 10, 120, 20);
        searchPanel.add(textFieldBookName1);

        JLabel labelAuthor1 = new JLabel("Tác giả");
        labelAuthor1.setFont(font);
        labelAuthor1.setBounds(860, 30 + 10, 70, 20);
        searchPanel.add(labelAuthor1);

        textFieldAuthor1 = new JTextField();
        textFieldAuthor1.setFont(font11);
        textFieldAuthor1.setBounds(940, 30 + 10, 120, 20);
        searchPanel.add(textFieldAuthor1);

        JLabel labelYearPublished1 = new JLabel("Năm xuất bản");
        labelYearPublished1.setFont(font);
        labelYearPublished1.setBounds(170, 60 + 10, 110, 20);
        searchPanel.add(labelYearPublished1);

        textFieldYearPublished1 = new JTextField();
        textFieldYearPublished1.setFont(font11);
        textFieldYearPublished1.setBounds(280, 60 + 10, 120, 20);
        searchPanel.add(textFieldYearPublished1);

        JLabel labelQuantity1 = new JLabel("Số lượng");
        labelQuantity1.setFont(font);
        labelQuantity1.setBounds(420, 60 + 10, 80, 20);
        searchPanel.add(labelQuantity1);

        textFieldQuantity1 = new JTextField();
        textFieldQuantity1.setFont(font11);
        textFieldQuantity1.setBounds(500, 60 + 10, 120, 20);
        searchPanel.add(textFieldQuantity1);

        JLabel labelPrice1 = new JLabel("Giá");
        labelPrice1.setFont(font);
        labelPrice1.setBounds(640, 60 + 10, 30, 20);
        searchPanel.add(labelPrice1);

        textFieldPrice1 = new JTextField();
        textFieldPrice1.setFont(font11);
        textFieldPrice1.setBounds(720, 60 + 10, 120, 20);
        searchPanel.add(textFieldPrice1);

        btnSearch = new JButton("Tìm Kiếm");
        btnSearch.setFont(font);
        btnSearch.setBackground(new Color(38, 55, 114));
        btnSearch.setForeground(new Color(255, 255, 255));
        btnSearch.setHorizontalAlignment(JButton.CENTER);
        btnSearch.setBounds(940, 60 + 10, 120, 25);
        searchPanel.add(btnSearch);

        //tao bang du lieu de hien thi ket qua
        String[] columnNames = {"Mã sách", "Thể loại", "Tên sách", "Tác giả", "Năm xuất bản", "Số lượng", "Giá"};

        // Tao model cho JTable
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);

        // Dat font chu va do cao cua dong trong bang
        table.setFont(new Font("Tahoma", Font.PLAIN, 12));
        table.setRowHeight(20);//dat chieu cao moi dong trong bang

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
        scrollPane.setBounds(50, 100 + 10, 1200, 270);
//        updateTable(bookController.getListOfBooks());
        searchPanel.add(scrollPane);

        JPanel panel3 = new JPanel();
        panel3.setBackground(new Color(255, 255, 255));
        panel3.setLayout(null);
        panel3.setBounds(0, 670, 1500, 300);
        panelContent.add(panel3);

        JLabel labelPriceMin = new JLabel("Đơn giá thấp nhất:");
        labelPriceMin.setFont(font);
        labelPriceMin.setBounds(100, 0, 150, 20);
        panel3.add(labelPriceMin);

        JLabel labelPriceMinValue = new JLabel("");
        labelPriceMinValue.setFont(font);
        labelPriceMinValue.setBounds(250, 0, 150, 20);
        panel3.add(labelPriceMinValue);

        JLabel labelPriceMax = new JLabel("Đơn giá cao nhất:");
        labelPriceMax.setFont(font);
        labelPriceMax.setBounds(100, 30, 150, 20);
        panel3.add(labelPriceMax);

        JLabel labelPriceMaxValue = new JLabel("");
        labelPriceMaxValue.setFont(font);
        labelPriceMaxValue.setBounds(250, 30, 150, 20);
        panel3.add(labelPriceMaxValue);

        JButton btnThongKe = createButton("Thống Kê", new Color(0x57DC59));
        btnThongKe.setBounds(800, 15, 120, 30);
        panel3.add(btnThongKe);

        JLabel labelBookTypeCount = new JLabel("Số loại sách:");
        labelBookTypeCount.setFont(font);
        labelBookTypeCount.setBounds(500, 0, 150, 20);
        panel3.add(labelBookTypeCount);

        JLabel labelBookTypeCountValue = new JLabel("");
        labelBookTypeCountValue.setFont(font);
        labelBookTypeCountValue.setBounds(650, 0, 150, 20);
        panel3.add(labelBookTypeCountValue);

        JLabel labelTotalBooks = new JLabel("Tổng số sách:");
        labelTotalBooks.setFont(font);
        labelTotalBooks.setBounds(500, 30, 150, 20);
        panel3.add(labelTotalBooks);

        JLabel labelTotalBooksValue = new JLabel("");
        labelTotalBooksValue.setFont(font);
        labelTotalBooksValue.setBounds(650, 30, 150, 20);
        panel3.add(labelTotalBooksValue);

//        thao tac them su kien cho cac nut bam
        BookController bookController = new BookController(this);
        btnAdd.addActionListener(bookController);
        btnChange.addActionListener(bookController);
        btnView.addActionListener(bookController);
        btnDelete.addActionListener(bookController);
        btnReset.addActionListener(bookController);
        btnSaveFile.addActionListener(bookController);
        btnSearch.addActionListener(bookController);
        return panelContent;
    }

    public JButton createButton(String title, Color color) {
        Font font = new Font("Tahoma", Font.BOLD, 15);
        JButton button = new JButton(title);
        button.setFont(font);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setHorizontalAlignment(JButton.CENTER);
        return button;
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

    //thuong thuc clear xu li khi them du lieu vao
    public void clear() {
        textFieldBookId.setText("");
        textFieldBookName.setText("");
        textFieldAuthor.setText("");
        textFieldYearPublished.setText("");
        textFieldPrice.setText("");
        textFieldQuantity.setText("");
        comboBoxCategory.setSelectedIndex(0);
    }

    //ham lay category tu db da thong qua controller them vao combobox
    private void loadCategories(JComboBox<String> comboBox) {
        ArrayList<Category> categories = categoryController.getCategories();
        comboBox.addItem("");
//        comboBox.removeAllItems();
        for (Category category : categories) {
            comboBox.addItem(category.getCategoryName());
        }
    }

    //ham update du lieu trong table
    public void updateTable(ArrayList<Books> listBook) {
        //xoa toan bo du lieu trong bang
        tableModel.setRowCount(0);
        //them du lieu vao trong bang
        for (Books book : listBook) {
            Object[] row = {book.getBookID(), book.getCategory().getCategoryName(),
                    book.getBookName(), book.getAuthor(), book.getYearPublished(), book.getQuantity(), book.getPrice(),};
            tableModel.addRow(row);
        }
    }

    public CategoryController getCategoryController() {
        return categoryController;
    }

    public void setCategoryController(CategoryController categoryController) {
        this.categoryController = categoryController;
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

    public JComboBox<String> getComboBoxCategory() {
        return comboBoxCategory;
    }

    public void setComboBoxCategory(JComboBox<String> comboBoxCategory) {
        this.comboBoxCategory = comboBoxCategory;
    }

    public JComboBox<String> getComboBoxCategory_search() {
        return comboBoxCategory_search;
    }

    public void setComboBoxCategory_search(JComboBox<String> comboBoxCategory_search) {
        this.comboBoxCategory_search = comboBoxCategory_search;
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

    public static void main(String[] args) {
        BookView a = new BookView();
        JPanel panel = a.initBookView();
        JFrame app = new App();
        app.add(panel, BorderLayout.CENTER);
    }


}
