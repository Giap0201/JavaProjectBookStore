package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderDetailsDAO {
    //phuong thuc xoa hoa don ma co rang buoc khoa lien quan den chi tiet hoa don
    public int deleteByOrderId(String orderID, Connection conn) throws SQLException{
        int rowAffected = 0;
        String sql = "delete from orderdetails where orderID = ?";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1,orderID);
            rowAffected = ps.executeUpdate();
        }finally {
            //khong dong connection o day chi dong preparedStament
            if(ps != null){
                try {
                    ps.close();
                }catch (SQLException e){
                    e.printStackTrace();

                }
            }
        }
        return rowAffected;
    }
}
