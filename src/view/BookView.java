package view;

import controller.BookController;
import controller.CategoryController;
import model.Category;
import utils.ImageUtils;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class BookView extends JPanel {
    private CategoryController categoryController;
    private JTextField textFieldBookId, textFieldBookId1;
    private JComboBox<String> comboBoxCategory, comboBoxCategory_search;
    private JTextField textFieldBookName, textFieldBookName1;
    private JTextField textFieldAuthor, textFieldAuthor1;
    private JTextField textFieldYearPublished,textFieldYearFrom,textFieldYearTo;
    private JTextField textFieldQuantity;
    private JTextField textFieldPrice,textFieldPriceFrom,textFieldPriceTo;
    private JButton btnAdd, btnChange, btnDelete, btnReset, btnSaveFile, btnView, btnSearch;
    private DefaultTableModel tableModel;
    private JTable table;
    private JLabel labelPriceMinValue,labelPriceMaxValue,labelBookTypeCountValue,labelTotalBooksValue;
    private JButton btnUploadImage;
    private JLabel labelImage6;
    private JLabel labelImagePreview;

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
        // tai len tu ham duoi db
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
        btnUploadImage = createButton("UPLOAD Ảnh", new Color(192, 192, 192));
        btnUploadImage.setBounds(680, 170, 150, 40);
        panelContent.add(btnUploadImage);


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

//        ImageIcon icon6 = scaleImage("images/icon6.png", 200, 200);
//        labelImage6 = new JLabel(icon6);
//        labelImage6.setBounds(850, 20, 200, 200);
//        panelContent.add(labelImage6);

        // Khởi tạo JLabel để hiển thị ảnh (thay thế labelImage6 cũ)
        labelImagePreview = new JLabel();
        labelImagePreview.setBounds(850, 20, 200, 200); // Vị trí và kích thước như cũ
        labelImagePreview.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Thêm đường viền để dễ thấy
        labelImagePreview.setHorizontalAlignment(JLabel.CENTER); // Căn giữa ảnh
        labelImagePreview.setText("Chưa có ảnh"); // Text mặc định
        // Load ảnh mặc định ban đầu (nếu có)
        // displayBookImage("images/icon6.png"); // Gọi hàm hiển thị ảnh mặc định (nếu cần)
        // Hoặc set icon mặc định nếu icon6 là resource nội bộ
        ImageIcon defaultIcon = ImageUtils.getDefaultScaledIcon("images/icon6.png",ImageUtils.DEFAULT_IMAGE_WIDTH,
                ImageUtils.DEFAULT_IMAGE_HEIGHT);
        //ImageIcon defaultIcon = scaleImageResource("images/icon6.png", 200, 200); // Dùng hàm riêng cho resource
        if (defaultIcon != null) {
            labelImagePreview.setIcon(defaultIcon);
            labelImagePreview.setText(null); // Xóa text khi có icon
        }else{
            labelImagePreview.setIcon(null);
            labelImagePreview.setText("Chưa có ảnh");
        }
        panelContent.add(labelImagePreview);




        // tao ra 1 panel moi chua tat ca phan tim kiem
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
        Font font11 = new Font("Tahoma", Font.PLAIN, 13);

        // First Row (y=40)
        JLabel labelBookId1 = new JLabel("Mã sách");
        labelBookId1.setFont(font);
        labelBookId1.setBounds(150, 40, 70, 20);
        searchPanel.add(labelBookId1);

        textFieldBookId1 = new JTextField();
        textFieldBookId1.setFont(font11);
        textFieldBookId1.setBounds(230, 40, 120, 20);
        searchPanel.add(textFieldBookId1);

        JLabel labelCategory1 = new JLabel("Thể loại");
        labelCategory1.setFont(font);
        labelCategory1.setBounds(410-50, 40, 70, 20);
        searchPanel.add(labelCategory1);

        comboBoxCategory_search = new JComboBox<>();
        loadCategories(comboBoxCategory_search);
        comboBoxCategory_search.setFont(font11);
        comboBoxCategory_search.setBounds(490-50, 40, 120, 20);
        searchPanel.add(comboBoxCategory_search);

        JLabel labelBookName1 = new JLabel("Tên sách");
        labelBookName1.setFont(font);
        labelBookName1.setBounds(620-50, 40, 70, 20);
        searchPanel.add(labelBookName1);

        textFieldBookName1 = new JTextField();
        textFieldBookName1.setFont(font11);
        textFieldBookName1.setBounds(700-50, 40, 120, 20);
        searchPanel.add(textFieldBookName1);

        JLabel labelAuthor1 = new JLabel("Tác giả");
        labelAuthor1.setFont(font);
        labelAuthor1.setBounds(830-50, 40, 70, 20);
        searchPanel.add(labelAuthor1);

        textFieldAuthor1 = new JTextField();
        textFieldAuthor1.setFont(font11);
        textFieldAuthor1.setBounds(910-50, 40, 120, 20);
        searchPanel.add(textFieldAuthor1);

        // Second Row (y=70)
        JLabel labelYearFrom = new JLabel("Năm từ");
        labelYearFrom.setFont(font);
        labelYearFrom.setBounds(150, 70, 70, 20);
        searchPanel.add(labelYearFrom);

        textFieldYearFrom = new JTextField();
        textFieldYearFrom.setFont(font11);
        textFieldYearFrom.setBounds(230, 70, 120, 20);
        searchPanel.add(textFieldYearFrom);

        JLabel labelYearTo = new JLabel("đến");
        labelYearTo.setFont(font);
        labelYearTo.setBounds(410-50, 70, 40, 20); // Adjusted x
        searchPanel.add(labelYearTo);

        textFieldYearTo = new JTextField();
        textFieldYearTo.setFont(font11);
        textFieldYearTo.setBounds(490-50, 70, 120, 20); // Adjusted x
        searchPanel.add(textFieldYearTo);

        JLabel labelPriceFrom = new JLabel("Giá từ");
        labelPriceFrom.setFont(font);
        labelPriceFrom.setBounds(620-50, 70, 50, 20); // Adjusted x
        searchPanel.add(labelPriceFrom);

        textFieldPriceFrom = new JTextField();
        textFieldPriceFrom.setFont(font11);
        textFieldPriceFrom.setBounds(700-50, 70, 120, 20); // Adjusted x
        searchPanel.add(textFieldPriceFrom);

        JLabel labelPriceTo = new JLabel("đến");
        labelPriceTo.setFont(font);
        labelPriceTo.setBounds(830-50, 70, 40, 20); // Adjusted x
        searchPanel.add(labelPriceTo);

        textFieldPriceTo = new JTextField();
        textFieldPriceTo.setFont(font11);
        textFieldPriceTo.setBounds(910-50, 70, 120, 20); // Adjusted x
        searchPanel.add(textFieldPriceTo);

        // Search Button
        btnSearch = new JButton("Tìm Kiếm");
        btnSearch.setFont(font);
        btnSearch.setBackground(new Color(38, 55, 114));
        btnSearch.setForeground(new Color(255, 255, 255));
        btnSearch.setHorizontalAlignment(JButton.CENTER);
        btnSearch.setBounds(1060, 50, 120, 30); // Adjusted x
        searchPanel.add(btnSearch);
        // tao bang du lieu de hien thi ket qua
        String[] columnNames = { "Mã sách", "Thể loại", "Tên sách", "Tác giả", "Năm xuất bản", "Số lượng", "Giá" };

        // Tao model cho JTable
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        //sap xep table
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        // Dat font chu va do cao cua dong trong bang
        table.setFont(new Font("Tahoma", Font.PLAIN, 12));
        table.setRowHeight(20);// dat chieu cao moi dong trong bang

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
        // updateTable(bookController.getListOfBooks());
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

        labelPriceMinValue = new JLabel("");
        labelPriceMinValue.setFont(font);
        labelPriceMinValue.setBounds(250, 0, 150, 20);
        panel3.add(labelPriceMinValue);

        JLabel labelPriceMax = new JLabel("Đơn giá cao nhất:");
        labelPriceMax.setFont(font);
        labelPriceMax.setBounds(100, 30, 150, 20);
        panel3.add(labelPriceMax);

        labelPriceMaxValue = new JLabel("");
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

        labelBookTypeCountValue = new JLabel("");
        labelBookTypeCountValue.setFont(font);
        labelBookTypeCountValue.setBounds(650, 0, 150, 20);
        panel3.add(labelBookTypeCountValue);

        JLabel labelTotalBooks = new JLabel("Tổng số sách:");
        labelTotalBooks.setFont(font);
        labelTotalBooks.setBounds(500, 30, 150, 20);
        panel3.add(labelTotalBooks);

        labelTotalBooksValue = new JLabel("");
        labelTotalBooksValue.setFont(font);
        labelTotalBooksValue.setBounds(650, 30, 150, 20);
        panel3.add(labelTotalBooksValue);

        // thao tac them su kien cho cac nut bam
        BookController bookController = new BookController(this);
        btnAdd.addActionListener(bookController);
        btnChange.addActionListener(bookController);
        btnView.addActionListener(bookController);
        btnDelete.addActionListener(bookController);
        btnReset.addActionListener(bookController);
        btnSaveFile.addActionListener(bookController);
        btnSearch.addActionListener(bookController);
        btnUploadImage.addActionListener(bookController);
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

    // Thêm phương thức này vào BookView.java
    /**
     * Đặt ảnh hiển thị trong JLabel xem trước (labelImagePreview).
     * Xử lý trường hợp icon là null bằng cách hiển thị icon mặc định hoặc thông báo lỗi.
     *
     * @param icon ImageIcon cần hiển thị, hoặc null để đặt lại về mặc định/lỗi.
     */
    public void setPreviewImage(ImageIcon icon) {
        if (icon != null) {
            labelImagePreview.setIcon(icon);
            labelImagePreview.setText(null); // Xóa text nếu có ảnh
        } else {
            // Nếu icon là null, thử hiển thị icon mặc định
            ImageIcon defaultIcon = ImageUtils.getDefaultScaledIcon("images/icon6.png",
                    ImageUtils.DEFAULT_IMAGE_WIDTH,
                    ImageUtils.DEFAULT_IMAGE_HEIGHT
            );
            if (defaultIcon != null) {
                labelImagePreview.setIcon(defaultIcon);
                labelImagePreview.setText(null);
            } else {
                // Dự phòng nếu cả icon mặc định/placeholder cũng lỗi
                labelImagePreview.setIcon(null);
                labelImagePreview.setText("Lỗi hiển thị ảnh");
            }
        }
    }

    // ham lay category tu db da thong qua controller them vao combobox
    private void loadCategories(JComboBox<String> comboBox) {
        ArrayList<Category> categories = categoryController.getCategories();
        comboBox.addItem("");
        // comboBox.removeAllItems();
        for (Category category : categories) {
            comboBox.addItem(category.getCategoryName());
        }
    }
    public CategoryController getCategoryController() {
        return categoryController;
    }

    public JTextField getTextFieldBookId() {
        return textFieldBookId;
    }

    public JTextField getTextFieldBookId1() {
        return textFieldBookId1;
    }

    public JComboBox<String> getComboBoxCategory() {
        return comboBoxCategory;
    }

    public JComboBox<String> getComboBoxCategory_search() {
        return comboBoxCategory_search;
    }

    public JTextField getTextFieldBookName() {
        return textFieldBookName;
    }

    public JTextField getTextFieldBookName1() {
        return textFieldBookName1;
    }

    public JTextField getTextFieldAuthor() {
        return textFieldAuthor;
    }

    public JTextField getTextFieldAuthor1() {
        return textFieldAuthor1;
    }

    public JTextField getTextFieldYearPublished() {
        return textFieldYearPublished;
    }

    public JTextField getTextFieldYearFrom() {
        return textFieldYearFrom;
    }

    public JTextField getTextFieldYearTo() {
        return textFieldYearTo;
    }

    public JTextField getTextFieldQuantity() {
        return textFieldQuantity;
    }

    public JTextField getTextFieldPrice() {
        return textFieldPrice;
    }

    public JTextField getTextFieldPriceFrom() {
        return textFieldPriceFrom;
    }

    public JTextField getTextFieldPriceTo() {
        return textFieldPriceTo;
    }

    public JButton getBtnAdd() {
        return btnAdd;
    }

    public JButton getBtnChange() {
        return btnChange;
    }

    public JButton getBtnDelete() {
        return btnDelete;
    }

    public JButton getBtnReset() {
        return btnReset;
    }

    public JButton getBtnSaveFile() {
        return btnSaveFile;
    }

    public JButton getBtnView() {
        return btnView;
    }

    public JButton getBtnSearch() {
        return btnSearch;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JTable getTable() {
        return table;
    }

    public JLabel getLabelPriceMinValue() {
        return labelPriceMinValue;
    }

    public JLabel getLabelPriceMaxValue() {
        return labelPriceMaxValue;
    }

    public JLabel getLabelBookTypeCountValue() {
        return labelBookTypeCountValue;
    }

    public JLabel getLabelTotalBooksValue() {
        return labelTotalBooksValue;
    }

    public JButton getBtnUploadImage() {
        return btnUploadImage;
    }

    public void setBtnUploadImage(JButton btnUploadImage) {
        this.btnUploadImage = btnUploadImage;
    }
}
