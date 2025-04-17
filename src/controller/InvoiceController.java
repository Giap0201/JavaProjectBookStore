package controller;

import model.Customers;
import model.Employees;
import model.Invoice;
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
    private SelectCustomerView selectCustomerView;
    private SelectEmployeeView selectEmployeeView;
    private Customers customersResult;
    private Employees employeesResult;

    public InvoiceController(ManageInvoiceView manageInvoiceView) {
        this.manageInvoiceView = manageInvoiceView;
        invoiceService = new InvoiceService();
        initializeView();
    }

    private void initializeView() {
        updateTableInvoice(invoiceService.getAllInvoice());
        registerButtonListeners();
        setEnabledFiled();
        addTableSelectionListener();

    }

    //xet cho cac truong nhap khong cho phep nhap
    private void setEnabledFiled() {
        manageInvoiceView.getTextFieldCustomerId().setEditable(false);
        manageInvoiceView.getTextFieldEmployee().setEditable(false);
    }

    //dang ki su kien cho cac nut bam
    private void registerButtonListeners() {
        manageInvoiceView.getBtnDelete().addActionListener(this);
        manageInvoiceView.getBtnSearchCustomer().addActionListener(this);
        manageInvoiceView.getBtnSearchEmployee().addActionListener(this);
        manageInvoiceView.getBtnSave().addActionListener(this);
        manageInvoiceView.getBtnChange().addActionListener(this);
        manageInvoiceView.getBtnLoad().addActionListener(this);

    }

    //lam trong form
    private void resetFrom() {
        manageInvoiceView.getTextFieldCustomerId().setText("");
        manageInvoiceView.getTextFieldEmployee().setText("");
        manageInvoiceView.getTextFieldInvoiceId().setText("");
        manageInvoiceView.getDate().setDate(null);
        manageInvoiceView.getjComboBoxTT().setSelectedIndex(0);
    }

    //lam moi
    private void resetAll() {
        resetFrom();
        updateTableInvoice(invoiceService.getAllInvoice());
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
                manageInvoiceView.getTextFieldInvoiceId().setEditable(false);
                updateInvoice();
            } else if (e.getSource() == manageInvoiceView.getBtnLoad()) {
                resetAll();
            }
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
            CommonView.showErrorMessage(manageInvoiceView, ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            CommonView.showInfoMessage(manageInvoiceView, "lỗi không xác định " + ex.getMessage());
        }
    }

    //chuc nang sua
    private void updateInvoice() {
        Invoice invoice = getLoadDataToForm();
        setDataToForm(invoice);
    }

    private Invoice getLoadDataToForm() {
        String invoiceID;
        int selectedRow = manageInvoiceView.getTableInvoice().getSelectedRow();
        if (selectedRow == -1) {
            invoiceID = manageInvoiceView.getTextFieldInvoiceId().getText().trim();
        } else {
            invoiceID = (String) manageInvoiceView.getTableInvoice().getValueAt(selectedRow, 0);
        }
        if (invoiceID.isEmpty()) {
            throw new IllegalArgumentException("Vui lòng chọn hóa đơn từ bảng hoặc nhập ID hóa đơn!");
        }

        Invoice invoice = invoiceService.getInvoiceByID(invoiceID);
        if (invoice == null) {
            throw new IllegalArgumentException("Hóa đơn với ID " + invoiceID + " không tồn tại!");
        }
        return invoice;
    }

    private void setDataToForm(Invoice invoice) {
        manageInvoiceView.getTextFieldInvoiceId().setText(invoice.getInvoiceID() != null ? invoice.getInvoiceID() : "");
        manageInvoiceView.getTextFieldCustomerId().setText(invoice.getCustomer() != null && invoice.getCustomer().getCustomerID() != null ? invoice.getCustomer().getCustomerID() : "");
        manageInvoiceView.getTextFieldEmployee().setText(invoice.getEmployee() != null && invoice.getEmployee().getEmployeeID() != null ? invoice.getEmployee().getEmployeeID() : "");
        manageInvoiceView.getDate().setDate(invoice.getDate());
        manageInvoiceView.getjComboBoxTT().setSelectedItem(invoice.getStatus() != null ? invoice.getStatus() : "");
    }

    //khi click vao tim kiem thi chon khach hang
    private void openSelectCustomer() {
        selectCustomerView = new SelectCustomerView();
        selectCustomerView.setVisible(true);
        customersResult = selectCustomerView.getCustomers();
//        System.out.println(customersResult.getCustomerID());
        if (customersResult != null) {
            manageInvoiceView.getTextFieldCustomerId().setText(customersResult.getCustomerID());
        }
    }

    //mo cua so tim kiem nhan vien
    private void openSelectEmployee() {
        selectEmployeeView = new SelectEmployeeView();
        selectEmployeeView.setVisible(true);
        employeesResult = selectEmployeeView.getEmployee();
        if (employeesResult != null) {
            manageInvoiceView.getTextFieldEmployee().setText(employeesResult.getEmployeeID());
        }
    }


    private void deleteInvoice() {
        // 1. Ưu tiên lấy danh sách ID từ bảng
        List<String> idsFromTable = getInvoiceTable();
        // 2. Nếu không có gì được chọn trong bảng, lấy ID từ text field
        String idFromTextField = manageInvoiceView.getTextFieldInvoiceId().getText().trim();

        List<String> idsToDelete = new ArrayList<>(); // Danh sách cuối cùng các ID cần xóa
        String confirmationMessage = "";

        // 3. Xác định danh sách ID cần xóa và thông báo xác nhận
        if (!idsFromTable.isEmpty()) {
            // Nếu có chọn trong bảng, dùng danh sách đó
            idsToDelete.addAll(idsFromTable);
            manageInvoiceView.getTextFieldInvoiceId().setText("");
            confirmationMessage = "Bạn có chắc chắn muốn xoá " + idsToDelete.size() + " hoá đơn đã chọn?";
        } else if (!idFromTextField.isEmpty()) {
            idsToDelete.add(idFromTextField);
            confirmationMessage = "Bạn có chắc chắn muốn xoá hoá đơn có ID: " + idFromTextField + "?";
        } else {
            CommonView.showErrorMessage(manageInvoiceView, "Vui lòng chọn hoá đơn từ bảng hoặc nhập ID hoá đơn cần xoá!");
            return;
        }
        if (CommonView.confirmAction(manageInvoiceView, confirmationMessage)) {
            int successCount = 0;
            List<String> failedIds = new ArrayList<>();

            // 5. Thực hiện xóa cho từng ID trong danh sách
            for (String id : idsToDelete) {
                try {
                    boolean checkDelete = invoiceService.deleteInvoice(id); // Gọi service để xóa
                    if (checkDelete) {
                        successCount++;
                    } else {
                        failedIds.add(id + " (Không thể xoá)");
                    }
                } catch (RuntimeException ex) {
                    // Bắt lỗi cụ thể từ service (ví dụ: ID không tồn tại, lỗi DB)
                    System.err.println("Lỗi khi xoá hoá đơn ID " + id + ": " + ex.getMessage());
                    failedIds.add(id + " (" + ex.getMessage() + ")"); // Ghi lại ID và lý do lỗi
                }
            }

            StringBuilder resultMessage = new StringBuilder();
            if (successCount > 0) {
                resultMessage.append("Đã xoá thành công ").append(successCount).append(" hoá đơn.\n");
                refreshInvoiceTable(); // Gọi phương thức làm mới bảng
                manageInvoiceView.getTextFieldInvoiceId().setText("");
            }
            if (!failedIds.isEmpty()) {
                resultMessage.append("Không thể xoá các hoá đơn sau:\n").append(String.join("\n", failedIds));
                CommonView.showErrorMessage(manageInvoiceView, resultMessage.toString()); // Hiển thị lỗi nếu có thất bại
            } else if (successCount > 0) {
                CommonView.showInfoMessage(manageInvoiceView, resultMessage.toString().trim()); // Chỉ hiển thị thành công nếu không có lỗi
            }
        }
    }

    // Phương thức làm mới bảng (cần tạo nếu chưa có)
    private void refreshInvoiceTable() {
        try {
            List<Invoice> invoiceList = invoiceService.getAllInvoice();
            updateTableInvoice(invoiceList); // Cập nhật lại bảng
        } catch (Exception e) {
            e.printStackTrace();
            CommonView.showErrorMessage(manageInvoiceView, "Lỗi khi tải lại danh sách hoá đơn: " + e.getMessage());
        }
    }

    private void updateTableInvoice(List<Invoice> invoiceList) {
        manageInvoiceView.getTableModelInvoice().setRowCount(0);
        if (invoiceList != null) {
            for (Invoice invoice : invoiceList) {
                Object[] row = {invoice.getInvoiceID(), invoice.getCustomer().getFirstName(), invoice.getCustomer().getLastName(),
                        invoice.getEmployee().getLastName(), invoice.getDate(), invoice.getStatus()};
                manageInvoiceView.getTableModelInvoice().addRow(row);
            }
        }
    }

    private List<String> getInvoiceTable() {
        ArrayList<String> listInvoiceID = new ArrayList<>();
        int[] rowSelected = manageInvoiceView.getTableInvoice().getSelectedRows();
        for (int row : rowSelected) {
            if (row >= 0 && row < manageInvoiceView.getTableInvoice().getRowCount()) {
                Object value = manageInvoiceView.getTableInvoice().getValueAt(row, 0);
                if (value != null) {
                    listInvoiceID.add(value.toString());
                }
            }
        }
        return listInvoiceID;
    }

    //theo tac them su kien cho bang de khi click vao thi tu dong hien thi du lieu
    private void addTableSelectionListener() {
        manageInvoiceView.getTableInvoice().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    try {
                        updateInvoice();
                    } catch (IllegalArgumentException ex) {
                        CommonView.showErrorMessage(manageInvoiceView, ex.getMessage());
                    }
                }
            }
        });
    }
}

