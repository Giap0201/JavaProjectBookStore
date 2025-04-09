package service;

import dao.DiscountDTO;
import model.Discount;

import java.util.ArrayList;

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
}
