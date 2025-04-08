package dao;

import database.JDBCUtil;
import model.Customers;
import model.Employees;
import model.Invoice;

import java.sql.*;
import java.util.ArrayList;
import java.util.Currency;

public class InvoiceDAO implements IInvoiceDAO {
    //viet ra 1 phuong thuc rieng de lay ra cac bang khac join du lieu
    private Employees getEmployee(ResultSet rs) throws SQLException{
        String employeeID = rs.getString("employeeID");
        String lastName = rs.getString("lastName");
        String firstName = rs.getString("firstName");
        String position = rs.getString("position");
        String email = rs.getString("email");
        String phoneNumber = rs.getString("phoneNumber");
        double salary = rs.getDouble("salary");
        Date dateOfBirth = rs.getDate("dateOfBirth");
        String note = rs.getString("notes");
        boolean gender = rs.getBoolean("gender");
        String genderPerson = Employees.setGenderPerson(gender);
        return new Employees(firstName,lastName,genderPerson,dateOfBirth,phoneNumber,email,note,employeeID,salary,position);
    }

    @Override
    public ArrayList<Invoice> getAllInvoices() {
        String query = "select * from Orders join Employees on Orders.EmployeeID = Employees.EmployeeID" +
                "join Customer on Customer.CustomerID = Orders.CustomerID";
        ArrayList<Invoice> listInvoices = new ArrayList<>();
        try(Connection conn = JDBCUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String OrderID = rs.getString("orderID");
                String transactionCode = rs.getString("transactionCode");
                Date date = rs.getDate("dayOfEstablishment");
                float discount = rs.getFloat("discount");
                float totalMoney = rs.getFloat("totalMoney");
                float intoMoney = rs.getFloat("intoMoney");
                Employees employee = new Employees(rs.getString("employeeID"),rs.getString(""));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return listInvoices;
    }
}
