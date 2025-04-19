package service;

import dao.OrderDAO;
import model.OrderDetails;
import model.Orders;

import java.util.ArrayList;

public class OrderService {
    private OrderDAO orderDAO;
    public OrderService() {
        this.orderDAO = new OrderDAO();
    }

    public boolean addOrder(OrderDetails orderDetails){
        if(orderDetails == null || orderDetails.getOrderId() == null){
            System.err.println("Lỗi service");
            return false;
        }
        return orderDAO.insertBook(orderDetails);
    }
    public ArrayList<OrderDetails> getAllOrderDetails(){
        return orderDAO.getAllOrderDetails();
    }

    public boolean insertOrder(Orders order){
        if(order == null || order.getOrderId() == null){
            return false;
        }
        int rs = orderDAO.insertOrder(order);
        return rs>0;
    }
}
