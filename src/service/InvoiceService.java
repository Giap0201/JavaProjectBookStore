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
    public boolean updateInvoice(Invoice invoice) {
        return invoiceDAO.updateInvoice(invoice) > 0;
    }
    public Invoice getInvoiceByID(String invoiceID){
        if(invoiceID != null && !invoiceID.isEmpty()){
            return invoiceDAO.getInvoiceByID(invoiceID);
        }
        return null;
    }
    public ArrayList<Invoice> getInvoiceSearch(Invoice invoice) {
        return invoiceDAO.listSearchInvoice(invoice);
    }
}
