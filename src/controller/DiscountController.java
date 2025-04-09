package controller;

import model.Discount;
import service.DiscountService;
import view.DiscountProgramView;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

public class DiscountController implements ActionListener {
    private DiscountProgramView view;
    private DiscountService discountService;

    public DiscountController(DiscountProgramView discountProgramView) {
        this.view = discountProgramView;
        this.discountService = new DiscountService();
        updateAllTable(discountService.getAllDiscounts());
        //su kien click vao cac hang cua bang
        addListenerTableDiscount();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.getBtnAdd()) {
            addDiscount();
        }
        if(e.getSource() == view.getBtnDisableAll()){
            ArrayList<Discount> listDiscounts = discountService.getAllDiscounts();
            if(!listDiscounts.isEmpty()){
                clearForm();
                updateAllTable(listDiscounts);
            }else {
                JOptionPane.showMessageDialog(null,"Không có giảm giá!!");
            }
        }
        if(e.getSource() == view.getBtnDelete()){
            deleteDiscount();
        }
        if(e.getSource() == view.getBtnEdit()){
            updateDiscount();
        }
    }

    public void addDiscount() {
        try {
            Discount discount = getDataDiscount();
            boolean check = discountService.addDiscount(discount);
            if (check) {
                JOptionPane.showMessageDialog(null, "Thêm chương trình giảm giá thành công!!!");
                updateTableDiscount(discount);
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
                }else{
                    JOptionPane.showMessageDialog(null,"Lỗi khi cập nhật!!");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
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
//    public void deleteRowDiscount() {
//        view.getTableModelTop().removeRow(view.getTableTop().getSelectedRow());
//    }




}
