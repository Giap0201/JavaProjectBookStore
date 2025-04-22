package controller;

import model.Books;
import model.Category;
import service.BookService;
import service.CategoryService;
import utils.CommonView;
import view.SelectBookView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SelectBookController implements ActionListener {
    private final SelectBookView view;
    private final BookService bookService;
    private final CategoryService categoryService;
    private final ArrayList<Books> listBook;

    public SelectBookController(SelectBookView view) {
        this.view = view;
        this.bookService = new BookService();
        this.categoryService = new CategoryService();
        this.listBook = new ArrayList<>();
        initializeView();
    }

    private void initializeView() {
        loadTable(bookService.getAllBooks());
        registerButtonListeners();
    }

    private void registerButtonListeners() {
        view.getSearchButton().addActionListener(this);
        view.getAllButton().addActionListener(this);
        view.getSelectButton().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == view.getSearchButton()) {
                searchBook();
            } else if (e.getSource() == view.getAllButton()) {
                loadTable(bookService.getAllBooks());
            } else if (e.getSource() == view.getSelectButton()) {
                selectBook();
            }
        } catch (IllegalArgumentException ex) {
            CommonView.showErrorMessage(null, ex.getMessage());
        } catch (Exception ex) {
            CommonView.showErrorMessage(null, "Lỗi hệ thống: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void searchBook() {
        String bookName = view.getSearchField().getText().trim();
        if (bookName.isEmpty()) {
            CommonView.showInfoMessage(null, "Vui lòng nhập tên sách cần tìm!");
            return;
        }
        ArrayList<Books> listBooks = bookService.bookSearchByName(bookName);
        if (listBooks.isEmpty()) {
            CommonView.showInfoMessage(null, "Không tìm thấy sách!");
        } else {
            loadTable(listBooks);
        }
    }

    private void selectBook() {
        int[] rowSelected = view.getTable().getSelectedRows();
        if (rowSelected.length == 0) {
            throw new IllegalArgumentException("Vui lòng chọn ít nhất 1 cuốn sách!");
        }
        boolean confirm = CommonView.confirmAction(null, "Bạn có chắc chắn muốn thêm?");
        if (confirm) {
            for (int row : rowSelected) {
                String bookID = (String) view.getTable().getValueAt(row, 0);
                String category = (String) view.getTable().getValueAt(row, 1);
//                Category categoryResult = new Category();
//                for (Category c : listCategory) {
//                    if (c.getCategoryName().equals(category)) {
//                        categoryResult = c;
//                        break;
//                    }
//                }
                Category categoryResult = categoryService.getCategoryByName(category);
//                System.out.println(categoryResult.getCategoryID());
//                System.out.println(categoryResult.getCategoryName());
                String bookName = (String) view.getTable().getValueAt(row, 2);
                String author = (String) view.getTable().getValueAt(row, 3);
                int year = (Integer) view.getTable().getValueAt(row, 4);
                int quantity = (Integer) view.getTable().getValueAt(row, 5);
                double priceDouble = (Double) view.getTable().getValueAt(row, 6);
                Books book = new Books(bookID, bookName, author, year, priceDouble, quantity, categoryResult);
                listBook.add(book);
            }
            view.setListBook(listBook);
            view.dispose();
            CommonView.showInfoMessage(null, "Đã thêm sách thành công!");
        }
    }

    private void loadTable(ArrayList<Books> books) {
        view.getTableModel().setRowCount(0);
        for (Books book : books) {
            Object[] row = {
                    book.getBookID(),
                    book.getCategory().getCategoryName(),
                    book.getBookName(),
                    book.getAuthor(),
                    book.getYearPublished(),
                    book.getQuantity(),
                    book.getPrice()
            };
            view.getTableModel().addRow(row);
        }
    }

    public ArrayList<Books> getListBook() {
        return listBook;
    }
}