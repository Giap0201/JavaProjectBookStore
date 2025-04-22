package dao;

import database.JDBCUtil;
import model.BookSearch;
import model.Books;
import model.Category;
import service.CategoryService;
import utils.ResultMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class BookDAO  {

    // Thêm sách vào CSDL
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
            ps.setString(8, books.getUrlImage());

            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Lấy toàn bộ sách từ CSDL
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
                String urlImage = rs.getString("urlImage");


                // Tìm Category theo ID
                Category category = new Category();
                for (Category c : categories) {
                    if (c.getCategoryID().equals(categoryID)) {
                        category = c;
                        break;
                    }
                }
                Books book = new Books(bookID, bookName, author, yearPublished, price, quantity, category,urlImage);
                listBooks.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listBooks;
    }

    public int update(Books books) {
        int result = 0;
        String query = "UPDATE books SET bookName = ?,author = ?,yearPublished = ?,price = ?,quantity = ?,categoryID = ?,urlImage=? WHERE bookID = ?";
        try (Connection conn = JDBCUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, books.getBookName());
            ps.setString(2, books.getAuthor());
            ps.setInt(3, books.getYearPublished());
            ps.setDouble(4, books.getPrice());
            ps.setInt(5, books.getQuantity());
            ps.setString(6, books.getCategory().getCategoryID());
            ps.setString(7,books.getUrlImage());
            ps.setString(8, books.getBookID());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int delete(Books books) {
        int result = 0;
        String query = "DELETE FROM books WHERE bookID = ?";
        String sqlSelectUrl = "SELECT urlImage FROM books WHERE bookID = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement p = conn.prepareStatement(sqlSelectUrl)) {

            // Set parameter cho p
            p.setString(1, books.getBookID());

            // Thực hiện truy vấn
            try (ResultSet rs = p.executeQuery()) {
                while (rs.next()) {
                    String imageUrl = rs.getString("urlImage");
                    deleteSinglePhysicalImageFile(imageUrl);
                }
            }

            // Sau khi lấy xong ảnh, thực hiện xóa sách
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, books.getBookID());
                result = ps.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void deleteSinglePhysicalImageFile(String relativeUrl) {
        if (relativeUrl == null || relativeUrl.trim().isEmpty()) return;

        try {
            Path imagePath = Paths.get(System.getProperty("user.dir"), relativeUrl.replace("/", File.separator));
            Path parentDir = imagePath.getParent();

            if (Files.deleteIfExists(imagePath)) {
                System.out.println("Đã xóa ảnh: " + imagePath);

                if (parentDir != null && Files.exists(parentDir) && isDirEmpty(parentDir)) {
                    Files.delete(parentDir);
                    System.out.println("Đã xóa thư mục rỗng: " + parentDir);
                }
            }
        } catch (IOException | SecurityException | InvalidPathException e) {
            System.err.println("Lỗi khi xóa ảnh/thư mục: " + e.getMessage());
        }
    }

    private boolean isDirEmpty(Path directory) throws IOException {
        try (Stream<Path> dirStream = Files.list(directory)) {
            return !dirStream.findAny().isPresent();
        }
    }

    public boolean updateImageUrl(String bookId, String newUrlImage) {
        if (bookId == null || bookId.trim().isEmpty()) return false;

        String sql = "UPDATE Books SET urlImage = ? WHERE bookID = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (newUrlImage == null || newUrlImage.trim().isEmpty()) {
                ps.setNull(1, java.sql.Types.VARCHAR);
            } else {
                ps.setString(1, newUrlImage.trim());
            }

            ps.setString(2, bookId);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected == 0) {
                System.out.println("Không tìm thấy sách để cập nhật urlImage cho bookID: " + bookId);
            }

            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi cập nhật urlImage: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


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
                String urlImage = rs.getString("urlImage");
                Books bookResult = new Books(bookID, bookName, author, yearPublished, price, quantity, category,urlImage);
                listBooks.add(bookResult);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listBooks;
    }
    //phuong thuc tim kiem theo ten
    public ArrayList<Books> SearchBookByName(String bookName) {
        ArrayList<Books> listBooks = new ArrayList<>();
        String query = "select * from books join category on category.categoryID = books.categoryID where bookName like ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            String condition = "%" + bookName + "%";
            ps.setString(1, condition);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Books books = ResultMapper.mapResultSetToBooks(rs);
                listBooks.add(books);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e + e.getMessage());
        }
        return listBooks;
    }

    public Books mapResultSetToBook(ResultSet rs) throws SQLException {
        Books book = new Books();

        book.setBookID(rs.getString("bookID")); // Lấy bookID từ cột 'bookID'
        book.setBookName(rs.getString("bookName")); // Lấy bookName
        book.setPrice(rs.getDouble("price"));       // Lấy price
        book.setQuantity(rs.getInt("quantity"));     // Lấy quantity
        book.setYearPublished(Integer.parseInt(rs.getString("yearPublished")));
        book.setAuthor(rs.getString("author"));     // Lấy author (nếu có)
        book.setUrlImage(rs.getString("urlImage"));
        String categoryID = rs.getString("categoryID");
        Category category = new CategoryDAO().getCategory(categoryID);
        book.setCategory(category);


        return book;
    }
}