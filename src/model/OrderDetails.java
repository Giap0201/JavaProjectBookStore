package model;

public class OrderDetails {
    private String orderId;
    private float discount;
    private Books book;
    private int quantity;
    private double total;

    public OrderDetails(){}

    public OrderDetails(String orderId,float discount, Books book,int quantity) {
        this.orderId = orderId;
        this.discount = discount;
        this.book = book;
        this.quantity = quantity;
        this.total = this.setTotal();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public Books getBook() {
        return book;
    }

    public void setBook(Books book) {
        this.book = book;
    }

    public double setTotal() {
        if(discount > 0){
            total = book.getPrice()- discount * book.getPrice()* book.getQuantity() /100;
        }else{
            total = book.getPrice()*book.getQuantity();
        }
        return total;
    }

    public double getTotal() {
        return total;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
