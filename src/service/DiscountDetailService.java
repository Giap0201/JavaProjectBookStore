package service;

import dao.DiscountDetailsDTO;
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
}
