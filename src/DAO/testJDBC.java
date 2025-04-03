package DAO;

import database.JDBCUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class testJDBC {
    public static void main(String[] args) throws SQLException {
        Connection conn = JDBCUtil.getConnection();
        JDBCUtil.close(conn);
        System.out.println(conn);
    }
}
