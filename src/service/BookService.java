package service;

import dao.BookDAO;
import model.Books;

import java.util.ArrayList;

public class BookService {
    private BookDAO bookDAO = new BookDAO();

    public boolean insertBook(Books book) {
        int rs = bookDAO.insert(book);
        if (rs == 0) {
            return false;
        }
        return true;
    }

    public ArrayList<Books> getAllBooks() {
        return bookDAO.getAll();
    }
}
