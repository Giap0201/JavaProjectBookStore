package service;

import dao.EmployeeDAO;
import model.Employees;

import java.util.ArrayList;

public class EmployeeService {
    private EmployeeDAO employeeDAO;

    public EmployeeService() {
        this.employeeDAO = new EmployeeDAO();
    }

    public ArrayList<Employees> getAllEmployees(){
        return employeeDAO.getAll();
    }

    public boolean insertEmployee(Employees employee){
        if (employee == null || employee.getEmployeeID() == null || employee.getEmployeeID().trim().isEmpty()) {
            System.err.println("Lỗi Service: Customer hoặc CustomerID không hợp lệ.");
            return false;
        }
        // Kiểm tra xem ID đã tồn tại chưa (nếu cần)
        if (employeeDAO.selectByID(employee.getEmployeeID()) != null) {
            System.err.println("Lỗi Service: Mã khách hàng '" + employee.getEmployeeID() + "' đã tồn tại.");
            return false; // Trả về false nếu mã đã tồn tại
        }
        int rs = employeeDAO.insert(employee);
        return rs > 0;
    }

    public Employees getEmployeeByID(String employeeID){
        if (employeeID == null || employeeID.trim().isEmpty()) {
            return null;
        }
        return employeeDAO.selectByID(employeeID);
    }

    public boolean updateEmployee(Employees employee){
        if (employee == null || employee.getEmployeeID() == null || employee.getEmployeeID().trim().isEmpty()) {
            System.err.println("Lỗi Service: Employee hoặc EmplyeeID không hợp lệ để cập nhật.");
            return false;
        }

        // Kiểm tra xem ID có tồn tại để update không
        if (employeeDAO.selectByID(employee.getEmployeeID()) == null) {
            System.err.println("Lỗi Service: Mã nhân viên " + employee.getEmployeeID() + "' không tồn tại để cập nhật.");
            return false;
        }

        int rs = employeeDAO.update(employee);
        return rs > 0;
    }

    public boolean deleteEmployee(String employeeID){
        int rs = employeeDAO.deleteById(employeeID);
        return rs > 0;
    }

    public ArrayList<Employees> searchEmployee(String field ,String value  ,String salaryFromValue , String salaryToValue){
        String trimmedValue = (value!=null)?value.trim():"";
        String trimmedSalaryFrom = (salaryFromValue!=null)?salaryFromValue.trim():"";
        String trimmedSalaryTo = (salaryToValue!=null)?salaryToValue.trim():"";
        return employeeDAO.Search(field,trimmedValue,trimmedSalaryFrom,trimmedSalaryTo);
    }


}
