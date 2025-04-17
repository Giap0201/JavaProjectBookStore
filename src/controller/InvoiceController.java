package controller;

import model.Customers;
import model.Employees;
import model.Invoice;
import service.CustomerService;
import service.EmployeeService;
import service.InvoiceService;
import utils.CommonView;
import view.ManageInvoiceView;
import view.SelectCustomerView;
import view.SelectEmployeeView;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class InvoiceController implements ActionListener {
    private final ManageInvoiceView manageInvoiceView;
    private final InvoiceService invoiceService;
    private final CustomerService customerService;
    private final EmployeeService employeeService;
    private SelectCustomerView selectCustomerView;
    private SelectEmployeeView selectEmployeeView;
    private Customers customersResult;
    private Employees employeesResult;

    public InvoiceController(ManageInvoiceView manageInvoiceView) {
        this.manageInvoiceView = manageInvoiceView;
        invoiceService = new InvoiceService();
        customerService = new CustomerService();
        employeeService = new EmployeeService();
        initializeView();
    }

    private void initializeView() {
        updateTableInvoice(invoiceService.getAllInvoice());
        registerButtonListeners();
        setEnabledFiled();
        addTableSelectionListener();
    }

    private void setEnabledFiled() {
        manageInvoiceView.getTextFieldCustomerId().setEditable(false);
        manageInvoiceView.getTextFieldEmployee().setEditable(false);
    }

    private void registerButtonListeners() {
        manageInvoiceView.getBtnDelete().addActionListener(this);
        manageInvoiceView.getBtnSearchCustomer().addActionListener(this);
        manageInvoiceView.getBtnSearchEmployee().addActionListener(this);
        manageInvoiceView.getBtnSave().addActionListener(this);
        manageInvoiceView.getBtnChange().addActionListener(this);
        manageInvoiceView.getBtnLoad().addActionListener(this);
        manageInvoiceView.getBtnSearch().addActionListener(this);
    }

    private void resetFrom() {
        manageInvoiceView.getTextFieldCustomerId().setText("");
        manageInvoiceView.getTextFieldEmployee().setText("");
        manageInvoiceView.getTextFieldInvoiceId().setText("");
        manageInvoiceView.getDate().setDate(null);
        manageInvoiceView.getjComboBoxTT().setSelectedIndex(0);
    }

    private void resetAll() {
        resetFrom();
        refreshInvoiceTable();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == manageInvoiceView.getBtnDelete()) {
                deleteInvoice();
            } else if (e.getSource() == manageInvoiceView.getBtnSearchCustomer()) {
                openSelectCustomer();
            } else if (e.getSource() == manageInvoiceView.getBtnSearchEmployee()) {
                openSelectEmployee();
            } else if (e.getSource() == manageInvoiceView.getBtnChange()) {
                updateInvoice();
            } else if (e.getSource() == manageInvoiceView.getBtnLoad()) {
                resetAll();
            } else if (e.getSource() == manageInvoiceView.getBtnSave()) {

            } else if (e.getSource() == manageInvoiceView.getBtnSearch()){
                searchInvoice();
            }
        } catch (IllegalArgumentException ex) {
            CommonView.showErrorMessage(manageInvoiceView, ex.getMessage());
        } catch (Exception ex) {
            CommonView.showErrorMessage(manageInvoiceView, "Lỗi không xác định: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    //thao tac cap nhat hoa don
    private void updateInvoice() {
        Invoice invoice = getInvoiceFromForm();
        if (CommonView.confirmAction(manageInvoiceView, "Bạn chắc chắn muốn sửa thông tin hoá đơn này?")) {
            if (!invoiceService.updateInvoice(invoice)) {
                throw new IllegalArgumentException("Không thể sửa hoá đơn!!");
            } else {
                CommonView.showInfoMessage(manageInvoiceView, "Cập nhật hoá đơn thành công!!");
                refreshInvoiceTable();
                resetFrom();
            }
        }
    }

    //phuong thuc lay du lieu tim kiem tu from, du lieu nay co the null, thuc hien tim kiem
    //theo nhieu dieu kien
    private Invoice getSearchInvoice() {
        String invoiceId = manageInvoiceView.getTextFieldInvoiceId().getText().trim();
        String customerId = manageInvoiceView.getTextFieldCustomerId().getText().trim();
        String employeeId = manageInvoiceView.getTextFieldEmployee().getText().trim();
        java.util.Date date = manageInvoiceView.getDate().getDate();
        if(invoiceId.isEmpty() && customerId.isEmpty() && employeeId.isEmpty()  &&
                manageInvoiceView.getjComboBoxTT().getSelectedIndex()<=0 && date == null){
            throw new IllegalArgumentException("Vui lòng thêm điều kiện tìm kiếm!");
        }
        Customers customer = null;
        if (!customerId.isEmpty()) {
            customer = customerService.getCustomerById(customerId);
        }
//        System.out.println(customer.getCustomerID());
        Employees employee = null;
        if (!employeeId.isEmpty()) {
            employee = employeeService.getEmployeeByID(employeeId);
        }
        java.sql.Date sqlDate = null;
        if (date != null) {
            sqlDate = new java.sql.Date(date.getTime());
        }
        String status = null;
        int index = manageInvoiceView.getjComboBoxTT().getSelectedIndex();
        if(index > 0){
            status = (String) manageInvoiceView.getjComboBoxTT().getSelectedItem();
        }
       return new Invoice(invoiceId, sqlDate, customer, employee, status);
    }

    private void searchInvoice() {
        Invoice invoice = getSearchInvoice();
        ArrayList<Invoice> listInvoice = invoiceService.getInvoiceSearch(invoice);
        if(!listInvoice.isEmpty()){
            updateTableInvoice(listInvoice);
        }else {
            updateTableInvoice(null);
            CommonView.showInfoMessage(manageInvoiceView,"Không tìm thấy!");
        }
    }

    //phuong thuc lay du lieu tu form va thuc hien thao tac sua,luu thay doi trong db, khong cho null
    private Invoice getInvoiceFromForm() {
        String invoiceId = manageInvoiceView.getTextFieldInvoiceId().getText().trim();
        String customerId = manageInvoiceView.getTextFieldCustomerId().getText().trim();
        String employeeId = manageInvoiceView.getTextFieldEmployee().getText().trim();
        java.util.Date date = manageInvoiceView.getDate().getDate();
        validateFormData(invoiceId, customerId, employeeId, date);

        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        String status = (String) manageInvoiceView.getjComboBoxTT().getSelectedItem();
        if (manageInvoiceView.getjComboBoxTT().getSelectedIndex() <= 0 || status == null) {
            throw new IllegalArgumentException("Trạng thái không hợp lệ!");
        }

        Customers customer = customerService.getCustomerById(customerId);
        if (customer == null) {
            throw new IllegalArgumentException("Khách hàng với ID " + customerId + " không tồn tại!");
        }
        Employees employee = employeeService.getEmployeeByID(employeeId);
        if (employee == null) {
            throw new IllegalArgumentException("Nhân viên với ID " + employeeId + " không tồn tại!");
        }

        return new Invoice(invoiceId, sqlDate, customer, employee, status);
    }

    // Kiểm tra dữ liệu form
    private void validateFormData(String invoiceId, String customerId, String employeeId, java.util.Date date) {
        if (invoiceId.isEmpty() || customerId.isEmpty() || employeeId.isEmpty()) {
            throw new IllegalArgumentException("Vui lòng điền đầy đủ thông tin hóa đơn!");
        }
        if (date == null) {
            throw new IllegalArgumentException("Vui lòng chọn ngày lập hóa đơn!");
        }
    }


    // Tải dữ liệu hóa đơn lên form (từ bảng hoặc ID cụ thể)
    private void loadInvoiceToForm(String invoiceId) {
        if (invoiceId == null) {
            int selectedRow = manageInvoiceView.getTableInvoice().getSelectedRow();
            if (selectedRow == -1) {
                invoiceId = manageInvoiceView.getTextFieldInvoiceId().getText().trim();
                if (invoiceId.isEmpty()) {
                    throw new IllegalArgumentException("Vui lòng chọn hóa đơn từ bảng hoặc nhập ID hóa đơn!");
                }
            } else {
                invoiceId = (String) manageInvoiceView.getTableInvoice().getValueAt(selectedRow, 0);
            }
        }

        Invoice invoice = invoiceService.getInvoiceByID(invoiceId);
        if (invoice == null) {
            throw new IllegalArgumentException("Hóa đơn với ID " + invoiceId + " không tồn tại!");
        }
        setDataToForm(invoice);
    }

    // Điền dữ liệu hóa đơn vào form
    private void setDataToForm(Invoice invoice) {
        manageInvoiceView.getTextFieldInvoiceId().setText(invoice.getInvoiceID() != null ? invoice.getInvoiceID() : "");
        manageInvoiceView.getTextFieldCustomerId().setText(invoice.getCustomer() != null && invoice.getCustomer().getCustomerID() != null ? invoice.getCustomer().getCustomerID() : "");
        manageInvoiceView.getTextFieldEmployee().setText(invoice.getEmployee() != null && invoice.getEmployee().getEmployeeID() != null ? invoice.getEmployee().getEmployeeID() : "");
        manageInvoiceView.getDate().setDate(invoice.getDate());
        manageInvoiceView.getjComboBoxTT().setSelectedItem(invoice.getStatus() != null ? invoice.getStatus() : "");
    }

    // Mở cửa sổ chọn khách hàng
    private void openSelectCustomer() {
        try {
            selectCustomerView = new SelectCustomerView();
            selectCustomerView.setVisible(true);
            customersResult = selectCustomerView.getCustomers();
            if (customersResult != null) {
                manageInvoiceView.getTextFieldCustomerId().setText(customersResult.getCustomerID());
            }
        } catch (Exception ex) {
            CommonView.showErrorMessage(manageInvoiceView, "Lỗi khi mở cửa sổ chọn khách hàng: " + ex.getMessage());
        }
    }

    // Mở cửa sổ chọn nhân viên
    private void openSelectEmployee() {
        try {
            selectEmployeeView = new SelectEmployeeView();
            selectEmployeeView.setVisible(true);
            employeesResult = selectEmployeeView.getEmployee();
            if (employeesResult != null) {
                manageInvoiceView.getTextFieldEmployee().setText(employeesResult.getEmployeeID());
            }
        } catch (Exception ex) {
            CommonView.showErrorMessage(manageInvoiceView, "Lỗi khi mở cửa sổ chọn nhân viên: " + ex.getMessage());
        }
    }

    // Xóa hóa đơn
    private void deleteInvoice() {
        String textFieldId = manageInvoiceView.getTextFieldInvoiceId().getText().trim();
        List<String> selectedIds = getInvoiceTable();
        if (textFieldId.isEmpty() && selectedIds.isEmpty()) {
            CommonView.showErrorMessage(manageInvoiceView, "Vui lòng nhập ID hóa đơn hoặc chọn từ bảng!");
            return;
        }
        // Bước 2: Xác định ID cần xóa và thông báo xác nhận
        List<String> invoiceIdsToDelete = new ArrayList<>();
        String confirmationMessage;

        if (!textFieldId.isEmpty()) {
            invoiceIdsToDelete.add(textFieldId);
            confirmationMessage = String.format("Bạn có chắc chắn muốn xóa hóa đơn có ID: %s?", textFieldId);
        } else {
            invoiceIdsToDelete.addAll(selectedIds);
            confirmationMessage = String.format("Bạn có chắc chắn muốn xóa %d hóa đơn đã chọn?", selectedIds.size());
        }
        if (!CommonView.confirmAction(manageInvoiceView, confirmationMessage)) {
            return;
        }
        List<String> failedDeletions = new ArrayList<>();
        int successfulDeletions = 0;

        for (String invoiceId : invoiceIdsToDelete) {
            try {
                Invoice invoice = invoiceService.getInvoiceByID(invoiceId);
                if (invoice == null) {
                    failedDeletions.add(String.format("%s (Không có hóa đơn này)", invoiceId));
                    continue;
                }
                if (invoiceService.deleteInvoice(invoiceId)) {
                    successfulDeletions++;
                } else {
                    failedDeletions.add(String.format("%s (Không thể xóa)", invoiceId));
                }
            } catch (RuntimeException ex) {
                failedDeletions.add(String.format("%s (%s)", invoiceId, ex.getMessage()));
            }
        }

        // Bước 5: Hiển thị kết quả
        StringBuilder resultMessage = new StringBuilder();
        if (successfulDeletions > 0) {
            resultMessage.append(String.format("Đã xóa thành công %d hóa đơn.\n", successfulDeletions));
            refreshInvoiceTable();
            manageInvoiceView.getTextFieldInvoiceId().setText("");
        }
        if (!failedDeletions.isEmpty()) {
            resultMessage.append("Không thể xóa các hóa đơn sau:\n").append(String.join("\n", failedDeletions));
        }

        if (!resultMessage.isEmpty()) {
            if (successfulDeletions > 0 && failedDeletions.isEmpty()) {
                CommonView.showInfoMessage(manageInvoiceView, resultMessage.toString().trim());
            } else {
                CommonView.showErrorMessage(manageInvoiceView, resultMessage.toString().trim());
            }
        }
    }

    private void refreshInvoiceTable() {
        try {
            List<Invoice> invoiceList = invoiceService.getAllInvoice();
            updateTableInvoice(invoiceList);
        } catch (Exception e) {
            CommonView.showErrorMessage(manageInvoiceView, "Lỗi khi tải lại danh sách hoá đơn: " + e.getMessage());
        }
    }

    private void updateTableInvoice(List<Invoice> invoiceList) {
        manageInvoiceView.getTableModelInvoice().setRowCount(0);
        if (invoiceList != null) {
            for (Invoice invoice : invoiceList) {
                Object[] row = {
                        invoice.getInvoiceID(),
                        invoice.getCustomer().getFirstName(),
                        invoice.getCustomer().getPhoneNumber(),
                        invoice.getEmployee().getEmployeeID(),
                        invoice.getEmployee().getFirstName(),
                        invoice.getDate(),
                        invoice.getStatus()
                };
                manageInvoiceView.getTableModelInvoice().addRow(row);
            }
        }
    }


    // Lấy danh sách ID hóa đơn từ bảng
    private List<String> getInvoiceTable() {
        List<String> invoiceIds = new ArrayList<>();
        int[] selectedRows = manageInvoiceView.getTableInvoice().getSelectedRows();
        for (int row : selectedRows) {
            if (row >= 0 && row < manageInvoiceView.getTableInvoice().getRowCount()) {
                Object value = manageInvoiceView.getTableInvoice().getValueAt(row, 0);
                if (value != null) {
                    invoiceIds.add(value.toString());
                }
            }
        }
        return invoiceIds;
    }

    private void addTableSelectionListener() {
        manageInvoiceView.getTableInvoice().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    try {
                        loadInvoiceToForm(null);
                    } catch (IllegalArgumentException ex) {
                        // Chỉ hiển thị lỗi khi người dùng thực sự tương tác, không hiển thị khi làm mới bảng
                        if (manageInvoiceView.getTableInvoice().getSelectedRow() != -1) {
                            CommonView.showErrorMessage(manageInvoiceView, ex.getMessage());
                        }
                    }
                }
            }
        });
    }
}