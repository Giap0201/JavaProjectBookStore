package model;

public class InvoiceDetails {
    private Invoice invoice;
    private Books books;
    private int quantity;
    private double price;
    private double discount;
    private double totalMoney;
    private String invoiceID;
    private String bookID;

    public InvoiceDetails() {
    }

    public String getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(String invoiceID) {
        this.invoiceID = invoiceID;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public InvoiceDetails(String invoiceID, String bookID, int quantity, double price){
        this.invoiceID = invoiceID;
        this.bookID = bookID;
        this.quantity = quantity;
        this.price = price;
    }
    public InvoiceDetails(Invoice invoice, Books books, int quantity, double price){
        this.invoice = invoice;
        this.books = books;
        this.quantity = quantity;
        this.price = price;
    }
    public InvoiceDetails(Invoice invoice, Books books, int quantity, double price, double discount) {
        this.invoice = invoice;
        this.books = books;
        this.quantity = quantity;
        this.price = price;
        this.discount = discount;
        this.totalMoney = this.price * this.quantity * this.discount;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Books getBooks() {
        return books;
    }

    public void setBooks(Books books) {
        this.books = books;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    @Override
    public String toString() {
        return "InvoiceDetails{" +
                "invoice=" + invoice +
                ", books=" + books +
                ", quantity=" + quantity +
                ", price=" + price +
                ", discount=" + discount +
                ", totalMoney=" + totalMoney +
                '}';
    }
}

