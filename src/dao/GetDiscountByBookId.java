package dao;

import database.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetDiscountByBookId {
    public float getDiscountByBookId(String bookId) {
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
