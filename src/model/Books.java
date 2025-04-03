package model;

public class Books {
    private String bookID;
    private String bookName;
    private String author;
    private int yearPublished;
    private double price;
    private int quantity;
    private String category;

    public Books() {
    }

    public Books(String bookID, String bookName, String author, int yearPublished, double price, int quantity, String category) {
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Books{" +
                "bookID='" + bookID + '\'' +
                ", bookName='" + bookName + '\'' +
                ", author='" + author + '\'' +
                ", yearPublished=" + yearPublished +
                ", price=" + price +
                ", quantity=" + quantity +
                ", category='" + category + '\'' +
                '}';
    }
}
