package service;

import dao.DiscountDTO;
import model.Discount;

import java.sql.Array;
import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class DiscountService {
    private DiscountDTO discountDTO;
    public DiscountService(){
        this.discountDTO = new DiscountDTO();
    }
    public DiscountService(DiscountDTO discountDTO) {
        this.discountDTO = discountDTO;
    }
    public boolean addDiscount(Discount discount) {
        return discountDTO.insert(discount) > 0;
    }
    public ArrayList<Discount> getAllDiscounts() {
        return discountDTO.selectAll();
    }
    public  boolean deleteDiscount(Discount discount) {
        return discountDTO.delete(discount)>0;
    }
    public boolean deleteCondition(String discountID){
        return discountDTO.deleteCondition(discountID)>0;
    }
    public boolean updateDiscount(Discount discount) {
        return discountDTO.update(discount)>0;
    }
    public ArrayList<Discount> listDiscountsSearch(Date dateStart, Date dateEnd) {
        return discountDTO.searchCondition(dateStart, dateEnd);
    }
    public ArrayList<String> listNameDiscount(){
        ArrayList<Discount> discounts = getAllDiscounts();
        ArrayList<String> nameDiscount = new ArrayList<>();
        for (Discount discount : discounts) {
            nameDiscount.add(discount.getNameDiscount());
        }
        return nameDiscount;
    }
    public LinkedHashMap<String,String> listMapDiscount(){
        ArrayList<Discount> discounts = getAllDiscounts();
        LinkedHashMap<String,String> mapDiscount = new LinkedHashMap<>();
        for(Discount x : discounts){
            mapDiscount.put(x.getDiscountID(), x.getNameDiscount());
        }
        return mapDiscount;
    }
}
