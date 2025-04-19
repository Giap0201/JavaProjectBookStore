package dao;


import database.JDBCUtil;
import model.Books;

import model.OrderDetails;
import model.Orders;
import service.BookService;

import java.sql.*;
import java.util.ArrayList;

public class OrderDAO {
    private BookService bookService = new BookService();
    private GetDiscountByBookId getDiscountByBookId = new GetDiscountByBookId();

    public boolean insertBook(OrderDetails orderDetails) {
        int result = 0 ;
        String sql = "INSERT INTO orderdetails(bookID,orderID,quantity,price) VALUES(?,?,?,?)";
        // String sql2 = "INSERT INTO orders(orderID,dayOfEstablishment,status,customerID) VALUES(?,?,?,?)";
        try(Connection connection= JDBCUtil.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1,orderDetails.getBook().getBookID());
            preparedStatement.setString(2,orderDetails.getOrderId());
            preparedStatement.setInt(3,orderDetails.getBook().getQuantity());
            preparedStatement.setDouble(4,orderDetails.getBook().getPrice());

            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result>0;
    }

    public int insertOrder(Orders order) {
        int result = 0 ;
        String sql = "INSERT INTO orders(orderID,dayOfEstablishment,customerID) VALUES(?,?,?)";
        try(Connection connection=JDBCUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,order.getOrderId());
            preparedStatement.setDate(2, new java.sql.Date(order.getOrderDate().getTime()) );
            preparedStatement.setString(3,order.getCustomer().getCustomerID());

            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<OrderDetails> getAllOrderDetails() {
        ArrayList<OrderDetails> orderDetails=new ArrayList<>();
        String sql = "SELECT * FROM orderdetails";
        try(Connection connection=JDBCUtil.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                OrderDetails orderDetails1= mapResultSetToOrderDetails(resultSet);
                orderDetails.add(orderDetails1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderDetails;
    }


    private OrderDetails mapResultSetToOrderDetails(ResultSet rs) throws SQLException {
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setOrderId(rs.getString("orderID"));
        String bookID = rs.getString("bookID");
        orderDetails.setBook(bookService.getBookByID(bookID));
        orderDetails.setDiscount(getDiscountByBookId.getDiscountByBookId(bookID));
        return orderDetails;
    }



}
