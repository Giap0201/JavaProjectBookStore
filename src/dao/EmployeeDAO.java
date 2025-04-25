package dao;

import database.JDBCUtil;
import model.Employees;
import utils.ConvertDate;
import utils.ResultMapper;

import java.sql.*;
import java.util.ArrayList;

public class EmployeeDAO{

    public int insert(Employees employee) {
        int ketQua = 0 ;
        String sql = "INSERT INTO employees(employeeID,lastName,firstName,position,email,phoneNumber,salary,gender,dateOfBirth) VALUES(?,?,?,?,?,?,?,?,?)";
        try(Connection conn = JDBCUtil.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,employee.getEmployeeID());
            ps.setString(2,employee.getLastName());
            ps.setString(3,employee.getFirstName());
            ps.setString(4,employee.getPosition());
            ps.setString(5,employee.getEmail());
            ps.setString(6,employee.getPhoneNumber());
            ps.setDouble(7,employee.getSalary());
            ps.setString(8,employee.getGender());
            ps.setDate(9, new java.sql.Date(employee.getDateOfBirth().getTime()));
            ketQua = ps.executeUpdate();
            System.out.println("Đã thực thi: " + sql);
            System.out.println("Có " + ketQua + " dòng bị thay đổi!");

        } catch (SQLException e) {
            e.printStackTrace(); // In lỗi ra console để debug
        }
        return ketQua;
    }

    public int update(Employees employee) {
        int ketQua = 0;
        String sql = "UPDATE employees SET lastName=?,firstName=?,position=?,email=?,phoneNumber=?,salary=?,gender=?,dateOfBirth=? WHERE employeeID=?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, employee.getLastName());
            stmt.setString(2, employee.getFirstName());
            stmt.setString(3, employee.getPosition());
            stmt.setString(4, employee.getEmail());
            stmt.setString(5, employee.getPhoneNumber());
            stmt.setDouble(6, employee.getSalary());
            stmt.setString(7, employee.getGender());
            if (employee.getDateOfBirth() != null) {
                stmt.setDate(8, new java.sql.Date(employee.getDateOfBirth().getTime()));
            } else {
                stmt.setNull(8, Types.DATE);
            }

            stmt.setString(9, employee.getEmployeeID());

            ketQua = stmt.executeUpdate();
            System.out.println("Đã thực thi: " + sql);
            System.out.println("Có " + ketQua + " dòng bị thay đổi!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    public int deleteById(String employeeId) {
        int ketQua = 0;
        String sql = "DELETE FROM employees WHERE employeeID=?";
        try(Connection conn = JDBCUtil.getConnection()){
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, employeeId);
            ketQua = ps.executeUpdate();

            System.out.println("Đã thực thi: " + sql);
            System.out.println("Có " + ketQua + " dòng bị thay đổi!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    public ArrayList<Employees> getAll() {
        ArrayList<Employees> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees";
        try(Connection conn = JDBCUtil.getConnection()){
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Employees employee = mapResultSetToEmployee(rs);
                employees.add(employee);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employees;
    }


    public ArrayList<Employees> Search(String field ,String value  ,String salaryFromValue , String salaryToValue) {
        ArrayList<Employees> employees = new ArrayList<>();
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM employees WHERE 1=1");

        if(value != null && !value.trim().isEmpty()){
            sqlBuilder.append(" AND ").append(field).append(" LIKE ?");
        }

        if(salaryFromValue != null && !salaryFromValue.trim().isEmpty()){
            sqlBuilder.append(" AND salary>=?");
        }
        if(salaryToValue != null && !salaryToValue.trim().isEmpty()){
            sqlBuilder.append(" AND salary<=?");
        }

        sqlBuilder.append(" ORDER BY employeeID ASC");

        String  sql = sqlBuilder.toString();
        System.out.println("Executing search query: " + sql);

        try(Connection connection = JDBCUtil.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            int paramIndex = 1;
            if(value != null && !value.trim().isEmpty()){
                ps.setString(paramIndex++, "%" + value + "%");
                System.out.println("Search value param: %" + value + "%");
            }
            if(salaryFromValue != null && !salaryFromValue.trim().isEmpty()){
                ps.setString(paramIndex++, salaryFromValue);
                System.out.println("Search value param: %" + salaryFromValue + "%");
            }
            if(salaryToValue != null && !salaryToValue.trim().isEmpty()){
                ps.setString(paramIndex++, salaryToValue);
                System.out.println("Search value param: %" + salaryToValue + "%");
            }
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Employees employee = mapResultSetToEmployee(rs);
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public Employees selectByID(String id) {
        Employees employee = null;
        String sql = "SELECT * FROM employees WHERE employeeID=?";
        try (Connection conn = JDBCUtil.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()){
                    employee = mapResultSetToEmployee(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }
    //ham tim kiem nhan vien theo ten
    public ArrayList<Employees> searchByName(String name){
        ArrayList<Employees> listEmployees = new ArrayList<>();
        String query = "SELECT * FROM employees WHERE firstName LIKE ?";
        try(Connection conn = JDBCUtil.getConnection();
        PreparedStatement ps = conn.prepareStatement(query)){
            String search = "%" + name + "%";
            ps.setString(1,search);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Employees employees = ResultMapper.mapResultSetToEmployee(rs);
                listEmployees.add(employees);
            }
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return listEmployees;
    }

    //Lấy nhân viên các hàng trong resultSet
    private Employees mapResultSetToEmployee(ResultSet rs) throws SQLException {
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



}
