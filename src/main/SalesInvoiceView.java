package view;

import com.toedter.calendar.JDateChooser;
import utils.CommonView;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class SalesInvoiceView {
    private JPanel panelContent;
    private JTextField textFieldInvoiceId;
    private JDateChooser dateChooserSaleDate;
    private JTextField textFieldCustomerId;
    private JTextField textFieldEmployeeId;
    private JComboBox<String> comboBoxStatus;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnAdd, btnDelete, btnEdit, btnViewDetails, btnPrint;

    public JPanel initSalesInvoiceView() {
        panelContent = new JPanel();
        panelContent.setLayout(null);
        panelContent.setBackground(new Color(157, 239, 227));

        // Thêm panelHeader và panelTable vào panelContent
        JPanel panelHeader = createHeaderPanel();
        JPanel panelTable = createTablePanel();

        panelContent.add(panelHeader);
        panelContent.add(panelTable);

        return panelContent;
    }

    private JPanel createHeaderPanel() {
        Font font = new Font("Tahoma", Font.BOLD, 15);
        Font font1 = new Font("Tahoma", Font.PLAIN, 15);
        JPanel panelHeader = new JPanel();
        panelHeader.setLayout(null);
        panelHeader.setBackground(new Color(157, 239, 227));
        panelHeader.setBounds(0, 0, 1450, 200);

        // Title
        JLabel labelTitle = new JLabel("QUẢN LÝ HÓA ĐƠN BÁN HÀNG");
        labelTitle.setBounds(550, 10, 400, 30);
        labelTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        panelHeader.add(labelTitle);

        // Invoice ID
        JLabel labelInvoiceId = new JLabel("Mã hóa đơn:");
        labelInvoiceId.setFont(font);
        labelInvoiceId.setBounds(10, 50, 100, 20);
        panelHeader.add(labelInvoiceId);

        textFieldInvoiceId = new JTextField();
        textFieldInvoiceId.setFont(font1);
        textFieldInvoiceId.setBounds(130, 50, 150, 30);
        panelHeader.add(textFieldInvoiceId);

        // Sale Date
        JLabel labelDate = new JLabel("Ngày lập:");
        labelDate.setFont(font);
        labelDate.setBounds(300, 50, 100, 20);
        panelHeader.add(labelDate);

        dateChooserSaleDate = new JDateChooser();
        dateChooserSaleDate.setDateFormatString("yyyy-MM-dd");
        dateChooserSaleDate.setFont(font1);
        dateChooserSaleDate.setBounds(400, 50, 150, 30);
        panelHeader.add(dateChooserSaleDate);

        // Customer ID
        JLabel labelCustomerId = new JLabel("Mã khách hàng:");
        labelCustomerId.setFont(font);
        labelCustomerId.setBounds(570, 50, 120, 20);
        panelHeader.add(labelCustomerId);

        textFieldCustomerId = new JTextField();
        textFieldCustomerId.setFont(font1);
        textFieldCustomerId.setBounds(700, 50, 150, 30);
        panelHeader.add(textFieldCustomerId);

        // Employee ID
        JLabel labelEmployeeId = new JLabel("Mã nhân viên:");
        labelEmployeeId.setFont(font);
        labelEmployeeId.setBounds(870, 50, 120, 20);
        panelHeader.add(labelEmployeeId);

        textFieldEmployeeId = new JTextField();
        textFieldEmployeeId.setFont(font1);
        textFieldEmployeeId.setBounds(1000, 50, 150, 30);
        panelHeader.add(textFieldEmployeeId);

        // Status
        JLabel labelStatus = new JLabel("Trạng thái:");
        labelStatus.setFont(font);
        labelStatus.setBounds(10, 100, 100, 20);
        panelHeader.add(labelStatus);

        comboBoxStatus = new JComboBox<>(new String[]{"Đã thanh toán", "Chưa thanh toán"});
        comboBoxStatus.setFont(font1);
        comboBoxStatus.setBounds(130, 100, 150, 30);
        panelHeader.add(comboBoxStatus);

        // Buttons
        btnAdd = CommonView.createButton("THÊM", new Color(32, 204, 35));
        btnAdd.setBounds(300, 100, 100, 40);
        panelHeader.add(btnAdd);

        btnEdit = CommonView.createButton("SỬA", new Color(255, 105, 180));
        btnEdit.setBounds(420, 100, 100, 40);
        panelHeader.add(btnEdit);

        btnDelete = CommonView.createButton("XÓA", new Color(250, 4, 4));
        btnDelete.setBounds(540, 100, 100, 40);
        panelHeader.add(btnDelete);

        btnViewDetails = CommonView.createButton("XEM CHI TIẾT", new Color(255, 165, 0));
        btnViewDetails.setBounds(660, 100, 150, 40);
        panelHeader.add(btnViewDetails);

        btnPrint = CommonView.createButton("IN HÓA ĐƠN", new Color(0, 0, 255));
        btnPrint.setBounds(830, 100, 150, 40);
        panelHeader.add(btnPrint);

        // Icon
        ImageIcon saleIcon = CommonView.scaleImage("images/icon11.png", 150, 150); // Thay bằng đường dẫn thực tế
        JLabel labelIcon = new JLabel(saleIcon);
        labelIcon.setBounds(1050, 20, 150, 150);
        panelHeader.add(labelIcon);

        return panelHeader;
    }

    private JPanel createTablePanel() {
        JPanel panelTable = new JPanel();
        panelTable.setLayout(null);
        panelTable.setBackground(new Color(157, 239, 227));
        panelTable.setBounds(0, 200, 1450, 600);

        // Table for Invoice List
        String[] columnNames = {"Mã hóa đơn", "Ngày lập", "Mã khách hàng", "Mã nhân viên", "Tổng tiền", "Trạng thái"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);

        table.setFont(new Font("Tahoma", Font.PLAIN, 12));
        table.setRowHeight(20);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Tahoma", Font.BOLD, 14));
        header.setBackground(Color.LIGHT_GRAY);
        header.setForeground(Color.BLACK);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 20, 1350, 550);
        panelTable.add(scrollPane);

        return panelTable;
    }


    // Getter cho các thành phần
    public JTextField getTextFieldInvoiceId() {
        return textFieldInvoiceId;
    }

    public JDateChooser getDateChooserSaleDate() {
        return dateChooserSaleDate;
    }

    public JTextField getTextFieldCustomerId() {
        return textFieldCustomerId;
    }

    public JTextField getTextFieldEmployeeId() {
        return textFieldEmployeeId;
    }

    public JComboBox<String> getComboBoxStatus() {
        return comboBoxStatus;
    }

    public JTable getTable() {
        return table;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JButton getBtnAdd() {
        return btnAdd;
    }

    public JButton getBtnDelete() {
        return btnDelete;
    }

    public JButton getBtnEdit() {
        return btnEdit;
    }

    public JButton getBtnViewDetails() {
        return btnViewDetails;
    }

    public JButton getBtnPrint() {
        return btnPrint;
    }
}