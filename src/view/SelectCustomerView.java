package view;

import utils.CommonView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SelectCustomerView extends JDialog {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton selectButton;
    private JButton searchButton;
    private JTextField searchField;

    public SelectCustomerView() {
        // Tạo một dialog không có cửa sổ cha, tiêu đề là "Chọn khách hàng", và sẽ chặn toàn bộ các cửa sổ khác cho đến khi người dùng đóng nó lại.
        super((Frame) null, "Chọn khách hàng", ModalityType.APPLICATION_MODAL);
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        display();
    }

    public void display() {
        // Tạo bảng hiển thị danh sách khách hàng
        String[] columnNames = { "Mã KH", "Tên KH", "Số điện thoại", "Email", "Địa chỉ" };
        tableModel = new DefaultTableModel();
        table = CommonView.createTable(tableModel, columnNames);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Chỉ cho phép chọn một dòng
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Tạo panel tìm kiếm
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(null); // Sử dụng layout null để căn chỉnh thủ công
        searchPanel.setPreferredSize(new Dimension(1000, 50));

        JLabel searchLabel = new JLabel("Tìm kiếm:");
        searchLabel.setBounds(20, 10, 70, 30); // Căn chỉnh vị trí label
        searchPanel.add(searchLabel);

        searchField = new JTextField();
        searchField.setBounds(100, 10, 700, 30); // Trường nhập liệu rộng hơn
        searchPanel.add(searchField);

        searchButton = CommonView.createButton("Tìm kiếm", new Color(0x4CAF50));
        searchButton.setBounds(820, 10, 120, 30); // Nút tìm kiếm căn bên phải
        searchPanel.add(searchButton);

        add(searchPanel, BorderLayout.NORTH);

        // Tạo panel chứa nút xác nhận
        JPanel buttonPanel = new JPanel();
        selectButton = CommonView.createButton("Xác nhận", new Color(0xE755D0));
        buttonPanel.add(selectButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Getter và Setter
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

    public JTextField getSearchField() {
        return searchField;
    }

    public static void main(String[] args) {
        SelectCustomerView s = new SelectCustomerView();
        s.setVisible(true);
    }
}