package model;

import java.util.ArrayList;

public class ListBook {
    private ArrayList<Books> listBook;
    public ListBook() {
        listBook = new ArrayList<>();
    }
    public ArrayList<Books> getListBook() {
        return listBook;
    }

    public void setListBook(ArrayList<Books> listBook) {
        this.listBook = listBook;
    }
}
