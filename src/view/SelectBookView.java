package view;

import controller.SelectBookController;
import model.Books;
import utils.CommonView;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;

//de khi click vao 1 button nao do thi hien len cua so popup
public class SelectBookView extends JDialog {
    private ArrayList<Books> listBook;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton selectButton;
    public SelectBookView() {
        //    Tạo một dialog không có cửa sổ cha, tiêu đề là "Chọn sách", và sẽ chặn toàn bộ các cửa sổ khác cho đến khi người dùng đóng nó lại.
        super((Frame) null, "Chọn sách", ModalityType.APPLICATION_MODAL);
        setSize(1000,400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        display();
    }
    public void display() {
        String[] columnNames = { "Mã sách", "Thể loại", "Tên sách", "Tác giả", "Năm xuất bản", "Số lượng", "Giá" };
        tableModel = new DefaultTableModel();
        table = CommonView.createTable(tableModel, columnNames);
        //giup cho table co the giu phim ctr+click chon nhieu dong
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane,BorderLayout.CENTER);
        selectButton = CommonView.createButton("Xác nhận",new Color(0xE755D0));
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(selectButton);
        add(buttonPanel,BorderLayout.SOUTH);
        SelectBookController controller = new SelectBookController(this);
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

    public ArrayList<Books> getListBook() {
        return listBook;
    }

    public void setListBook(ArrayList<Books> listBook) {
        this.listBook = listBook;
    }
}
