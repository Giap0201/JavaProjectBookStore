package view;

import controller.SelectBookForOrderController;
import model.Books;
import utils.CommonView;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class SelectBookForOrderView extends JDialog {
    private Books book;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton selectButton;

    public SelectBookForOrderView() {
        super((Frame) null, "Chọn sách cho đơn hàng", ModalityType.APPLICATION_MODAL);
        setSize(1000, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        display();
    }

    public void display() {
        String[] columnNames = { "Mã sách", "Thể loại", "Tên sách", "Tác giả", "Năm xuất bản", "Số lượng", "Giá" ,"URL"};
        tableModel = new DefaultTableModel();
        table = CommonView.createTable(tableModel, columnNames);
        // Chỉ cho phép chọn một dòng
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        selectButton = CommonView.createButton("Xác nhận", new Color(0xE755D0));
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(selectButton);
        add(buttonPanel, BorderLayout.SOUTH);
        SelectBookForOrderController controller = new SelectBookForOrderController(this);
        selectButton.addActionListener(controller);
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

    public Books getBook() {
        return book;
    }

    public void setBook(Books book) {
        this.book = book;
    }
}