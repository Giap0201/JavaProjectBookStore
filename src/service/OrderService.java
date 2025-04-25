package service;

import dao.OrderDAO;
import model.Books; // Cần import Books
import model.OrderDetails;
import model.Orders;

import java.util.ArrayList;
import java.util.List; // Cần import List

public class OrderService {
    private OrderDAO orderDAO;

    public OrderService() {
        this.orderDAO = new OrderDAO();
    }

    /**
     * Lưu đơn hàng mới bao gồm thông tin header và danh sách chi tiết.
     * Đồng thời cập nhật số lượng tồn kho sách.
     */

    public boolean saveOrderWithDetails(Orders order, List<OrderDetails> orderDetailsList) {
        if (order == null || order.getOrderId() == null || orderDetailsList == null || orderDetailsList.isEmpty()) {
            System.err.println("Lỗi service: Dữ liệu đơn hàng hoặc chi tiết đơn hàng không hợp lệ.");
            return false;
        }
        boolean success = orderDAO.saveOrderTransaction(order, orderDetailsList);

        return success;
    }

    // Hàm kiểm tra sự tồn tại của mã hóa đơn
    // public boolean checkInvoiceIdExists(String invoiceId) {
    //    return orderDAO.checkInvoiceIdExists(invoiceId);
    // }
}