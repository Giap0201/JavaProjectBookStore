package controller;

import model.BookSearch;
import model.Books;
import model.Category;
import service.BookService;
import service.CategoryService;
import utils.CommonView;
import utils.ImageUtils;
import utils.ValidateForm;
import view.BookView;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class BookController implements ActionListener {
    private final BookView bookView;
    private final BookService bookService;
    private final CategoryService categoryService;
    private final ArrayList<Category> categories;
    private File selectedImageFileForNewBook = null; // Temporary storage for selected image

    public BookController(BookView bookView) {
        this.bookView = bookView;
        this.bookService = new BookService();
        this.categoryService = new CategoryService();
        this.categories = categoryService.getCategory();
        initializeView();
    }

    // Initialize view: add table listener and update initial data
    private void initializeView() {
        addTableSelectionListener();
        try {
            ArrayList<Books> allBooks = bookService.getAllBooks();
            updateTable(allBooks);
            statistical(allBooks);
        } catch (Exception ex) {
            CommonView.showErrorMessage(bookView, "Lỗi khi khởi tạo danh sách sách: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // Handle button events
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == bookView.getBtnAdd()) {
                addBook();
            } else if (e.getSource() == bookView.getBtnChange()) {
                updateBook();
            } else if (e.getSource() == bookView.getBtnDelete()) {
                deleteBook();
            } else if (e.getSource() == bookView.getBtnSearch()) {
                searchBooks();
            } else if (e.getSource() == bookView.getBtnReset()) {
                refreshView();
            } else if (e.getSource() == bookView.getBtnSaveFile()) {
                exportToCSV2();
            } else if (e.getSource() == bookView.getBtnUploadImage()) {
                selectImageForNewBook();
            }
        } catch (IllegalArgumentException ex) {
            CommonView.showErrorMessage(bookView, ex.getMessage());
        } catch (Exception ex) {
            CommonView.showErrorMessage(bookView, "Lỗi hệ thống: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // Select image for book
    private void selectImageForNewBook() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Chọn ảnh cho sách");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Hình ảnh (jpg, png, gif, jpeg)", "jpg", "png", "gif", "jpeg");
        chooser.setFileFilter(filter);
        chooser.setAcceptAllFileFilterUsed(false);

        int result = chooser.showOpenDialog(bookView);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedImageFileForNewBook = chooser.getSelectedFile();
            ImageIcon previewIcon = ImageUtils.scaleImageFromFile(
                    selectedImageFileForNewBook.getAbsolutePath(),
                    ImageUtils.DEFAULT_IMAGE_WIDTH,
                    ImageUtils.DEFAULT_IMAGE_HEIGHT
            );
            bookView.setPreviewImage(previewIcon);
            System.out.println("Đã chọn ảnh: " + selectedImageFileForNewBook.getName());
        } else {
            bookView.setPreviewImage(null);
            selectedImageFileForNewBook = null;
            System.out.println("Người dùng hủy chọn ảnh.");
        }
    }

    // Add book
    public void addBook() {
        if (!CommonView.confirmAction(bookView, "Bạn có chắc chắn muốn thêm sách này?")) {
            return;
        }

        Books book = null;
        Path copiedImagePath = null;
        String urlPathForDb = null;

        try {
            book = getBookInForm();

            if (selectedImageFileForNewBook != null) {
                String projectDir = System.getProperty("user.dir");
                Path imageDirPath = Paths.get(projectDir, "images", book.getBookID());
                Files.createDirectories(imageDirPath);

                String originalFileName = selectedImageFileForNewBook.getName();
                String fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.'));
                String cleanBookName = book.getBookName().replaceAll("[^a-zA-Z0-9.\\-]", "_");
                String destinationFileName = cleanBookName + fileExtension;
                copiedImagePath = imageDirPath.resolve(destinationFileName);

                Files.copy(selectedImageFileForNewBook.toPath(), copiedImagePath, StandardCopyOption.REPLACE_EXISTING);
                urlPathForDb = "images/" + book.getBookID() + "/" + destinationFileName;
                book.setUrlImage(urlPathForDb);
            }

            if (!bookService.addBook(book)) {
                if (copiedImagePath != null) {
                    Files.deleteIfExists(copiedImagePath);
                }
                throw new IllegalArgumentException("Không thể thêm sách. Mã sách có thể đã tồn tại.");
            }

            CommonView.showInfoMessage(bookView, "Thêm sách thành công!");
            refreshView();
            selectedImageFileForNewBook = null;
        } catch (IOException ex) {
            CommonView.showErrorMessage(bookView, "Lỗi khi xử lý file ảnh: " + ex.getMessage());
            if (copiedImagePath != null) {
                try {
                    Files.deleteIfExists(copiedImagePath);
                } catch (IOException ioex) {
                    System.err.println("Lỗi khi xóa file tạm: " + ioex.getMessage());
                }
            }
        } catch (IllegalArgumentException ex) {
            CommonView.showErrorMessage(bookView, ex.getMessage());
        } catch (Exception ex) {
            CommonView.showErrorMessage(bookView, "Lỗi khi thêm sách: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // Update book
    public void updateBook() {
        String bookID = bookView.getTextFieldBookId().getText().trim();
        if (bookID.isEmpty()) {
            throw new IllegalArgumentException("Vui lòng chọn sách cần sửa!");
        }

        Books oldBook = bookService.getBookByID(bookID);
        if (oldBook == null) {
            throw new IllegalArgumentException("Không tìm thấy sách với mã: " + bookID);
        }

        if (!CommonView.confirmAction(bookView, "Bạn có chắc chắn muốn sửa thông tin sách này?")) {
            return;
        }

        try {
            Books newBook = getBookInForm();
            newBook.setBookID(bookID);
            String oldImageUrl = oldBook.getUrlImage();

            if (selectedImageFileForNewBook != null) {
                String projectDir = System.getProperty("user.dir");
                Path imageDirPath = Paths.get(projectDir, "images", bookID);
                Files.createDirectories(imageDirPath);

                String originalFileName = selectedImageFileForNewBook.getName();
                String fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.'));
                String cleanBookName = newBook.getBookName().replaceAll("[^a-zA-Z0-9.\\-]", "_");
                String newFileName = cleanBookName + fileExtension;
                Path newImagePath = imageDirPath.resolve(newFileName);

                if (oldImageUrl != null && !oldImageUrl.isEmpty()) {
                    Path oldImagePath = Paths.get(projectDir, oldImageUrl.replace("/", File.separator));
                    if (Files.exists(oldImagePath)) {
                        Files.delete(oldImagePath);
                    }
                }

                Files.copy(selectedImageFileForNewBook.toPath(), newImagePath, StandardCopyOption.REPLACE_EXISTING);
                String newUrl = "images/" + bookID + "/" + newFileName;
                newBook.setUrlImage(newUrl);
            } else {
                newBook.setUrlImage(oldImageUrl);
            }

            if (!bookService.updateBook(newBook)) {
                throw new IllegalArgumentException("Không thể sửa sách. Vui lòng thử lại.");
            }

            CommonView.showInfoMessage(bookView, "Sửa sách thành công!");
            refreshView();
            selectedImageFileForNewBook = null;
        } catch (IOException ex) {
            CommonView.showErrorMessage(bookView, "Lỗi khi xử lý file ảnh: " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            CommonView.showErrorMessage(bookView, ex.getMessage());
        } catch (Exception ex) {
            CommonView.showErrorMessage(bookView, "Lỗi khi cập nhật sách: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // Delete book
    public void deleteBook() {
        String bookID = bookView.getTextFieldBookId().getText().trim();
        if (bookID.isEmpty()) {
            throw new IllegalArgumentException("Vui lòng chọn sách cần xóa!");
        }

        Books book = bookService.getBookByID(bookID);
        if (book == null) {
            throw new IllegalArgumentException("Không tìm thấy sách với mã: " + bookID);
        }

        if (!CommonView.confirmAction(bookView, "Bạn có chắc chắn muốn xóa sách này? Ảnh liên quan cũng sẽ bị xóa.")) {
            return;
        }

        try {
            String imageUrl = book.getUrlImage();
            if (imageUrl != null && !imageUrl.isEmpty()) {
                Path imagePath = Paths.get(System.getProperty("user.dir"), imageUrl.replace("/", File.separator));
                if (Files.exists(imagePath)) {
                    Files.delete(imagePath);
                }
            }

            if (!bookService.deleteBook(book)) {
                throw new IllegalArgumentException("Không thể xóa sách!");
            }

            CommonView.showInfoMessage(bookView, "Xóa sách thành công!");
            refreshView();
        } catch (IOException ex) {
            CommonView.showErrorMessage(bookView, "Lỗi khi xóa file ảnh: " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            CommonView.showErrorMessage(bookView, ex.getMessage());
        } catch (Exception ex) {
            CommonView.showErrorMessage(bookView, "Lỗi khi xóa sách: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // Search books
    public void searchBooks() {
        try {
            BookSearch condition = getFormBookSearch();
            ArrayList<Books> booksResult = bookService.listBookSearch(condition);
            statistical(booksResult);
            if (booksResult.isEmpty()) {
                CommonView.showInfoMessage(bookView, "Không tìm thấy sách!");
                updateTable(booksResult);
            } else {
                updateTable(booksResult);
            }
        } catch (IllegalArgumentException ex) {
            CommonView.showErrorMessage(bookView, ex.getMessage());
        } catch (Exception ex) {
            CommonView.showErrorMessage(bookView, "Lỗi khi tìm kiếm sách: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // Refresh view
    private void refreshView() {
        ArrayList<Books> allBooks = bookService.getAllBooks();
        updateTable(allBooks);
        statistical(allBooks);
        clearForm();
    }

    // Export to CSV using BufferedWriter
    private void exportToCSV2() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Xuất file CSV");
        int userSelection = fileChooser.showSaveDialog(bookView);
        if (userSelection != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File fileToSave = fileChooser.getSelectedFile();
        String filePath = fileToSave.getAbsolutePath();
        if (!filePath.endsWith(".csv")) {
            filePath += ".csv";
        }
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8"))) {
            writer.write("\uFEFF");
            writer.write("Mã sách,Thể loại,Tên sách,Tác giả,Năm xuất bản,Số lượng,Giá");
            writer.newLine();

            for (int i = 0; i < bookView.getTableModel().getRowCount(); i++) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < bookView.getTableModel().getColumnCount(); j++) {
                    Object value = bookView.getTableModel().getValueAt(i, j);
                    if (value != null) sb.append(value.toString());
                    else sb.append("");
                    if (j != bookView.getTableModel().getColumnCount() - 1) {
                        sb.append(",");
                    }
                }
                writer.write(sb.toString());
                writer.newLine();
            }
            CommonView.showInfoMessage(bookView, "Xuất file CSV thành công!");
        } catch (IOException ex) {
            CommonView.showErrorMessage(bookView, "Lỗi khi ghi file CSV: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // Add table selection listener
    public void addTableSelectionListener() {
        bookView.getTable().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && bookView.getTable().getSelectedRow() != -1) {
                try {
                    int selectRow = bookView.getTable().getSelectedRow();
                    int modelRow = bookView.getTable().convertRowIndexToModel(selectRow);
                    String bookID = bookView.getTableModel().getValueAt(modelRow, 0).toString();
                    Books book = bookService.getBookByID(bookID);

                    if (book != null) {
                        bookView.getTextFieldBookId().setText(book.getBookID());
                        bookView.getComboBoxCategory().setSelectedItem(book.getCategory().getCategoryName());
                        bookView.getTextFieldBookName().setText(book.getBookName());
                        bookView.getTextFieldAuthor().setText(book.getAuthor());
                        bookView.getTextFieldYearPublished().setText(String.valueOf(book.getYearPublished()));
                        bookView.getTextFieldQuantity().setText(String.valueOf(book.getQuantity()));
                        bookView.getTextFieldPrice().setText(String.valueOf(book.getPrice()));

                        String imageUrl = book.getUrlImage();
                        ImageIcon bookIcon = null;
                        if (imageUrl != null && !imageUrl.isEmpty()) {
                            String absolutePath = ImageUtils.getAbsolutePathFromRelative(imageUrl);
                            if (absolutePath != null && Files.exists(Paths.get(absolutePath))) {
                                bookIcon = ImageUtils.scaleImageFromFile(
                                        absolutePath,
                                        ImageUtils.DEFAULT_IMAGE_WIDTH,
                                        ImageUtils.DEFAULT_IMAGE_HEIGHT
                                );
                            }
                        }
                        bookView.setPreviewImage(bookIcon);
                    } else {
                        clearForm();
                    }
                } catch (Exception ex) {
                    CommonView.showErrorMessage(bookView, "Lỗi khi tải dữ liệu sách lên form: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });
    }

    // Get book data from form
    public Books getBookInForm() {
        String bookID = bookView.getTextFieldBookId().getText().trim();
        String bookName = bookView.getTextFieldBookName().getText().trim();
        String author = bookView.getTextFieldAuthor().getText().trim();
        String yearPublishedStr = bookView.getTextFieldYearPublished().getText().trim();
        String priceStr = bookView.getTextFieldPrice().getText().trim();
        String quantityStr = bookView.getTextFieldQuantity().getText().trim();
        int choiceCategory = bookView.getComboBoxCategory().getSelectedIndex();

        String validationError = validateBookInput(bookID, bookName, author, yearPublishedStr, priceStr, quantityStr, choiceCategory);
        if (validationError != null) {
            throw new IllegalArgumentException(validationError);
        }

        int yearPublished;
        try {
            yearPublished = ValidateForm.isInteger(yearPublishedStr, "Năm xuất bản");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (yearPublished < 0 || yearPublished > 2025) {
            throw new IllegalArgumentException("Năm xuất bản phải từ 0 đến 2025!");
        }

        int quantity;
        try {
            quantity = ValidateForm.isInteger(quantityStr, "Số lượng");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Số lượng phải là số dương!");
        }

        double price ;
        try {
            price = ValidateForm.isDouble(priceStr, "Giá");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (price < 0) {
            throw new IllegalArgumentException("Giá phải là số dương!");
        }

        Category category = categories.get(choiceCategory - 1);
        Books book = new Books(bookID, bookName, author, yearPublished, price, quantity, category);
        return book;
    }

    // Validate book input
    private String validateBookInput(String bookID, String bookName, String author, String yearPublishedStr,
                                     String priceStr, String quantityStr, int choiceCategory) {
        if (bookID.isEmpty()) return "Mã sách không được để trống!";
        if (bookName.isEmpty()) return "Tên sách không được để trống!";
        if (author.isEmpty()) return "Tác giả không được để trống!";
        if (yearPublishedStr.isEmpty()) return "Năm xuất bản không được để trống!";
        if (priceStr.isEmpty()) return "Giá không được để trống!";
        if (quantityStr.isEmpty()) return "Số lượng không được để trống!";
        if (choiceCategory <= 0) return "Vui lòng chọn thể loại!";

        try {
            Integer.parseInt(yearPublishedStr);
        } catch (NumberFormatException e) {
            return "Năm xuất bản phải là số nguyên!";
        }

        try {
            Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
            return "Số lượng phải là số nguyên!";
        }

        try {
            Double.parseDouble(priceStr);
        } catch (NumberFormatException e) {
            return "Giá phải là số thực!";
        }

        return null;
    }

    // Get search conditions from form
    public BookSearch getFormBookSearch() {
        String bookID = bookView.getTextFieldBookId1().getText().trim();
        String bookName = bookView.getTextFieldBookName1().getText().trim();
        String author = bookView.getTextFieldAuthor1().getText().trim();
        String priceFromStr = bookView.getTextFieldPriceFrom().getText().trim();
        String priceToStr = bookView.getTextFieldPriceTo().getText().trim();
        String yearFromStr = bookView.getTextFieldYearFrom().getText().trim();
        String yearToStr = bookView.getTextFieldYearTo().getText().trim();
        int index = bookView.getComboBoxCategory_search().getSelectedIndex();

        if (bookID.isEmpty() && bookName.isEmpty() && author.isEmpty() && priceFromStr.isEmpty()
                && priceToStr.isEmpty() && yearFromStr.isEmpty() && yearToStr.isEmpty() && index <= 0) {
            throw new IllegalArgumentException("Vui lòng thêm điều kiện tìm kiếm!");
        }

        Integer yearFrom = null;
        if (!yearFromStr.isEmpty()) {
            try {
                yearFrom = ValidateForm.isInteger(yearFromStr, "Năm xuất bản từ");
                if (yearFrom < 0 || yearFrom > 2025) {
                    throw new IllegalArgumentException("Năm xuất bản từ phải từ 0 đến 2025!");
                }
            } catch (Exception e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }

        Integer yearTo = null;
        if (!yearToStr.isEmpty()) {
            try {
                yearTo = ValidateForm.isInteger(yearToStr, "Năm xuất bản đến");
                if (yearTo > 2025 || (yearFrom != null && yearFrom > yearTo)) {
                    throw new IllegalArgumentException("Năm xuất bản đến phải lớn hơn hoặc bằng năm từ và nhỏ hơn 2025!");
                }
            } catch (Exception e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }

        Double priceFrom = null;
        if (!priceFromStr.isEmpty()) {
            try {
                priceFrom = ValidateForm.isDouble(priceFromStr, "Giá từ");
                if (priceFrom < 0) {
                    throw new IllegalArgumentException("Giá từ phải là số dương!");
                }
            } catch (Exception e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }

        Double priceTo = null;
        if (!priceToStr.isEmpty()) {
            try {
                priceTo = ValidateForm.isDouble(priceToStr, "Giá đến");
                if (priceTo < 0) {
                    throw new IllegalArgumentException("Giá đến phải là số dương!");
                }
                if (priceFrom != null && priceTo < priceFrom) {
                    throw new IllegalArgumentException("Giá đến phải lớn hơn hoặc bằng giá từ!");
                }
            } catch (Exception e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }

        String categoryName = null;
        if (index > 0) {
            categoryName = categories.get(index - 1).getCategoryName();
        }

        return new BookSearch(bookID, categoryName, bookName, author, yearFrom, yearTo, priceFrom, priceTo);
    }

    // Clear form
    public void clearForm() {
        bookView.getTextFieldBookId().setText("");
        bookView.getTextFieldBookName().setText("");
        bookView.getTextFieldAuthor().setText("");
        bookView.getTextFieldPrice().setText("");
        bookView.getTextFieldQuantity().setText("");
        bookView.getTextFieldYearPublished().setText("");
        bookView.getComboBoxCategory().setSelectedIndex(0);
        bookView.setPreviewImage(null);
        selectedImageFileForNewBook = null;
    }

    // Update table
    public void updateTable(ArrayList<Books> listBook) {
        bookView.getTableModel().setRowCount(0);
        for (Books book : listBook) {
            Object[] row = {
                    book.getBookID(),
                    book.getCategory().getCategoryName(),
                    book.getBookName(),
                    book.getAuthor(),
                    book.getYearPublished(),
                    book.getQuantity(),
                    book.getPrice()
            };
            bookView.getTableModel().addRow(row);
        }
    }

    // Statistical data
    public void statistical(ArrayList<Books> listBook) {
        if (listBook.isEmpty()) {
            bookView.getLabelBookTypeCountValue().setText("0");
            bookView.getLabelTotalBooksValue().setText("0");
            bookView.getLabelPriceMinValue().setText("0");
            bookView.getLabelPriceMaxValue().setText("0");
        } else {
            bookView.getLabelTotalBooksValue().setText(String.valueOf(listBook.size()));

            double minPrice = Double.MAX_VALUE;
            double maxPrice = Double.MIN_VALUE;
            for (Books book : listBook) {
                minPrice = Math.min(minPrice, book.getPrice());
                maxPrice = Math.max(maxPrice, book.getPrice());
            }
            bookView.getLabelPriceMinValue().setText(String.valueOf(minPrice));
            bookView.getLabelPriceMaxValue().setText(String.valueOf(maxPrice));

            Set<String> categorySet = new HashSet<>();
            for (Books book : listBook) {
                categorySet.add(book.getCategory().getCategoryName());
            }
            bookView.getLabelBookTypeCountValue().setText(String.valueOf(categorySet.size()));
        }
    }
}