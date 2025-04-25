package dao;

import database.JDBCUtil;
import model.Customers;
import utils.ResultMapper;

import java.sql.*;
import java.util.ArrayList;

public class CustomerDAO {


    public int insert(Customers customer) {
        int ketQua = 0;
        String sql = "INSERT INTO customer (customerID, lastName, firstName, gender, phoneNumber, email, dateOfBirth, totalMoney, creationDate, note) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customer.getCustomerID());
            stmt.setString(2, customer.getLastName());
            stmt.setString(3, customer.getFirstName());
            stmt.setString(4, customer.getGender());
            stmt.setString(5, customer.getPhoneNumber());
            stmt.setString(6, customer.getEmail());
            if (customer.getDateOfBirth() != null) {
                stmt.setDate(7, new java.sql.Date(customer.getDateOfBirth().getTime()));
            } else {
                stmt.setNull(7, Types.DATE);
            }
            stmt.setDouble(8, customer.getTotalMoney());
            if (customer.getCreationDate() != null) {
                stmt.setDate(9, new java.sql.Date(customer.getCreationDate().getTime()));
            } else {
                stmt.setNull(9, Types.DATE);
            }
            stmt.setString(10, customer.getNote());

            ketQua = stmt.executeUpdate();
            System.out.println("Đã thực thi: " + sql);
            System.out.println("Có " + ketQua + " dòng bị thay đổi!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    public int update(Customers customer) {
        int ketQua = 0;
        String sql = "UPDATE customer SET lastName=?, firstName=?, gender=?, phoneNumber=?, email=?, dateOfBirth=?, totalMoney=?, creationDate=?, note=? WHERE customerID=?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customer.getLastName());
            stmt.setString(2, customer.getFirstName());
            stmt.setString(3, customer.getGender());
            stmt.setString(4, customer.getPhoneNumber());
            stmt.setString(5, customer.getEmail());
            if (customer.getDateOfBirth() != null) {
                stmt.setDate(6, new java.sql.Date(customer.getDateOfBirth().getTime()));
            } else {
                stmt.setNull(6, Types.DATE);
            }
            stmt.setDouble(7, customer.getTotalMoney());
            if (customer.getCreationDate() != null) {
                stmt.setDate(8, new java.sql.Date(customer.getCreationDate().getTime()));
            } else {
                stmt.setNull(8, Types.DATE);
            }
            stmt.setString(9, customer.getNote());
            stmt.setString(10, customer.getCustomerID());

            ketQua = stmt.executeUpdate();
            System.out.println("Đã thực thi: " + sql);
            System.out.println("Có " + ketQua + " dòng bị thay đổi!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    public int deleteById(String customerId) {
        int ketQua = 0;
        String sql = "DELETE FROM customer WHERE customerID=?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customerId);
            ketQua = stmt.executeUpdate();
            System.out.println("Đã thực thi: " + sql);
            System.out.println("Có " + ketQua + " dòng bị thay đổi!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    public ArrayList<Customers> getAll() {
        ArrayList<Customers> customers = new ArrayList<>();
        String sql = "SELECT * FROM customer ORDER BY creationDate DESC, customerID ASC"; // Sắp xếp kết quả
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) { // ResultSet cũng cần đóng

            while (rs.next()) {
                Customers customer = mapResultSetToCustomer(rs);
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public Customers getCustomerByPhoneNumber(String phoneNumber) {
        Customers customer = null;
        String sql = "SELECT * FROM customer WHERE phoneNumber=?";
        try{
            Connection conn = JDBCUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, phoneNumber);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                customer = mapResultSetToCustomer(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    // Thay đổi tham số hoặc tạo hàm mới để chọn theo ID
    public Customers selectById(String customerId) {
        Customers customer = null;
        String sql = "SELECT * FROM customer WHERE customerID=?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customerId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    customer = mapResultSetToCustomer(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    public int updateSpending(String customerId, double spending){
        int ketQua = 0;
        String sql = "UPDATE customer SET totalMoney=totalMoney+? WHERE customerID=?";
        try(Connection connection=JDBCUtil.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setDouble(1, spending);
            ps.setString(2, customerId);
            ketQua = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }




    // Phương thức search linh hoạt hơn
    public ArrayList<Customers> search(String field, String value, String gender) {
        ArrayList<Customers> customers = new ArrayList<>();
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM customer WHERE 1=1");

        if (value != null && !value.trim().isEmpty()) {
            sqlBuilder.append(" AND ").append(field).append(" LIKE ?");
        }

        if (gender != null && !gender.isEmpty()) {
            sqlBuilder.append(" AND gender = ?");
        }

        sqlBuilder.append(" ORDER BY creationDate DESC, customerID ASC"); // Sắp xếp kết quả

        String sql = sqlBuilder.toString();
        System.out.println("Executing search query: " + sql);

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            int paramIndex = 1;
            if (value != null && !value.trim().isEmpty()) {
                stmt.setString(paramIndex++, "%" + value + "%");
                System.out.println("Search value param: %" + value + "%");
            }
            if (gender != null && !gender.isEmpty()) {
                stmt.setString(paramIndex++, gender);
                System.out.println("Search gender param: " + gender);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Customers customer = mapResultSetToCustomer(rs);
                    customers.add(customer);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }



    //tim kiem khach hang theo ten, dung liKe de loc khach hang co tenn nhu vay
    public ArrayList<Customers> searchByName(String name){
        ArrayList<Customers> listCustomers = new ArrayList<>();
        String query = "SELECT * FROM customer WHERE firstName LIKE ?";
        try(Connection conn = JDBCUtil.getConnection();
        PreparedStatement ps = conn.prepareStatement(query)){
            String search = "%" + name + "%";
            ps.setString(1,search);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Customers customer = ResultMapper.mapResultSetToCustomer(rs);
                listCustomers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return listCustomers;
    }

    // Phương thức tiện ích để map ResultSet sang đối tượng Customers
    private Customers mapResultSetToCustomer(ResultSet rs) throws SQLException {
        Customers customer = new Customers();
        customer.setCustomerID(rs.getString("customerID"));
        customer.setFirstName(rs.getString("firstName"));
        customer.setLastName(rs.getString("lastName"));
        customer.setGender(rs.getString("gender"));
        customer.setPhoneNumber(rs.getString("phoneNumber"));
        customer.setEmail(rs.getString("email"));
        // Lấy Date từ ResultSet và chuyển đổi sang java.util.Date
        java.sql.Date dateOfBirthSql = rs.getDate("dateOfBirth");
        customer.setDateOfBirth(dateOfBirthSql != null ? new java.util.Date(dateOfBirthSql.getTime()) : null);
        customer.setTotalMoney(rs.getDouble("totalMoney"));
        java.sql.Date creationDateSql = rs.getDate("creationDate");
        customer.setCreationDate(creationDateSql != null ? new java.util.Date(creationDateSql.getTime()) : null);
        customer.setNote(rs.getString("note"));
        return customer;
    }
}