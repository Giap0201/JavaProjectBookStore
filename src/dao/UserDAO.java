package dao;

import database.JDBCUtil;
import model.User;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public User getUserByUsername(String username) {
        String query = "select * from user where username = ?";
        User user = null;
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id_rs = rs.getInt("id");
                String username_rs = rs.getString("username");
                String password_rs = rs.getString("password");
                user = new User(id_rs, username_rs, password_rs);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return user;

    }
    public static UserDAO getInstance() {
        return new UserDAO();
    }

}
