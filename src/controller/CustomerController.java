package controller;

import model.Customers;
import service.CustomerService;
import view.CustomerView;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CustomerController implements ActionListener {
    private CustomerView customerView;
    private CustomerService customerService;

    public CustomerController(CustomerView customerView) {
        this.customerView = customerView;
        this.customerService = new CustomerService();
        customerView.tableModel = new DefaultTableModel();
        updateCustomerTable();
        //updateTotalCustomers();
    }

    public void updateCustomerTable() {
        ArrayList<Customers> customers = customerService.getAllCustomers();
        customerView.updateTable(customers);
    }

    //    public void updateTotalCustomers() {
//        int total = customerService.getAllCustomers().size();
//        customerView.setTextFieldTotalCustomers();
//    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == customerView.getBtnAdd()) {
            handleAddCustomer();
        } else if (e.getSource() == customerView.getBtnUpdate()) {
            //handleUpdateCustomer();
        } else if (e.getSource() == customerView.getBtnDelete()) {
            //handleDeleteCustomer();
        } else if (e.getSource() == customerView.getBtnSearch()) {
            //handleSearchCustomer();
        } else if (e.getSource() == customerView.getBtnReset()) {
            // handleReset();
        }
    }

    private void handleAddCustomer() {
        try {
            Customers customer = createCustomerFromInput();
//            if (customerService.getCustomerById(customer.getCustomerID()) != null) {
//                customerView.showMessage("Mã khách hàng đã tồn tại!");
//                return;
//            }
            customerService.insertCustomer(customer);
            updateCustomerTable();
            //updateTotalCustomers();
            customerView.showMessage("Thêm khách hàng thành công!");
        } catch (Exception ex) {
            customerView.showMessage("Lỗi khi thêm khách hàng: " + ex.getMessage());
        }
    }

//    private void handleUpdateCustomer() {
//        try {
//            Customers customer = createCustomerFromInput();
//            if (customerService.getCustomerById(customer.getCustomerID()) == null) {
//                customerView.showMessage("Mã khách hàng không tồn tại!");
//                return;
//            }
//            customerService.updateCustomer(customer);
//            updateCustomerTable();
//            updateTotalCustomers();
//            customerView.showMessage("Cập nhật khách hàng thành công!");
//        } catch (Exception ex) {
//            customerView.showMessage("Lỗi khi cập nhật khách hàng: " + ex.getMessage());
//        }
//    }
//
//    private void handleDeleteCustomer() {
//        String customerId = customerView.getTextFieldCustomerId().getText();
//        if (customerId.isEmpty()) {
//            customerView.showMessage("Vui lòng nhập mã khách hàng để xóa!");
//            return;
//        }
//        if (customerService.getCustomerById(customerId) == null) {
//            customerView.showMessage("Mã khách hàng không tồn tại!");
//            return;
//        }
//        customerService.deleteCustomer(customerId);
//        updateCustomerTable();
//        updateTotalCustomers();
//        customerView.showMessage("Xóa khách hàng thành công!");
//    }
//
//    private void handleSearchCustomer() {
//        String searchField = customerView.getCheckcombobox().getSelectedItem().toString();
//        String searchValue = customerView.getTextFieldSearch().getText();
//        String gender = "";
//        if (customerView.getRadioNamSearch().isSelected()) {
//            gender = "Nam";
//        } else if (customerView.getRadioNuSearch().isSelected()) {
//            gender = "Nữ";
//        }
//
//        String fieldInDb;
//        switch (searchField) {
//            case "Mã KH":
//                fieldInDb = "customerID";
//                break;
//            case "Họ KH":
//                fieldInDb = "lastName";
//                break;
//            case "Tên KH":
//                fieldInDb = "firstName";
//                break;
//            case "Số ĐT":
//                fieldInDb = "phoneNumber";
//                break;
//            case "Email":
//                fieldInDb = "email";
//                break;
//            default:
//                fieldInDb = "customerID";
//        }
//
//        ArrayList<Customers> customers = customerService.searchCustomers(fieldInDb, searchValue, gender);
//        customerView.updateTable(customers);
//    }
//
//    private void handleReset() {
//        customerView.clear();
//        updateCustomerTable();
//        updateTotalCustomers();
//    }

    private Customers createCustomerFromInput() throws ParseException, ParseException {
        String customerId = customerView.getTextFieldCustomerId().getText();
        String firstName = customerView.getTextFieldFirstName().getText();
        String lastName = customerView.getTextFieldLastName().getText();
        String gender = customerView.getRadioPositionNam().isSelected() ? "Nam" : "Nữ";
        String phoneNumber = customerView.getTextFieldPhone().getText();
        String email = customerView.getTextFieldEmail().getText();
        String dobStr = customerView.getTextFieldDob().getText();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date dateOfBirth = sdf.parse(dobStr);
        Date creationDate = new Date(); // Ngày lập thẻ là ngày hiện tại
        String note = ""; // Ghi chú để trống
        double totalMoney = 0.0; // Tổng chi tiêu mặc định là 0

        return new Customers(firstName, lastName, dateOfBirth, phoneNumber, email, gender, customerId, totalMoney, creationDate, note);
    }
}
