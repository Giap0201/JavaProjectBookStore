//package dao;
//
//import database.JDBCUtil;
//import model.Invoice;
//import model.InvoiceDetails;
//import utils.ResultMapper;
//
//import javax.swing.*;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class InvoiceDetailDAO {
//    //lay thong tin ve chi tiet ma giam gia
//    public InvoiceDetails getInvoiceDetails(String invoiceID) {
//        String query = "select * from orderDetails where OrderID = ?";
//        InvoiceDetails invoiceDetails = new InvoiceDetails();
//        Invoice invoice =
//        try(Connection conn = JDBCUtil.getConnection();
//            PreparedStatement ps = conn.prepareStatement(query)){
//            ps.setString(1,invoiceID);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()){
//
//            }
//        }catch (SQLException e){
//            e.printStackTrace();
//        }
//        return invoiceDetails;
//    }
//}
