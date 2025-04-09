package dao;

import database.JDBCUtil;
import model.Discount;

import java.sql.*;
import java.util.ArrayList;

public class DiscountDTO implements IDiscountDTO {
    @Override
    public int insert(Discount discount) {
        int result = 0;
        String query = "insert into discount values(?,?,?,?,?)";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, discount.getDiscountID());
            ps.setString(2, discount.getNameDiscount());
            ps.setString(3, discount.getTypeDiscount());
            ps.setDate(4, discount.getStartDate());
            ps.setDate(5, discount.getEndDate());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int update(Discount discount) {
        int result = 0;
        String query = "update discount set nameDiscount = ?,typeDiscount = ?,startDate = ?,endDate = ? where discountID = ?";
        try(Connection conn = JDBCUtil.getConnection();
        PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1,discount.getNameDiscount());
            ps.setString(2,discount.getTypeDiscount());
            ps.setDate(3,discount.getStartDate());
            ps.setDate(4,discount.getEndDate());
            ps.setString(5,discount.getDiscountID());
            result = ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int delete(Discount discount) {
        int result = 0;
        String query = "delete from discount where discountID = ?";
        try(Connection conn = JDBCUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1,discount.getDiscountID());
            result = ps.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public ArrayList<Discount> selectAll() {
        ArrayList<Discount> listDiscounts = new ArrayList<>();
        String query = "select * from discount";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String discountID = rs.getString("discountID");
                String discountName = rs.getString("nameDiscount");
                String typeDiscount = rs.getString("typeDiscount");
                Date startDate = rs.getDate("startDate");
                Date endDate = rs.getDate("endDate");
                Discount discount = new Discount(discountID, discountName, typeDiscount, startDate, endDate);
                listDiscounts.add(discount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listDiscounts;
    }
    @Override
    public int deleteCondition(String discountID) {
        int result = 0;
        String query = "delete from discount where discountID = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, discountID);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

}
