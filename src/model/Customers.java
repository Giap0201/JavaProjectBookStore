package model;

import java.util.Date;

public class Customers extends Person{
    private String customerID ;
    private double totalMoney ;
    private Date creationDate ;
    private String note ;

    public Customers() {}

    public Customers(String firstName, String lastName, Date dateOfBirth, String phoneNumber, String email, String gender, String customerID, double totalMoney, Date creationDate, String note) {
        super(firstName, lastName, dateOfBirth, phoneNumber, email, gender);
        this.customerID = customerID;
        this.totalMoney = totalMoney;
        this.creationDate = creationDate;
        this.note = note;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
