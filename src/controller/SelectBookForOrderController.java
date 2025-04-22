package controller;

import model.Books;
import model.Category;
import service.BookService;
import service.CategoryService;
import utils.CommonView;
import view.SelectBookForOrderView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SelectBookForOrderController implements ActionListener {
    private final SelectBookForOrderView view;
    private final BookService bookService;
    private final CategoryService categoryService;
    private Books selectedBook;

    public SelectBookForOrderController(SelectBookForOrderView view) {
        this.view = view;
        this.bookService = new BookService();
        this.categoryService = new CategoryService();
        initializeView();
    }

    private void initializeView() {
        addDataToTable(bookService.getAllBooks());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.getSelectButton()) {
            try {
                selectBook();
                view.setBook(selectedBook);
            } catch (IllegalArgumentException ex) {
                CommonView.showErrorMessage(null, ex.getMessage());
            } catch (Exception ex) {
                CommonView.showErrorMessage(null, "Lỗi hệ thống: " + ex.getMessage());
            }
        }
    }

    private void selectBook() {
        int[] rowSelected = view.getTable().getSelectedRows();
        if (rowSelected.length == 0) {
            throw new IllegalArgumentException("Vui lòng chọn một cuốn sách!");
        }
        if (rowSelected.length > 1) {
            throw new IllegalArgumentException("Chỉ được chọn một cuốn sách cho đơn hàng!");
        }
        boolean confirm = CommonView.confirmAction(null, "Bạn có chắc chắn muốn chon sách này kh");
        if (confirm) {
            int row = rowSelected[0]; // Chỉ lấy dòng đầu tiên (vì chỉ cho phép chọn một dòng)
            String bookID = (String) view.getTable().getValueAt(row, 0);
            String category = (String) view.getTable().getValueAt(row, 1);
            ArrayList<Category> listCategory = categoryService.getCategory();
            Category categoryResult = new Category();
            for (Category c : listCategory) {
                if (c.getCategoryName().equals(category)) {
                    categoryResult = c;
                    break;
                }
            }
            String bookName = (String) view.getTable().getValueAt(row, 2);
            String author = (String) view.getTable().getValueAt(row, 3);
            int year = (Integer) view.getTable().getValueAt(row, 4);
            double priceDouble = (Double) view.getTable().getValueAt(row, 6);
            int quantity = (Integer) view.getTable().getValueAt(row, 5);
            String url = (String) view.getTable().getValueAt(row, 7);
            selectedBook = new Books(bookID, bookName, author, year, priceDouble, quantity, categoryResult,url);
//            CommonView.showInfoMessage(null, "Đã thêm sách vào đơn hàng thành công!");
            view.dispose();
        }
    }

    private void addDataToTable(ArrayList<Books> books) {
        view.getTableModel().setRowCount(0);
        for (Books book : books) {
            Object[] row = {
                    book.getBookID(),
                    book.getCategory().getCategoryName(),
                    book.getBookName(),
                    book.getAuthor(),
                    book.getYearPublished(),
                    book.getQuantity(),
                    book.getPrice(),
                    book.getUrlImage()
            };
            view.getTableModel().addRow(row);
        }
    }
}