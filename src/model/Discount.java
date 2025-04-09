package model;

import java.sql.Date;

public class Discount {
    private String discountID;
    private String nameDiscount;
    private String typeDiscount;
    private Date startDate;
    private Date endDate;

    public Discount() {
    }

    public Discount(String discountID, String nameDiscount, String typeDiscount, Date startDate, Date endDate) {
        this.discountID = discountID;
        this.nameDiscount = nameDiscount;
        this.typeDiscount = typeDiscount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getDiscountID() {
        return discountID;
    }

    public void setDiscountID(String discountID) {
        this.discountID = discountID;
    }

    public String getNameDiscount() {
        return nameDiscount;
    }

    public void setNameDiscount(String nameDiscount) {
        this.nameDiscount = nameDiscount;
    }

    public String getTypeDiscount() {
        return typeDiscount;
    }

    public void setTypeDiscount(String typeDiscount) {
        this.typeDiscount = typeDiscount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}


