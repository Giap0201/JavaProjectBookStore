package controller;

import model.Books;
import model.Discount;
import model.DiscountDetails;
import model.ListBook;
import service.DiscountService;
import utils.ValidateForm;
import view.DiscountProgramView;
import view.SelectBookView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class DiscountController implements ActionListener {
    private DiscountProgramView view;
    private DiscountService discountService;
    private ArrayList<Books> listBook;

    public DiscountController(DiscountProgramView discountProgramView) {
        this.view = discountProgramView;
        this.discountService = new DiscountService();
        updateAllTable(discountService.getAllDiscounts());
        setComboBox(discountService.listMapDiscount());
        //su kien click vao cac hang cua bang
        addListenerTableDiscount();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.getBtnAdd()) {
            addDiscount();
        }
        else if(e.getSource() == view.getBtnDisableAll()){
            ArrayList<Discount> listDiscounts = discountService.getAllDiscounts();
            if(!listDiscounts.isEmpty()){
                clearForm();
                updateAllTable(listDiscounts);
            }else {
                JOptionPane.showMessageDialog(null,"Không có giảm giá!!");
            }
        }
        else if(e.getSource() == view.getBtnDelete()){
            deleteDiscount();
        }
        else if(e.getSource() == view.getBtnEdit()){
            updateDiscount();
        }
        else if(e.getSource() == view.getDateSearch()){
            searchDiscount();
        }else if(e.getSource() == view.getBtnAddDetail()){
            try{
                ArrayList<DiscountDetails> test = getDataFormDiscountDetail();
            }catch (Exception ex){
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null,ex.getMessage());
            }
        }else if(e.getSource() == view.getButtonClick()){
            SelectBookView selectBookView = new SelectBookView();
            selectBookView.setVisible(true);
            listBook = selectBookView.getListBook();

        }
    }

    public void addDiscount() {
        try {
            Discount discount = getDataDiscount();
            boolean check = discountService.addDiscount(discount);
            if (check) {
                JOptionPane.showMessageDialog(null, "Thêm chương trình giảm giá thành công!!!");
                updateTableDiscount(discount);
                setComboBox(discountService.listMapDiscount());
                clearForm();
            } else {
                JOptionPane.showMessageDialog(null, "Lỗi khi thêm chương trình giảm giá, có thễ đã có cùng mã chương trình!!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public Discount getDataDiscount() throws Exception {
        String discountID = view.getTextFieldDiscountId().getText().trim();
        String discountName = view.getTextFieldProgramName().getText().trim();
        String discountType = view.getTextFieldProgramType().getText().trim();
        Date dateStart = view.getDateChooserStart().getDate();
        Date dateEnd = view.getDateChooserEnd().getDate();
        if (discountID.isEmpty() || discountName.isEmpty() || discountType.isEmpty() || dateStart == null || dateEnd == null) {
            throw new Exception("Vui lòng điền đầy đủ thông tin giảm giá!!!");
        }
        if (dateStart.after(dateEnd)) {
            throw new Exception("Ngày bắt đầu phải bé hơn hoặc bằng ngày kết thúc!!");
        }
        //chuyen kieu du lieu date util.java  thanh dang sql de luu vao co so du lieu
        java.sql.Date dateSqlStart = new java.sql.Date(dateStart.getTime());
        java.sql.Date dateSqlEnd = new java.sql.Date(dateEnd.getTime());
        return new Discount(discountID, discountName, discountType, dateSqlStart, dateSqlEnd);
    }

    //chuc nang xoa
    public void deleteDiscount() {
        String discountID = view.getTextFieldDiscountId().getText().trim();
        if(discountID.isEmpty()){
            JOptionPane.showMessageDialog(null,"Vui lòng chọn chương trình cần xoá!!");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(null,"Bạn có chắc chắn muốn xoá?","Confirmation",JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean checkDelete = discountService.deleteCondition(discountID);
            if (checkDelete) {
                JOptionPane.showMessageDialog(null,"Đã xoá thành công!!");
                clearForm();
                updateAllTable(discountService.getAllDiscounts());
                setComboBox(discountService.listMapDiscount());
            }else {
                JOptionPane.showMessageDialog(null,"Lỗi khi xoá chương trình, có thể chương trình không tồn tại!!");
            }
        }
    }

    //chuc nang sua
    public void updateDiscount() {
        String discountID = view.getTextFieldDiscountId().getText().trim();
        if(discountID.isEmpty()){
            JOptionPane.showMessageDialog(null,"Vui lòng chọn chương trình cần sửa!!!");
            return;
        }
        try {
            Discount discount = getDataDiscount();
            int confirm = JOptionPane.showConfirmDialog(null,"Bạn chắc chắn muốn sửa?","Confirmation",JOptionPane.YES_NO_OPTION);
            if(confirm == JOptionPane.YES_OPTION){
                boolean checkDelete = discountService.updateDiscount(discount);
                if (checkDelete) {
                    JOptionPane.showMessageDialog(null,"Cập nhập thành công!!");
                    clearForm();
                    updateAllTable(discountService.getAllDiscounts());
                    setComboBox(discountService.listMapDiscount());
                }else{
                    JOptionPane.showMessageDialog(null,"Lỗi khi cập nhật!!");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    //chuc nang tim kiem theo ngay
    public  void searchDiscount(){
        Date dateStart = view.getDateStartSearch().getDate();
        Date dateEnd = view.getDateEndSearch().getDate();
        //chuyen du lieu ngay sang dang sql
        if(dateStart == null && dateEnd == null){
            JOptionPane.showMessageDialog(null,"Vui lòng chọn thời gian tìm kiếm!!");
            return;
        }
        //khong nhap gi se tra ve null, khong kiem tra dieu kien tim kiem
        java.sql.Date dateSqlStart = null;
        if(dateStart != null){
            dateSqlStart = new java.sql.Date(dateStart.getTime());
        }
        java.sql.Date dateSqlEnd = null;
        if(dateEnd != null){
            dateSqlEnd = new java.sql.Date(dateEnd.getTime());
        }
        if(dateSqlStart != null && dateSqlEnd != null){
            if(dateSqlStart.after(dateEnd)){
                JOptionPane.showMessageDialog(null,"Ngày bắt đầu phải nhỏ hơn hoặc bằng ngày kết thúc!!");
                return;
            }
        }
        ArrayList<Discount> listResult = discountService.listDiscountsSearch(dateSqlStart,dateSqlEnd);
        if(listResult.isEmpty()){
            JOptionPane.showMessageDialog(null,"Không tìm thấy chương trình giảm giá!!");
            view.getTableModelTop().setRowCount(0);
        }else{
            updateAllTable(listResult);
        }
    }

    public void clearForm() {
        view.getTextFieldDiscountId().setText("");
        view.getTextFieldProgramName().setText("");
        view.getTextFieldProgramType().setText("");
        view.getDateChooserStart().setDate(null);
        view.getDateChooserEnd().setDate(null);
    }

    //thao tac them su kien cho table
    public void addListenerTableDiscount() {
        view.getTableTop().getSelectionModel().addListSelectionListener(e -> {
                    int selectRow = view.getTableTop().getSelectedRow();
                    if (selectRow >= 0) {
                        String discountID = (String) view.getTableTop().getValueAt(selectRow,0);
                        String discountName = (String) view.getTableTop().getValueAt(selectRow,1);
                        String discountType = (String) view.getTableTop().getValueAt(selectRow,2);
                        Date startDate = (Date) view.getTableTop().getValueAt(selectRow,3);
                        Date endDate = (Date) view.getTableTop().getValueAt(selectRow,4);
                        view.getTextFieldDiscountId().setText(discountID);
                        view.getTextFieldProgramName().setText(discountName);
                        view.getTextFieldProgramType().setText(discountType);
                        view.getDateChooserStart().setDate(startDate);
                        view.getDateChooserEnd().setDate(endDate);
                    }
                }
        );
    }
    public void updateAllTable(ArrayList<Discount> listDiscount) {
        view.getTableModelTop().setRowCount(0);
        for(Discount discount : listDiscount) {
            Object[] row = {discount.getDiscountID(),discount.getNameDiscount(),discount.getTypeDiscount(),discount.getStartDate(),discount.getEndDate()};
            view.getTableModelTop().addRow(row);
        }
    }
    //thao tac them mot cot vao
    public void updateTableDiscount(Discount discount) {
        Object[] row = {discount.getDiscountID(),discount.getNameDiscount(),discount.getTypeDiscount(),discount.getStartDate(),discount.getEndDate()};
        view.getTableModelTop().addRow(row);
    }
    public void setComboBox(LinkedHashMap<String,String> mapDiscount) {
        //xoa toan bo combobox cu, them combobox moi vao
        view.getComboBoxNameDiscount().removeAllItems();
        view.getComboBoxNameDiscount().addItem("Chọn chương trình");
        for(Map.Entry<String,String> entry : mapDiscount.entrySet()){
            view.getComboBoxNameDiscount().addItem(entry.getValue());
        }
    }
    //lay du lieu tu form view phan chi tiet giam gia
    public ArrayList<DiscountDetails> getDataFormDiscountDetail () throws Exception{
        int index = view.getComboBoxNameDiscount().getSelectedIndex();
        if(index<=0) {
            throw new Exception("Vui lòng chọn chương trình giảm giá!!");
        }
        ArrayList<Discount> listDiscount = discountService.getAllDiscounts();
        Discount discount = listDiscount.get(index);
        System.out.println(discount.getDiscountID());
        if(listBook.isEmpty()){
            throw new Exception("Vui lòng chọn sách giảm giá!!");
        }
        for(Books book : listBook){
            System.out.println(book.getBookID());
        }
        String percent = view.getTextFieldDiscountPercent().getText().trim();
        if(percent.isEmpty() || percent.equals("null")){
            throw new Exception("Vui lòng nhập phần trăm!!");
        }
        double percentDouble = 0;
        try {
            percentDouble = ValidateForm.isDouble(percent,"Phần trăm");
            if(percentDouble < 0 || percentDouble > 100){
                throw new Exception("Phần trăm phải nằm trong khoảng từ 0 đến 100");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,e.getMessage());
        }

        ArrayList<DiscountDetails> listDiscountDetails = new ArrayList<>();
        for (Books books : listBook) {
            DiscountDetails x = new DiscountDetails(discount,percentDouble,books);
            listDiscountDetails.add(x);
        }
        for (DiscountDetails x : listDiscountDetails) {
            System.out.println(x.getDiscount().getDiscountID() + " " + x.getPercent() + " " + x.getBook().getBookID());
        }
        return listDiscountDetails;
    }

    //phan chi tiet chuong trinh giam gia




}
//package controller;
//
//import model.Books;
//import model.Discount;
//import model.DiscountDetails;
//import service.DiscountService;
//import utils.ValidateForm;
//import view.DiscountProgramView;
//import view.SelectBookView;
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.LinkedHashMap;
//
//public class DiscountController implements ActionListener {
//    private DiscountProgramView view;
//    private DiscountService discountService;
//    private SelectBookView selectBookView;
//    private SelectBookController selectBookController;
//    private ArrayList<Books> selectedBooks;
//
//    public DiscountController(DiscountProgramView discountProgramView) {
//        this.view = discountProgramView;
//        this.discountService = new DiscountService();
//        this.selectBookView = new SelectBookView();
//        this.selectBookController = new SelectBookController(selectBookView);
//        this.selectedBooks = new ArrayList<>();
//
//        // Thêm WindowListener để cập nhật selectedBooks khi SelectBookView đóng
//        selectBookView.addWindowListener(new WindowAdapter() {
//            @Override
//            public void windowClosed(WindowEvent e) {
//                selectedBooks = selectBookController.getListBookSelected();
//                System.out.println("Số sách được chọn sau khi đóng SelectBookView: " + selectedBooks.size());
//                for (Books book : selectedBooks) {
//                    System.out.println(" - " + book.getBookID());
//                }
//            }
//        });
//
//        updateAllTable(discountService.getAllDiscounts());
//        setComboBox(discountService.listMapDiscount());
//        addListenerTableDiscount();
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        try {
//            if (e.getSource() == view.getBtnAdd()) {
//                addDiscount();
//            } else if (e.getSource() == view.getBtnDelete()) {
//                deleteDiscount();
//            } else if (e.getSource() == view.getBtnEdit()) {
//                updateDiscount();
//            } else if (e.getSource() == view.getDateSearch()) {
//                searchDiscount();
//            } else if (e.getSource() == view.getBtnAddDetail()) {
//                addDiscountDetail();
//            } else if (e.getSource() == view.getButtonClick()) {
//                selectBookView.setVisible(true);
//                // Không cập nhật selectedBooks ở đây nữa
//            } else if (e.getSource() == view.getBtnDisableAll()) {
//                clearForm();
//                updateAllTable(discountService.getAllDiscounts());
//            }
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(view, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//    private void addDiscountDetail() throws Exception {
//        // Kiểm tra chương trình giảm giá
//        int discountIndex = view.getComboBoxNameDiscount().getSelectedIndex();
//        if (discountIndex <= 0) {
//            throw new Exception("Vui lòng chọn chương trình giảm giá!");
//        }
//        ArrayList<Discount> discounts = discountService.getAllDiscounts();
//        if (discountIndex > discounts.size()) {
//            throw new Exception("Chương trình giảm giá không hợp lệ!");
//        }
//        Discount discount = discounts.get(discountIndex - 1);
//
//        // Kiểm tra danh sách sách
//        if (selectedBooks.isEmpty()) {
//            throw new Exception("Vui lòng chọn ít nhất một cuốn sách trước khi thêm chi tiết!");
//        }
//
//        // Kiểm tra phần trăm giảm giá
//        String percentText = view.getTextFieldDiscountPercent().getText().trim();
//        if (percentText.isEmpty()) {
//            throw new Exception("Vui lòng nhập phần trăm giảm giá!");
//        }
//        double percent = ValidateForm.isDouble(percentText, "Phần trăm giảm giá");
//        if (percent <= 0 || percent > 100) {
//            throw new Exception("Phần trăm giảm giá phải từ 0 đến 100!");
//        }
//
//        // Tạo danh sách chi tiết giảm giá
//        ArrayList<DiscountDetails> discountDetails = new ArrayList<>();
//        for (Books book : selectedBooks) {
//            discountDetails.add(new DiscountDetails(discount, percent, book));
//        }
//
//        // Lưu vào cơ sở dữ liệu
////        boolean success = discountService.addDiscountDetails(discountDetails);
////        if (success) {
////            JOptionPane.showMessageDialog(view, "Thêm chi tiết giảm giá thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
////            clearDetailForm();
////        } else {
////            throw new Exception("Lỗi khi thêm chi tiết giảm giá!");
////        }
//        for (DiscountDetails discountDetail : discountDetails) {
//            System.out.println(discountDetail.getDiscount().getDiscountID()+" "+discountDetail.getPercent()+" "+discountDetail.getBook().getBookID());
//        }
//    }
//
//    private void addDiscount() throws Exception {
//        Discount discount = getDataDiscount();
//        if (discountService.addDiscount(discount)) {
//            JOptionPane.showMessageDialog(view, "Thêm chương trình giảm giá thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
//            updateTableDiscount(discount);
//            setComboBox(discountService.listMapDiscount());
//            clearForm();
//        } else {
//            throw new Exception("Mã chương trình đã tồn tại!");
//        }
//    }
//
//    private void deleteDiscount() throws Exception {
//        String discountID = view.getTextFieldDiscountId().getText().trim();
//        if (discountID.isEmpty()) {
//            throw new Exception("Vui lòng chọn chương trình cần xóa!");
//        }
//        int confirm = JOptionPane.showConfirmDialog(view, "Bạn có chắc chắn muốn xóa?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
//        if (confirm == JOptionPane.YES_OPTION) {
//            if (discountService.deleteCondition(discountID)) {
//                JOptionPane.showMessageDialog(view, "Xóa thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
//                clearForm();
//                updateAllTable(discountService.getAllDiscounts());
//                setComboBox(discountService.listMapDiscount());
//            } else {
//                throw new Exception("Lỗi khi xóa chương trình!");
//            }
//        }
//    }
//
//    private void updateDiscount() throws Exception {
//        String discountID = view.getTextFieldDiscountId().getText().trim();
//        if (discountID.isEmpty()) {
//            throw new Exception("Vui lòng chọn chương trình cần sửa!");
//        }
//        Discount discount = getDataDiscount();
//        int confirm = JOptionPane.showConfirmDialog(view, "Bạn có chắc chắn muốn sửa?", "Xác nhận sửa", JOptionPane.YES_NO_OPTION);
//        if (confirm == JOptionPane.YES_OPTION) {
//            if (discountService.updateDiscount(discount)) {
//                JOptionPane.showMessageDialog(view, "Cập nhật thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
//                clearForm();
//                updateAllTable(discountService.getAllDiscounts());
//                setComboBox(discountService.listMapDiscount());
//            } else {
//                throw new Exception("Lỗi khi cập nhật chương trình!");
//            }
//        }
//    }
//
//    private void searchDiscount() throws Exception {
//        Date dateStart = view.getDateStartSearch().getDate();
//        Date dateEnd = view.getDateEndSearch().getDate();
//        java.sql.Date dateSqlStart = dateStart != null ? new java.sql.Date(dateStart.getTime()) : null;
//        java.sql.Date dateSqlEnd = dateEnd != null ? new java.sql.Date(dateEnd.getTime()) : null;
//
//        if (dateSqlStart == null && dateSqlEnd == null) {
//            throw new Exception("Vui lòng chọn thời gian tìm kiếm!");
//        }
//        if (dateSqlStart != null && dateSqlEnd != null && dateSqlStart.after(dateSqlEnd)) {
//            throw new Exception("Ngày bắt đầu phải nhỏ hơn hoặc bằng ngày kết thúc!");
//        }
//
//        ArrayList<Discount> results = discountService.listDiscountsSearch(dateSqlStart, dateSqlEnd);
//        if (results.isEmpty()) {
//            JOptionPane.showMessageDialog(view, "Không tìm thấy chương trình giảm giá!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
//            view.getTableModelTop().setRowCount(0);
//        } else {
//            updateAllTable(results);
//        }
//    }
//
//    private Discount getDataDiscount() throws Exception {
//        String discountID = view.getTextFieldDiscountId().getText().trim();
//        String discountName = view.getTextFieldProgramName().getText().trim();
//        String discountType = view.getTextFieldProgramType().getText().trim();
//        Date dateStart = view.getDateChooserStart().getDate();
//        Date dateEnd = view.getDateChooserEnd().getDate();
//
//        validateDiscountInput(discountID, discountName, discountType, dateStart, dateEnd);
//
//        java.sql.Date dateSqlStart = new java.sql.Date(dateStart.getTime());
//        java.sql.Date dateSqlEnd = new java.sql.Date(dateEnd.getTime());
//        return new Discount(discountID, discountName, discountType, dateSqlStart, dateSqlEnd);
//    }
//
//    private void validateDiscountInput(String id, String name, String type, Date start, Date end) throws Exception {
//        if (id.isEmpty() || name.isEmpty() || type.isEmpty()) {
//            throw new Exception("Vui lòng điền đầy đủ thông tin giảm giá!");
//        }
//        if (start == null || end == null) {
//            throw new Exception("Vui lòng chọn ngày bắt đầu và kết thúc!");
//        }
//        if (start.after(end)) {
//            throw new Exception("Ngày bắt đầu phải nhỏ hơn hoặc bằng ngày kết thúc!");
//        }
//        if (!id.matches("[A-Za-z0-9]+")) {
//            throw new Exception("Mã giảm giá chỉ được chứa chữ cái và số!");
//        }
//    }
//
//    private void clearForm() {
//        view.getTextFieldDiscountId().setText("");
//        view.getTextFieldProgramName().setText("");
//        view.getTextFieldProgramType().setText("");
//        view.getDateChooserStart().setDate(null);
//        view.getDateChooserEnd().setDate(null);
//    }
//
//    private void clearDetailForm() {
//        view.getComboBoxNameDiscount().setSelectedIndex(0);
//        view.getTextFieldDiscountPercent().setText("");
//        selectBookController.clearSelectedBooks();
//        selectedBooks.clear();
//    }
//
//    private void addListenerTableDiscount() {
//        view.getTableTop().getSelectionModel().addListSelectionListener(e -> {
//            if (!e.getValueIsAdjusting()) {
//                int selectedRow = view.getTableTop().getSelectedRow();
//                if (selectedRow >= 0) {
//                    String discountID = (String) view.getTableTop().getValueAt(selectedRow, 0);
//                    String discountName = (String) view.getTableTop().getValueAt(selectedRow, 1);
//                    String discountType = (String) view.getTableTop().getValueAt(selectedRow, 2);
//                    Date startDate = (Date) view.getTableTop().getValueAt(selectedRow, 3);
//                    Date endDate = (Date) view.getTableTop().getValueAt(selectedRow, 4);
//                    view.getTextFieldDiscountId().setText(discountID);
//                    view.getTextFieldProgramName().setText(discountName);
//                    view.getTextFieldProgramType().setText(discountType);
//                    view.getDateChooserStart().setDate(startDate);
//                    view.getDateChooserEnd().setDate(endDate);
//                }
//            }
//        });
//    }
//
//    private void updateAllTable(ArrayList<Discount> discounts) {
//        DefaultTableModel model = view.getTableModelTop();
//        model.setRowCount(0);
//        for (Discount discount : discounts) {
//            Object[] row = {
//                    discount.getDiscountID(),
//                    discount.getNameDiscount(),
//                    discount.getTypeDiscount(),
//                    discount.getStartDate(),
//                    discount.getEndDate()
//            };
//            model.addRow(row);
//        }
//    }
//
//    private void updateTableDiscount(Discount discount) {
//        Object[] row = {
//                discount.getDiscountID(),
//                discount.getNameDiscount(),
//                discount.getTypeDiscount(),
//                discount.getStartDate(),
//                discount.getEndDate()
//        };
//        view.getTableModelTop().addRow(row);
//    }
//
//    private void setComboBox(LinkedHashMap<String, String> mapDiscount) {
//        JComboBox<String> comboBox = view.getComboBoxNameDiscount();
//        comboBox.removeAllItems();
//        comboBox.addItem("Chọn chương trình");
//        mapDiscount.values().forEach(comboBox::addItem);
//    }
//}