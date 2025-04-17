package controller;

import model.Employees;
import service.EmployeeService;
import utils.CommonView;
import view.SelectEmployeeView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SelectEmployeeController implements ActionListener {
    private final SelectEmployeeView selectEmployeeView;
    private final EmployeeService employeeService;
    private Employees employeeResult;

    public SelectEmployeeController(SelectEmployeeView selectEmployeeView) {
        this.selectEmployeeView = selectEmployeeView;
        this.employeeService = new EmployeeService();
        initializeView();
    }

    public void initializeView() {
        loadTable(employeeService.getAllEmployees());
        registerButtonListener();
    }

    private void registerButtonListener() {
        selectEmployeeView.getSelectButton().addActionListener(this);
        selectEmployeeView.getAllButton().addActionListener(this);
        selectEmployeeView.getSearchButton().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == selectEmployeeView.getSearchButton()) {
                searchEmployee();
            } else if (e.getSource() == selectEmployeeView.getAllButton()) {
                loadTable(employeeService.getAllEmployees());
            } else if (e.getSource() == selectEmployeeView.getSelectButton()) {
                selectEmployee();
            }
        } catch (IllegalArgumentException ex) {
            CommonView.showErrorMessage(null, ex.getMessage());
        } catch (Exception ex) {
            CommonView.showErrorMessage(null, "Lỗi không xác định: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void searchEmployee() {
        String firstName = selectEmployeeView.getSearchField().getText().trim();
        if (firstName.isEmpty()) {
            CommonView.showErrorMessage(null, "Vui lòng nhập thông tin nhân viên cần tìm!");
            return;
        }
        ArrayList<Employees> employeesList = employeeService.searchEmployeeByName(firstName);
        if (employeesList.isEmpty()) {
            CommonView.showInfoMessage(null, "Không tìm thấy nhân viên!");
        } else {
            loadTable(employeesList);
        }
    }

    private void selectEmployee() {
        int row = selectEmployeeView.getTable().getSelectedRow();
        if (row == -1) {
            throw new IllegalArgumentException("Vui lòng chọn nhân viên!");
        }
        if (CommonView.confirmAction(null, "Bạn có chắc chắn chọn nhân viên này?")) {
            employeeResult = createEmployeeFromRow(row);
            selectEmployeeView.setEmployee(employeeResult); // Update view with selected employee
            selectEmployeeView.dispose();
            CommonView.showInfoMessage(null, "Chọn thành công!");
        }
    }

    private Employees createEmployeeFromRow(int row) {
        // Safely retrieve values with null checks
        String employeeID = (String) selectEmployeeView.getTable().getValueAt(row, 0);
        String lastName = (String) selectEmployeeView.getTable().getValueAt(row, 1);
        String firstName = (String) selectEmployeeView.getTable().getValueAt(row, 2);
        String position = (String) selectEmployeeView.getTable().getValueAt(row, 3);
        String email = (String) selectEmployeeView.getTable().getValueAt(row, 4);
        String phoneNumber = (String) selectEmployeeView.getTable().getValueAt(row, 5);
        Double salary = (Double) selectEmployeeView.getTable().getValueAt(row, 6);
        Object genderObj = selectEmployeeView.getTable().getValueAt(row, 7);
        Date dateOfBirth = (Date) selectEmployeeView.getTable().getValueAt(row, 8);

        // Handle null values
        employeeID = employeeID != null ? employeeID : "";
        lastName = lastName != null ? lastName : "";
        firstName = firstName != null ? firstName : "";
        position = position != null ? position : "";
        email = email != null ? email : "";
        phoneNumber = phoneNumber != null ? phoneNumber : "";
        salary = salary != null ? salary : 0.0;
        dateOfBirth = dateOfBirth != null ? dateOfBirth : new Date(); // Default to current date or null if constructor allows

        // Handle gender
        String gender;
        if (genderObj instanceof Integer) {
            gender = ((Integer) genderObj == 1) ? "Nam" : "Nữ";
        } else if (genderObj instanceof String) {
            gender = (String) genderObj;
        } else {
            gender = "Không xác định"; // Default for unexpected cases
        }

        return new Employees(firstName, lastName, dateOfBirth, phoneNumber, email, gender, employeeID, position, salary);
    }

    private void loadTable(List<Employees> employeesList) {
        selectEmployeeView.getTableModel().setRowCount(0);
        for (Employees employee : employeesList) {
            Object[] row = {
                    employee.getEmployeeID(),
                    employee.getLastName(),
                    employee.getFirstName(),
                    employee.getPosition(),
                    employee.getEmail(),
                    employee.getPhoneNumber(),
                    employee.getSalary(),
                    employee.getGender(),
                    employee.getDateOfBirth()
            };
            selectEmployeeView.getTableModel().addRow(row);
        }
    }

    public Employees getEmployeeResult() {
        return employeeResult;
    }
}