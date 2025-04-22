package service;

import dao.BookDAO;
import model.BookSearch;
import model.Books;

import java.util.ArrayList;


public class BookService {
    private BookDAO bookDAO;
    public BookService() {
        this.bookDAO = new BookDAO();
    }
    public BookService(BookDAO dao) {
        this.bookDAO = dao;
    }
    public boolean addBook(Books book) {
        return bookDAO.insert(book) > 0;
    }
    public ArrayList<Books> getAllBooks() {
        return bookDAO.getAll();
    }
    public boolean updateBook(Books book) {
        return bookDAO.update(book) > 0;
    }
    public boolean deleteBook(Books book){
        return bookDAO.delete(book) > 0;
    }
    public ArrayList<Books> listBookSearch(BookSearch condition){
        return bookDAO.listSearchBooks(condition);
    }
    public ArrayList<Books> bookSearchByName(String bookName){
        return bookDAO.SearchBookByName(bookName);
    }
    public Books getBookByID(String bookID){
        return bookDAO.getBookByID(bookID);
    }
    public boolean updateImageUrl(String bookId, String newUrlImage){
        return bookDAO.updateImageUrl(bookId, newUrlImage);
    }
}
