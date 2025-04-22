package controller;

import model.*;
import service.BookService;
import service.CategoryService;
import utils.ImageUtils;
import utils.ValidateForm;
import view.BookView;


import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;

public class BookController implements ActionListener {
    private BookView bookView;
    private BookService bookService;
    private CategoryService categoryService;
    private ArrayList<Category> categories;

    // ===> BIẾN TẠM ĐỂ LƯU ẢNH ĐÃ CHỌN KHI THÊM MỚI <===
    private File selectedImageFileForNewBook = null; // Hoặc dùng Path

    public BookController(BookView bookView) {
        this.bookView = bookView;
        this.bookService = new BookService();
        this.categoryService = new CategoryService();
        this.categories = categoryService.getCategory();
        // them su kien cho bang
        addTableSelectionListener();
        updateTable(bookService.getAllBooks());
        statistical(bookService.getAllBooks());

    }

    // cac su kien cua cac nut bam thuc hien chuc nang
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bookView.getBtnAdd()) {
            addBook();
        } else if (e.getSource() == bookView.getBtnChange()) {
            updateBook();
        } else if (e.getSource() == bookView.getBtnDelete()) {
            deleteBook();
        } else if (e.getSource() == bookView.getBtnSearch()) {
            searchBooks();
        } else if (e.getSource() == bookView.getBtnReset()) {
            ArrayList<Books> allBooks = bookService.getAllBooks();
            statistical(allBooks);
            clearForm();
        } else if(e.getSource() == bookView.getBtnSaveFile()){
            exportToCSV();
        } else if(e.getSource() == bookView.getBtnUploadImage()){
            selectImageForNewBook();
        }
    }

    private void selectImageForNewBook() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Chọn ảnh cho sách mới");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Hình ảnh (jpg,png,gif,jpeg)", "jpg", "png", "gif", "jpeg");
        chooser.setFileFilter(filter);
        chooser.setAcceptAllFileFilterUsed(false);

        int result = chooser.showOpenDialog(bookView);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedImageFileForNewBook = chooser.getSelectedFile();

            // Thay đổi kích thước và lấy icon xem trước bằng ImageUtils
            ImageIcon previewIcon = ImageUtils.scaleImageFromFile(
                    selectedImageFileForNewBook.getAbsolutePath(),
                    ImageUtils.DEFAULT_IMAGE_WIDTH, // Dùng hằng số
                    ImageUtils.DEFAULT_IMAGE_HEIGHT // Dùng hằng số
            );

            // Đặt ảnh xem trước trong view thông qua phương thức mới
            bookView.setPreviewImage(previewIcon); // Gọi setter mới của view

            System.out.println("Đã chọn ảnh để thêm mới: " + selectedImageFileForNewBook.getName());
        } else {
            System.out.println("Người dùng đã hủy chọn file ảnh.");
            // Tùy chọn: Đặt lại ảnh xem trước về mặc định nếu người dùng hủy
            // bookView.setPreviewImage(null); // Truyền null, phương thức sẽ tự xử lý
        }
    }


    // chuc nang them sach (Không thay đổi nhiều, nhưng urlImage sẽ là null ban đầu)
    public void addBook() {
        Books book = null; // Sách lấy từ form
        Path copiedImagePath = null; // Đường dẫn file đã copy (để xóa nếu lỗi DB)
        String urlPathForDb = null; // URL lưu vào DB

        try {
            // 1. Lấy thông tin sách từ form (chưa có urlImage)
            book = getBookInForm(); // Hàm này có thể ném Exception nếu validate lỗi

            // 2. Xử lý ảnh đã chọn (nếu có) - Thực hiện trước khi lưu DB
            if (selectedImageFileForNewBook != null) {
                System.out.println("Đang xử lý ảnh đã chọn: " + selectedImageFileForNewBook.getName());
                // Tạo thư mục đích
                String projectDir = System.getProperty("user.dir");
                Path imageDirPath = Paths.get(projectDir, "images", book.getBookID()); // Dùng bookID vừa lấy từ form
                Files.createDirectories(imageDirPath);

                // Tạo tên file đích
                String originalFileName = selectedImageFileForNewBook.getName();
                String fileExtension = "";
                int lastIndex = originalFileName.lastIndexOf('.');
                if (lastIndex > 0 && lastIndex < originalFileName.length() - 1) {
                    fileExtension = originalFileName.substring(lastIndex);
                }
                // Sử dụng tên sách từ đối tượng 'book' vừa lấy được
                String cleanBookName = book.getBookName().replaceAll("[^a-zA-Z0-9.\\-]", "_");
                String destinationFileName = cleanBookName + fileExtension;
                copiedImagePath = imageDirPath.resolve(destinationFileName); // Đường dẫn tuyệt đối file mới

                // Sao chép file
                Files.copy(selectedImageFileForNewBook.toPath(), copiedImagePath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Đã sao chép ảnh tới: " + copiedImagePath);

                // Tạo URL tương đối để lưu DB
                urlPathForDb = "images" + "/" + book.getBookID() + "/" + destinationFileName;
            } else {
                System.out.println("Không có ảnh nào được chọn để thêm.");
                urlPathForDb = null; // Đảm bảo là null nếu không có ảnh
            }

            // 3. Gán URL ảnh vào đối tượng sách (có thể là null)
            book.setUrlImage(urlPathForDb);

            // 4. Gọi service/DAO để thêm sách vào DB
            boolean check1=bookService.addBook(book);
            boolean check = bookService.updateImageUrl(book.getBookID(), book.getUrlImage()); // Gọi service

            // 5. Xử lý kết quả
            if (check && check1) {
                JOptionPane.showMessageDialog(bookView, "Thêm sách thành công!");
                updateTable(bookService.getAllBooks());
                statistical(bookService.getAllBooks());
                clearForm(); // Clear form sẽ xóa cả selectedImageFileForNewBook
            } else {
                JOptionPane.showMessageDialog(bookView, "Không thể thêm sách. Có thể mã sách đã tồn tại.");
                // *** QUAN TRỌNG: Xóa file vừa copy nếu thêm sách vào DB thất bại ***
                if (copiedImagePath != null) {
                    try { Files.deleteIfExists(copiedImagePath); System.out.println("Đã xóa file ảnh vừa copy do thêm sách lỗi DB: "+copiedImagePath);}
                    catch (IOException ioex) { System.err.println("Lỗi khi xóa file tạm: "+ioex.getMessage());}
                }
            }
            selectedImageFileForNewBook = null;
        } catch (IOException ex) { // Bắt lỗi IO (ví dụ: không copy được file)
            ex.printStackTrace();
            JOptionPane.showMessageDialog(bookView, "Lỗi khi xử lý file ảnh:\n" + ex.getMessage(), "Lỗi File IO", JOptionPane.ERROR_MESSAGE);
            // Không cần xóa file tạm vì lỗi xảy ra trước khi/trong khi copy
        } catch (Exception ex) { // Bắt lỗi từ getBookInForm hoặc lỗi khác
            ex.printStackTrace();
            JOptionPane.showMessageDialog(bookView, "Lỗi khi thêm sách: " + ex.getMessage());
            // Không cần xóa file tạm vì lỗi xảy ra trước khi copy
        }
    }

    // chuc nang cap nhat sach (Cần xử lý đổi tên file ảnh nếu tên sách đổi)
    public void updateBook() {
        String bookID = bookView.getTextFieldBookId().getText().trim();
        String bookName = bookView.getTextFieldBookName().getText().trim();

        if (bookID.isEmpty()) {
            JOptionPane.showMessageDialog(bookView, "Vui lòng chọn sách cần sửa!!");
            return;
        }

        if (bookName.isEmpty()) {
            JOptionPane.showMessageDialog(bookView, "Vui lòng nhập Tên sách (dùng để đặt tên file ảnh).", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Books oldBook = bookService.getBookByID(bookID);
        if (oldBook == null) {
            JOptionPane.showMessageDialog(bookView, "Không tìm thấy sách với Mã sách: " + bookID, "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String oldImageUrl = oldBook.getUrlImage();

        int confirm = JOptionPane.showConfirmDialog(bookView, "Bạn có muốn cập nhật thông tin sách không?", "Xác nhận sửa", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            System.out.println("Người dùng đã hủy thao tác sửa sách.");
            return;
        }

        try {
            Books newBookFromForm = getBookInForm(); // Lấy dữ liệu mới từ form
            newBookFromForm.setBookID(bookID); // Đảm bảo đúng ID

            // === XỬ LÝ ẢNH MỚI (NẾU CÓ) ===
            if (selectedImageFileForNewBook != null) {
                try {
                    String projectDir = System.getProperty("user.dir");
                    String fileExtension = "";

                    String originalFileName = selectedImageFileForNewBook.getName();
                    int lastDot = originalFileName.lastIndexOf('.');
                    if (lastDot > 0 && lastDot < originalFileName.length() - 1) {
                        fileExtension = originalFileName.substring(lastDot);
                    }

                    String cleanBookName = bookName.replaceAll("[^a-zA-Z0-9.\\-]", "_");
                    String newFileName = cleanBookName + fileExtension;

                    Path newImagePath = Paths.get(projectDir, "images", bookID, newFileName);
                    Path newImageDir = newImagePath.getParent();

                    if (!Files.exists(newImageDir)) {
                        Files.createDirectories(newImageDir);
                    }

                    // Xóa ảnh cũ nếu tồn tại
                    if (oldImageUrl != null && !oldImageUrl.trim().isEmpty()) {
                        Path oldImagePath = Paths.get(projectDir, oldImageUrl.replace("/", File.separator));
                        if (Files.exists(oldImagePath)) {
                            Files.delete(oldImagePath);
                            System.out.println("Đã xóa ảnh cũ: " + oldImagePath);
                        }
                    }

                    // Sao chép ảnh mới vào thư mục
                    Files.copy(selectedImageFileForNewBook.toPath(), newImagePath, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Đã lưu ảnh mới: " + newImagePath);

                    // Cập nhật URL ảnh trong đối tượng sách
                    String newUrl = "images/" + bookID + "/" + newFileName;
                    newBookFromForm.setUrlImage(newUrl);
                } catch (IOException ioEx) {
                    ioEx.printStackTrace();
                    JOptionPane.showMessageDialog(bookView, "Lỗi khi lưu ảnh mới:\n" + ioEx.getMessage(), "Lỗi ảnh", JOptionPane.ERROR_MESSAGE);
                    newBookFromForm.setUrlImage(oldImageUrl); // fallback về ảnh cũ nếu lỗi
                }
            } else {
                // Không có ảnh mới => giữ ảnh cũ
                newBookFromForm.setUrlImage(oldImageUrl);
            }
            selectedImageFileForNewBook = null;

            // === CẬP NHẬT VÀ HIỂN THỊ ===
            boolean bookUpdateSuccess = bookService.updateBook(newBookFromForm);
            if (bookUpdateSuccess) {
                JOptionPane.showMessageDialog(bookView, "Sửa sách thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                updateTable(bookService.getAllBooks());
                statistical(bookService.getAllBooks());
                clearForm();
                // handleTableRowSelection(bookID); // Bật nếu bạn muốn reload ảnh mới luôn
            } else {
                JOptionPane.showMessageDialog(bookView, "Không thể sửa sách trong CSDL. Vui lòng thử lại.", "Lỗi Cập nhật", JOptionPane.ERROR_MESSAGE);
            }

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(bookView, "Dữ liệu nhập không hợp lệ:\n" + ex.getMessage(), "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(bookView, "Đã xảy ra lỗi khi cập nhật:\n" + ex.getMessage(), "Lỗi hệ thống", JOptionPane.ERROR_MESSAGE);
        }
    }




    // chuc nang xoa sach
    public void deleteBook() {
        String bookID = bookView.getTextFieldBookId().getText().trim();
        if (bookID.isEmpty()) { // Sửa điều kiện kiểm tra
            JOptionPane.showMessageDialog(bookView, "Vui lòng chọn sách cần xoá!!");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(bookView, "Bạn có chắc chắn muốn xoá sách '" + bookID + "'?\nẢnh liên quan (file) cũng sẽ bị xóa.", // Thông báo rõ hơn
                "Xác nhận xoá", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE); // Thêm icon cảnh báo

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Books book = getBookInForm();
                // Gọi thẳng DAO hoặc Service để xóa bằng ID
                // boolean check = bookService.deleteBookById(bookID); // Nếu service có hàm này
                boolean check = bookService.deleteBook(book); // Gọi trực tiếp DAO (đã xử lý file)

                if (check) { // DAO trả về true nếu xóa DB thành công
                    JOptionPane.showMessageDialog(bookView, "Xoá thành công!!");
                    updateTable(bookService.getAllBooks()); // Cập nhật bảng
                    statistical(bookService.getAllBooks()); // Cập nhật thống kê
                    clearForm(); // Xóa form
                } else {
                    // DAO trả về false có thể do sách không tồn tại hoặc lỗi DB (đã log trong DAO)
                    JOptionPane.showMessageDialog(bookView, "Xóa sách thất bại.\nCó thể sách không tồn tại hoặc có lỗi xảy ra.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    // Vẫn có thể clear form nếu sách không tồn tại
                    if (bookService.deleteBook(book)) {
                        clearForm();
                    }
                }
            } catch (Exception e) { // Bắt các lỗi không mong muốn từ DAO
                e.printStackTrace();
                JOptionPane.showMessageDialog(bookView, "Đã xảy ra lỗi không mong muốn khi xóa:\n" + e.getMessage(), "Lỗi hệ thống", JOptionPane.ERROR_MESSAGE);
            }
        }
    }



    // them su kien cho bang table
    public void addTableSelectionListener() {
        bookView.getTable().getSelectionModel().addListSelectionListener(e -> {
            // Kiểm tra để tránh xử lý nhiều lần và khi không có dòng nào được chọn
            if (!e.getValueIsAdjusting() && bookView.getTable().getSelectedRow() != -1) {
                int selectedRowView = bookView.getTable().getSelectedRow();
                // Chuyển chỉ số view sang model (quan trọng nếu có sort/filter)
                int selectedRowModel = bookView.getTable().convertRowIndexToModel(selectedRowView);

                // Lấy bookID từ model
                String bookID = bookView.getTableModel().getValueAt(selectedRowModel, 0).toString();

                // Lấy đối tượng sách đầy đủ từ DAO
                Books selectedBook = bookService.getBookByID(bookID);

                if (selectedBook != null) {
                    // Hiển thị ảnh
                    String imageUrl = selectedBook.getUrlImage();
                    ImageIcon bookIcon = null;
                    if (imageUrl != null && !imageUrl.trim().isEmpty()) {
                        String absolutePath = ImageUtils.getAbsolutePathFromRelative(imageUrl);
                        if (absolutePath != null && Files.exists(Paths.get(absolutePath))) {
                            bookIcon = ImageUtils.scaleImageFromFile(absolutePath,ImageUtils.DEFAULT_IMAGE_WIDTH,ImageUtils.DEFAULT_IMAGE_HEIGHT);
                        } else {
                            System.err.println("Ảnh không tìm thấy tại: " + absolutePath);
                        }
                    } else {
                        System.err.println("URL ảnh không hợp lệ hoặc không thể chuyển đổi: " + imageUrl);
                        // URL không hợp lệ -> bookIcon vẫn là null
                    }

                    bookView.setPreviewImage(bookIcon);

                    // Điền thông tin vào form
                    bookView.getTextFieldBookId().setText(selectedBook.getBookID());
                    bookView.getTextFieldBookName().setText(selectedBook.getBookName());
                    bookView.getTextFieldAuthor().setText(selectedBook.getAuthor());
                    bookView.getTextFieldYearPublished().setText(String.valueOf(selectedBook.getYearPublished()));
                    bookView.getTextFieldQuantity().setText(String.valueOf(selectedBook.getQuantity()));
                    // Giả sử getPrice() trả về giá gốc hoặc giá đã giảm tùy thuộc vào model/logic
                    bookView.getTextFieldPrice().setText(String.valueOf(selectedBook.getPrice()));
                    // Xử lý ComboBox Category
                    String categoryName = selectedBook.getCategory() != null ? selectedBook.getCategory().getCategoryName() : ""; // Lấy tên Category nếu đối tượng tồn tại
                    // Tìm và chọn đúng item trong ComboBox
                    ComboBoxModel<String> model = bookView.getComboBoxCategory().getModel();
                    for (int i = 0; i < model.getSize(); i++) {
                        if (model.getElementAt(i).equals(categoryName)) {
                            bookView.getComboBoxCategory().setSelectedIndex(i);
                            break;
                        }
                    }
                    if (bookView.getComboBoxCategory().getSelectedIndex() == -1 && !categoryName.isEmpty()){
                        System.err.println("Không tìm thấy thể loại '"+categoryName+"' trong ComboBox.");
                        // Có thể thêm vào ComboBox nếu cần hoặc để trống
                    }


                } else {
                    System.err.println("Không thể lấy thông tin chi tiết cho sách ID: " + bookID);
                    clearForm(); // Xóa form nếu không lấy được sách
                }
            }
        });
    }

    public Books getBookInForm() throws Exception {
        //thao tac lay du lieu tu view
        String bookID = bookView.getTextFieldBookId().getText().trim();
        String bookName = bookView.getTextFieldBookName().getText().trim();
        String author = bookView.getTextFieldAuthor().getText().trim();
        String yearPublishedStr = bookView.getTextFieldYearPublished().getText().trim();
        String priceStr = bookView.getTextFieldPrice().getText().trim();
        String quantityStr = bookView.getTextFieldQuantity().getText().trim();
        int choiceCategory = bookView.getComboBoxCategory().getSelectedIndex();

        if (bookID.isEmpty() || author.isEmpty() || bookName.isEmpty() || yearPublishedStr.isEmpty()
                || priceStr.isEmpty() || quantityStr.isEmpty() || choiceCategory <= 0) {
            throw new Exception("Vui lòng điền đầy đủ thông tin!");
        }
        //vi o da kiem tra toan bo dieu kien khong rong roi nen co the khong dung lop Grapher
        int yearPublished = ValidateForm.isInteger(yearPublishedStr, "Năm xuất bản");
        if (yearPublished < 0 || yearPublished > 2025)
            throw new Exception("Năm xuất bản không hợp lệ!!");
        int quantity = ValidateForm.isInteger(quantityStr, "Số lượng");
        if (quantity < 0)
            throw new Exception("Số lượng phải là số dương!!");
        double price = ValidateForm.isDouble(priceStr, "Giá");
        if (price < 0)
            throw new Exception("Giá phải là số dương!!");
        if (choiceCategory <= 0)
            throw new Exception("Vui lòng chọn thể loại!!");
        Category category = categories.get(choiceCategory - 1);
        Books book = new Books();
        book.setBookID(bookID);
        book.setBookName(bookName);
        book.setAuthor(author);
        book.setYearPublished(yearPublished);
        book.setPrice(price);
        book.setQuantity(quantity);
        book.setCategory(category);

        return book;
    }

    public BookSearch getFormBookSearch() throws Exception {
        String bookID = bookView.getTextFieldBookId1().getText().trim();
        String bookName = bookView.getTextFieldBookName1().getText().trim();
        String author = bookView.getTextFieldAuthor1().getText().trim();
        String priceFromStr = bookView.getTextFieldPriceFrom().getText().trim();
        String priceToStr = bookView.getTextFieldPriceTo().getText().trim();
        String yearFromStr = bookView.getTextFieldYearFrom().getText().trim();
        String yearToStr = bookView.getTextFieldYearTo().getText().trim();
        int index = bookView.getComboBoxCategory_search().getSelectedIndex();
        if (bookID.isEmpty() && bookName.isEmpty() && author.isEmpty() && priceFromStr.isEmpty()
                && priceToStr.isEmpty() && yearFromStr.isEmpty() && yearToStr.isEmpty() && index <= 0)
            throw new Exception("Vui lòng thêm điều kiện tìm kiếm!!");
        Integer yearFrom = null;
        if (!yearFromStr.isEmpty()) {
            yearFrom = ValidateForm.isInteger(yearFromStr, "Năm xuất bản");
            if (yearFrom < 0 || yearFrom > 2025) {
                throw new Exception("Năm xuất bản từ phải nằm trong khoảng từ 0 đến 2025!");
            }
        }
        Integer yearTo = null;
        if (!yearToStr.isEmpty()) {
            yearTo = ValidateForm.isInteger(yearToStr, "Năm xuất bản");
            if ((yearFrom != null && yearFrom > yearTo) || yearTo > 2025) {
                throw new Exception("Năm xuất bản đến phải lớn hơn hoặc bằng năm từ và bé hơn 2025!");
            }
        }
        // neu nhap vao moi thuc hien thao tac ben duoi
        Double priceFrom = null;
        if (!priceFromStr.isEmpty()) {
            priceFrom = ValidateForm.isDouble(priceFromStr, "Giá");
            if (priceFrom < 0) {
                throw new Exception("Giá từ phải là số dương!");
            }
        }
        Double priceTo = null;
        // kiem tra neu nhap vao thi moi thao tac ben duoi
        if (!priceToStr.isEmpty()) {
            priceTo = ValidateForm.isDouble(priceToStr, "Giá");
            if (priceTo < 0) {
                throw new Exception("Giá đến phải là số dương!");
            }
            if (priceFrom != null && priceTo < priceFrom) {
                throw new Exception("Giá đến phải lớn hơn hoặc bằng giá từ!");
            }
        }
        String categoryName = null;
        if (index > 0) {
            categoryName = categories.get(index - 1).getCategoryName();
        }
        return new BookSearch(bookID, categoryName, bookName, author, yearFrom, yearTo, priceFrom, priceTo);
    }

    // chuc nang tim kiem sach
    public void searchBooks() {
        try {
            BookSearch condition = getFormBookSearch();
            ArrayList<Books> booksResult = bookService.listBookSearch(condition);
            statistical(booksResult);
            if (booksResult.isEmpty()) {
                JOptionPane.showMessageDialog(bookView, "Không tìm thấy sách!!!");
                updateTable(booksResult);
            } else {
                updateTable(booksResult);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(bookView, e.getMessage());
        }
    }

    // ham xoa du lieu form
    public void clearForm() {
        bookView.getTextFieldBookId().setText("");
        bookView.getTextFieldBookName().setText("");
        bookView.getTextFieldAuthor().setText("");
        bookView.getTextFieldPrice().setText("");
        bookView.getTextFieldQuantity().setText("");
        bookView.getTextFieldYearPublished().setText("");
        bookView.getComboBoxCategory().setSelectedIndex(0);
        bookView.setPreviewImage(null);

        selectedImageFileForNewBook =null;
    }

    // ham cap nhat lai bang
    public void updateTable(ArrayList<Books> listBook) {
        // xoa toan bo du lieu trong bang
        bookView.getTableModel().setRowCount(0);
        // them du lieu vao trong bang
        for (Books book : listBook) {
            Object[] row = {book.getBookID(), book.getCategory().getCategoryName(),
                    book.getBookName(), book.getAuthor(), book.getYearPublished(), book.getQuantity(),
                    book.getPrice(),};
            bookView.getTableModel().addRow(row);
        }
    }
    // Import thêm dòng này nếu cần

    public void exportToCSV() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file CSV");
        int userSelection = fileChooser.showSaveDialog(bookView);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.endsWith(".csv")) {
                filePath += ".csv";
            }

            try (PrintWriter writer = new PrintWriter(
                    new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8"))) {

                // Ghi tiêu đề
                writer.println("Mã sách,Thể loại,Tên sách,Tác giả,Năm xuất bản,Số lượng,Giá");

                // Ghi từng dòng dữ liệu từ bảng
                for (int i = 0; i < bookView.getTableModel().getRowCount(); i++) {
                    StringBuilder sb = new StringBuilder();
                    for (int j = 0; j < bookView.getTableModel().getColumnCount(); j++) {
                        sb.append(bookView.getTableModel().getValueAt(i, j));
                        if (j != bookView.getTableModel().getColumnCount() - 1) {
                            sb.append(",");
                        }
                    }
                    writer.println(sb);
                }

                JOptionPane.showMessageDialog(bookView, "Xuất file CSV thành công!");
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(bookView, "Lỗi khi ghi file CSV: " + ex.getMessage());
            }
        }
    }
    //chuc nang tu dong thong ke du lieu sau khi tim kiem
    public void statistical(ArrayList<Books> listBook) {
        //neu danh sach rong thi 0 het
        if (listBook.isEmpty()) {
            bookView.getLabelBookTypeCountValue().setText("0");
            bookView.getLabelTotalBooksValue().setText("0");
            bookView.getLabelPriceMinValue().setText("0");
            bookView.getLabelPriceMaxValue().setText("0");
        } else {
            String totalBooksValue = String.valueOf(listBook.size());
            bookView.getLabelTotalBooksValue().setText(totalBooksValue);
            Double minPrice = 1e9;
            Double maxPrice = -1e9;
            //thao tac tim gia min
            for (int i = 0; i < listBook.size(); i++) {
                minPrice = Math.min(minPrice, listBook.get(i).getPrice());
                maxPrice = Math.max(maxPrice, listBook.get(i).getPrice());
            }
            bookView.getLabelPriceMinValue().setText(String.valueOf(minPrice));
            bookView.getLabelPriceMaxValue().setText(String.valueOf(maxPrice));
            //thao tac thong ke so loai sach
            Set<String> categorySet = new HashSet<>();
            for (Books books : listBook) {
                categorySet.add(books.getCategory().getCategoryName());
            }
            bookView.getLabelBookTypeCountValue().setText(String.valueOf(categorySet.size()));
        }
    }
}