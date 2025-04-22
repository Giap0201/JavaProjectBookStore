package service;

import dao.OrderDAO;
import model.Books; // Cần import Books
import model.OrderDetails;
import model.Orders;

import java.util.ArrayList;
import java.util.List; // Cần import List

public class OrderService {
    private OrderDAO orderDAO;
    private BookService bookService; // Thêm BookService để cập nhật tồn kho

    public OrderService() {
        this.orderDAO = new OrderDAO();
        this.bookService = new BookService(); // Khởi tạo
    }

    // Phương thức cũ để thêm 1 chi tiết (có thể không cần nữa)
    @Deprecated
    public boolean addOrder(OrderDetails orderDetails) {
        if (orderDetails == null || orderDetails.getOrderId() == null) {
            System.err.println("Lỗi service: Thông tin chi tiết đơn hàng không hợp lệ.");
            return false;
        }
        // Cần kiểm tra logic ở đây hoặc trong DAO
        return orderDAO.insertBookDetail(orderDetails); // Đổi tên hàm DAO cho rõ
    }

    // Phương thức cũ để lấy chi tiết (vẫn có thể dùng)
    public ArrayList<OrderDetails> getAllOrderDetails(String orderId) {
        return orderDAO.getAllOrderDetails(orderId);
    }

    // Phương thức cũ để thêm header (có thể không cần nữa)
    @Deprecated
    public boolean insertOrder(Orders order) {
        if (order == null || order.getOrderId() == null) {
            System.err.println("Lỗi service: Thông tin đơn hàng không hợp lệ.");
            return false;
        }
        int rs = orderDAO.insertOrderHeader(order); // Đổi tên hàm DAO cho rõ
        return rs > 0;
    }

    // --- PHƯƠNG THỨC MỚI QUAN TRỌNG ---
    /**
     * Lưu đơn hàng mới bao gồm thông tin header và danh sách chi tiết.
     * Đồng thời cập nhật số lượng tồn kho sách.
     * Nên được thực hiện trong một transaction ở tầng DAO.
     *
     * @param order          Thông tin header của đơn hàng.
     * @param orderDetailsList Danh sách các chi tiết sản phẩm trong đơn hàng.
     * @return true nếu lưu thành công, false nếu có lỗi.
     */
    public boolean saveOrderWithDetails(Orders order, List<OrderDetails> orderDetailsList) {
        if (order == null || order.getOrderId() == null || orderDetailsList == null || orderDetailsList.isEmpty()) {
            System.err.println("Lỗi service: Dữ liệu đơn hàng hoặc chi tiết đơn hàng không hợp lệ.");
            return false;
        }

        // *** PHẦN NÀY CẦN TRIỂN KHAI TRONG DAO VỚI TRANSACTION ***
        boolean success = orderDAO.saveOrderTransaction(order, orderDetailsList);

        return success;
    }

    // Hàm kiểm tra sự tồn tại của mã hóa đơn (nếu cần)
    // public boolean checkInvoiceIdExists(String invoiceId) {
    //    return orderDAO.checkInvoiceIdExists(invoiceId);
    // }
}