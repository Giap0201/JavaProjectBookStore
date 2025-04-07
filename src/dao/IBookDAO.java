package dao;

import java.util.ArrayList;

import model.BookSearch;
import model.Books;

public interface IBookDAO {
    public int insert(Books book);
    public int delete(Books book);
    public int update(Books book);
    public ArrayList<Books> getAll();
    public ArrayList<Books> listSearchBooks(BookSearch condition);
    
}