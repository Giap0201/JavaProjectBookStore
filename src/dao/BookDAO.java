package dao;

import database.JDBCUtil;
import model.BookSearch;
import model.Books;
import model.Category;
import service.CategoryService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookDAO implements IBookDAO {

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
            e.printStackTrace();
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
                Category category = new Category();
                for (Category c : categories) {
                    if (c.getCategoryID().equals(categoryID)) {
                        category = c;
                    }
                }
                Books book = new Books(bookID, bookName, author, yearPublished, price, quantity, category);
                listBooks.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listBooks;
    }

    @Override
    public int update(Books books) {
        int result = 0;
        String query = "UPDATE books SET bookName = ?,author = ?,yearPublished = ?,price = ?,quantity = ?,categoryID = ? WHERE bookID = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, books.getBookName());
            ps.setString(2, books.getAuthor());
            ps.setInt(3, books.getYearPublished());
            ps.setDouble(4, books.getPrice());
            ps.setInt(5, books.getQuantity());
            ps.setString(6, books.getCategory().getCategoryID());
            ps.setString(7, books.getBookID());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int delete(Books books) {
        int result = 0;
        String query = "DELETE FROM books WHERE bookID = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, books.getBookID());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public ArrayList<Books> listSearchBooks(BookSearch condition) {
        ArrayList<Books> listBooks = new ArrayList<>();
        StringBuilder query = new StringBuilder(
                "select * from books join category on category.categoryID = books.categoryID where 1 = 1");
        ArrayList<Object> params = new ArrayList<>();

        // xay dung cac dieu kien tim kiem
        if (condition.getBookID() != null && !condition.getBookID().isEmpty()) {
            query.append(" and bookID like ?");
            params.add("%" + condition.getBookID() + "%");
        }
        if (condition.getBookName() != null && !condition.getBookName().isEmpty()) {
            query.append(" and bookName like ?");
            params.add("%" + condition.getBookName() + "%");
        }
        if (condition.getAuthor() != null && !condition.getAuthor().isEmpty()) {
            query.append(" and author like ?");
            params.add("%" + condition.getAuthor() + "%");
        }
        if (condition.getYearStart() != null) {
            query.append(" and yearPublished >= ?");
            params.add(condition.getYearStart());
        }
        if (condition.getYearEnd() != null) {
            query.append(" and yearPublished <= ?");
            params.add(condition.getYearEnd());
        }
        if (condition.getPriceMin() != null) {
            query.append(" and price >= ?");
            params.add(condition.getPriceMin());
        }
        if (condition.getPriceMax() != null) {
            query.append(" and price <= ?");
            params.add(condition.getPriceMax());
        }
        if (condition.getCategoryName() != null && !condition.getCategoryName().isEmpty()) {
            query.append(" and categoryName = ?");
            params.add(condition.getCategoryName());
        }
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query.toString())) {

            // gan gia tri cho ?
            for (int i = 0; i < params.size(); i++) {
                Object cObject = params.get(i);
                if (cObject instanceof Integer) {
                    ps.setInt(i + 1, (Integer) cObject);
                } else if (cObject instanceof Double) {
                    ps.setDouble(i + 1, (Double) cObject);
                } else {
                    ps.setString(i + 1, (String) cObject);
                }
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Category category = new Category(rs.getString("categoryID"), rs.getString("categoryName"));
                String bookID = rs.getString("bookID");
                String bookName = rs.getString("bookName");
                String author = rs.getString("author");
                double price = rs.getDouble("price");
                int yearPublished = rs.getInt("yearPublished");
                int quantity = rs.getInt("quantity");
                Books bookResult = new Books(bookID, bookName, author, yearPublished, price, quantity, category);
                listBooks.add(bookResult);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listBooks;
    }
}