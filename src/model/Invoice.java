package model;

import java.sql.Date;

public class Invoice {
    private String invoiceID;
    private Date date;
    private Customers customer;
    private Employees employee;
    private String status;
    private double totalMonney;
}
