package view;

import controller.SelectBookController;
import model.Books;
import utils.CommonView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class SelectBookView extends JDialog {
    private ArrayList<Books> listBook;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton selectButton;
    private JButton searchButton;
    private JButton allButton;
    private JTextField searchField;

    public SelectBookView() {
        super((Frame) null, "Chọn sách", ModalityType.APPLICATION_MODAL);
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        display();
    }

    public void display() {
        String[] columnNames = { "Mã sách", "Thể loại", "Tên sách", "Tác giả", "Năm xuất bản", "Số lượng", "Giá" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = CommonView.createTable(tableModel, columnNames);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(null);
        searchPanel.setPreferredSize(new Dimension(1000, 50));

        JLabel searchLabel = new JLabel("Tìm kiếm:");
        searchLabel.setBounds(20, 10, 70, 30);
        searchPanel.add(searchLabel);

        searchField = new JTextField();
        searchField.setBounds(100, 10, 550, 30);
        searchPanel.add(searchField);

        searchButton = CommonView.createButton("Tìm kiếm", new Color(0x4CAF50));
        searchButton.setBounds(670, 10, 120, 30);
        searchPanel.add(searchButton);

        allButton = CommonView.createButton("Tất cả", new Color(0x2196F3));
        allButton.setBounds(800, 10, 120, 30);
        searchPanel.add(allButton);

        add(searchPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        selectButton = CommonView.createButton("Xác nhận", new Color(0xE755D0));
        buttonPanel.add(selectButton);
        add(buttonPanel, BorderLayout.SOUTH);

        SelectBookController controller = new SelectBookController(this);
    }

    // Getters
    public JTable getTable() {
        return table;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JButton getSelectButton() {
        return selectButton;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public JButton getAllButton() {
        return allButton;
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public ArrayList<Books> getListBook() {
        return listBook;
    }

    public void setListBook(ArrayList<Books> listBook) {
        this.listBook = listBook;
    }
}