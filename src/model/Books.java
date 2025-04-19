package model;


public class Books {
    private String bookID;
    private String bookName;
    private String author;
    private int yearPublished;
    private double price;
    private int quantity;
    private Category category;
    public Books() {
    }
    //ham nay la sach trong chi tiet hoa don
    public Books(String bookID, int quantity,double price){
        this.bookID = bookID;
        this.quantity = quantity;
        this.price = price;
    }
    public Books(String bookID, String bookName, String author, int yearPublished, double price, int quantity, Category category) {
        this.bookID = bookID;
        this.bookName = bookName;
        this.author = author;
        this.yearPublished = yearPublished;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }

    public String getBookID() {
        return bookID;
    }
    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYearPublished() {
        return yearPublished;
    }

    public void setYearPublished(int yearPublished) {
        this.yearPublished = yearPublished;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}