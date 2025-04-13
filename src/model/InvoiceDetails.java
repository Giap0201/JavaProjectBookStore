package model;

public class InvoiceDetails {
    private Invoice invoice;
    private Books books;
    private int quantity;
    private double price;

    public InvoiceDetails(Invoice invoice, Books books, int quantity, double price) {
        this.invoice = invoice;
        this.books = books;
        this.quantity = quantity;
        this.price = price;
    }

    public InvoiceDetails() {
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
}

