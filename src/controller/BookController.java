package controller;

import model.Books;
import view.BookView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BookController implements ActionListener {
    private BookView bookView;
    private Books books;

    public BookController(BookView bookView) {
        this.bookView = bookView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
