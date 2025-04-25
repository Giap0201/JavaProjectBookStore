package dao;

import database.JDBCUtil;
import model.*;
import service.BookService;
import service.CategoryService;

import java.sql.*;
import java.util.ArrayList;

import java.util.List; // Import List


public class OrderDAO {
    public boolean saveOrderTransaction(Orders order, List<OrderDetails> orderDetailsList) {
        Connection connection = null;
        boolean success = false;

        //Câu lenh sql
        String insertOrderSQL = "INSERT INTO orders(orderID, customerID, dayOfEstablishment, status,employeeID) VALUES(?,?,?,?,?)";
        String insertDetailSQL = "INSERT INTO orderdetails(orderID, bookID, quantity, price) VALUES(?,?,?,?)";
        String updateStockSQL = "UPDATE books SET quantity = quantity - ? WHERE bookID = ?";

        try {
            connection = JDBCUtil.getConnection();
            connection.setAutoCommit(false); // Bắt đầu transaction

            //Insert Order vao bảng orders
            try (PreparedStatement psOrder = connection.prepareStatement(insertOrderSQL)) {
                psOrder.setString(1, order.getOrderId());
                psOrder.setString(2, order.getCustomer().getCustomerID());
                if (order.getOrderDate() != null) {
                    psOrder.setDate(3, new java.sql.Date(order.getOrderDate().getTime()));
                } else {
                    psOrder.setNull(3, Types.DATE);
                }
                psOrder.setString(4, order.getStatus());
                psOrder.setString(5, order.getEmployeeID());
                int orderResult = psOrder.executeUpdate();
                if (orderResult == 0) {
                    throw new SQLException("Tạo header hóa đơn thất bại, không có dòng nào được thêm.");
                }
            }

            //Them tung san pham vao don hang
            try (PreparedStatement psDetail = connection.prepareStatement(insertDetailSQL)) {
                for (OrderDetails detail : orderDetailsList) {
                    psDetail.setString(1, order.getOrderId());
                    psDetail.setString(2, detail.getBook().getBookID());
                    psDetail.setInt(3, detail.getQuantity());
                    psDetail.setDouble(4, detail.getBook().getPrice());
                    psDetail.addBatch();
                }
                int[] detailResults = psDetail.executeBatch();
                for (int result : detailResults) {
                    if (result == Statement.EXECUTE_FAILED) {
                        throw new SQLException("Tạo chi tiết hóa đơn thất bại trong batch.");
                    }
                }
            }

            //Update so luong sach
            try (PreparedStatement psStock = connection.prepareStatement(updateStockSQL)) {
                for (OrderDetails detail : orderDetailsList) {
                    psStock.setInt(1, detail.getQuantity());
                    psStock.setString(2, detail.getBook().getBookID());
                    psStock.addBatch();
                }
                int[] stockResults = psStock.executeBatch();
                for (int result : stockResults) {
                    if (result == Statement.EXECUTE_FAILED) {
                        throw new SQLException("Cập nhật tồn kho sách thất bại trong batch.");
                    }
                }
            }

            //  Commit Transaction
            connection.commit();
            success = true;
            System.out.println("Transaction lưu hóa đơn thành công!");

        } catch (SQLException e) {
            System.err.println("SQL Error trong transaction lưu hóa đơn: " + e.getMessage());
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                    System.err.println("Transaction đã rollback.");
                } catch (SQLException ex) {
                    System.err.println("Lỗi khi rollback transaction: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
            success = false;
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Lỗi khi đóng connection hoặc reset autoCommit: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        return success;
    }
}
