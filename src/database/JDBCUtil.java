package database;

import java.sql.*;

public class JDBCUtil {
    public static Connection getConnection() throws SQLException {
        Connection conn = null;

        try{
            //Đăng kí MySQL với DriverManager
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            String url = "jdbc:mysql://localhost:3306/nhasach";
            String user = "root";
            String password = "thai123";

            //Tạo kết nối
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e){
            e.printStackTrace();
        }

        return conn;
    }

    public static void close(Connection conn) {
        try{
            if(conn != null){
                conn.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
