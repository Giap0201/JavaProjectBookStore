package controller;

import model.Books;
import model.Discount;
import model.DiscountDetails;
import service.DiscountDetailService;
import service.DiscountService;
import utils.CommonView;
import utils.ValidateForm;
import view.DiscountProgramView;
import view.SelectBookView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

// dung final để cố định giao diện, dịch vụ, danh sách, tránh lỗi gán lại.
public class DiscountController implements ActionListener {
    private final DiscountProgramView view;
    private final DiscountService discountService;
    private final DiscountDetailService discountDetailService;
    private ArrayList<Books> listBookSelect;
    private SelectBookView selectBookView;

    public DiscountController(DiscountProgramView view) {
        this.view = view;
        this.discountService = new DiscountService();
        this.discountDetailService = new DiscountDetailService();
        this.listBookSelect = new ArrayList<>();
        initializeView();
    }

    private void initializeView() {
        updateAllTable(discountService.getAllDiscounts());
        updateAllTableDetails(discountDetailService.getAllDiscountDetails());
        setComboBox(discountService.listMapDiscount());
        addListenerTableDiscount();
        registerButtonListeners();
    }

    private void registerButtonListeners() {
        view.getBtnAdd().addActionListener(this);
        view.getBtnEdit().addActionListener(this);
        view.getBtnDelete().addActionListener(this);
        view.getBtnDeleteDetail().addActionListener(this);
        view.getDateSearch().addActionListener(this);
        view.getBtnAddDetail().addActionListener(this);
        view.getButtonClick().addActionListener(this);
        view.getBtnDisableAll().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == view.getBtnAdd()) {
                addDiscount();
            } else if (e.getSource() == view.getBtnEdit()) {
                updateDiscount();
            } else if (e.getSource() == view.getBtnDelete()) {
                deleteDiscount();
            } else if (e.getSource() == view.getBtnDeleteDetail()) {
                deleteDiscountDetail();
            } else if (e.getSource() == view.getDateSearch()) {
                searchDiscount();
            } else if (e.getSource() == view.getBtnAddDetail()) {
                addDiscountDetails();
            } else if (e.getSource() == view.getButtonClick()) {
                openSelectBookView();
            } else if (e.getSource() == view.getBtnDisableAll()) {
                refreshView();
            }
        } catch (IllegalArgumentException ex) {
            CommonView.showErrorMessage(view, ex.getMessage());
        } catch (Exception ex) {
            CommonView.showErrorMessage(view, "Lỗi hệ thống: " + ex.getMessage());
        }
    }

    //chuc nang them mot chuong trinh giam gia moi
    private void addDiscount() {
        Discount discount = getDataFormDiscount();
        if (!discountService.addDiscount(discount)) {
            throw new IllegalArgumentException("Không thể thêm chương trình. Mã chương trình có thể đã tồn tại.");
        }
        CommonView.showInfoMessage(view, "Thêm chương trình giảm giá thành công!");
        refreshView();
    }

    //chuc nang sua chuong trinh giam gia
    private void updateDiscount() {
        String discountID = getDiscountID();
        if (discountID.isEmpty()) {
            throw new IllegalArgumentException("Vui lòng chọn chương trình cần sửa!");
        }
        if (CommonView.confirmAction(view, "Bạn chắc chắn muốn sửa chương trình này?")) {
            Discount discount = getDataFormDiscount();
            if (!discountService.updateDiscount(discount)) {
                throw new IllegalArgumentException("Không thể cập nhật chương trình.");
            }
            CommonView.showInfoMessage(view, "Cập nhật chương trình thành công!");
            refreshView();
        }
    }

    //chuc nang xoa chuong trinh giam gia
    private void deleteDiscount() {
        String discountID = getDiscountID();
        if (discountID.isEmpty()) {
            throw new IllegalArgumentException("Vui lòng chọn chương trình cần xóa!");
        }
        if (CommonView.confirmAction(view, "Bạn có chắc chắn muốn xóa chương trình này?")) {
            if (!discountService.deleteCondition(discountID)) {
                throw new IllegalArgumentException("Không thể xóa chương trình.");
            }
            CommonView.showInfoMessage(view, "Xóa chương trình giảm giá thành công!");
            refreshView();
        }
    }

    //chuc nang tim kiem chuong trinh giam gia theo ngay
    private void searchDiscount() {
        java.util.Date dateStart = view.getDateStartSearch().getDate();
        java.util.Date dateEnd = view.getDateEndSearch().getDate();
        if (dateStart == null && dateEnd == null) {
            throw new IllegalArgumentException("Vui lòng chọn ít nhất một ngày để tìm kiếm!");
        }
        java.sql.Date dateSqlStart = dateStart != null ? new java.sql.Date(dateStart.getTime()) : null;
        java.sql.Date dateSqlEnd = dateEnd != null ? new java.sql.Date(dateEnd.getTime()) : null;
        if (dateSqlStart != null && dateSqlEnd != null && dateSqlStart.after(dateSqlEnd)) {
            throw new IllegalArgumentException("Ngày bắt đầu phải nhỏ hơn hoặc bằng ngày kết thúc!");
        }
        ArrayList<Discount> listResult = discountService.listDiscountsSearch(dateSqlStart, dateSqlEnd);
        updateAllTable(listResult);
        if (listResult.isEmpty()) {
            CommonView.showInfoMessage(view, "Không tìm thấy chương trình giảm giá!");
        }
    }

    //chuc nang them chi tiet giam gia
    private void addDiscountDetails() {
        ArrayList<DiscountDetails> listDiscountDetails = getDataFormDiscountDetail();
        if (CommonView.confirmAction(view, "Bạn có chắc chắn muốn thêm các chi tiết giảm giá này?")) {
            discountDetailService.insert(listDiscountDetails);
            CommonView.showInfoMessage(view, "Thêm chi tiết giảm giá thành công!");
            updateAllTableDetails(discountDetailService.getAllDiscountDetails());
            clearFormDiscountDetails();
        }
    }

    //chuc nang xoa chi tiet chuong trinh giam gia
    private void deleteDiscountDetail() {
        int selectedRow = view.getTableDetails().getSelectedRow();
        if (selectedRow < 0) {
            throw new IllegalArgumentException("Vui lòng chọn chi tiết giảm giá để xoá!!");
        }
        String discountID = (String) view.getTableDetails().getValueAt(selectedRow, 0);
        String bookID = (String) view.getTableDetails().getValueAt(selectedRow, 2);
        if (CommonView.confirmAction(view, "Bạn có chắc chắn muốn xóa chi tiết giảm giá này?")) {
            if (!discountDetailService.deleteDiscountDetails(discountID, bookID)) {
                throw new IllegalArgumentException("Không thể xoá chi tiết giảm giá!!");
            }
        }
        CommonView.showInfoMessage(view, "Xoá chi tiết giảm giá thành công!!");
        updateAllTableDetails(discountDetailService.getAllDiscountDetails());
    }

    //mo view chon sach
    private void openSelectBookView() {
        selectBookView = new SelectBookView();
        selectBookView.setVisible(true);
        listBookSelect = selectBookView.getListBook();
    }

    //khoi dong lai toan bo
    private void refreshView() {
        clearFormDiscount();
        clearFormDiscountDetails();
        updateAllTable(discountService.getAllDiscounts());
        updateAllTableDetails(discountDetailService.getAllDiscountDetails());
        setComboBox(discountService.listMapDiscount());
    }

    //lay du lieu tu form giam gia
    private Discount getDataFormDiscount() {
        String discountID = view.getTextFieldDiscountId().getText().trim();
        String discountName = view.getTextFieldProgramName().getText().trim();
        String discountType = view.getTextFieldProgramType().getText().trim();
        java.util.Date startDate = view.getDateChooserStart().getDate();
        java.util.Date endDate = view.getDateChooserEnd().getDate();
        validateDiscountInput(discountID, discountName, discountType, startDate, endDate);
        java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
        java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
        return new Discount(discountID, discountName, discountType, sqlStartDate, sqlEndDate);
    }

    //validate cho du lieu lay tu form giam gia
    private void validateDiscountInput(String discountID, String discountName, String discountType,
                                       java.util.Date startDate, java.util.Date endDate) {
        if (discountID.isEmpty() || discountName.isEmpty() || discountType.isEmpty()) {
            throw new IllegalArgumentException("Vui lòng điền đầy đủ thông tin chương trình giảm giá!");
        }
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Vui lòng chọn ngày bắt đầu và kết thúc!");
        }
        if (startDate.after(endDate)) {
            throw new IllegalArgumentException("Ngày bắt đầu phải nhỏ hơn hoặc bằng ngày kết thúc!");
        }
    }

    //lay du lieu tu form chi tiet giam gia
    private ArrayList<DiscountDetails> getDataFormDiscountDetail() {
        int index = view.getComboBoxNameDiscount().getSelectedIndex();
        if (index <= 0) {
            throw new IllegalArgumentException("Vui lòng chọn chương trình giảm giá!");
        }
        ArrayList<Discount> listDiscount = discountService.getAllDiscounts();
        if (index > listDiscount.size()) {
            throw new IllegalArgumentException("Chương trình giảm giá không hợp lệ!");
        }
        Discount discount = listDiscount.get(index - 1);
        if (listBookSelect == null || listBookSelect.isEmpty()) {
            throw new IllegalArgumentException("Vui lòng chọn ít nhất một cuốn sách!");
        }
        String percentText = view.getTextFieldDiscountPercent().getText().trim();
        if (percentText.isEmpty()) {
            throw new IllegalArgumentException("Vui lòng nhập phần trăm giảm giá!");
        }
        Double percentDouble = null;
        try {
            percentDouble = ValidateForm.isDouble(percentText, "Phần trăm giảm giá");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (percentDouble <= 0.0 || percentDouble > 100.0) {
            throw new IllegalArgumentException("Phần trăm giảm giá phải từ 0 đến 100!");
        }
        ArrayList<DiscountDetails> listDiscountDetails = new ArrayList<>();
        for (Books books : listBookSelect) {
            listDiscountDetails.add(new DiscountDetails(discount, percentDouble, books));
        }
        return listDiscountDetails;
    }

    private String getDiscountID() {
        return view.getTextFieldDiscountId().getText().trim();
    }

    //them su kien cho bang giam gia
    private void addListenerTableDiscount() {
        view.getTableTop().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectRow = view.getTableTop().getSelectedRow();
                if (selectRow >= 0) {
                    String discountID = (String) view.getTableTop().getValueAt(selectRow, 0);
                    String discountName = (String) view.getTableTop().getValueAt(selectRow, 1);
                    String discountType = (String) view.getTableTop().getValueAt(selectRow, 2);
                    java.util.Date startDate = (java.util.Date) view.getTableTop().getValueAt(selectRow, 3);
                    java.util.Date endDate = (java.util.Date) view.getTableTop().getValueAt(selectRow, 4);
                    view.getTextFieldDiscountId().setText(discountID);
                    view.getTextFieldProgramName().setText(discountName);
                    view.getTextFieldProgramType().setText(discountType);
                    view.getDateChooserStart().setDate(startDate);
                    view.getDateChooserEnd().setDate(endDate);
                }
            }
        });
    }

    //cap nhat mot dong cho bang giam gia
    public void updateTableDiscount(Discount discount) {
        Object[] row = {
                discount.getDiscountID(),
                discount.getNameDiscount(),
                discount.getTypeDiscount(),
                discount.getStartDate(),
                discount.getEndDate()
        };
        view.getTableModelTop().addRow(row);
    }

    //cap nhap toan bo bang giam gia
    public void updateAllTable(ArrayList<Discount> listDiscount) {
        view.getTableModelTop().setRowCount(0);
        for (Discount discount : listDiscount) {
            Object[] row = {
                    discount.getDiscountID(),
                    discount.getNameDiscount(),
                    discount.getTypeDiscount(),
                    discount.getStartDate(),
                    discount.getEndDate()
            };
            view.getTableModelTop().addRow(row);
        }
    }

    //cap nhat toan bo bang chi tiet giam gia
    public void updateAllTableDetails(ArrayList<DiscountDetails> listDiscountDetails) {
        view.getTableModelDetails().setRowCount(0);
        for (DiscountDetails detail : listDiscountDetails) {
            Object[] row = {
                    detail.getDiscount().getDiscountID(),
                    detail.getDiscount().getNameDiscount(),
                    detail.getBook().getBookID(),
                    detail.getBook().getBookName(),
                    detail.getBook().getCategory().getCategoryName(),
                    detail.getPercent()
            };
            view.getTableModelDetails().addRow(row);
        }
    }

    //tu dong hien cac chuong trinh giam gia co san trong phan chi tiet ma giam gia
    private void setComboBox(LinkedHashMap<String, String> mapDiscount) {
        view.getComboBoxNameDiscount().removeAllItems();
        view.getComboBoxNameDiscount().addItem("Chọn chương trình");
        for (Map.Entry<String, String> entry : mapDiscount.entrySet()) {
            view.getComboBoxNameDiscount().addItem(entry.getValue());
        }
    }

    //xoa toan bo form nhap giam gia
    private void clearFormDiscount() {
        view.getTextFieldDiscountId().setText("");
        view.getTextFieldProgramName().setText("");
        view.getTextFieldProgramType().setText("");
        view.getDateChooserStart().setDate(null);
        view.getDateChooserEnd().setDate(null);
    }

    //xoa toan bo form chi tiet giam gia
    private void clearFormDiscountDetails() {
        view.getTextFieldDiscountPercent().setText("");
        view.getComboBoxNameDiscount().setSelectedIndex(0);
        listBookSelect.clear();
    }
}