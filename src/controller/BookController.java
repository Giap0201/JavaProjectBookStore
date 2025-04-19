package controller;

import model.BookSearch;
import model.Books;
import model.Category;
import service.BookService;
import service.CategoryService;
import utils.CommonView;
import utils.ValidateForm;
import view.BookView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class BookController implements ActionListener {
    private final BookView bookView;
    private final BookService bookService;
    private final CategoryService categoryService;
    private final ArrayList<Category> categories;

    public BookController(BookView bookView) {
        this.bookView = bookView;
        this.bookService = new BookService();
        this.categoryService = new CategoryService();
        this.categories = categoryService.getCategory();
        initializeView();
    }

    // Khởi tạo view: thêm sự kiện cho bảng và cập nhật dữ liệu ban đầu
    private void initializeView() {
        addTableSelectionListener();
        try {
            ArrayList<Books> allBooks = bookService.getAllBooks();
            updateTable(allBooks);
            statistical(allBooks);
        } catch (Exception ex) {
            CommonView.showErrorMessage(bookView, "Lỗi khi khởi tạo danh sách sách: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // Xử lý sự kiện cho các nút bấm
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == bookView.getBtnAdd()) {
                addBook();
            } else if (e.getSource() == bookView.getBtnChange()) {
                updateBook();
            } else if (e.getSource() == bookView.getBtnDelete()) {
                deleteBook();
            } else if (e.getSource() == bookView.getBtnSearch()) {
                searchBooks();
            } else if (e.getSource() == bookView.getBtnReset()) {
                refreshView();
            } else if (e.getSource() == bookView.getBtnSaveFile()) {
                exportToCSV();
            }
        } catch (IllegalArgumentException ex) {
            CommonView.showErrorMessage(bookView, ex.getMessage());
        } catch (Exception ex) {
            CommonView.showErrorMessage(bookView, "Lỗi hệ thống: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // Chức năng thêm sách
    public void addBook() {
        if (!CommonView.confirmAction(bookView, "Bạn có chắc chắn muốn thêm sách này?")) {
            return;
        }
        Books book = getBookInForm();
        if (!bookService.addBook(book)) {
            throw new IllegalArgumentException("Không thể thêm sách. Mã sách có thể đã tồn tại.");
        }
        CommonView.showInfoMessage(bookView, "Thêm sách thành công!");
        refreshView();
    }

    // Chức năng cập nhật sách
    public void updateBook() {
        String bookID = bookView.getTextFieldBookId().getText().trim();
        if (bookID.isEmpty()) {
            throw new IllegalArgumentException("Vui lòng chọn sách cần sửa!");
        }
        if (!CommonView.confirmAction(bookView, "Bạn có chắc chắn muốn sửa thông tin sách này?")) {
            return;
        }
        Books book = getBookInForm();
        if (!bookService.updateBook(book)) {
            throw new IllegalArgumentException("Không thể sửa sách. Vui lòng thử lại.");
        }
        CommonView.showInfoMessage(bookView, "Sửa sách thành công!");
        refreshView();
    }

    // Chức năng xóa sách
    public void deleteBook() {
        String bookID = bookView.getTextFieldBookId().getText().trim();
        if (bookID.isEmpty()) {
            throw new IllegalArgumentException("Vui lòng chọn sách cần xóa!");
        }
        if (!CommonView.confirmAction(bookView, "Bạn có chắc chắn muốn xóa sách này?")) {
            return;
        }
        Books book = getBookInForm();
        if (!bookService.deleteBook(book)) {
            throw new IllegalArgumentException("Không thể xóa sách!");
        }
        CommonView.showInfoMessage(bookView, "Xóa sách thành công!");
        refreshView();
    }

    // Chức năng tìm kiếm sách
    public void searchBooks() {
        BookSearch condition = getFormBookSearch();
        ArrayList<Books> booksResult = bookService.listBookSearch(condition);
        statistical(booksResult);
        if (booksResult.isEmpty()) {
            CommonView.showInfoMessage(bookView, "Không tìm thấy sách!");
            updateTable(booksResult);
        } else {
            updateTable(booksResult);
        }
    }

    // Chức năng làm mới view (reset)
    private void refreshView() {
        ArrayList<Books> allBooks = bookService.getAllBooks();
        updateTable(allBooks);
        statistical(allBooks);
        clearForm();
//        CommonView.showInfoMessage(bookView, "Đã làm mới danh sách sách!");
    }

    // Chức năng xuất dữ liệu ra file CSV
    public void exportToCSV() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file CSV");
        int userSelection = fileChooser.showSaveDialog(bookView);
        if (userSelection != JFileChooser.APPROVE_OPTION) {
            return; // Người dùng hủy thao tác
        }

        File fileToSave = fileChooser.getSelectedFile();
        String filePath = fileToSave.getAbsolutePath();
        if (!filePath.endsWith(".csv")) {
            filePath += ".csv";
        }

        try (PrintWriter writer = new PrintWriter(
                new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8"))) {
            // Ghi tiêu đề
            writer.write("\uFEFF");
            writer.println("Mã sách,Thể loại,Tên sách,Tác giả,Năm xuất bản,Số lượng,Giá");

            // Ghi từng dòng dữ liệu từ bảng
            for (int i = 0; i < bookView.getTableModel().getRowCount(); i++) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < bookView.getTableModel().getColumnCount(); j++) {
                    sb.append(bookView.getTableModel().getValueAt(i, j));
                    if (j != bookView.getTableModel().getColumnCount() - 1) {
                        sb.append(",");
                    }
                }
                writer.println(sb);
            }

            CommonView.showInfoMessage(bookView, "Xuất file CSV thành công!");
        } catch (IOException ex) {
            throw new RuntimeException("Lỗi khi ghi file CSV: " + ex.getMessage(), ex);
        }
    }

    // Thêm sự kiện cho bảng (khi chọn một dòng, điền dữ liệu vào form)
    public void addTableSelectionListener() {
        bookView.getTable().getSelectionModel().addListSelectionListener(e -> {
            try {
                int selectRow = bookView.getTable().getSelectedRow();
                if (selectRow >= 0) {
                    String bookID = (String) bookView.getTable().getValueAt(selectRow, 0);
                    String categoryName = (String) bookView.getTable().getValueAt(selectRow, 1);
                    String bookName = (String) bookView.getTable().getValueAt(selectRow, 2);
                    String author = (String) bookView.getTable().getValueAt(selectRow, 3);
                    int yearPublished = (int) bookView.getTable().getValueAt(selectRow, 4);
                    int quantity = (int) bookView.getTable().getValueAt(selectRow, 5);
                    double price = (double) bookView.getTable().getValueAt(selectRow, 6);

                    bookView.getTextFieldBookId().setText(bookID);
                    bookView.getComboBoxCategory().setSelectedItem(categoryName);
                    bookView.getTextFieldBookName().setText(bookName);
                    bookView.getTextFieldAuthor().setText(author);
                    bookView.getTextFieldYearPublished().setText(String.valueOf(yearPublished));
                    bookView.getTextFieldQuantity().setText(String.valueOf(quantity));
                    bookView.getTextFieldPrice().setText(String.valueOf(price));
                }
            } catch (Exception ex) {
                CommonView.showErrorMessage(bookView, "Lỗi khi tải dữ liệu sách lên form: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
    }

    // Lấy dữ liệu sách từ form
    public Books getBookInForm() {
        String bookID = bookView.getTextFieldBookId().getText().trim();
        String bookName = bookView.getTextFieldBookName().getText().trim();
        String author = bookView.getTextFieldAuthor().getText().trim();
        String yearPublishedStr = bookView.getTextFieldYearPublished().getText().trim();
        String priceStr = bookView.getTextFieldPrice().getText().trim();
        String quantityStr = bookView.getTextFieldQuantity().getText().trim();
        int choiceCategory = bookView.getComboBoxCategory().getSelectedIndex();

        validateBookInput(bookID, bookName, author, yearPublishedStr, priceStr, quantityStr, choiceCategory);

        int yearPublished;
        try {
            yearPublished = ValidateForm.isInteger(yearPublishedStr, "Năm xuất bản");
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        if (yearPublished < 0 || yearPublished > 2025) {
            throw new IllegalArgumentException("Năm xuất bản không hợp lệ (phải từ 0 đến 2025)!");
        }

        int quantity;
        try {
            quantity = ValidateForm.isInteger(quantityStr, "Số lượng");
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Số lượng phải là số dương!");
        }

        double price;
        try {
            price = ValidateForm.isDouble(priceStr, "Giá");
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        if (price < 0) {
            throw new IllegalArgumentException("Giá phải là số dương!");
        }

        Category category = categories.get(choiceCategory - 1);

        return new Books(bookID, bookName, author, yearPublished, price, quantity, category);
    }

    // Kiểm tra dữ liệu nhập vào form
    private void validateBookInput(String bookID, String bookName, String author, String yearPublishedStr,
                                   String priceStr, String quantityStr, int choiceCategory) {
        if (bookID.isEmpty() || bookName.isEmpty() || author.isEmpty() ||
                yearPublishedStr.isEmpty() || priceStr.isEmpty() || quantityStr.isEmpty() || choiceCategory <= 0) {
            throw new IllegalArgumentException("Vui lòng điền đầy đủ thông tin!");
        }
    }

    // Lấy điều kiện tìm kiếm từ form
    public BookSearch getFormBookSearch() {
        String bookID = bookView.getTextFieldBookId1().getText().trim();
        String bookName = bookView.getTextFieldBookName1().getText().trim();
        String author = bookView.getTextFieldAuthor1().getText().trim();
        String priceFromStr = bookView.getTextFieldPriceFrom().getText().trim();
        String priceToStr = bookView.getTextFieldPriceTo().getText().trim();
        String yearFromStr = bookView.getTextFieldYearFrom().getText().trim();
        String yearToStr = bookView.getTextFieldYearTo().getText().trim();
        int index = bookView.getComboBoxCategory_search().getSelectedIndex();

        if (bookID.isEmpty() && bookName.isEmpty() && author.isEmpty() && priceFromStr.isEmpty()
                && priceToStr.isEmpty() && yearFromStr.isEmpty() && yearToStr.isEmpty() && index <= 0) {
            throw new IllegalArgumentException("Vui lòng thêm điều kiện tìm kiếm!");
        }

        Integer yearFrom = null;
        if (!yearFromStr.isEmpty()) {
            try {
                yearFrom = ValidateForm.isInteger(yearFromStr, "Năm xuất bản từ");
                if (yearFrom < 0 || yearFrom > 2025) {
                    throw new IllegalArgumentException("Năm xuất bản từ phải nằm trong khoảng từ 0 đến 2025!");
                }
            } catch (Exception e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }

        Integer yearTo = null;
        if (!yearToStr.isEmpty()) {
            try {
                yearTo = ValidateForm.isInteger(yearToStr, "Năm xuất bản đến");
                if (yearTo > 2025 || (yearFrom != null && yearFrom > yearTo)) {
                    throw new IllegalArgumentException("Năm xuất bản đến phải lớn hơn hoặc bằng năm từ và nhỏ hơn 2025!");
                }
            } catch (Exception e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }

        Double priceFrom = null;
        if (!priceFromStr.isEmpty()) {
            try {
                priceFrom = ValidateForm.isDouble(priceFromStr, "Giá từ");
                if (priceFrom < 0) {
                    throw new IllegalArgumentException("Giá từ phải là số dương!");
                }
            } catch (Exception e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }

        Double priceTo = null;
        if (!priceToStr.isEmpty()) {
            try {
                priceTo = ValidateForm.isDouble(priceToStr, "Giá đến");
                if (priceTo < 0) {
                    throw new IllegalArgumentException("Giá đến phải là số dương!");
                }
                if (priceFrom != null && priceTo < priceFrom) {
                    throw new IllegalArgumentException("Giá đến phải lớn hơn hoặc bằng giá từ!");
                }
            } catch (Exception e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }

        String categoryName = null;
        if (index > 0) {
            categoryName = categories.get(index - 1).getCategoryName();
        }

        return new BookSearch(bookID, categoryName, bookName, author, yearFrom, yearTo, priceFrom, priceTo);
    }

    // Xóa dữ liệu form
    public void clearForm() {
        bookView.getTextFieldBookId().setText("");
        bookView.getTextFieldBookName().setText("");
        bookView.getTextFieldAuthor().setText("");
        bookView.getTextFieldPrice().setText("");
        bookView.getTextFieldQuantity().setText("");
        bookView.getTextFieldYearPublished().setText("");
        bookView.getComboBoxCategory().setSelectedIndex(0);
    }

    // Cập nhật bảng sách
    public void updateTable(ArrayList<Books> listBook) {
        bookView.getTableModel().setRowCount(0);
        for (Books book : listBook) {
            Object[] row = {
                    book.getBookID(),
                    book.getCategory().getCategoryName(),
                    book.getBookName(),
                    book.getAuthor(),
                    book.getYearPublished(),
                    book.getQuantity(),
                    book.getPrice()
            };
            bookView.getTableModel().addRow(row);
        }
    }

    // Chức năng thống kê dữ liệu sách
    public void statistical(ArrayList<Books> listBook) {
        if (listBook.isEmpty()) {
            bookView.getLabelBookTypeCountValue().setText("0");
            bookView.getLabelTotalBooksValue().setText("0");
            bookView.getLabelPriceMinValue().setText("0");
            bookView.getLabelPriceMaxValue().setText("0");
        } else {
            // Tổng số sách
            bookView.getLabelTotalBooksValue().setText(String.valueOf(listBook.size()));

            // Tìm giá min và max
            double minPrice = Double.MAX_VALUE;
            double maxPrice = Double.MIN_VALUE;
            for (Books book : listBook) {
                minPrice = Math.min(minPrice, book.getPrice());
                maxPrice = Math.max(maxPrice, book.getPrice());
            }
            bookView.getLabelPriceMinValue().setText(String.valueOf(minPrice));
            bookView.getLabelPriceMaxValue().setText(String.valueOf(maxPrice));

            // Đếm số loại sách (dựa trên Category)
            Set<String> categorySet = new HashSet<>();
            for (Books book : listBook) {
                categorySet.add(book.getCategory().getCategoryName());
            }
            bookView.getLabelBookTypeCountValue().setText(String.valueOf(categorySet.size()));
        }
    }
}