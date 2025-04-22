package model;


public class Books {
    private String bookID;
    private String bookName;
    private String author;
    private int yearPublished;
    private double price;
    private int quantity;
    private Category category;
    private Discount discount;
    private String urlImage ;

    public Books() {
    }
    public Books(String bookID, String bookName, String author, int yearPublished, double price, int quantity, Category category,String urlImage) {
        this.bookID = bookID;
        this.bookName = bookName;
        this.author = author;
        this.yearPublished = yearPublished;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.urlImage = urlImage;
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


    public Books(String bookID, String bookName, String author, int yearPublished, double price, int quantity, Category category, Discount discount) {
        this.bookID = bookID;
        this.bookName = bookName;
        this.author = author;
        this.yearPublished = yearPublished;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.discount = discount;
    }
    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
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

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}