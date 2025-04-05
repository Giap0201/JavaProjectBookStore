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
}
