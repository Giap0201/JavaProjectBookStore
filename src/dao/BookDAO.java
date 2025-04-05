package dao;

import database.JDBCUtil;
import model.Books;
import model.Category;
import service.CategoryService;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookDAO implements DAOInterface<Books> {

    // Thêm sách vào CSDL
    @Override
    public int insert(Books books) {
        int result = 0;
        String sql = "INSERT INTO books(bookID, bookName, author, yearPublished, price, quantity, categoryID) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, books.getBookID());
            ps.setString(2, books.getBookName());
            ps.setString(3, books.getAuthor());
            ps.setInt(4, books.getYearPublished());
            ps.setDouble(5, books.getPrice());
            ps.setInt(6, books.getQuantity());
            ps.setString(7, books.getCategory().getCategoryID());

            result = ps.executeUpdate();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Lỗi khi thêm sách: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Lỗi không xác định khi thêm sách: " + e.getMessage(), "Unknown Error", JOptionPane.ERROR_MESSAGE);
        }

        return result;
    }

    // Lấy toàn bộ sách từ CSDL
    @Override
    public ArrayList<Books> getAll() {
        ArrayList<Books> listBooks = new ArrayList<>();
        String sql = "SELECT * FROM books";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            ArrayList<Category> categories = new CategoryService().getCategory();

            while (rs.next()) {
                String bookID = rs.getString("bookID");
                String bookName = rs.getString("bookName");
                String author = rs.getString("author");
                int yearPublished = rs.getInt("yearPublished");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");
                String categoryID = rs.getString("categoryID");

                // Tìm Category theo ID
                Category category = categories.stream()
                        .filter(c -> c.getCategoryID().equals(categoryID))
                        .findFirst()
                        .orElse(null);

                // Nếu không tìm thấy category phù hợp => cảnh báo và bỏ qua sách này
                if (category == null) {
                    System.err.println("Không tìm thấy thể loại cho sách có ID: " + bookID);
                    continue;
                }

                Books book = new Books(bookID, bookName, author, yearPublished, price, quantity, category);
                listBooks.add(book);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Lỗi khi truy vấn danh sách sách: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Lỗi không xác định khi truy vấn sách: " + e.getMessage(), "Unknown Error", JOptionPane.ERROR_MESSAGE);
        }

        return listBooks;
    }

    @Override
    public Books selectbyId(Books books) {
        // Có thể viết sau nếu cần
        return null;
    }

    @Override
    public ArrayList<Books> selectbyCondition(String condition) {
        // Có thể viết sau nếu cần
        return null;
    }

    @Override
    public int update(Books books) {
        // Có thể viết sau nếu cần
        return 0;
    }

    @Override
    public int delete(Books books) {
        // Có thể viết sau nếu cần
        return 0;
    }
}
