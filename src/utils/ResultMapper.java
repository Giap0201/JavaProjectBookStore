package utils;

import model.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultMapper {
    public static Employees mapResultSetToEmployee(ResultSet rs) throws SQLException {
        Employees employee = new Employees();
        employee.setEmployeeID(rs.getString("e_employeeID"));
        employee.setLastName(rs.getString("e_lastName"));
        employee.setFirstName(rs.getString("e_firstName"));
        employee.setPosition(rs.getString("e_position"));
        employee.setEmail(rs.getString("e_email"));
        employee.setPhoneNumber(rs.getString("e_phoneNumber"));
        employee.setSalary(rs.getDouble("e_salary"));
        employee.setGender(rs.getString("e_gender"));
        java.sql.Date dateOfBirthSql = rs.getDate("e_dateOfBirth");
        employee.setDateOfBirth(dateOfBirthSql != null ? new java.util.Date(dateOfBirthSql.getTime()) : null);
        employee.setGender(rs.getString("e_gender"));
        return employee;
    }


    // Phương thức tiện ích để map ResultSet sang đối tượng Customers
    public static Customers mapResultSetToCustomer(ResultSet rs) throws SQLException {
        Customers customer = new Customers();
        customer.setCustomerID(rs.getString("c_customerID"));
        customer.setFirstName(rs.getString("c_firstName"));
        customer.setLastName(rs.getString("c_lastName"));
        customer.setGender(rs.getString("c_gender"));
        customer.setPhoneNumber(rs.getString("c_phoneNumber"));
        customer.setEmail(rs.getString("c_email"));
        // Lấy Date từ ResultSet và chuyển đổi sang java.util.Date
        java.sql.Date dateOfBirthSql = rs.getDate("c_dateOfBirth");
        customer.setDateOfBirth(dateOfBirthSql != null ? new java.util.Date(dateOfBirthSql.getTime()) : null);
        customer.setTotalMoney(rs.getDouble("c_totalMoney"));
        java.sql.Date creationDateSql = rs.getDate("c_creationDate");
        customer.setCreationDate(creationDateSql != null ? new java.util.Date(creationDateSql.getTime()) : null);
        customer.setNote(rs.getString("c_note"));
        return customer;
    }

    public static Invoice mapResultSetToInvoice(ResultSet rs) throws SQLException {
        return new Invoice(
                rs.getString("orderID"),
                rs.getDate("dayOfEstablishment"),
                mapResultSetToCustomer(rs),
                mapResultSetToEmployee(rs),
                rs.getString("status")
        );
    }

    public static Category mapResultSetToCategory(ResultSet rs) throws SQLException {
        return new Category(rs.getString("categoryID"), rs.getString("categoryName"));
    }

    public static Books mapResultSetToBooks(ResultSet rs) throws SQLException {
        return new Books(rs.getString("bookID"), rs.getString("bookName"), rs.getString("author")
                , rs.getInt("yearPublished"), rs.getDouble("price"), rs.getInt("quantity"), mapResultSetToCategory(rs));
    }
    public static InvoiceDetails mapResultSetToInvoiceDetails(ResultSet rs) throws SQLException {
        return new InvoiceDetails(mapResultSetToInvoice(rs),mapResultSetToBooks(rs),rs.getInt("quantity"),rs.getDouble("price"));
    }
}