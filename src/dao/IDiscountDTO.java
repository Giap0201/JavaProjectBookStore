package dao;

import model.Discount;

import java.util.ArrayList;

public interface IDiscountDTO {
    public int insert(Discount discount);
    public int update(Discount discount);
    public int delete(Discount discount);
    public ArrayList<Discount> selectAll();
    public int deleteCondition(String discountID);
}
