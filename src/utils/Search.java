package utils;

import model.Books;
import service.BookService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Search {
    public static ArrayList<Books> bookAll = new BookService().getAllBooks();
    public static Map<String,Books> addBookToMap(){
        Map<String,Books> booksData = new HashMap<>();
        for (Books book : bookAll){
            booksData.put(book.getBookID(),book);
        }
        return booksData;
    }
    public static ArrayList<Books> listBooks(ArrayList<String> bookIDs) {
        Map<String,Books> booksData = addBookToMap();
        ArrayList<Books> booksList = new ArrayList<>();
        if (bookIDs == null || bookIDs.isEmpty()) {
            return booksList;
        }
        for (String bookID : bookIDs) {
            Books books = findBook(bookID,booksData);
            if(books != null){
                booksList.add(books);
            }
        }
        return booksList;
    }
    //ham tim kiem theo ID trong map
    public static Books findBook(String bookID,Map<String,Books> booksData) {
        if(bookID == null){
            return null;
        }
        return booksData.get(bookID);
    }
}
