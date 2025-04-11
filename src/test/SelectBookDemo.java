package test;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class SelectBookDemo extends JFrame {
    private JButton btnChooseBooks;
    private JTextArea textAreaResult;

    public SelectBookDemo() {
        setTitle("Demo chọn sách");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        btnChooseBooks = new JButton("Chọn sách");
        textAreaResult = new JTextArea();
        textAreaResult.setEditable(false);

        add(btnChooseBooks, BorderLayout.NORTH);
        add(new JScrollPane(textAreaResult), BorderLayout.CENTER);

        // Sự kiện click nút
        btnChooseBooks.addActionListener(e -> {
            ArrayList<Book> books = getSampleBooks(); // danh sách demo
            SelectBookDialog dialog = new SelectBookDialog(this, books);
            dialog.setVisible(true);

            ArrayList<Book> selectedBooks = dialog.getSelectedBooks();
            if (!selectedBooks.isEmpty()) {
                textAreaResult.setText(""); // clear cũ
                for (Book book : selectedBooks) {
                    textAreaResult.append("ID: " + book.getId() +
                            ", Tên: " + book.getTitle() +
                            ", Tác giả: " + book.getAuthor() +
                            ", Giá: " + book.getPrice() + "\n");
                }
            } else {
                textAreaResult.setText("Chưa chọn sách nào!");
            }
        });
    }

    private ArrayList<Book> getSampleBooks() {
        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book("B001", "Lập trình Java", "Nguyễn Văn A", 100.0));
        books.add(new Book("B002", "Cấu trúc dữ liệu", "Trần Văn B", 120.0));
        books.add(new Book("B003", "Hệ điều hành", "Lê Văn C", 150.0));
        books.add(new Book("B004", "Mạng máy tính", "Phạm Thị D", 130.0));
        return books;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SelectBookDemo().setVisible(true);
        });
    }
}

// =========================================
// LỚP BOOK
class Book {
    private String id, title, author;
    private double price;

    public Book(String id, String title, String author, double price) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public double getPrice() { return price; }
}

// =========================================
// LỚP JDialog chọn sách
class SelectBookDialog extends JDialog {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnConfirm;
    private ArrayList<Book> selectedBooks = new ArrayList<>();

    public SelectBookDialog(JFrame parent, ArrayList<Book> books) {
        super(parent, "Chọn sách", true);
        setSize(500, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        String[] columns = {"Mã sách", "Tên sách", "Tác giả", "Giá"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        for (Book book : books) {
            Object[] row = {book.getId(), book.getTitle(), book.getAuthor(), book.getPrice()};
            tableModel.addRow(row);
        }

        btnConfirm = new JButton("Xác nhận");
        btnConfirm.addActionListener(e -> {
            int[] selectedRows = table.getSelectedRows();
            selectedBooks.clear();
            for (int row : selectedRows) {
                String id = (String) tableModel.getValueAt(row, 0);
                String title = (String) tableModel.getValueAt(row, 1);
                String author = (String) tableModel.getValueAt(row, 2);
                double price = (double) tableModel.getValueAt(row, 3);
                selectedBooks.add(new Book(id, title, author, price));
            }
            dispose();
        });

        add(new JScrollPane(table), BorderLayout.CENTER);
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(btnConfirm);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public ArrayList<Book> getSelectedBooks() {
        return selectedBooks;
    }
}
