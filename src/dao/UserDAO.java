package dao;

import database.JDBCUtil;
import model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public User getUserByUsername(String username) {
        String query = "SELECT * FROM users WHERE username = ?";
        User user = null;

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
//                user = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
                int id = rs.getInt("id");
                String username_rs = rs.getString("username");
                String password_rs = rs.getString("password");
                user = new User(id, username_rs, password_rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
//    public User getUserById(int id){
//
//    }
    public  static UserDAO getInstance() {
        return new UserDAO();
    }

}
