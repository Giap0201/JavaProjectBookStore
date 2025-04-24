package dao;

import database.JDBCUtil;
import model.Books;
import model.Category;
import model.Discount;
import model.DiscountDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DiscountDetailsDAO {

    public void insert(ArrayList<DiscountDetails> discountDetails) {
        String query = "insert into discountdetails values(?,?,?)";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            for (DiscountDetails discountDetail : discountDetails) {
                ps.setString(2, discountDetail.getDiscount().getDiscountID());
                ps.setDouble(3, discountDetail.getPercent());
                ps.setString(1, discountDetail.getBook().getBookID());
                ps.addBatch();
                //gom nhom cau lenh lai de thuc hien them 1 lan luon
            }
            //thuc hien them
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error in inserting discount details " + e.getMessage());
        }
    }

    public ArrayList<DiscountDetails> getAllDiscountDetails() {
        ArrayList<DiscountDetails> listDiscountDetails = new ArrayList<>();
        String query = "select * from discountdetails join discount on discountdetails.discountID=discount.discountID \n" +
                "join books on books.bookID=discountdetails.bookID join category on category.categoryID = books.categoryID;";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Category category = new Category(rs.getString("categoryID"), rs.getString("categoryName"));
                Books book = new Books(rs.getString("bookID"), rs.getString("bookName"), rs.getString("author"),
                        rs.getInt("yearPublished"), rs.getDouble("price"), rs.getInt("quantity"), category);
                Discount discount = new Discount(rs.getString("discountID"), rs.getString("nameDiscount"), rs.getString("typeDiscount"),
                        rs.getDate("startDate"), rs.getDate("endDate"));
                double percent = rs.getDouble("percent");
                DiscountDetails discountDetails = new DiscountDetails(discount, percent, book);
                listDiscountDetails.add(discountDetails);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listDiscountDetails;
    }
    //ham xoa toan bo chuong trinh giam gia
    public int deleteAll(String discountId) {
        String query = "delete from discountdetails where discountID=?";
        int result = 0;
        try(Connection conn = JDBCUtil.getConnection();
        PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1, discountId);
            result = ps.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public int deleteDiscountDetails(String discountID,String bookID) {
        String query = "delete from discountdetails where discountID=? and bookID=?";
        int result = 0;
        try (Connection conn = JDBCUtil.getConnection();
        PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1,discountID);
            ps.setString(2,bookID);
            result = ps.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public ArrayList<DiscountDetails> getDiscountDetailsByID(Discount discount) {
        ArrayList<DiscountDetails> listDiscountDetails = new ArrayList<>();
        String query = "select * from discountdetails join discount on discountdetails.discountID=discount.discountID \n" +
                "join books on books.bookID=discountdetails.bookID join category on category.categoryID = books.categoryID where discount.discountID=?";
        try (Connection conn = JDBCUtil.getConnection();
        PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1,discount.getDiscountID());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Category category = new Category(rs.getString("categoryID"), rs.getString("categoryName"));
                Books book = new Books(rs.getString("bookID"), rs.getString("bookName"), rs.getString("author"),
                        rs.getInt("yearPublished"), rs.getDouble("price"), rs.getInt("quantity"), category);
                Discount discount_result = new Discount(rs.getString("discountID"), rs.getString("nameDiscount"), rs.getString("typeDiscount"),
                        rs.getDate("startDate"), rs.getDate("endDate"));
                double percent = rs.getDouble("percent");
                DiscountDetails discountDetails = new DiscountDetails(discount_result, percent, book);
                listDiscountDetails.add(discountDetails);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return listDiscountDetails;
    }

    public float getDiscount(String bookId) {
        String sql = """
            SELECT percent
            FROM discount d
            JOIN discountdetails dt ON d.discountID = dt.discountID
            WHERE dt.bookID = ? AND CURDATE() BETWEEN d.startDate AND d.endDate
            """;

        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, bookId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getFloat("percent");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0.0f;
    }
}
