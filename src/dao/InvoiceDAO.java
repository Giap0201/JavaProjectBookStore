package dao;

import database.JDBCUtil;
import model.Customers;
import model.Employees;
import model.Invoice;
import utils.ResultMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InvoiceDAO {
    public int deleteInvoice(String invoiceID){
        int result = 0;
        String sql = "delete from orders where orderID=?";
        try(Connection conn = JDBCUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, invoiceID);
            result = ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    //lay ra toan bo hoa don
    public ArrayList<Invoice> getAllInvoice(){
        ArrayList<Invoice> listInvoice = new ArrayList<>();
        String sql = "select * from orders join employees on orders.employeeID = employees.employeeID join customer on orders.customerID = customer.customerID";
        try(Connection conn = JDBCUtil.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Invoice invoice = ResultMapper.mapResultSetToInvoice(rs);
                listInvoice.add(invoice);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return listInvoice;
    }

}