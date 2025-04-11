package view;

import com.toedter.calendar.JDateChooser;
import controller.DiscountController;
import utils.CommonView;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;


public class DiscountProgramView extends JPanel{
    private JTextField textFieldProgramType, textFieldProgramName, textFieldDiscountId;
    private JDateChooser dateChooserStart, dateChooserEnd, dateStartSearch, dateEndSearch;
    private JButton btnAdd, btnEdit, btnDelete, btnDisableAll, dateSearch, btnAddDetail, btnDeleteDetail;
    private DefaultTableModel tableModelTop, tableModelDetails;
    private JTable tableTop, tableDetails;
    private JButton btnEditDetail,buttonClick;
    private JTextField textFieldDiscountPercent;
    private JComboBox<String> comboBoxNameDiscount;

    public JPanel initDiscountProgramView() {
        Font font = new Font("Tahoma", Font.BOLD, 15);
        Font font1 = new Font("Tahoma", Font.PLAIN, 15);
        JPanel panelContent = new JPanel();
        panelContent.setLayout(null);
        panelContent.setBackground(new Color(157, 239, 227));

        // Tiêu đề
        JLabel labelTitle = new JLabel("CHƯƠNG TRÌNH GIẢM GIÁ");
        labelTitle.setBounds(500, 10, 300, 30);
        labelTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        panelContent.add(labelTitle);

        // Panel đầu vào
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(null);
        inputPanel.setBackground(new Color(157, 239, 227));
        inputPanel.setBounds(0, 50, 1500, 420); // Tăng chiều cao để chứa bảng
        panelContent.add(inputPanel);

        ImageIcon discount = CommonView.scaleImage("images/discount2.png", 300, 150);
        JLabel lblDiscount = new JLabel(discount);
        lblDiscount.setBounds(80, 0, discount.getIconWidth(), discount.getIconHeight());
        inputPanel.add(lblDiscount);

        // Bảng hiển thị trong inputPanel
        String[] columnNamesTop = {"Mã chương trình", "Tên chương trình", "Loại khuyến mãi", "Ngày BĐ", "Ngày KT"};
        tableModelTop = new DefaultTableModel();
        tableTop = CommonView.createTable(tableModelTop,columnNamesTop);
        JScrollPane scrollPaneTop = new JScrollPane(tableTop);
        scrollPaneTop.setBounds(450, 10, 800, 350); // Đặt ở góc bên phải trong inputPanel
        inputPanel.add(scrollPaneTop);

        // Mã giảm giá
        JLabel labelDiscountId = new JLabel("Mã chương trình:");
        labelDiscountId.setFont(font);
        labelDiscountId.setBounds(50, 170, 150, 30); // Điều chỉnh y để nhường chỗ cho bảng
        inputPanel.add(labelDiscountId);

        textFieldDiscountId = new JTextField();
        textFieldDiscountId.setFont(font1);
        textFieldDiscountId.setBounds(190, 170, 180, 30);
        inputPanel.add(textFieldDiscountId);

        // Tên chương trình
        JLabel labelProgramName = new JLabel("Tên chương trình:");
        labelProgramName.setFont(font);
        labelProgramName.setBounds(50, 210, 150, 30);
        inputPanel.add(labelProgramName);

        textFieldProgramName = new JTextField("");
        textFieldProgramName.setFont(font1);
        textFieldProgramName.setBounds(190, 210, 180, 30);
        inputPanel.add(textFieldProgramName);

        // Loại chương trình
        JLabel labelProgramType = new JLabel("Loại chương trình:");
        labelProgramType.setFont(font);
        labelProgramType.setBounds(50, 250, 150, 30);
        inputPanel.add(labelProgramType);

        textFieldProgramType = new JTextField("");
        textFieldProgramType.setFont(font1);
        textFieldProgramType.setBounds(190, 250, 180, 30);
        inputPanel.add(textFieldProgramType);

        // Ngày bắt đầu
        JLabel labelStartDate = new JLabel("Ngày bắt đầu:");
        labelStartDate.setFont(font);
        labelStartDate.setBounds(50, 290, 150, 30);
        inputPanel.add(labelStartDate);

        // JDateChooser cho ngày bắt đầu
        dateChooserStart = CommonView.createDateChooser();
        dateChooserStart.setBounds(190, 290, 200, 30);
        inputPanel.add(dateChooserStart);

        // Ngày kết thúc
        JLabel labelEndDate = new JLabel("Ngày kết thúc:");
        labelEndDate.setFont(font);
        labelEndDate.setBounds(50, 330, 150, 30);
        inputPanel.add(labelEndDate);

        //calendar cho ngay ket thuc
        dateChooserEnd = CommonView.createDateChooser();
        dateChooserEnd.setBounds(190, 330, 200, 30);
        inputPanel.add(dateChooserEnd);

        // Nút Thêm, Sửa, Xóa, Tắt Cả
        btnAdd = CommonView.createButton("THÊM", new Color(32, 204, 35));
        btnAdd.setBounds(50, 380, 100, 40);
        inputPanel.add(btnAdd);

        btnEdit = CommonView.createButton("SỬA", new Color(0, 0, 255));
        btnEdit.setBounds(160, 380, 100, 40);
        inputPanel.add(btnEdit);

        btnDelete = CommonView.createButton("XÓA", new Color(255, 0, 0));
        btnDelete.setBounds(270, 380, 100, 40);
        inputPanel.add(btnDelete);

        btnDisableAll = CommonView.createButton("TẤT CẢ", new Color(0, 0, 0));
        btnDisableAll.setBounds(380, 380, 100, 40);
        inputPanel.add(btnDisableAll);

        // Panel tìm kiếm
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(null);
        searchPanel.setBackground(new Color(157, 239, 227));
        searchPanel.setBounds(50, 480, 1200, 80);
        searchPanel.setBorder(BorderFactory.createLineBorder(new Color(228, 13, 13)));
        panelContent.add(searchPanel);

        JLabel labelSearch = new JLabel("Tìm kiếm");
        labelSearch.setFont(new Font("Tahoma", Font.BOLD, 24));
        labelSearch.setBounds(50, 10, 500, 20);
        searchPanel.add(labelSearch);

        JLabel labelSearchStartDate = new JLabel("Ngày bắt đầu:");
        labelSearchStartDate.setFont(font);
        labelSearchStartDate.setBounds(170, 40, 110, 30);
        searchPanel.add(labelSearchStartDate);

        dateStartSearch = CommonView.createDateChooser();
        dateStartSearch.setBounds(280, 40, 170, 30);
        searchPanel.add(dateStartSearch);

        JLabel labelSearchEndDate = new JLabel("Ngày kết thúc:");
        labelSearchEndDate.setFont(font);
        labelSearchEndDate.setBounds(500, 40, 150, 30);
        searchPanel.add(labelSearchEndDate);

        //search daystart using Jdatechooser
        dateEndSearch = CommonView.createDateChooser();
        dateEndSearch.setBounds(630, 40, 170, 30);
        searchPanel.add(dateEndSearch);

        dateSearch = CommonView.createButton("TÌM KIẾM", new Color(0, 0, 0));
        dateSearch.setBounds(950, 40, 130, 30);
        searchPanel.add(dateSearch);

        // Panel chi tiết giảm giá
        JPanel discountDetailsPanel = new JPanel();
        discountDetailsPanel.setLayout(null);
        discountDetailsPanel.setBackground(new Color(157, 239, 227));
        discountDetailsPanel.setBounds(0, 550, 1500, 250); // Đặt ngay dưới searchPanel
        panelContent.add(discountDetailsPanel);

        JLabel labelDiscountDetails = new JLabel("CHI TIẾT CHƯƠNG TRÌNH GIẢM GIÁ");
        labelDiscountDetails.setFont(new Font("Tahoma", Font.BOLD, 17));
        labelDiscountDetails.setBounds(50, 10, 500, 20);
        discountDetailsPanel.add(labelDiscountDetails);

        // Bảng chi tiết chương trình giảm giá
        String[] columnNames = {"Mã GG", "% Giảm giá", "Mã sách"};
        tableModelDetails = new DefaultTableModel();
        tableDetails = CommonView.createTable(tableModelDetails, columnNames);
        JScrollPane scrollPane = new JScrollPane(tableDetails);
        scrollPane.setBounds(30, 30, 460, 200);
        discountDetailsPanel.add(scrollPane);

        JLabel labelDiscountName = new JLabel("Chọn chương trình");
        labelDiscountName.setFont(new Font("Tahoma", Font.BOLD, 15));
        labelDiscountName.setBounds(520, 30, 150, 30); // ✅ dịch phải thêm 50px
        discountDetailsPanel.add(labelDiscountName);

        comboBoxNameDiscount = new JComboBox<>();
        comboBoxNameDiscount.setBounds(670, 30, 200, 30); // giữ cách đều với label
        discountDetailsPanel.add(comboBoxNameDiscount);
        comboBoxNameDiscount.setFont(new Font("Tahoma", Font.PLAIN, 14));

        JLabel labelBookName = new JLabel("Chọn sách giảm");
        labelBookName.setFont(new Font("Tahoma", Font.BOLD, 15));
        labelBookName.setBounds(520, 75, 150, 30);
        discountDetailsPanel.add(labelBookName);

        buttonClick = CommonView.createButton("Chọn sách", new Color(0x984C8C));
        buttonClick.setBounds(670, 75, 200, 30);
        discountDetailsPanel.add(buttonClick);

        JLabel labelDiscountPercent = new JLabel("Phần trăm giảm");
        labelDiscountPercent.setFont(new Font("Tahoma", Font.BOLD, 15));
        labelDiscountPercent.setBounds(520, 120, 150, 30);
        discountDetailsPanel.add(labelDiscountPercent);

        textFieldDiscountPercent = new JTextField();
        textFieldDiscountPercent.setFont(font1);
        textFieldDiscountPercent.setBounds(670, 120, 200, 30); 
        discountDetailsPanel.add(textFieldDiscountPercent);

        btnAddDetail = CommonView.createButton("THÊM", new Color(32, 204, 35));
        btnAddDetail.setBounds(520, 170, 100, 40); // Dưới input
        discountDetailsPanel.add(btnAddDetail);

        btnDeleteDetail = CommonView.createButton("XÓA", new Color(255, 0, 0));
        btnDeleteDetail.setBounds(640, 170, 100, 40);
        discountDetailsPanel.add(btnDeleteDetail);

        btnEditDetail = CommonView.createButton("SỬA",new Color(0, 0, 255));
        btnEditDetail.setBounds(760, 170, 100, 40);
        discountDetailsPanel.add(btnEditDetail);


// Hình ảnh giảm giá
        ImageIcon discount2 = CommonView.scaleImage("images/discount.png", 300, 200);
        JLabel lblDiscount2 = new JLabel(discount2);
        lblDiscount2.setBounds(940, 40, discount2.getIconWidth(), discount2.getIconHeight()); // dịch phải thêm 20
        discountDetailsPanel.add(lblDiscount2);

        //them cac su kien vao view
        DiscountController controller = new DiscountController(this);
        btnAdd.addActionListener(controller);
        btnDelete.addActionListener(controller);
        btnDeleteDetail.addActionListener(controller);
        btnEdit.addActionListener(controller);
        btnDisableAll.addActionListener(controller);
        btnAddDetail.addActionListener(controller);
        dateSearch.addActionListener(controller);
        buttonClick.addActionListener(controller);
        btnEditDetail.addActionListener(controller);
        return panelContent;
    }

