package view;

import controller.SelectEmployeeController;
import model.Employees;
import utils.CommonView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SelectEmployeeView extends JDialog {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton selectButton;
    private JButton searchButton;
    private JButton allButton; // << 1. Khai báo biến cho nút "Tất cả"
    private JTextField searchField;
    private Employees employee;

    public SelectEmployeeView() {
        super((Frame) null, "Chọn nhân viên", ModalityType.APPLICATION_MODAL);
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        display();
    }

    public void display() {
        // Tạo bảng hiển thị danh sách nhân viên
        String[] columnNames = {"Mã NV", "Họ", "Tên", "Chức vụ", "Email", "SĐT", "Lương", "Phái", "Ngày sinh"};
        // Khởi tạo tableModel với tên cột và 0 hàng ban đầu
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Giữ nguyên không cho sửa bảng
            }
        };
        table = CommonView.createTable(tableModel, columnNames);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Tạo panel tìm kiếm - Giữ nguyên cấu trúc
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(null);
        searchPanel.setPreferredSize(new Dimension(1000, 50));

        JLabel searchLabel = new JLabel("Tìm kiếm:");
        searchLabel.setBounds(20, 10, 70, 30);
        searchPanel.add(searchLabel);

        searchField = new JTextField();
        // Đặt bounds giống hệt trong SelectCustomerView ví dụ
        searchField.setBounds(100, 10, 550, 30); // << Sử dụng bounds từ ví dụ
        searchPanel.add(searchField);

        // Đặt nút "Tìm kiếm" trước
        searchButton = CommonView.createButton("Tìm kiếm", new Color(0x4CAF50));
        // Đặt bounds giống hệt trong SelectCustomerView ví dụ
        searchButton.setBounds(670, 10, 120, 30); // << Sử dụng bounds từ ví dụ
        searchPanel.add(searchButton);

        // 2. Khởi tạo nút "Tất cả"
        allButton = CommonView.createButton("Tất cả", new Color(0x2196F3)); // Màu xanh dương
        // 3. Đặt vị trí nút "Tất cả" bên phải nút "Tìm kiếm", giống hệt ví dụ
        allButton.setBounds(800, 10, 120, 30); // << Sử dụng bounds từ ví dụ
        // 4. Thêm nút "Tất cả" vào panel
        searchPanel.add(allButton);

        add(searchPanel, BorderLayout.NORTH);

        // Tạo panel chứa nút xác nhận - Giữ nguyên
        JPanel buttonPanel = new JPanel();
        selectButton = CommonView.createButton("Xác nhận", new Color(0xE755D0));
        buttonPanel.add(selectButton);
        add(buttonPanel, BorderLayout.SOUTH);

        SelectEmployeeController controller = new SelectEmployeeController(this);
    }

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

    // 5. Tạo getter cho nút "Tất cả"
    public JButton getAllButton() {
        return allButton;
    }

    public JTextField getSearchField() {
        return searchField;
    }

     public Employees getEmployee() {
        return employee;
    }

    public void setEmployee(Employees employee) {
        this.employee = employee;
    }

    public static void main(String[] args) {
        // Sử dụng invokeLater cho an toàn luồng Swing - Giữ nguyên
        SwingUtilities.invokeLater(() -> {
            SelectEmployeeView s = new SelectEmployeeView();
            s.setVisible(true);
        });
    }
}