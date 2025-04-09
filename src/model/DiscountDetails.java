package model;

public class DiscountDetails {
    private Discount discount;
    private Books book;
    private float percent;

    public DiscountDetails(Discount discount, float percent, Books book) {
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

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }

    @Override
    public String toString() {
        return "DiscountDetails{" +
                "discount=" + discount +
                ", book=" + book +
                ", percent=" + percent +
                '}';
    }
}
