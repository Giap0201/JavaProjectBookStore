package controller;

import model.Invoice;
import service.InvoiceService;
import utils.CommonView;
import view.ManageInvoiceView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class InvoiceController implements ActionListener {
    private final ManageInvoiceView manageInvoiceView;
    private final InvoiceService invoiceService;

    public InvoiceController(ManageInvoiceView manageInvoiceView) {
        this.manageInvoiceView = manageInvoiceView;
        invoiceService = new InvoiceService();
        initializeView();
    }

    private void initializeView() {
        updateTableInvoice(invoiceService.getAllInvoice());
        registerButtonListeners();

    }

    private void registerButtonListeners() {
        manageInvoiceView.getBtnDelete().addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == manageInvoiceView.getBtnDelete()) {
                deleteInvoice();
            }
        } catch (IllegalArgumentException ex) {
            CommonView.showErrorMessage(manageInvoiceView, ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            CommonView.showInfoMessage(manageInvoiceView, "lỗi không xác định " + ex.getMessage());
        }
    }

    private void deleteInvoice() {
        String invoiceID = manageInvoiceView.getTextFieldInvoiceId().getText().trim();
        if (invoiceID.isEmpty()) {
            throw new IllegalArgumentException("Vui lòng nhập hoá đơn cần xoá!");
        }
        if (CommonView.confirmAction(manageInvoiceView, "Bạn có chắc chắn muốn xoá!")) {
            boolean checkDelete = invoiceService.deleteInvoice(invoiceID);
            if (!checkDelete) {
                throw new IllegalArgumentException("Không thể xoá hoá đơn");
            }
            CommonView.showInfoMessage(manageInvoiceView, "Đã xoá hoá đơn thành công!");
        }
    }

    private void updateTableInvoice(ArrayList<Invoice> invoiceList) {
        manageInvoiceView.getTableModelInvoice().setRowCount(0);
        for (Invoice invoice : invoiceList) {
            Object[] row = {invoice.getInvoiceID(), invoice.getCustomer().getFirstName(), invoice.getCustomer().getLastName(), invoice.getEmployee().getLastName(), invoice.getDate(), invoice.getStatus()};
            manageInvoiceView.getTableModelInvoice().addRow(row);
        }
    }


}

