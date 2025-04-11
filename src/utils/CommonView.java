package utils;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class CommonView {
    public static JButton createButton(String title, Color color){
        Font font = new Font("Tahoma", Font.BOLD, 15);
        JButton button = new JButton(title);
        button.setFont(font);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setHorizontalAlignment(JButton.CENTER);
        return button;
    }

//    **
//            * Hàm hỗ trợ scale ảnh
//     */
    public static ImageIcon scaleImage(String path, int width, int height) {
        try {
            ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource(path));
            Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception e) {
            System.err.println("No Image: " + path);
            return new ImageIcon(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)); // Trả về ảnh trống
        }
    }
    //ham chung su dung JCalendar
    public static JDateChooser createDateChooser() {
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setFont(new Font("Tahoma", Font.PLAIN, 15));
        // Chặn không cho người dùng nhập trực tiếp
//        JTextField editor = ((JTextField) dateChooser.getDateEditor().getUiComponent());
//        editor.setEditable(false); // <-- Chặn nhập
        return dateChooser;
    }
    //tao ra table
    public static JTable createTable(DefaultTableModel tableModel,String[] columns) {
        tableModel.setColumnIdentifiers(columns);
        JTable table = new JTable(tableModel);
        table.setFont(new Font("Tahoma", Font.PLAIN, 12));
        //kich thuoc moi hang la 20
        table.setRowHeight(20);
        //tu dong sap xep
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Tahoma", Font.BOLD, 14));
        header.setBackground(Color.GRAY);
        header.setForeground(Color.BLACK);
        //tao doi tuong can giua noi dung
        DefaultTableCellRenderer defaultTableCellRenderer = new DefaultTableCellRenderer();
        defaultTableCellRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i<table.getColumnCount(); i++){
            table.getColumnModel().getColumn(i).setCellRenderer(defaultTableCellRenderer);
        }
        return table;
    }
}
