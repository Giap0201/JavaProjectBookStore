package service;

import dao.DiscountDetailsDAO;
import model.Discount;
import model.DiscountDetails;

import java.util.ArrayList;

public class DiscountDetailService {
    private DiscountDetailsDAO discountDetailsDTO;
    public DiscountDetailService(DiscountDetailsDAO discountDetailsDTO) {
        this.discountDetailsDTO = discountDetailsDTO;
    }
    public DiscountDetailService(){
        this.discountDetailsDTO = new DiscountDetailsDAO();
    }
    public void insert(ArrayList<DiscountDetails> discountDetails) {
        discountDetailsDTO.insert(discountDetails);
    }
    public ArrayList<DiscountDetails> getAllDiscountDetails() {
        return discountDetailsDTO.getAllDiscountDetails();
    }
    public boolean deleteDiscountDetails(String discountID, String bookID) {
        return discountDetailsDTO.deleteDiscountDetails(discountID,bookID)>0;
    }
    public ArrayList<DiscountDetails> getDiscountDetailsByID(Discount discount) {
        return discountDetailsDTO.getDiscountDetailsByID(discount);
    }
    public boolean deleteDiscountDetailsByID(String discountID) {
        return discountDetailsDTO.deleteAll(discountID) > 0;
    }

    public float getDiscountByBookId(String bookId){
        return discountDetailsDTO.getDiscount(bookId);
    }
}
