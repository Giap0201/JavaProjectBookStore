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

    //xoa 1 dong hoa don
    public void deleteInvoice(ArrayList<String> listInvoiceID){
        String sql = "delete from orders where orderID=?";
        try (Connection conn = JDBCUtil.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)){
            for (String invoiceID : listInvoiceID) {
                ps.setString(1,invoiceID);
                ps.addBatch();
            }
            ps.executeBatch();
        }catch (SQLException e){
            e.printStackTrace();
        }
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
    public int updateInvoice(Invoice invoice){
        String query = "UPDATE orders SET employeeID=?, customerID=?,status = ?, DayOfEstablisment = ? WHERE orderID=?";
        int result = 0;
        try(Connection conn = JDBCUtil.getConnection();
        PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1,invoice.getInvoiceID());
            ps.setString(2,invoice.getCustomer().getCustomerID());
            ps.setString(3,invoice.getStatus());
            ps.setDate(4,invoice.getDate());
            ps.setString(5,invoice.getInvoiceID());
            result = ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }
    public Invoice getInvoiceByID(String invoiceID){
        Invoice invoice = new Invoice();
        String sql = "select * from orders join employees on orders.employeeID = employees.employeeID join customer on orders.customerID = customer.customerID where orderID=?";
        try(Connection conn = JDBCUtil.getConnection();
        PreparedStatement ps = conn.prepareStatement((sql))){
            ps.setString(1,invoiceID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                invoice = ResultMapper.mapResultSetToInvoice(rs);
            }
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return invoice;
    }

}