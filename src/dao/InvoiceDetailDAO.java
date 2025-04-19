package dao;

import model.InvoiceDetails;

import java.util.ArrayList;
import java.util.List;

public class InvoiceDetailDAO {
    //phuong thuc lay ra toan bo chi tiet hoa don
    public List<InvoiceDetails> getAllInvoiceDetails() {
        String query = "select * from invoicedetails join books on books.bookID = invoicedetails.bookID join ";
    }
}
