package controller;

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
    private ArrayList<Books> listOfBooks;


    public BookController(BookView bookView) {
        this.bookView = bookView;
        this.bookService = new BookService();
        this.categoryService = new CategoryService();
        this.categories = categoryService.getCategory();
        listOfBooks = bookService.getAllBooks();
        bookView.updateTable(listOfBooks);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bookView.getBtnAdd()) {
            try {
                // Lấy dữ liệu và kiểm tra hợp lệ
                String bookID = bookView.getTextFieldBookId().getText().trim();
                String bookName = bookView.getTextFieldBookName().getText().trim();
                String author = bookView.getTextFieldAuthor().getText().trim();
                String yearStr = bookView.getTextFieldYearPublished().getText().trim();
                String priceStr = bookView.getTextFieldPrice().getText().trim();
                String quantityStr = bookView.getTextFieldQuantity().getText().trim();

                if (bookID.isEmpty() || bookName.isEmpty() || author.isEmpty()
                        || yearStr.isEmpty() || priceStr.isEmpty() || quantityStr.isEmpty()) {
                    JOptionPane.showMessageDialog(bookView, "Vui lòng điền đầy đủ thông tin.");
                    return;
                }

                int yearPublished;
                try {
                    yearPublished = Integer.parseInt(yearStr);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(bookView, "Năm xuất bản phải là số nguyên.");
                    return;
                }

                double price;
                try {
                    price = Double.parseDouble(priceStr);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(bookView, "Giá sách phải là số thực.");
                    return;
                }

                int quantity;
                try {
                    quantity = Integer.parseInt(quantityStr);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(bookView, "Số lượng phải là số nguyên.");
                    return;
                }

                int index = bookView.getComboBoxCategory().getSelectedIndex();
                if (index <= 0) {
                    JOptionPane.showMessageDialog(bookView, "Vui lòng chọn thể loại.");
                    return;
                }

                Category category = categories.get(index - 1);
                Books book = new Books(bookID, bookName, author, yearPublished, price, quantity, category);

                boolean check = bookService.addBook(book);
                if (check) {
                    JOptionPane.showMessageDialog(bookView, "Thêm sách thành công!");
                    bookView.updateTable(bookService.getAllBooks());
                    bookView.clear();
                } else {
                    JOptionPane.showMessageDialog(bookView, "Không thể thêm sách. Có thể mã sách đã tồn tại.");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(bookView, "Lỗi không xác định: " + ex.getMessage());
            }
        }
    }
}