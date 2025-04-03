package model;

import java.util.Date;

public class Customers extends Person{
    private String customerID ;
    private String spending ;
    private String note ;

    public Customers(String customerID, String spending, String note) {
        this.customerID = customerID;
        this.spending = spending;
        this.note = note;
    }

    public Customers(String firstName, String lastName, Date dateOfBirth, String phoneNumber, String email, String gender, String customerID, String spending, String note) {
        super(firstName, lastName, dateOfBirth, phoneNumber, email, gender);
        this.customerID = customerID;
        this.spending = spending;
        this.note = note;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getSpending() {
        return spending;
    }

    public void setSpending(String spending) {
        this.spending = spending;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
