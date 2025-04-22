package database;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtil {
    //dung final de ngan chan viec thay doi gia tri cua bien, phuong thuc, khong the gi de, khong the ke thua
    //dung static de khong can tao doi tuong moi, no nhu la bien tinh
    private static final String URL = "jdbc:mysql://localhost:3306/bookstore";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() {
        Connection c = null;
        try {
            // Load driver (không cần registerDriver)
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi");
        }
        return c;
    }

    public static void closeConnection(Connection c) {
        try {
            if (c != null) {
                c.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Lỗi");
        }
    }
}
