package dao;

import database.JDBCUtil;
import model.Books;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookDAO implements DAOInterface<Books> {

    @Override
    public int insert(Books books) {
        int ketQua = 0;
        String sql = "insert into books values(?,?,?,?,?,?,?)";
        try(Connection conn = JDBCUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1,books.getBookID());
            ps.setString(2,books.getBookName());
            ps.setString(3,books.getAuthor());
            ps.setInt(4,books.getYearPublished());
            ps.setDouble(5,books.getPrice());
            ps.setInt(6,books.getQuantity());
            ps.setString(7,books.getCategory());
            ketQua = ps.executeUpdate();
            System.out.println(ketQua);
            System.out.println("Insert Books Successfully");
        }catch (SQLException e){
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int update(Books books) {
        return 0;
    }

    @Override
    public int delete(Books books) {
        return 0;
    }

    @Override
    public ArrayList<Books> getAll() {
        ArrayList<Books> listBooks = new ArrayList<Books>();
        String sql = "select * from books";
        //viet theo cach nay tu dong ket noi va tu dong dong ket noi
        try (Connection conn = JDBCUtil.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()){
            while (rs.next()){
                String bookID = rs.getString("bookID");
                String bookName = rs.getString("bookName");
                String author = rs.getString("author");
                int yearPublished = rs.getInt("yearPublished");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");
                String category = rs.getString("category");
                Books book = new Books(bookID,bookName,author,yearPublished,price,quantity,category);
                listBooks.add(book);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return listBooks;
    }

    @Override
    public Books selectbyId(Books books) {
        return null;
    }

    @Override
    public ArrayList<Books> selectbyCondition(String condition) {
        return null;
    }
}
