package service;

import dao.CustomerDAO;
import model.Customers;

import java.util.ArrayList;
import java.util.List;

public class CustomerService {
    private CustomerDAO customerDAO ;

    public CustomerService(){
        this.customerDAO = new CustomerDAO();
    }

    public boolean insertCustomer(Customers customers) {
        int rs = customerDAO.insert(customers);
        if (rs == 0) {
            return false;
        }
        return true;
    }

    public ArrayList<Customers> getAllCustomers() {
        return customerDAO.getAll();
    }

}
