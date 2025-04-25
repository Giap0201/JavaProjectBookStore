package model;

public class OrderDetails {
    private String orderId;
    private float discount;
    private Books book;
    private int quantity;
    private double total;


    public OrderDetails() {
    }
    public OrderDetails(String orderId, float discount, Books book, int quantity) {
        this.orderId = orderId;
        this.discount = discount;
        this.book = book;
        this.quantity = quantity;
        calculateTotal();
    }

    public double calculateTotal() {
        if (book != null && quantity > 0) {
            double price = book.getPrice();
            double effectivePrice = price * (1 - (double) discount / 100.0);
            this.total = effectivePrice * quantity;
        } else {
            this.total = 0;
        }
        return this.total;
    }


    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
        calculateTotal();
    }

    public Books getBook() {
        return book;
    }

    public void setBook(Books book) {
        this.book = book;
        calculateTotal();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity >= 0) {
            this.quantity = quantity;
            calculateTotal();
        } else {
            this.quantity = 0;
            calculateTotal();
        }
    }

    public double getTotal() {
        calculateTotal();
        return total;
    }

    // Phương thức tiện ích để lấy dữ liệu cho bảng
    public Object[] toTableRow() {
        if (book == null) return new Object[6];
        return new Object[]{
                book.getBookID(),
                book.getBookName(),
                quantity,
                book.getPrice(),
                discount,
                getTotal()
        };
    }
}
