package dao;

import database.JDBCUtil;
import model.Customers;

import java.sql.*;
import java.util.ArrayList;

public class CustomerDAO implements DAOInterface<Customers> {

    @Override
    public int insert(Customers customer) {
        int ketQua = 0;
        try (Connection conn = JDBCUtil.getConnection()) {
            String sql = "INSERT INTO customer (customerID, lastName, firstName, gender, phoneNumber, email, dateOfBirth ,totalMoney, creationDate, note) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, customer.getCustomerID());
            stmt.setString(2, customer.getLastName());
            stmt.setString(3, customer.getFirstName());
            stmt.setString(4, customer.getGender());
            stmt.setString(5, customer.getPhoneNumber());
            stmt.setString(6, customer.getEmail());
            stmt.setDate(7, customer.getDateOfBirth() != null ? new java.sql.Date(customer.getDateOfBirth().getTime()) : null);
            stmt.setDouble(8, customer.getTotalMoney());
            stmt.setDate(9, customer.getCreationDate() != null ? new java.sql.Date(customer.getCreationDate().getTime()) : null);
            stmt.setString(10, customer.getNote());

            System.out.println(sql  );
            ketQua = stmt.executeUpdate();

            JDBCUtil.closeConnection(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int update(Customers customer) {

        return 0;
    }

    @Override
    public int delete(Customers customers) {
        return 0;
    }

    @Override
    public ArrayList<Customers> getAll() {
        ArrayList<Customers> customers = new ArrayList<>();
        try (Connection conn = JDBCUtil.getConnection()) {
            String sql = "SELECT * FROM customer";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Customers customer = new Customers();
                customer.setCustomerID(rs.getString("customerID"));
                customer.setFirstName(rs.getString("firstName"));
                customer.setLastName(rs.getString("lastName"));
                customer.setGender(rs.getString("gender"));
                customer.setPhoneNumber(rs.getString("phoneNumber"));
                customer.setEmail(rs.getString("email"));
                java.sql.Date dateOfBirth = rs.getDate("dateOfBirth");
                customer.setDateOfBirth(dateOfBirth != null ? new java.util.Date(dateOfBirth.getTime()) : null);
                customer.setTotalMoney(rs.getDouble("totalMoney"));
                java.sql.Date creationDate = rs.getDate("creationDate");
                customer.setCreationDate(creationDate != null ? new java.util.Date(creationDate.getTime()) : null);
                customer.setNote(rs.getString("note"));

                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    @Override
    public Customers selectbyId(Customers customers) {
        return null;
    }

    @Override
    public ArrayList<Customers> selectbyCondition(String condition) {
        return null;
    }
}
