package dao;

import database.JDBCUtil;
import model.Category;
import utils.ResultMapper;

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
                Category category = ResultMapper.mapResultSetToCategory(rs);
                list.add(category);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return list;
    }
    //phuong thuc lay category theo then
    public Category getCategory(String categoryName) {
        String sql = "select * from Category where CategoryName=?";
        Category category = null;
        try(Connection conn = JDBCUtil.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1,categoryName);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                category = ResultMapper.mapResultSetToCategory(rs);
            }
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return category;
    }

}
