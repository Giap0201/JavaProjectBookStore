package service;

import dao.DiscountDetailsDTO;
import model.Discount;
import model.DiscountDetails;

import java.util.ArrayList;

public class DiscountDetailService {
    private DiscountDetailsDTO discountDetailsDTO;
    public DiscountDetailService(DiscountDetailsDTO discountDetailsDTO) {
        this.discountDetailsDTO = discountDetailsDTO;
    }
    public DiscountDetailService(){
        this.discountDetailsDTO = new DiscountDetailsDTO();
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
}
