package model;

public class DiscountDetails {
    private Discount discount;
    private Books book;
    private double percent;

    public DiscountDetails(Discount discount, double percent, Books book) {
        this.discount = discount;
        this.percent = percent;
        this.book = book;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public Books getBook() {
        return book;
    }

    public void setBook(Books book) {
        this.book = book;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }
}
