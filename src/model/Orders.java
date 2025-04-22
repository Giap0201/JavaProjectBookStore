package model;


import java.util.Date;

public class Orders {
    private String orderId;
    private Customers customer;
    private String status ;
    private Date orderDate;

    public Orders(){}

    public Orders(String orderId, Customers customer, Date orderDate, String status) {
        this.orderId = orderId;
        this.customer = customer;
        this.orderDate = orderDate;
        this.status = status;
    }

    public Orders(String orderId, Customers customer, String status, Date orderDate) {
        this.orderId = orderId;
        this.customer = customer;
        this.status = status;
        this.orderDate = orderDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Customers getCustomer() {
        return customer;
    }

    public void setCustomer(Customers customer) {
        this.customer = customer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
}
