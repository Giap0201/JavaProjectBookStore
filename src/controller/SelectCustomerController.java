package controller;

import model.Customers;
import service.CustomerService;
import utils.CommonView;
import view.SelectCustomerView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

public class SelectCustomerController implements ActionListener {
    private final SelectCustomerView selectCustomerView;
    private final CustomerService customerService;
    private Customers customerResult;

    public SelectCustomerController(SelectCustomerView selectCustomerView) {
        this.selectCustomerView = selectCustomerView;
        customerService = new CustomerService();
        initializeView();
    }

    private void initializeView() {
        loadTable(customerService.getAllCustomers());
        registerButtonListeners();
    }

    private void registerButtonListeners() {
        selectCustomerView.getSearchButton().addActionListener(this);
        selectCustomerView.getAllButton().addActionListener(this);
        selectCustomerView.getSelectButton().addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == selectCustomerView.getSearchButton()) {
                searchCustomer();
            } else if (e.getSource() == selectCustomerView.getAllButton()) {
                loadTable(customerService.getAllCustomers());
            } else if (e.getSource() == selectCustomerView.getSelectButton()) {
                selectCustomer();
            }
        } catch (IllegalArgumentException ex) {
            CommonView.showErrorMessage(null, ex.getMessage());
        } catch (Exception ex) {
            CommonView.showErrorMessage(null, "Lỗi không xác định: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void searchCustomer() {
        String firstName = selectCustomerView.getSearchField().getText().trim();
        if (firstName.isEmpty()) {
            CommonView.showInfoMessage(null, "Vui lòng nhập thông tin khách hàng cần tìm!");
            return;
        }
        ArrayList<Customers> listCustomers = customerService.searchCustomerByName(firstName);
        if (listCustomers.isEmpty()) {
            CommonView.showInfoMessage(null, "Không tìm thấy khách hàng!");
        } else {
            loadTable(listCustomers);
        }
    }

    private void selectCustomer() {
        int row = selectCustomerView.getTable().getSelectedRow();
        if (row == -1) {
            throw new IllegalArgumentException("Vui lòng chọn khách hàng!");
        }

        if (CommonView.confirmAction(null, "Bạn chắc chắn chọn khách hàng này?")) {
            customerResult = createCustomerFromRow(row);
            selectCustomerView.setCustomers(customerResult);
            selectCustomerView.dispose();
            CommonView.showInfoMessage(null, "Chọn thành công!");
        }
    }

    private Customers createCustomerFromRow(int row) {
        String customerID = (String) selectCustomerView.getTable().getValueAt(row, 0);
        String lastName = (String) selectCustomerView.getTable().getValueAt(row, 1);
        String firstName = (String) selectCustomerView.getTable().getValueAt(row, 2);
        Object genderObj = selectCustomerView.getTable().getValueAt(row, 3);
        String phoneNumber = (String) selectCustomerView.getTable().getValueAt(row, 4);
        String email = (String) selectCustomerView.getTable().getValueAt(row, 5);
        Date dateOfBirth = (Date) selectCustomerView.getTable().getValueAt(row, 6);
        Double totalMoney = (Double) selectCustomerView.getTable().getValueAt(row, 7);
        Date creationDate = (Date) selectCustomerView.getTable().getValueAt(row, 8);
        String note = (String) selectCustomerView.getTable().getValueAt(row, 9);

        // Handle null values
        customerID = customerID != null ? customerID : "";
        lastName = lastName != null ? lastName : "";
        firstName = firstName != null ? firstName : "";
        phoneNumber = phoneNumber != null ? phoneNumber : "";
        email = email != null ? email : "";
        totalMoney = totalMoney != null ? totalMoney : 0.0;
        note = note != null ? note : "";
        dateOfBirth = dateOfBirth != null ? dateOfBirth : new Date();
        creationDate = creationDate != null ? creationDate : new Date();

        // Handle gender
        String gender;
        if (genderObj instanceof Integer) {
            gender = ((Integer) genderObj == 1) ? "Nam" : "Nữ";
        } else if (genderObj instanceof String) {
            gender = (String) genderObj;
        } else {
            gender = "Không xác định"; // Default for unexpected cases
        }

        return new Customers(firstName, lastName, dateOfBirth, phoneNumber, email, gender, customerID,
                totalMoney, creationDate, note);
    }

    private void loadTable(ArrayList<Customers> listCustomer) {
        selectCustomerView.getTableModel().setRowCount(0);
        for (Customers customer : listCustomer) {
            Object[] row = {
                    customer.getCustomerID(),
                    customer.getLastName(),
                    customer.getFirstName(),
                    customer.getGender(),
                    customer.getPhoneNumber(),
                    customer.getEmail(),
                    customer.getDateOfBirth(),
                    customer.getTotalMoney(),
                    customer.getCreationDate(),
                    customer.getNote()
            };
            selectCustomerView.getTableModel().addRow(row);
        }
    }

    public Customers getCustomerResult() {
        return customerResult;
    }
}