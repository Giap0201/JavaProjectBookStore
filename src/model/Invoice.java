package model;

import java.util.Date;

public class Invoice {
    private String invoiceID;
    private String transactionID;
    private Date dayOfEstablishment;
    private double totalMoney, intoMoney;
    private Employees employees;
    private Customers customers;
    private Discount discount;

    public Invoice() {
    }

    public Invoice(String invoiceID, String transactionID, Date dayOfEstablishment, double totalMoney, double intoMoney, Employees employees, Customers customers, Discount discount) {
        this.invoiceID = invoiceID;
        this.transactionID = transactionID;
        this.dayOfEstablishment = dayOfEstablishment;
        this.totalMoney = totalMoney;
        this.intoMoney = intoMoney;
        this.employees = employees;
        this.customers = customers;
        this.discount = discount;
    }

    public String getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(String invoiceID) {
        this.invoiceID = invoiceID;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public Date getDayOfEstablishment() {
        return dayOfEstablishment;
    }

    public void setDayOfEstablishment(Date dayOfEstablishment) {
        this.dayOfEstablishment = dayOfEstablishment;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public double getIntoMoney() {
        return intoMoney;
    }

    public void setIntoMoney(double intoMoney) {
        this.intoMoney = intoMoney;
    }

    public Employees getEmployees() {
        return employees;
    }

    public void setEmployees(Employees employees) {
        this.employees = employees;
    }

    public Customers getCustomers() {
        return customers;
    }

    public void setCustomers(Customers customers) {
        this.customers = customers;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "invoiceID='" + invoiceID + '\'' +
                ", transactionID='" + transactionID + '\'' +
                ", dayOfEstablishment=" + dayOfEstablishment +
                ", totalMoney=" + totalMoney +
                ", intoMoney=" + intoMoney +
                ", employees=" + employees +
                ", customers=" + customers +
                ", discount=" + discount +
                '}';
    }
}
