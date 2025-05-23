package controller;

import model.Customers;
import service.CustomerService;
import utils.DatePickerUtil;
import view.CustomerView;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerController implements ActionListener {
    private CustomerView customerView;
    private CustomerService customerService;
    private final SimpleDateFormat displayDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private final SimpleDateFormat parseDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^0\\d{9,10}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
    );


    public CustomerController(CustomerView customerView) {
        this.customerView = customerView;
        this.customerService = new CustomerService();
        this.parseDateFormat.setLenient(false);

        updateCustomerTable();
        updateTotalCustomers();
        addTableSelectionListener();
        initializeButtonStates();
    }

    private void initializeButtonStates() {
        customerView.getBtnUpdate().setEnabled(false);
        customerView.getBtnDelete().setEnabled(false);
        customerView.getBtnAdd().setEnabled(true);
        customerView.getTextFieldCustomerId().setEditable(true);
    }

    public void addTableSelectionListener() {
        customerView.getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectRow = customerView.getTable().getSelectedRow();
                if (!e.getValueIsAdjusting() && selectRow != -1 ) {

                    if (selectRow >= 0) {
                        DefaultTableModel model = customerView.getTableModel();
                        // Lấy dữ liệu từ model, xử lý null an toàn
                        String customerID = getStringValueFromModel(model, selectRow, 0);
                        String lastName = getStringValueFromModel(model, selectRow, 1);
                        String firstName = getStringValueFromModel(model, selectRow, 2);
                        String gender = getStringValueFromModel(model, selectRow, 3);
                        String phone = getStringValueFromModel(model, selectRow, 4);
                        String email = getStringValueFromModel(model, selectRow, 5);
                        String dobStr = getStringValueFromModel(model, selectRow, 6);
                        String note = "";
                        if (model.getColumnCount() > 9) {
                            note = getStringValueFromModel(model, selectRow, 9);
                        }

                        // --- Điền dữ liệu ---
                        customerView.getTextFieldCustomerId().setText(customerID);
                        customerView.getTextFieldLastName().setText(lastName);
                        customerView.getTextFieldFirstName().setText(firstName);
                        customerView.getTextFieldPhone().setText(phone);
                        customerView.getTextFieldEmail().setText(email);
                        customerView.getTextFieldDob().setText(dobStr);
                        customerView.getTextFieldNote().setText(note); // <-- Hiển thị note

                        if ("Nam".equalsIgnoreCase(gender)) {
                            customerView.getRadioPositionNam().setSelected(true);
                        } else if ("Nữ".equalsIgnoreCase(gender)) {
                            customerView.getRadioPositionNu().setSelected(true);
                        } else {
                            // Clear selection nếu giới tính không xác định
                            customerView.getRadioPositionNam().setSelected(false);
                            customerView.getRadioPositionNu().setSelected(false);
                            // Nếu có getter cho ButtonGroup: customerView.getPositionGroup().clearSelection();
                        }

                        // --- Cập nhật trạng thái nút ---
                        customerView.getBtnAdd().setEnabled(false);
                        customerView.getTextFieldCustomerId().setEditable(false); // Không cho sửa ID
                        customerView.getBtnUpdate().setEnabled(true);
                        customerView.getBtnDelete().setEnabled(true);
                    } else {
                        // Nếu không có hàng nào được chọn
                        resetInputFieldsState();
                    }
                }
            }
        });
    }

    // Hàm tiện ích lấy String từ model, xử lý null
    private String getStringValueFromModel(DefaultTableModel model, int row, int col) {
        Object value = model.getValueAt(row, col);
        return (value == null) ? "" : value.toString();
    }

    private void resetInputFieldsState() {
        customerView.clear();
        customerView.getBtnAdd().setEnabled(true);
        customerView.getTextFieldCustomerId().setEditable(true);
        customerView.getBtnUpdate().setEnabled(false);
        customerView.getBtnDelete().setEnabled(false);
        // Xóa lựa chọn trên bảng
        customerView.getTable().clearSelection();
    }


    public void updateCustomerTable() {
        ArrayList<Customers> customers = customerService.getAllCustomers();
        if (customerView == null || customerView.getTableModel() == null) {
            System.err.println("Lỗi: CustomerView hoặc TableModel chưa được khởi tạo.");
            return;
        }
        DefaultTableModel model = customerView.getTableModel();
        model.setRowCount(0);

        if (customers != null) {
            for (Customers customer : customers) {
                Date dob = customer.getDateOfBirth();
                Date creation = customer.getCreationDate();
                String dobStr = (dob != null) ? displayDateFormat.format(dob) : "";
                String creationStr = (creation != null) ? displayDateFormat.format(creation) : "";

                model.addRow(new Object[]{
                        customer.getCustomerID(),
                        customer.getLastName(),
                        customer.getFirstName(),
                        customer.getGender(),
                        customer.getPhoneNumber(),
                        customer.getEmail(),
                        dobStr,
                        customer.getTotalMoney(),
                        creationStr,
                        customer.getNote()
                });
            }
        }
    }

    public void updateTotalCustomers() {
        int total = (customerView != null && customerView.getTableModel() != null)
                ? customerView.getTableModel().getRowCount() : 0;
        if (customerView != null && customerView.getTextFieldTotalCustomers() != null) {
            customerView.getTextFieldTotalCustomers().setText(String.valueOf(total));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == customerView.getBtnAdd()) {
            handleAddCustomer();
        } else if (source == customerView.getBtnUpdate()) {
            handleUpdateCustomer();
        } else if (source == customerView.getBtnDelete()) {
            handleDeleteCustomer();
        } else if (source == customerView.getBtnSearch()) {
            handleSearchCustomer();
        } else if (source == customerView.getBtnReset()) {
            handleReset();
        } else if (source == customerView.getCalendarButton()) {
            showCalendar();
        }
        // Cập nhật tổng số sau các hành động thay đổi dữ liệu hoặc reset/search
        if (source == customerView.getBtnAdd() || source == customerView.getBtnUpdate() ||
                source == customerView.getBtnDelete() || source == customerView.getBtnSearch() ||
                source == customerView.getBtnReset()) {
            updateTotalCustomers();
        }
    }

    private void handleAddCustomer() {
        try {
            customerView.getTextFieldCustomerId().setEditable(true);
            //  Lấy và validate dữ liệu
            Customers customer = createCustomerFromInput();
            //  Gọi Service để thêm
            if (customerService.insertCustomer(customer)) {
                updateCustomerTable();
                customerView.showMessage("Thêm khách hàng thành công!");
                resetInputFieldsState();
            } else {
                customerView.showMessage("Thêm khách hàng thất bại! (Mã KH có thể đã tồn tại)");
                customerView.getTextFieldCustomerId().setEditable(true); // Giữ editable nếu thất bại do trùng mã
            }
            //  Bắt và hiển thị lỗi validation hoặc lỗi khác
        } catch (IllegalArgumentException | ParseException ex) {
            customerView.showMessage("Lỗi nhập liệu: " + ex.getMessage());
        } catch (Exception ex) {
            customerView.showMessage("Lỗi không xác định khi thêm: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void handleUpdateCustomer() {
        int selectedRow = customerView.getTable().getSelectedRow();
        if (selectedRow < 0) {
            customerView.showMessage("Vui lòng chọn khách hàng cần sửa trên bảng.");
            return;
        }
        try {
            String customerIdToUpdate = getStringValueFromModel(customerView.getTableModel(), selectedRow, 0);

            //  Lấy và validate dữ liệu mới
            Customers updatedCustomer = createCustomerFromInput();
            updatedCustomer.setCustomerID(customerIdToUpdate);

            // Lấy thông tin cũ để giữ lại ngày tạo, tổng tiền
            Customers existingCustomer = customerService.getCustomerById(customerIdToUpdate);
            if (existingCustomer != null) {
                updatedCustomer.setCreationDate(existingCustomer.getCreationDate());
                updatedCustomer.setTotalMoney(existingCustomer.getTotalMoney());
            } else {
                customerView.showMessage("Lỗi: Không tìm thấy thông tin khách hàng gốc.");
                return;
            }

            // Gọi Service để cập nhật
            if (customerService.updateCustomer(updatedCustomer)) {
                int modelRow = customerView.getTable().convertRowIndexToModel(selectedRow);
                updateCustomerTable();
                if (modelRow < customerView.getTable().getRowCount()) {
                    customerView.getTable().setRowSelectionInterval(modelRow, modelRow);
                }
                customerView.showMessage("Cập nhật khách hàng thành công!");
                // Giữ trạng thái nút
                customerView.getBtnAdd().setEnabled(false);
                customerView.getTextFieldCustomerId().setEditable(false);
                customerView.getBtnUpdate().setEnabled(true);
                customerView.getBtnDelete().setEnabled(true);
            } else {
                customerView.showMessage("Cập nhật khách hàng thất bại!");
            }
            //  Bắt và hiển thị lỗi validation hoặc lỗi khác
        } catch (IllegalArgumentException | ParseException ex) {
            customerView.showMessage("Lỗi nhập liệu: " + ex.getMessage());
        } catch (Exception ex) {
            customerView.showMessage("Lỗi không xác định khi cập nhật: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void handleDeleteCustomer() {
        int selectedRow = customerView.getTable().getSelectedRow();
        if (selectedRow < 0) {
            customerView.showMessage("Vui lòng chọn khách hàng cần xóa trên bảng.");
            return;
        }

        String customerIdToDelete = getStringValueFromModel(customerView.getTableModel(), selectedRow, 0);
        String lastName = getStringValueFromModel(customerView.getTableModel(), selectedRow, 1);
        String firstName = getStringValueFromModel(customerView.getTableModel(), selectedRow, 2);
        String customerName = lastName + " " + firstName;

        int confirm = JOptionPane.showConfirmDialog(customerView,
                "Xác nhận xóa khách hàng:\nID: " + customerIdToDelete + "\nTên: " + customerName,
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            if (customerService.deleteCustomer(customerIdToDelete)) {
                updateCustomerTable();
                customerView.showMessage("Xóa khách hàng thành công!");
                resetInputFieldsState();
            } else {
                customerView.showMessage("Xóa khách hàng thất bại!");
            }
        }
    }


    private void handleSearchCustomer() {
        String searchField = "";
        Object selectedItem = customerView.getCheckcombobox().getSelectedItem();
        if (selectedItem == null) {
            customerView.showMessage("Vui lòng chọn tiêu chí tìm kiếm.");
            return;
        }
        String searchCriteria = selectedItem.toString();
        String searchValue = customerView.getTextFieldSearch().getText();
        String gender = "";

        if (customerView.getRadioNamSearch().isSelected()) {
            gender = "Nam";
        } else if (customerView.getRadioNuSearch().isSelected()) {
            gender = "Nữ";
        }

        switch (searchCriteria) {
            case "Mã KH": searchField = "customerID"; break;
            case "Họ KH": searchField = "lastName"; break;
            case "Tên KH": searchField = "firstName"; break;
            case "Số ĐT": searchField = "phoneNumber"; break;
            case "Email": searchField = "email"; break;
            default:
                customerView.showMessage("Tiêu chí tìm kiếm không hợp lệ.");
                return;
        }

        if (searchValue.trim().isEmpty() && gender.isEmpty()) {
            customerView.showMessage("Vui lòng nhập thông tin hoặc chọn giới tính để tìm kiếm.");
            return;
        }

        ArrayList<Customers> customers = customerService.searchCustomers(searchField, searchValue, gender);

        // Cập nhật bảng
        DefaultTableModel model = customerView.getTableModel();
        model.setRowCount(0);
        if (customers != null) {
            for (Customers customer : customers) {
                Date dob = customer.getDateOfBirth();
                Date creation = customer.getCreationDate();
                String dobStr = (dob != null) ? displayDateFormat.format(dob) : "";
                String creationStr = (creation != null) ? displayDateFormat.format(creation) : "";

                model.addRow(new Object[]{
                        customer.getCustomerID(), customer.getLastName(), customer.getFirstName(),
                        customer.getGender(), customer.getPhoneNumber(), customer.getEmail(),
                        dobStr, customer.getTotalMoney(), creationStr,
                        customer.getNote() // <-- Thêm note
                });
            }
        }
        customerView.showMessage("Tìm thấy " + (customers != null ? customers.size() : 0) + " khách hàng.");
        resetInputFieldsState();
        customerView.getRadioNamSearch().setSelected(false);
        customerView.getRadioNuSearch().setSelected(false);
    }

    private void handleReset() {
        resetInputFieldsState(); // Reset input fields và nút

        // Reset search fields
        customerView.getCheckcombobox().setSelectedIndex(0);
        customerView.getTextFieldSearch().setText("");
        customerView.getRadioNamSearch().setSelected(false);
        customerView.getRadioNuSearch().setSelected(false);

        // Tải lại toàn bộ dữ liệu
        updateCustomerTable();
    }

    private void showCalendar() {
        // Gọi tiện ích chọn ngày
        Date selectedDate = DatePickerUtil.showDatePickerDialog(
                customerView,
                "Chọn ngày sinh",
                customerView.getTextFieldDob().getText(),
                parseDateFormat,
                "dd/MM/yyyy"
        );

        if (selectedDate != null) {

            customerView.getTextFieldDob().setText(displayDateFormat.format(selectedDate));
        } else {
            System.out.println("Người dùng đã hủy chọn ngày hoặc xóa ngày.");
        }
    } // Đóng phương thức showCalendar


    public Customers createCustomerFromInput() throws ParseException, IllegalArgumentException {
        String customerId = customerView.getTextFieldCustomerId().getText().trim();
        String firstName = customerView.getTextFieldFirstName().getText().trim();
        String lastName = customerView.getTextFieldLastName().getText().trim();
        String phoneNumber = customerView.getTextFieldPhone().getText().trim();
        String email = customerView.getTextFieldEmail().getText().trim();
        String dobStr = customerView.getTextFieldDob().getText().trim();
        String note = customerView.getTextFieldNote().getText().trim();
        String gender = null;
        if (customerView.getRadioPositionNam().isSelected()) gender = "Nam";
        else if (customerView.getRadioPositionNu().isSelected()) gender = "Nữ";

        // --- Thực hiện Validation ---

        //  Kiểm tra các trường bắt buộc
        if (customerId.isEmpty()) throw new IllegalArgumentException("Mã khách hàng không được để trống.");
        if (lastName.isEmpty()) throw new IllegalArgumentException("Họ không được để trống.");
        if (firstName.isEmpty()) throw new IllegalArgumentException("Tên không được để trống.");
        if (gender == null) throw new IllegalArgumentException("Vui lòng chọn giới tính.");
        if (phoneNumber.isEmpty()) throw new IllegalArgumentException("Số điện thoại không được để trống.");
        // Email và Ngày sinh có thể không bắt buộc trống, tùy yêu cầu

        //  Validate định dạng Số điện thoại (dùng Regex)
        Matcher phoneMatcher = PHONE_NUMBER_PATTERN.matcher(phoneNumber);
        if (!phoneMatcher.matches()) {
            throw new IllegalArgumentException("Số điện thoại không hợp lệ (cần 10-11 số, bắt đầu bằng 0).");
        }

        //  Validate định dạng Email (nếu người dùng có nhập)
        if (!email.isEmpty()) {
            Matcher emailMatcher = EMAIL_PATTERN.matcher(email);
            if (!emailMatcher.matches()) {
                throw new IllegalArgumentException("Địa chỉ email không hợp lệ.");
            }
        }

        //  Validate và Parse Ngày sinh
        Date dateOfBirth = null;
        if (!dobStr.isEmpty()) {
            try {
                // parseDateFormat đã setLenient(false) nên sẽ báo lỗi nếu ngày không tồn tại
                dateOfBirth = parseDateFormat.parse(dobStr);

                //  Validate logic: Ngày sinh không được là ngày trong tương lai
                // Tạo ngày hiện tại (chỉ lấy phần ngày, bỏ qua giờ phút giây để so sánh chính xác)
                Calendar calToday = Calendar.getInstance();
                calToday.set(Calendar.HOUR_OF_DAY, 0);
                calToday.set(Calendar.MINUTE, 0);
                calToday.set(Calendar.SECOND, 0);
                calToday.set(Calendar.MILLISECOND, 0);
                Date today = calToday.getTime();

                // So sánh ngày sinh với ngày hôm nay
                if (dateOfBirth.after(today)) {
                    throw new IllegalArgumentException("Ngày sinh không được là một ngày trong tương lai.");
                }

            } catch (ParseException e) {
                throw new ParseException("Định dạng ngày sinh không hợp lệ (cần dd/MM/yyyy)", e.getErrorOffset());
            }
        }

        // Validate độ dài Note (ví dụ, nếu cần giới hạn)

        int maxNoteLength = 200;
        if (note.length() > maxNoteLength) {
            throw new IllegalArgumentException("Ghi chú không được vượt quá " + maxNoteLength + " ký tự.");
        }

        Date creationDate = new Date();
        double totalMoney = 0.0;
        return new Customers(firstName, lastName, dateOfBirth, phoneNumber, email, gender, customerId, totalMoney, creationDate, note);

    }
}