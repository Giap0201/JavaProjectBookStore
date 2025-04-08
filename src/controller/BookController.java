package controller;

import model.BookSearch;
import model.Books;
import model.Category;
import service.BookService;
import service.CategoryService;
import util.ValidateForm;
import view.BookView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class BookController implements ActionListener {
    private BookView bookView;
    private BookService bookService;
    private CategoryService categoryService;
    private ArrayList<Category> categories;

    public BookController(BookView bookView) {
        this.bookView = bookView;
        this.bookService = new BookService();
        this.categoryService = new CategoryService();
        this.categories = categoryService.getCategory();
        // them su kien cho bang
        addTableSelectionListener();
        updateTable(bookService.getAllBooks());
        statistical(bookService.getAllBooks());

    }

    // cac su kien cua cac nut bam thuc hien chuc nang
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bookView.getBtnAdd()) {
            addBook();
        } else if (e.getSource() == bookView.getBtnChange()) {
            updateBook();
        } else if (e.getSource() == bookView.getBtnDelete()) {
            deleteBook();
        } else if (e.getSource() == bookView.getBtnSearch()) {
            searchBooks();
        } else if (e.getSource() == bookView.getBtnReset()) {
            ArrayList<Books> allBooks = bookService.getAllBooks();
            statistical(allBooks);
            clearForm();
        } else if(e.getSource() == bookView.getBtnSaveFile()){
            exportToCSV();
        }
    }

    // chuc nang them sach
    public void addBook() {
        try {
            Books book = getBookInForm();
            boolean check = bookService.addBook(book);
            if (check) {
                JOptionPane.showMessageDialog(bookView, "Thêm sách thành công!");
                updateTable(bookService.getAllBooks());
                statistical(bookService.getAllBooks());
                clearForm();
            } else {
                JOptionPane.showMessageDialog(bookView, "Không thể thêm sách. Có thể mã sách đã tồn tại.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();// nhung loi khong bat duoc se hien thi trong consolog
            JOptionPane.showMessageDialog(bookView, ex.getMessage());
        }
    }

    // chuc nang cap nhat sach
    public void updateBook() {
        String bookID = bookView.getTextFieldBookId().getText().trim();
        if (bookID.equals("") || bookID == null) {
            JOptionPane.showMessageDialog(bookView, "Vui lòng chọn sách cần sửa!!");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(bookView, "Bạn có muốn cập nhập thông tin sách không?",
                "Xác nhận sửa", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                // lay du lieu tu form
                Books book = getBookInForm();
                boolean check = bookService.updateBook(book);
                if (check) {
                    JOptionPane.showMessageDialog(bookView, "Sửa sách thành công!");
                    updateTable(bookService.getAllBooks()); // cap nhat lai table
                    statistical(bookService.getAllBooks());
                    clearForm(); // lam trong form
                } else {
                    JOptionPane.showMessageDialog(bookView, "Không thể sửa sách. Vui lòng thử lại.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(bookView, ex.getMessage());
            }
        }
    }

    // chuc nang xoa sach
    public void deleteBook() {
        String bookID = bookView.getTextFieldBookId().getText().trim();
        if (bookID.equals("") || bookID == null) {
            JOptionPane.showMessageDialog(bookView, "Vui lòng chọn sách cần xoá!!");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(bookView, "Bạn có chắc chắn muốn xoá sách ?",
                "Xác nhận xoá", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Books book = getBookInForm();
                boolean check = bookService.deleteBook(book);
                if (check == true) {
                    JOptionPane.showMessageDialog(bookView, "Xoá thành công!!");
                    updateTable(bookService.getAllBooks());
                    statistical(bookService.getAllBooks());
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(bookView, "Lỗi");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // them su kien cho bang table
    public void addTableSelectionListener() {
        bookView.getTable().getSelectionModel().addListSelectionListener(e -> {
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
        });
    }

    // phuong thuc nay co the nem ra loi trong qua trinh chay, phai xu li trong try
    // catch
    public Books getBookInForm() throws Exception {
        //thao tac lay du lieu tu view
        String bookID = bookView.getTextFieldBookId().getText().trim();
        String bookName = bookView.getTextFieldBookName().getText().trim();
        String author = bookView.getTextFieldAuthor().getText().trim();
        String yearPublishedStr = bookView.getTextFieldYearPublished().getText().trim();
        String priceStr = bookView.getTextFieldPrice().getText().trim();
        String quantityStr = bookView.getTextFieldQuantity().getText().trim();
        int choiceCategory = bookView.getComboBoxCategory().getSelectedIndex();

        if (bookID.isEmpty() || author.isEmpty() || bookName.isEmpty() || yearPublishedStr.isEmpty()
                || priceStr.isEmpty() || quantityStr.isEmpty() || choiceCategory <= 0) {
            throw new Exception("Vui lòng điền đầy đủ thông tin!");
        }
        //vi o da kiem tra toan bo dieu kien khong rong roi nen co the khong dung lop Grapher
        int yearPublished = ValidateForm.isInteger(yearPublishedStr, "Năm xuất bản");
        if (yearPublished < 0 || yearPublished > 2025)
            throw new Exception("Năm xuất bản không hợp lệ!!");
        int quantity = ValidateForm.isInteger(quantityStr, "Số lượng");
        if (quantity < 0)
            throw new Exception("Số lượng phải là số dương!!");
        double price = ValidateForm.isDouble(priceStr, "Giá");
        if (price < 0)
            throw new Exception("Giá phải là số dương!!");
        if (choiceCategory <= 0)
            throw new Exception("Vui lòng chọn thể loại!!");
        Category category = categories.get(choiceCategory - 1);
        return new Books(bookID, bookName, author, yearPublished, price, quantity, category);
    }

    public BookSearch getFormBookSearch() throws Exception {
        String bookID = bookView.getTextFieldBookId1().getText().trim();
        String bookName = bookView.getTextFieldBookName1().getText().trim();
        String author = bookView.getTextFieldAuthor1().getText().trim();
        String priceFromStr = bookView.getTextFieldPriceFrom().getText().trim();
        String priceToStr = bookView.getTextFieldPriceTo().getText().trim();
        String yearFromStr = bookView.getTextFieldYearFrom().getText().trim();
        String yearToStr = bookView.getTextFieldYearTo().getText().trim();
        int index = bookView.getComboBoxCategory_search().getSelectedIndex();
        if (bookID.isEmpty() && bookName.isEmpty() && author.isEmpty() && priceFromStr.isEmpty()
                && priceToStr.isEmpty() && yearFromStr.isEmpty() && yearToStr.isEmpty() && index <= 0)
            throw new Exception("Vui lòng thêm điều kiện tìm kiếm!!");
        Integer yearFrom = null;
        if (!yearFromStr.isEmpty()) {
            yearFrom = ValidateForm.isInteger(yearFromStr, "Năm xuất bản");
            if (yearFrom < 0 || yearFrom > 2025) {
                throw new Exception("Năm xuất bản từ phải nằm trong khoảng từ 0 đến 2025!");
            }
        }
        Integer yearTo = null;
        if (!yearToStr.isEmpty()) {
            yearTo = ValidateForm.isInteger(yearToStr, "Năm xuất bản");
            if ((yearFrom != null && yearFrom > yearTo) || yearTo > 2025) {
                throw new Exception("Năm xuất bản đến phải lớn hơn hoặc bằng năm từ và bé hơn 2025!");
            }
        }
        // neu nhap vao moi thuc hien thao tac ben duoi
        Double priceFrom = null;
        if (!priceFromStr.isEmpty()) {
            priceFrom = ValidateForm.isDouble(priceFromStr, "Giá");
            if (priceFrom < 0) {
                throw new Exception("Giá từ phải là số dương!");
            }
        }
        Double priceTo = null;
        // kiem tra neu nhap vao thi moi thao tac ben duoi
        if (!priceToStr.isEmpty()) {
            priceTo = ValidateForm.isDouble(priceToStr, "Giá");
            if (priceTo < 0) {
                throw new Exception("Giá đến phải là số dương!");
            }
            if (priceFrom != null && priceTo < priceFrom) {
                throw new Exception("Giá đến phải lớn hơn hoặc bằng giá từ!");
            }
        }
        String categoryName = null;
        if (index > 0) {
            categoryName = categories.get(index - 1).getCategoryName();
        }
        return new BookSearch(bookID, categoryName, bookName, author, yearFrom, yearTo, priceFrom, priceTo);
    }

    // chuc nang tim kiem sach
    public void searchBooks() {
        try {
            BookSearch condition = getFormBookSearch();
            ArrayList<Books> booksResult = bookService.listBookSearch(condition);
            statistical(booksResult);
            if (booksResult.isEmpty()) {
                JOptionPane.showMessageDialog(bookView, "Không tìm thấy sách!!!");
                updateTable(booksResult);
            } else {
                updateTable(booksResult);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(bookView, e.getMessage());
        }
    }

    // ham xoa du lieu form
    public void clearForm() {
        bookView.getTextFieldBookId().setText("");
        bookView.getTextFieldBookName().setText("");
        bookView.getTextFieldAuthor().setText("");
        bookView.getTextFieldPrice().setText("");
        bookView.getTextFieldQuantity().setText("");
        bookView.getTextFieldYearPublished().setText("");
        bookView.getComboBoxCategory().setSelectedIndex(0);
    }

    // ham cap nhat lai bang
    public void updateTable(ArrayList<Books> listBook) {
        // xoa toan bo du lieu trong bang
        bookView.getTableModel().setRowCount(0);
        // them du lieu vao trong bang
        for (Books book : listBook) {
            Object[] row = {book.getBookID(), book.getCategory().getCategoryName(),
                    book.getBookName(), book.getAuthor(), book.getYearPublished(), book.getQuantity(),
                    book.getPrice(),};
            bookView.getTableModel().addRow(row);
        }
    }
    // Import thêm dòng này nếu cần

    public void exportToCSV() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file CSV");
        int userSelection = fileChooser.showSaveDialog(bookView);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.endsWith(".csv")) {
                filePath += ".csv";
            }

            try (PrintWriter writer = new PrintWriter(
                    new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8"))) {

                // Ghi tiêu đề
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

                JOptionPane.showMessageDialog(bookView, "Xuất file CSV thành công!");
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(bookView, "Lỗi khi ghi file CSV: " + ex.getMessage());
            }
        }
    }
    //chuc nang tu dong thong ke du lieu sau khi tim kiem
    public void statistical(ArrayList<Books> listBook) {
        //neu danh sach rong thi 0 het
        if (listBook.isEmpty()) {
            bookView.getLabelBookTypeCountValue().setText("0");
            bookView.getLabelTotalBooksValue().setText("0");
            bookView.getLabelPriceMinValue().setText("0");
            bookView.getLabelPriceMaxValue().setText("0");
        } else {
            String totalBooksValue = String.valueOf(listBook.size());
            bookView.getLabelTotalBooksValue().setText(totalBooksValue);
            Double minPrice = 1e9;
            Double maxPrice = -1e9;
            //thao tac tim gia min
            for (int i = 0; i < listBook.size(); i++) {
                minPrice = Math.min(minPrice, listBook.get(i).getPrice());
                maxPrice = Math.max(maxPrice, listBook.get(i).getPrice());
            }
            bookView.getLabelPriceMinValue().setText(String.valueOf(minPrice));
            bookView.getLabelPriceMaxValue().setText(String.valueOf(maxPrice));
            //thao tac thong ke so loai sach
            Set<String> categorySet = new HashSet<>();
            for (Books books : listBook) {
                categorySet.add(books.getCategory().getCategoryName());
            }
            bookView.getLabelBookTypeCountValue().setText(String.valueOf(categorySet.size()));
        }
    }
}