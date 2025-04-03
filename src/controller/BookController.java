package controller;

import model.Books;
import service.BookService;
import view.BookView;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BookController implements ActionListener {
    private BookView bookView;
    private BookService bookService;

    public BookController(BookView bookView) {
        this.bookView = bookView;
        this.bookService = new BookService();
        bookView.tableModel = new DefaultTableModel();
        // Đảm bảo bảng được cập nhật với tất cả các sách khi khởi tạo
        this.bookView.updateTable(bookService.getAllBooks());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bookView.getBtnAdd()) {
            bookView.clear();  // Xóa các trường nhập liệu
        } else if (e.getSource() == bookView.getBtnView()) {
            try {
                // Lấy dữ liệu từ các trường nhập liệu và thêm vào cơ sở dữ liệu
                String bookName = bookView.getTextFieldBookName().getText().trim();
                String author = bookView.getTextFieldAuthor().getText().trim();
                String bookID = bookView.getTextFieldBookId().getText().trim();
                String yearTest = bookView.getTextFieldYearPublished().getText().trim();
                int yearPublished = Integer.parseInt(yearTest);
                float price = Float.parseFloat(bookView.getTextFieldPrice().getText().trim());
                int quantity = Integer.parseInt(bookView.getTextFieldQuantity().getText().trim());
                String category = bookView.getTextFieldCategory().getText().trim();

                // Tạo đối tượng Book và thêm vào database
                Books book = new Books(bookID, bookName, author, yearPublished, price, quantity, category);
                boolean check = bookService.insertBook(book);

                if (check) {
                    bookView.showMessage("Book added successfully.");
                    bookView.updateTable(bookService.getAllBooks());  // Cập nhật bảng
                    bookView.clear();  // Xóa các trường nhập liệu
                } else {
                    bookView.showMessage("Insert Book Failed.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                bookView.showMessage("An error occurred while adding the book.");
            }
        } else if (e.getSource() == bookView.getBtnDelete()) {
            bookView.updateTable(bookService.getAllBooks());  // Cập nhật bảng

        }
    }
}
