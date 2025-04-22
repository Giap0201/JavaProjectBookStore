package service;

import dao.CustomerDAO;
import model.Customers;

import java.util.ArrayList;
import java.util.List;
// Bỏ import List nếu không dùng
// import java.util.List;

public class CustomerService {
    private CustomerDAO customerDAO;

    public CustomerService() {
        this.customerDAO = new CustomerDAO();
    }

    public boolean insertCustomer(Customers customer) {
        // Có thể thêm logic kiểm tra nghiệp vụ ở đây trước khi gọi DAO
        // Ví dụ: kiểm tra trùng mã, validate dữ liệu,...
        if (customer == null || customer.getCustomerID() == null || customer.getCustomerID().trim().isEmpty()) {
            System.err.println("Lỗi Service: Customer hoặc CustomerID không hợp lệ.");
            return false;
        }
        // Kiểm tra xem ID đã tồn tại chưa (nếu cần)
        if (customerDAO.selectById(customer.getCustomerID()) != null) {
            System.err.println("Lỗi Service: Mã khách hàng '" + customer.getCustomerID() + "' đã tồn tại.");
            return false; // Trả về false nếu mã đã tồn tại
        }

        int rs = customerDAO.insert(customer);
        return rs > 0; // Trả về true nếu insert thành công (số dòng bị ảnh hưởng > 0)
    }

    public boolean updateSpending(String customerId, double spending){
        if (customerId == null || customerId.trim().isEmpty()) {
            return false;
        }
        int rs = customerDAO.updateSpending(customerId, spending);
        return rs > 0;
    }

    public boolean updateCustomer(Customers customer) {
        if (customer == null || customer.getCustomerID() == null || customer.getCustomerID().trim().isEmpty()) {
            System.err.println("Lỗi Service: Customer hoặc CustomerID không hợp lệ để cập nhật.");
            return false;
        }
        // Kiểm tra xem ID có tồn tại để update không
        if (customerDAO.selectById(customer.getCustomerID()) == null) {
            System.err.println("Lỗi Service: Mã khách hàng '" + customer.getCustomerID() + "' không tồn tại để cập nhật.");
            return false;
        }

        int rs = customerDAO.update(customer);
        return rs > 0;
    }

    public boolean deleteCustomer(String customerId) {
        if (customerId == null || customerId.trim().isEmpty()) {
            System.err.println("Lỗi Service: CustomerID không hợp lệ để xóa.");
            return false;
        }
        // Kiểm tra xem ID có tồn tại để xóa không
        if (customerDAO.selectById(customerId) == null) {
            System.err.println("Lỗi Service: Mã khách hàng '" + customerId + "' không tồn tại để xóa.");
            return false;
        }
        // Có thể thêm kiểm tra ràng buộc khác (ví dụ: khách hàng có hóa đơn không?)

        int rs = customerDAO.deleteById(customerId);
        return rs > 0;
    }

    public Customers getCustomerById(String customerId) {
        if (customerId == null || customerId.trim().isEmpty()) {
            return null;
        }
        return customerDAO.selectById(customerId);
    }


    public ArrayList<Customers> getAllCustomers() {
        return customerDAO.getAll();
    }

    public ArrayList<Customers> searchCustomers(String field, String value, String gender) {
        // Xử lý đầu vào trước khi gọi DAO
        if (field == null || field.trim().isEmpty()) {
            System.err.println("Lỗi Service: Trường tìm kiếm không được để trống.");
            return new ArrayList<>(); // Trả về rỗng nếu trường tìm kiếm không hợp lệ
        }
        // Làm sạch giá trị tìm kiếm (ví dụ: loại bỏ khoảng trắng thừa)
        String trimmedValue = (value != null) ? value.trim() : "";
        String trimmedGender = (gender != null) ? gender.trim() : "";


        // Kiểm tra xem field có hợp lệ không (phòng SQL Injection nếu xây dựng query không an toàn)
        // Trong trường hợp này DAO đã xử lý an toàn bằng PreparedStatement
        // nhưng kiểm tra vẫn tốt
        List<String> allowedFields = List.of("customerID", "lastName", "firstName", "phoneNumber", "email");
        if (!allowedFields.contains(field)) {
            System.err.println("Lỗi Service: Trường tìm kiếm '" + field + "' không hợp lệ.");
            return new ArrayList<>();
        }


        return customerDAO.search(field, trimmedValue, trimmedGender);
    }
    public ArrayList<Customers> searchCustomerByName(String firstName) {
        if (firstName == null || firstName.trim().isEmpty()) {
            System.err.println("Lỗi Service: Trường tìm kiếm " + firstName +" không hợp lệ.");
            return new ArrayList<>();
        }
        return customerDAO.searchByName(firstName);
    }

    public Customers getCustomerByPhoneNumber(String phoneNumber){
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return null;
        }
        return customerDAO.getCustomerByPhoneNumber(phoneNumber);
    }

    public Customers getCustomerByCustomerId(String customerId){
        if (customerId == null || customerId.trim().isEmpty()) {
            return null;
        }
        return customerDAO.selectById(customerId);
    }

}