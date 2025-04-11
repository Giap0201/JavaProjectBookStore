package dao;

import model.DiscountDetails;

import java.util.ArrayList;

public class DiscountDetailsDTO implements DAOInterface<DiscountDetails> {
    @Override
    public int insert(DiscountDetails discountDetails) {
        return 0;
    }

    @Override
    public int update(DiscountDetails discountDetails) {
        return 0;
    }

    @Override
    public int delete(DiscountDetails discountDetails) {
        return 0;
    }

    @Override
    public ArrayList<DiscountDetails> getAll() {
        return null;
    }

    @Override
    public DiscountDetails selectbyId(DiscountDetails discountDetails) {
        return null;
    }

    @Override
    public ArrayList<DiscountDetails> selectbyCondition(String condition) {
        return null;
    }
}
