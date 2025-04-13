package utils;

import model.Customers;
import model.Employees;
import model.Invoice;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultMapper {
    public static Employees mapResultSetToEmployee(ResultSet rs) throws SQLException {
        Employees employee = new Employees();
        employee.setEmployeeID(rs.getString("employeeID"));
        employee.setLastName(rs.getString("lastName"));
        employee.setFirstName(rs.getString("firstName"));
        employee.setPosition(rs.getString("position"));
        employee.setEmail(rs.getString("email"));
        employee.setPhoneNumber(rs.getString("phoneNumber"));
        employee.setSalary(rs.getDouble("salary"));
        employee.setGender(rs.getString("gender"));
        java.sql.Date dateOfBirthSql = rs.getDate("dateOfBirth");
        employee.setDateOfBirth(dateOfBirthSql != null ? new java.util.Date(dateOfBirthSql.getTime()) : null);
        employee.setGender(rs.getString("gender"));
        return employee;
    }
    public static Customers mapResultSetToCustomer(ResultSet rs) throws SQLException {
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
    public static Invoice mapResultSetToInvoice(ResultSet rs) throws SQLException {
        return new Invoice(rs.getString("OrderID"),rs.getDate("dayOfEstablisment"),mapResultSetToCustomer(rs),mapResultSetToEmployee(rs),rs.getString("status"));
    }


}