    public JTextField getTextFieldProgramType() {
        return textFieldProgramType;
    }

    public JTextField getTextFieldProgramName() {
        return textFieldProgramName;
    }

    public JTextField getTextFieldDiscountId() {
        return textFieldDiscountId;
    }

    public JDateChooser getDateChooserStart() {
        return dateChooserStart;
    }

    public JDateChooser getDateChooserEnd() {
        return dateChooserEnd;
    }

    public JDateChooser getDateStartSearch() {
        return dateStartSearch;
    }

    public JDateChooser getDateEndSearch() {
        return dateEndSearch;
    }

    public JButton getBtnAdd() {
        return btnAdd;
    }

    public JButton getBtnEdit() {
        return btnEdit;
    }

    public JButton getBtnDelete() {
        return btnDelete;
    }

    public JButton getBtnDisableAll() {
        return btnDisableAll;
    }

    public JButton getDateSearch() {
        return dateSearch;
    }

    public JButton getBtnAddDetail() {
        return btnAddDetail;
    }

    public JButton getBtnDeleteDetail() {
        return btnDeleteDetail;
    }

    public DefaultTableModel getTableModelTop() {
        return tableModelTop;
    }

    public DefaultTableModel getTableModelDetails() {
        return tableModelDetails;
    }

    public JTable getTableTop() {
        return tableTop;
    }

    public JTable getTableDetails() {
        return tableDetails;
    }

    public JButton getBtnEditDetail() {
        return btnEditDetail;
    }

    public JTextField getTextFieldDiscountPercent() {
        return textFieldDiscountPercent;
    }

    public JButton getButtonClick() {
        return buttonClick;
    }

    public JComboBox<String> getComboBoxNameDiscount() {
        return comboBoxNameDiscount;
    }
}
