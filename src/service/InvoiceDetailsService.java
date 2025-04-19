package service;

import dao.InvoiceDetailDAO;
import model.InvoiceDetails;

public class InvoiceDetailsService {
    private InvoiceDetailDAO invoiceDetailDAO;
    public InvoiceDetailsService(InvoiceDetailDAO invoiceDetailDAO) {
        this.invoiceDetailDAO = invoiceDetailDAO;
    }

}
