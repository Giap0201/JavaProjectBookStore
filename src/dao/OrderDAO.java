package dao;

import database.JDBCUtil;
import model.*;
import service.BookService;
import service.CategoryService;

import java.sql.*;
import java.util.ArrayList;

import java.util.List; // Import List


public class OrderDAO {
    // BookService có thể không cần trực tiếp trong DAO nếu chỉ lấy ID
    // private BookService bookService = new BookService();
    private GetDiscountByBookId getDiscountByBookId = new GetDiscountByBookId(); // Vẫn cần để lấy discount khi get

    // Đổi tên hàm cho rõ ràng
    public boolean insertBookDetail(OrderDetails orderDetails) {
        int result = 0;
        // Sửa lại tên bảng/cột nếu cần, thêm cột discount nếu lưu cả % giảm giá item
        String sql = "INSERT INTO orderdetails(orderID, bookID, quantity, price) VALUES(?,?,?,?)";
        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, orderDetails.getOrderId());
            preparedStatement.setString(2, orderDetails.getBook().getBookID());
            preparedStatement.setInt(3, orderDetails.getQuantity());
            preparedStatement.setDouble(4, orderDetails.getBook().getPrice());

            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQL Error khi insert OrderDetail: " + e.getMessage());
            e.printStackTrace();
        }
        return result > 0;
    }

    // Đổi tên hàm cho rõ ràng
    public int insertOrderHeader(Orders order) {
        int result = 0;
        // Sửa lại tên bảng/cột nếu cần
        String sql = "INSERT INTO orders(orderID, customerID, dayOfEstablishment, status) VALUES(?,?,?,?)";
        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, order.getOrderId());
            preparedStatement.setString(2, order.getCustomer().getCustomerID());
            // Chuyển java.util.Date sang java.sql.Date
            if (order.getOrderDate() != null) {
                preparedStatement.setDate(3, new java.sql.Date(order.getOrderDate().getTime()));
            } else {
                preparedStatement.setNull(3, Types.DATE);
            }
            preparedStatement.setString(4, order.getStatus());

            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQL Error khi insert OrderHeader: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    // Lấy tất cả chi tiết của một đơn hàng (có thể cần lấy thêm discount_percent)
    public ArrayList<OrderDetails> getAllOrderDetails(String orderId) {
        ArrayList<OrderDetails> orderDetailsList = new ArrayList<>();
        // Thêm cột discount_percent vào câu SELECT
        String sql = "SELECT od.orderID,  od.quantity, od.price, b.* " + // Lấy cả thông tin sách
                "FROM orderdetails od " +
                "JOIN books b ON od.bookID = b.bookID " + // Join với bảng books
                "WHERE od.orderID = ?";
        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, orderId);
            ResultSet resultSet = preparedStatement.executeQuery();

            BookDAO bookDAO = new BookDAO(); // Dùng BookDAO để map sách


            while (resultSet.next()) {
                OrderDetails detail = new OrderDetails();
                detail.setOrderId(resultSet.getString("orderID"));
                detail.setQuantity(resultSet.getInt("quantity"));

                // Map thông tin sách từ ResultSet
                Books book = bookDAO.mapResultSetToBook(resultSet); // Giả sử BookDAO có hàm này

                detail.setBook(book);

                orderDetailsList.add(detail);
            }
        } catch (SQLException e) {
            System.err.println("SQL Error khi getAllOrderDetails: " + e.getMessage());
            e.printStackTrace();
        }
        return orderDetailsList;
    }

    // --- PHƯƠNG THỨC MỚI QUAN TRỌNG VỚI TRANSACTION ---
    public boolean saveOrderTransaction(Orders order, List<OrderDetails> orderDetailsList) {
        Connection connection = null;
        boolean success = false;

        // SQL statements
        String insertOrderSQL = "INSERT INTO orders(orderID, customerID, dayOfEstablishment, status) VALUES(?,?,?,?)";
        String insertDetailSQL = "INSERT INTO orderdetails(orderID, bookID, quantity, price) VALUES(?,?,?,?)";
        String updateStockSQL = "UPDATE books SET quantity = quantity - ? WHERE bookID = ?";

        try {
            connection = JDBCUtil.getConnection();
            connection.setAutoCommit(false); // Bắt đầu transaction

            // 1. Insert Order Header (Giữ nguyên)
            try (PreparedStatement psOrder = connection.prepareStatement(insertOrderSQL)) {
                psOrder.setString(1, order.getOrderId());
                psOrder.setString(2, order.getCustomer().getCustomerID());
                if (order.getOrderDate() != null) {
                    psOrder.setDate(3, new java.sql.Date(order.getOrderDate().getTime()));
                } else {
                    psOrder.setNull(3, Types.DATE);
                }
                psOrder.setString(4, order.getStatus());
                int orderResult = psOrder.executeUpdate();
                if (orderResult == 0) {
                    throw new SQLException("Tạo header hóa đơn thất bại, không có dòng nào được thêm.");
                }
            }

            // 2. Insert Order Details (Chỉ insert các cột có trong DB)
            try (PreparedStatement psDetail = connection.prepareStatement(insertDetailSQL)) {
                for (OrderDetails detail : orderDetailsList) {
                    psDetail.setString(1, order.getOrderId());
                    psDetail.setString(2, detail.getBook().getBookID());
                    psDetail.setInt(3, detail.getQuantity());
                    // *** Luôn lưu giá gốc của sách ***
                    psDetail.setDouble(4, detail.getBook().getPrice());
                    // Không set discount_percent nữa
                    psDetail.addBatch();
                }
                int[] detailResults = psDetail.executeBatch();
                for (int result : detailResults) {
                    if (result == Statement.EXECUTE_FAILED) {
                        throw new SQLException("Tạo chi tiết hóa đơn thất bại trong batch.");
                    }
                }
            }

            // 3. Update Book Stock (Giữ nguyên)
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


            // 4. Commit Transaction
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
                    connection.setAutoCommit(true); // Trả về trạng thái auto commit
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
