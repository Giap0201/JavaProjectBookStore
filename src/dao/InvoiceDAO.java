package dao;

import database.JDBCUtil;
import model.Invoice;
import utils.ResultMapper;

import java.sql.*;
import java.util.ArrayList;

public class InvoiceDAO {
    public int deleteInvoice(String invoiceID) {
        int result = 0;
        String sql = "DELETE FROM orders WHERE orderID = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, invoiceID);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void deleteInvoice(ArrayList<String> listInvoiceID) {
        String sql = "DELETE FROM orders WHERE orderID = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            for (String invoiceID : listInvoiceID) {
                ps.setString(1, invoiceID);
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Invoice> getAllInvoice() {
        ArrayList<Invoice> listInvoice = new ArrayList<>();
        String sql = "SELECT o.orderID, o.dayOfEstablishment, o.status, " +
                "c.customerID AS c_customerID, c.firstName AS c_firstName, c.lastName AS c_lastName, " +
                "c.gender AS c_gender, c.phoneNumber AS c_phoneNumber, c.email AS c_email, " +
                "c.dateOfBirth AS c_dateOfBirth, c.totalMoney AS c_totalMoney, c.creationDate AS c_creationDate, c.note AS c_note, " +
                "e.employeeID AS e_employeeID, e.firstName AS e_firstName, e.lastName AS e_lastName, " +
                "e.position AS e_position, e.email AS e_email, e.phoneNumber AS e_phoneNumber, " +
                "e.salary AS e_salary, e.gender AS e_gender, e.dateOfBirth AS e_dateOfBirth " +
                "FROM orders o " +
                "JOIN employees e ON o.employeeID = e.employeeID " +
                "JOIN customer c ON o.customerID = c.customerID";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Invoice invoice = ResultMapper.mapResultSetToInvoice(rs);
                listInvoice.add(invoice);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listInvoice;
    }

    public int updateInvoice(Invoice invoice) {
        String query = "UPDATE orders SET employeeID = ?, customerID = ?, status = ?, dayOfEstablishment = ? WHERE orderID = ?";
        int result = 0;
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, invoice.getEmployee().getEmployeeID());
            ps.setString(2, invoice.getCustomer().getCustomerID());
            ps.setString(3, invoice.getStatus());
            ps.setDate(4, invoice.getDate());
            ps.setString(5, invoice.getInvoiceID());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }

    public Invoice getInvoiceByID(String invoiceID) {
        Invoice invoice = new Invoice();
        String sql = "SELECT o.orderID, o.dayOfEstablishment, o.status, " +
                "c.customerID AS c_customerID, c.firstName AS c_firstName, c.lastName AS c_lastName, " +
                "c.gender AS c_gender, c.phoneNumber AS c_phoneNumber, c.email AS c_email, " +
                "c.dateOfBirth AS c_dateOfBirth, c.totalMoney AS c_totalMoney, c.creationDate AS c_creationDate, c.note AS c_note, " +
                "e.employeeID AS e_employeeID, e.firstName AS e_firstName, e.lastName AS e_lastName, " +
                "e.position AS e_position, e.email AS e_email, e.phoneNumber AS e_phoneNumber, " +
                "e.salary AS e_salary, e.gender AS e_gender, e.dateOfBirth AS e_dateOfBirth " +
                "FROM orders o " +
                "JOIN employees e ON o.employeeID = e.employeeID " +
                "JOIN customer c ON o.customerID = c.customerID " +
                "WHERE o.orderID = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, invoiceID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                invoice = ResultMapper.mapResultSetToInvoice(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return invoice;
    }

    public ArrayList<Invoice> listSearchInvoice(Invoice invoice) {
        ArrayList<Invoice> listInvoice = new ArrayList<>();
        StringBuilder query = new StringBuilder(
                "SELECT o.orderID, o.dayOfEstablishment, o.status, " +
                        "c.customerID AS c_customerID, c.firstName AS c_firstName, c.lastName AS c_lastName, " +
                        "c.gender AS c_gender, c.phoneNumber AS c_phoneNumber, c.email AS c_email, " +
                        "c.dateOfBirth AS c_dateOfBirth, c.totalMoney AS c_totalMoney, c.creationDate AS c_creationDate, c.note AS c_note, " +
                        "e.employeeID AS e_employeeID, e.firstName AS e_firstName, e.lastName AS e_lastName, " +
                        "e.position AS e_position, e.email AS e_email, e.phoneNumber AS e_phoneNumber, " +
                        "e.salary AS e_salary, e.gender AS e_gender, e.dateOfBirth AS e_dateOfBirth " +
                        "FROM orders o " +
                        "JOIN employees e ON o.employeeID = e.employeeID " +
                        "JOIN customer c ON o.customerID = c.customerID " +
                        "WHERE 1 = 1"
        );
        ArrayList<Object> params = new ArrayList<>();
        if (invoice.getInvoiceID() != null && !invoice.getInvoiceID().isEmpty()) {
            query.append(" AND o.orderID LIKE ?");
            params.add("%" + invoice.getInvoiceID() + "%");
        }
        if (invoice.getCustomer() != null && !invoice.getCustomer().getCustomerID().isEmpty()) {
            query.append(" AND o.customerID = ?");
            params.add(invoice.getCustomer().getCustomerID());
        }
        if (invoice.getEmployee() != null && !invoice.getEmployee().getEmployeeID().isEmpty()) {
            query.append(" AND o.employeeID = ?");
            params.add(invoice.getEmployee().getEmployeeID());
        }
        if (invoice.getStatus() != null && !invoice.getStatus().isEmpty()) {
            query.append(" AND o.status = ?");
            params.add(invoice.getStatus());
        }
        if (invoice.getDate() != null) {
            query.append(" AND o.dayOfEstablishment = ?");
            params.add(invoice.getDate());
        }
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query.toString())) {
            for (int i = 0; i < params.size(); i++) {
                Object param = params.get(i);
                if (param instanceof String) {
                    ps.setString(i + 1, (String) param);
                } else if (param instanceof Date) {
                    ps.setDate(i + 1, (Date) param);
                }
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Invoice invoice_rs = ResultMapper.mapResultSetToInvoice(rs);
                listInvoice.add(invoice_rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi tìm kiếm hóa đơn: " + e.getMessage(), e);
        }
        return listInvoice;
    }
}