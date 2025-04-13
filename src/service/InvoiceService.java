package service;

import dao.InvoiceDAO;
import model.Invoice;

import java.util.ArrayList;

public class InvoiceService {
    private InvoiceDAO invoiceDAO;
    public InvoiceService() {
        this.invoiceDAO = new InvoiceDAO();
    }
    public InvoiceService(InvoiceDAO invoiceDAO) {
        this.invoiceDAO = invoiceDAO;
    }

     public boolean deleteInvoice(String invoiceId) {
        return invoiceDAO.deleteInvoice(invoiceId) > 0;
    }

    public ArrayList<Invoice> getAllInvoice(){
        return invoiceDAO.getAllInvoice();
    }
}
