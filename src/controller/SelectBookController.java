package controller;

import model.Books;
import model.Category;
import service.BookService;
import view.SelectBookView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SelectBookController implements ActionListener {
    private SelectBookView view;
    private BookService bookService;
    private CategoryController categoryController;
    public SelectBookController(SelectBookView view) {
        this.view = view;
        bookService = new BookService();
        categoryController = new CategoryController();
        addDataToTable(bookService.getAllBooks());
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == view.getSelectButton()){
            try {
                selectBook();
                view.dispose();
            }catch (Exception e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(null,e1.getMessage());
            }
        }
    }

    public void selectBook() throws Exception {
        ArrayList<Books> listBook= new ArrayList<>();
        int[] rowSelected = view.getTable().getSelectedRows();
        if(rowSelected.length == 0){
            throw new Exception("Vui lòng chọn sách !!");
        }
        int confirm = JOptionPane.showConfirmDialog(null,"Bạn có chắn chắn muốn thêm?","Xác nhận thêm",JOptionPane.YES_NO_OPTION);
        if(confirm == JOptionPane.YES_OPTION){
            for (int row : rowSelected) {
                String bookID = (String) view.getTable().getValueAt(row, 0);
                String category = (String) view.getTable().getValueAt(row, 1);
                ArrayList<Category> listCategory = categoryController.getCategories();
                Category categoryResult = new Category();
                for(Category c : listCategory){
                    if(c.getCategoryName().equals(category)){
                        categoryResult = c;
                        break;
                    }
                }
                String bookName = (String) view.getTable().getValueAt(row, 2);
                String author = (String) view.getTable().getValueAt(row, 3);
                int year = (Integer) view.getTable().getValueAt(row, 4);
                double priceDouble = (Double) view.getTable().getValueAt(row, 6);
                int quantity = (Integer) view.getTable().getValueAt(row, 5);
                Books book = new Books(bookID,bookName,author,year,priceDouble,quantity,categoryResult);
                listBook.add(book);
            }
        }
        JOptionPane.showMessageDialog(null,"Đã thêm thành công!!");
        view.setListBook(listBook);
    }
    public void addDataToTable(ArrayList<Books> books) {
        view.getTableModel().setRowCount(0);
        for(Books book : books){
            Object[] row = {book.getBookID(),book.getCategory().getCategoryName(),book.getBookName(),book.getAuthor(),book.getYearPublished(),book.getQuantity(),book.getPrice()};
            view.getTableModel().addRow(row);
        }
    }
}
//package controller;
//
//import model.Books;
//import model.Category;
//import service.BookService;
//import view.SelectBookView;
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//public class SelectBookController implements ActionListener {
//    private SelectBookView view;
//    private BookService bookService;
//    private ArrayList<Books> listBookSelected;
//    private CategoryController categoryController;
//
//    public SelectBookController(SelectBookView view) {
//        this.view = view;
//        this.bookService = new BookService();
//        this.categoryController = new CategoryController();
//        this.listBookSelected = new ArrayList<>();
//        view.getTable().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
//        addDataToTable(bookService.getAllBooks());
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        if (e.getSource() == view.getSelectButton()) {
//            try {
//                selectBooks();
//                view.dispose();
//            } catch (Exception ex) {
//                JOptionPane.showMessageDialog(view, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
//            }
//        }
//    }
//
//    public ArrayList<Books> getListBookSelected() {
//        return new ArrayList<>(listBookSelected);
//    }
//
//    public void clearSelectedBooks() {
//        listBookSelected.clear();
//    }
//
//    private void selectBooks() throws Exception {
//        int[] selectedRows = view.getTable().getSelectedRows();
//        if (selectedRows.length == 0) {
//            throw new Exception("Vui lòng chọn ít nhất một cuốn sách!");
//        }
//
//        Map<String, Category> categoryMap = new HashMap<>();
//        for (Category c : categoryController.getCategories()) {
//            categoryMap.put(c.getCategoryName(), c);
//        }
//
//        ArrayList<Books> newSelection = new ArrayList<>();
//        for (int row : selectedRows) {
//            String bookID = (String) view.getTable().getValueAt(row, 0);
//            String categoryName = (String) view.getTable().getValueAt(row, 1);
//            String bookName = (String) view.getTable().getValueAt(row, 2);
//            String author = (String) view.getTable().getValueAt(row, 3);
//            int year = (Integer) view.getTable().getValueAt(row, 4);
//            int quantity = (Integer) view.getTable().getValueAt(row, 5);
//            double price = (Double) view.getTable().getValueAt(row, 6);
//
//            validateBookData(bookID, bookName, year, quantity, price);
//
//            Category category = categoryMap.getOrDefault(categoryName, new Category());
//            Books book = new Books(bookID, bookName, author, year, price, quantity, category);
//            newSelection.add(book);
//        }
//
//        int confirm = JOptionPane.showConfirmDialog(
//                view,
//                "Bạn có chắc chắn muốn chọn " + newSelection.size() + " cuốn sách?",
//                "Xác nhận chọn sách",
//                JOptionPane.YES_NO_OPTION
//        );
//        if (confirm == JOptionPane.YES_OPTION) {
//            listBookSelected = newSelection;
//            System.out.println("Số sách được chọn trong SelectBookController: " + listBookSelected.size());
//            for (Books book : listBookSelected) {
//                System.out.println(" - " + book.getBookID());
//            }
//            JOptionPane.showMessageDialog(view, "Đã chọn sách thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
//        } else {
//            throw new Exception("Hủy chọn sách!");
//        }
//    }
//
//    private void validateBookData(String bookID, String bookName, int year, int quantity, double price) throws Exception {
//        if (bookID.isEmpty() || bookName.isEmpty()) {
//            throw new Exception("Thông tin sách không được để trống!");
//        }
//        if (year < 0 || quantity < 0 || price < 0) {
//            throw new Exception("Năm, số lượng và giá phải lớn hơn hoặc bằng 0!");
//        }
//    }
//
//    public void addDataToTable(ArrayList<Books> books) {
//        DefaultTableModel model = view.getTableModel();
//        model.setRowCount(0);
//        for (Books book : books) {
//            Object[] row = {
//                    book.getBookID(),
//                    book.getCategory().getCategoryName(),
//                    book.getBookName(),
//                    book.getAuthor(),
//                    book.getYearPublished(),
//                    book.getQuantity(),
//                    book.getPrice()
//            };
//            model.addRow(row);
//        }
//    }
//}