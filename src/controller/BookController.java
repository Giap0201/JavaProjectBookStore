package controller;

import model.BookSearch;
import model.Books;
import model.Category;
import service.BookService;
import service.CategoryService;
import view.BookView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
    }

    // phuong thuc nay co the nem ra loi trong qua trinh chay, phai xu li trong try
    // catch
    public Books getBookInForm() throws Exception {
        String bookID = bookView.getTextFieldBookId().getText().trim();
        String bookName = bookView.getTextFieldBookName().getText().trim();
        String author = bookView.getTextFieldAuthor().getText().trim();
        String yearPublishedStr = bookView.getTextFieldYearPublished().getText().trim();
        String priceStr = bookView.getTextFieldPrice().getText().trim();
        String quantityStr = bookView.getTextFieldQuantity().getText().trim();
        if (bookID.isEmpty() || author.isEmpty() || bookName.isEmpty() || yearPublishedStr.isEmpty()
                || priceStr.isEmpty() || quantityStr.isEmpty()) {
            throw new Exception("Vui lòng điền đầy đủ thông tin!");
            // khi gap loi thi chu dong tao ra exception va dung, nhay vao try catch de xu
            // li loi nay
        }
        int yearPublished;
        try {
            yearPublished = Integer.parseInt(yearPublishedStr);
            if (yearPublished > 2025 || yearPublished < 0)
                throw new Exception("Năm xuất bản không hợp lệ!!");
            // loi numberFormatException là loi chuyen tu String sang kieu so
        } catch (NumberFormatException e) {
            throw new Exception("Năm xuất bản phải là số nguyên");
        }
        double price;
        try {
            price = Double.parseDouble(priceStr);
            if (price < 0)
                throw new Exception("Giá phải là số dương!!");
        } catch (NumberFormatException e) {
            throw new Exception("Giá sách phải là số thực!!");
        }
        int quantity;
        try {
            quantity = Integer.parseInt(quantityStr);
            if (quantity < 0)
                throw new Exception("Số lượng phải là số dương!!");
        } catch (NumberFormatException e) {
            throw new Exception("Số lượng phải là số !!");
        }
        int choiceCaterogy = bookView.getComboBoxCategory().getSelectedIndex();
        if (choiceCaterogy <= 0)
            throw new Exception("Vui lòng chọn thể loại!!");
        Category category = categories.get(choiceCaterogy - 1);
        return new Books(bookID, bookName, author, yearPublished, price, quantity, category);
    }

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
            updateTable(bookService.getAllBooks());
        }
    }

    public void addBook() {
        try {
            Books book = getBookInForm();
            boolean check = bookService.addBook(book);
            if (check) {
                JOptionPane.showMessageDialog(bookView, "Thêm sách thành công!");
                updateTable(bookService.getAllBooks());
                clearForm();
            } else {
                JOptionPane.showMessageDialog(bookView, "Không thể thêm sách. Có thể mã sách đã tồn tại.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();// nhung loi khong bat duoc se hien thi trong consolog
            JOptionPane.showMessageDialog(bookView, ex.getMessage());
        }
    }

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
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(bookView, "Lỗi");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

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

    public BookSearch getFormBookSearch() throws Exception {
        String bookID = bookView.getTextFieldBookId1().getText().trim();
        String bookName = bookView.getTextFieldBookName1().getText().trim();
        String author = bookView.getTextFieldAuthor1().getText().trim();
        String priceFromStr = bookView.getTextFieldPriceFrom().getText().trim();
        String priceToStr = bookView.getTextFieldPriceTo().getText().trim();
        String yearFromStr = bookView.getTextFieldYearFrom().getText().trim();
        String yearToStr = bookView.getTextFieldYearTo().getText().trim();
        int index = bookView.getComboBoxCategory_search().getSelectedIndex();
        if(bookID.isEmpty()&& bookName.isEmpty() && author.isEmpty() && priceFromStr.isEmpty()
        && priceToStr.isEmpty() && yearFromStr.isEmpty() && yearToStr.isEmpty() && index <= 0) throw new Exception("vui lòng thêm điều kiện tìm kiếm!!");
        Integer yearFrom = null;
        if (!yearFromStr.isEmpty()) {
            try {
                yearFrom = Integer.parseInt(yearFromStr);
                if (yearFrom < 0 || yearFrom > 2025) {
                    throw new Exception("Năm xuất bản từ phải nằm trong khoảng từ 0 đến 2025!");
                }
            } catch (NumberFormatException e) {
                throw new Exception("Năm xuất bản từ phải là số nguyên!");
            }
        }
        Integer yearTo = null;
        if (!yearToStr.isEmpty()) {
            try {
                yearTo = Integer.parseInt(yearToStr);
                if (yearTo < 0 || yearTo > 2025) {
                    throw new Exception("Năm xuất bản đến phải nằm trong khoảng từ 0 đến 2025!");
                }
                if (yearFrom != null && yearTo < yearFrom) {
                    throw new Exception("Năm xuất bản đến phải lớn hơn hoặc bằng năm từ!");
                }
            } catch (NumberFormatException e) {
                throw new Exception("Năm xuất bản đến phải là số nguyên!");
            }
        }
        Double priceFrom = null;
        if (!priceFromStr.isEmpty()) {
            try {
                priceFrom = Double.parseDouble(priceFromStr);
                if (priceFrom < 0) {
                    throw new Exception("Giá từ phải là số dương!");
                }
            } catch (NumberFormatException e) {
                throw new Exception("Giá từ phải là số thực!");
            }
        }
        Double priceTo = null;
        if (!priceToStr.isEmpty()) {
            try {
                priceTo = Double.parseDouble(priceToStr);
                if (priceTo < 0) {
                    throw new Exception("Giá đến phải là số dương!");
                }
                if (priceFrom != null && priceTo < priceFrom) {
                    throw new Exception("Giá đến phải lớn hơn hoặc bằng giá từ!");
                }
            } catch (NumberFormatException e) {
                throw new Exception("Giá đến phải là số thực!");
            }
        }
        String categoryName = null;
        if (index > 0) {
            categoryName = categories.get(index - 1).getCategoryName();
        }
        return new BookSearch(bookID, categoryName, bookName, author, yearFrom, yearTo, priceFrom, priceTo);
    }

    public void searchBooks() {
        try {
            BookSearch condition = getFormBookSearch();
            ArrayList<Books> booksResult = bookService.listBookSearch(condition);
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
            Object[] row = { book.getBookID(), book.getCategory().getCategoryName(),
                    book.getBookName(), book.getAuthor(), book.getYearPublished(), book.getQuantity(),
                    book.getPrice(), };
            bookView.getTableModel().addRow(row);
        }
    }
}