package view;

import controller.SelectCustomerController; // Đảm bảo bạn có controller này
import model.Customers;
import utils.CommonView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SelectCustomerView extends JDialog {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton selectButton;
    private JButton searchButton;
    private JButton allButton; // << Khai báo biến
    private JTextField searchField;
    private Customers customers;
    public SelectCustomerView() {
        super((Frame) null, "Chọn khách hàng", ModalityType.APPLICATION_MODAL);
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        display();
    }

    public void display() {
        // Tạo bảng hiển thị danh sách khách hàng
        String[] columnNames = {"Mã KH", "Họ", "Tên", "Giới tính", "Số ĐT", "Email", "Ngày sinh", "Tổng chi tiêu", "Ngày lập thẻ", "Note"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = CommonView.createTable(tableModel, columnNames);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Tạo panel tìm kiếm
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(null);
        searchPanel.setPreferredSize(new Dimension(1000, 50));

        JLabel searchLabel = new JLabel("Tìm kiếm:");
        searchLabel.setBounds(20, 10, 70, 30);
        searchPanel.add(searchLabel);

        searchField = new JTextField();
        // Vẫn giảm chiều rộng searchField để có đủ chỗ cho 2 nút bên phải
        searchField.setBounds(100, 10, 550, 30); // << Điều chỉnh chiều rộng nếu cần
        searchPanel.add(searchField);

        // Đặt nút "Tìm kiếm" trước
        searchButton = CommonView.createButton("Tìm kiếm", new Color(0x4CAF50));
        // Vị trí nút "Tìm kiếm" ngay sau trường tìm kiếm
        searchButton.setBounds(670, 10, 120, 30); // << Đặt vị trí cho nút tìm kiếm
        searchPanel.add(searchButton);

        // Khởi tạo và đặt nút "Tất cả" bên phải nút "Tìm kiếm"
        allButton = CommonView.createButton("Tất cả", new Color(0x2196F3));
        // Vị trí nút "Tất cả" bên phải nút "Tìm kiếm", có khoảng cách (ví dụ: 10px)
        allButton.setBounds(800, 10, 120, 30); // << Đặt vị trí cho nút tất cả (760 + 100 + 10 = 870)
        searchPanel.add(allButton);

        add(searchPanel, BorderLayout.NORTH);

        // Tạo panel chứa nút xác nhận
        JPanel buttonPanel = new JPanel();
        selectButton = CommonView.createButton("Xác nhận", new Color(0xE755D0));
        buttonPanel.add(selectButton);
        add(buttonPanel, BorderLayout.SOUTH);

        SelectCustomerController controller = new SelectCustomerController(this);
    }

    // --- Getters ---
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

    public Customers getCustomers() {
        return customers;
    }

    public void setCustomers(Customers customers) {
        this.customers = customers;
    }


}