package dao;

import database.JDBCUtil;
import model.Category;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryDAO {
    public ArrayList<Category> listCategory() {
        ArrayList<Category> list = new ArrayList<Category>();
        String sql = "select * from Category";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String categoryID = rs.getString("CategoryID");
                String categoryName = rs.getString("CategoryName");
                Category category = new Category(categoryID, categoryName);
                list.add(category);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return list;
    }

    public Category getCategory(String categoryID) {
        Category category = null;
        String sql = "select * from Category where CategoryID = ?";
        try(Connection connection=JDBCUtil.getConnection()){
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, categoryID);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                String categoryName = rs.getString("CategoryName");
                category = new Category(categoryID, categoryName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category;
    }
}
