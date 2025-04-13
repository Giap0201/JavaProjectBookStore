package model;

import java.sql.Date;

public class Invoice {
    private String invoiceID;
    private Date date;
    private Customers customer;
    private Employees employee;
    private String status;

    public Invoice() {
    }

    public Invoice(String invoiceID, Date date, Customers customer, Employees employee, String status) {
        this.invoiceID = invoiceID;
        this.date = date;
        this.customer = customer;
        this.employee = employee;
        this.status = status;
    }

    public String getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(String invoiceID) {
        this.invoiceID = invoiceID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Customers getCustomer() {
        return customer;
    }

    public void setCustomer(Customers customer) {
        this.customer = customer;
    }

    public Employees getEmployee() {
        return employee;
    }

    public void setEmployee(Employees employee) {
        this.employee = employee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
