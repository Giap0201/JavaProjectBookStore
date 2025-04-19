package dao;

import database.JDBCUtil;
import model.Discount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetDiscountByBookId {
    public float getDiscountByBookId(String bookId) {
        Discount discount = null;
        String sql = "select * \n" +
                "from discount d\n" +
                "join discountdetails dt\n" +
                "\ton d.discountID = dt.discountID\n" +
                "where dt.bookID=? and  (CURDATE() between d.startDate and d.endDate ) ";
        try(Connection connection= JDBCUtil.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, bookId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                discount = new Discount();
                discount.setDiscountID(resultSet.getString("discountID"));
                discount.setPercent(resultSet.getFloat("percent"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(discount==null){
            return 0.0f;
        }
        return discount.getPercent();
    }
}
