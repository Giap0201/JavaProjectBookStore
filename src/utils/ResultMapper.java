package utils;

import model.Customers;
import model.Employees;
import model.Invoice;

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
        return employee;
    }

    public static Customers mapResultSetToCustomer(ResultSet rs) throws SQLException {
        Customers customer = new Customers();
        customer.setCustomerID(rs.getString("c_customerID"));
        customer.setFirstName(rs.getString("c_firstName"));
        customer.setLastName(rs.getString("c_lastName"));
        // Chuyển đổi gender từ TINYINT sang String
        int genderValue = rs.getInt("c_gender");
        if (!rs.wasNull()) {
            customer.setGender(genderValue == 1 ? "Male" : "Female");
        } else {
            customer.setGender(null);
        }
        customer.setPhoneNumber(rs.getString("c_phoneNumber"));
        customer.setEmail(rs.getString("c_email"));
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
}