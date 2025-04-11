package controller;

import model.Employees;
import service.EmployeeService;
import utils.DatePickerUtil;
import view.EmployeeView;

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

public class EmployeeController implements ActionListener {
    private EmployeeView employeeView;
    private EmployeeService employeeService;

    private final SimpleDateFormat displayDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private final SimpleDateFormat parseDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    // --- Định nghĩa các biểu thức chính quy (Regex) cho validation ---
    // Regex cho SĐT Việt Nam (10 hoặc 11 số, bắt đầu bằng 0)
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^0\\d{9,10}$");
    // Regex cho Email (một phiên bản tương đối phổ biến)
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
    );


    public EmployeeController(EmployeeView employeeView) {
        this.employeeView = employeeView;
        this.employeeService = new EmployeeService();
        this.parseDateFormat.setLenient(false);

        updateEmployeeTable();
        addTableSelectionListener();
        initializeButtonStates();
    }


    public void updateEmployeeTable(){
        ArrayList<Employees>  employees = employeeService.getAllEmployees();
        if(employeeView == null || employeeView.getTableModel() ==null ){
            System.err.println("Lỗi: EmployeeView hoặc TableModel chưa được khởi tạo.");
            return;
        }
        DefaultTableModel tableModel = employeeView.getTableModel();
        tableModel.setRowCount(0);
        for(Employees employee : employees){
            Date dob = employee.getDateOfBirth();
            String dobStr = (dob != null) ? displayDateFormat.format(dob) : "";
            tableModel.addRow(new Object[]{
                    employee.getEmployeeID(),
                    employee.getLastName(),
                    employee.getFirstName(),
                    employee.getPosition(),
                    employee.getEmail(),
                    employee.getPhoneNumber(),
                    employee.getSalary(),
                    employee.getGender(),
                    dobStr
            });
        }
    }

    /**
     * Đăng ký một ListSelectionListener cho bảng khách hàng trong View.
     * Listener này sẽ được kích hoạt khi người dùng chọn hoặc bỏ chọn một hàng.
     * Nó cập nhật các trường nhập liệu với dữ liệu từ hàng được chọn và quản lý trạng thái các nút.
     */

    public void addTableSelectionListener(){
        // Lấy SelectionModel của bảng (quản lý việc chọn hàng) và thêm listener
        employeeView.getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // e.getValueIsAdjusting() trả về true khi người dùng đang trong quá trình chọn (ví dụ: giữ chuột kéo)
                // Chỉ xử lý khi việc lựa chọn đã kết thúc (false) để tránh xử lý nhiều lần không cần thiết.
                if(e.getValueIsAdjusting()){
                    int selectedRow = employeeView.getTable().getSelectedRow();
                    if(selectedRow != -1){
                        DefaultTableModel model =  employeeView.getTableModel();

                        String employeeID = model.getValueAt(selectedRow, 0).toString();
                        String lastName = model.getValueAt(selectedRow, 1).toString();
                        String firstName = model.getValueAt(selectedRow, 2).toString();
                        String position = model.getValueAt(selectedRow, 3).toString();
                        String email = model.getValueAt(selectedRow, 4).toString();
                        String phoneNumber = model.getValueAt(selectedRow, 5).toString();
                        String salary = model.getValueAt(selectedRow, 6).toString();
                        String gender = model.getValueAt(selectedRow, 7).toString();
                        String dateOfBirth = model.getValueAt(selectedRow, 8).toString();


                        //Điền dữ liệu
                        employeeView.getTextFieldEmployeeId().setText(employeeID);
                        employeeView.getTextFieldLastName().setText(lastName);
                        employeeView.getTextFieldFirstName().setText(firstName);
                        employeeView.getTextFieldPosition().setText(position);
                        employeeView.getTextFieldEmail().setText(email);
                        employeeView.getTextFieldPhone().setText(phoneNumber);
                        employeeView.getTextFieldSalary().setText(salary);
                        employeeView.getTextFieldDob().setText(dateOfBirth);


                        //Xử lý giới tính
                        if(gender.equalsIgnoreCase("Nam")){
                            employeeView.getRadioPositionNam().setSelected(true);
                        }else if(gender.equalsIgnoreCase("Nữ")){
                            employeeView.getRadioPositionNu().setSelected(true);
                        }

                        // Cập nhật trạng thái nút
                        employeeView.getBtnAdd().setEnabled(false);
                        employeeView.getTextFieldEmployeeId().setEditable(false);
                        employeeView.getBtnDelete().setEnabled(true);
                        employeeView.getBtnUpdate().setEnabled(true);
                    }else{
                        resetInputFieldArea();
                    }
                }
            }
        });
    }

    // Đặt trạng thái ban đầu cho các nút Sửa/Xóa
    private void initializeButtonStates() {
        employeeView.getBtnUpdate().setEnabled(false);
        employeeView.getBtnDelete().setEnabled(false);
        employeeView.getBtnAdd().setEnabled(true);
        employeeView.getTextFieldEmployeeId().setEditable(true);
    }

    //Hàm reset trường nhap dữ liệu
    private void resetInputFieldArea(){
        employeeView.getTextFieldEmployeeId().setText("");
        employeeView.getTextFieldLastName().setText("");
        employeeView.getTextFieldFirstName().setText("");
        employeeView.getTextFieldPosition().setText("");
        employeeView.getTextFieldEmail().setText("");
        employeeView.getTextFieldPhone().setText("");
        employeeView.getTextFieldSalary().setText("");
        employeeView.getTextFieldDob().setText("");

        //cập nhật các nút
        employeeView.getBtnAdd().setEnabled(true);
        employeeView.getTextFieldEmployeeId().setEditable(true);
        employeeView.getBtnDelete().setEnabled(false);
        employeeView.getBtnUpdate().setEnabled(false);


        //xóa lựa chọn trên bảng
        employeeView.getTable().clearSelection();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source == employeeView.getBtnAdd()){
            handleAddEmployee();
        }else if(source == employeeView.getBtnUpdate()){
            handleUpdateEmployee();
        }else if(source == employeeView.getBtnDelete()){
            handleDeleteEmployee();
        }else if(source == employeeView.getBtnReset()){
            handleReset();
        }else if (source == employeeView.getBtnSearchAll()){
            handleSearchEmployee();
        }else if(source == employeeView.getCalendarButton()){
            showCalender();
        }
    }

    private void handleAddEmployee(){
        try{
            employeeView.getTextFieldEmployeeId().setEditable(true);

            Employees employee = createEmployeeFromInput();


            if(employeeService.insertEmployee(employee)){
                updateEmployeeTable();
                employeeView.showMessage("Thêm nhân viên thành công !!");
                resetInputFieldArea();
            }else {
                employeeView.showMessage("Thêm nhân viên thấ bại (mã NV có thể bị trùng)");
                employeeView.getTextFieldEmployeeId().setEditable(true);
            }
        }  catch (IllegalArgumentException ex) {
            // Bắt lỗi validate (IllegalArgumentException) hoặc lỗi parse ngày (ParseException)
            employeeView.showMessage("Lỗi nhập liệu: " + ex.getMessage());
        } catch (Exception ex) {
            employeeView.showMessage("Lỗi không xác định khi thêm: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void handleUpdateEmployee(){
        // Lấy chỉ số hàng đang được chọn
        int selectedRow = employeeView.getTable().getSelectedRow();
        if(selectedRow < -1){
            employeeView.showMessage("Vui lòng chọn nhân viên cần sửa trên bảng.");
            return;
        }
        try{
            DefaultTableModel model =  employeeView.getTableModel();
            //Laasy ID
            String employeeIdToUpdate = model.getValueAt(selectedRow, 0).toString();

            // Bước 1 lấy validate dữ liệu mới
            Employees updateEmployee = createEmployeeFromInput();
            updateEmployee.setEmployeeID(employeeIdToUpdate);

            // Bước 2 gọi service để cập nhật
            if (employeeService.updateEmployee(updateEmployee)){
                // Nếu cập nhật thành công:
                // Lấy chỉ số hàng trong model (có thể khác chỉ số view nếu có sắp xếp/lọc)
                int modelRow = employeeView.getTable().convertRowIndexToModel(selectedRow);
                updateEmployeeTable();
                if(modelRow < employeeView.getTable().getRowCount()){
                    employeeView.getTable().getSelectionModel().setSelectionInterval(modelRow, modelRow);
                }

                employeeView.showMessage("Cập nhật nhân viên thành công!");

                employeeView.getBtnAdd().setEnabled(false);
                employeeView.getTextFieldEmployeeId().setEditable(false);
                employeeView.getBtnDelete().setEnabled(true);
                employeeView.getBtnUpdate().setEnabled(true);

            }else {
                employeeView.showMessage("Cập nhật nhân viên thất bại!");
            }
        }catch (IllegalArgumentException | ParseException ex) {
            employeeView.showMessage("Lỗi nhập liệu: " + ex.getMessage());
        } catch (Exception ex) {
            employeeView.showMessage("Lỗi không xác định khi cập nhật: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void handleDeleteEmployee(){
        int selectedRow = employeeView.getTable().getSelectedRow();
        if (selectedRow < 0){
            employeeView.showMessage("Vui lòng chọn nhân viên cần xóa trên bảng!!");
            return  ;
        }
        String employeeIdToDelete = employeeView.getTable().getValueAt(selectedRow, 0).toString();
        String lastName = employeeView.getTable().getValueAt(selectedRow, 1).toString();
        String firstName = employeeView.getTable().getValueAt(selectedRow, 2).toString();
        String employeeName = lastName + " " + firstName;

        int confirm = JOptionPane.showConfirmDialog(employeeView,
                "Xác nhaận xóa nhân viên :\nID: " + employeeIdToDelete + "\nTên: " + employeeName,
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if(confirm == JOptionPane.YES_OPTION){
            if(employeeService.deleteEmployee(employeeIdToDelete)){
                updateEmployeeTable();
                employeeView.showMessage("Xóa nhân viên thành công!");
                resetInputFieldArea();
            }else{
                employeeView.showMessage("Xóa nhân viên thất bại !");
            }
        }

    }

    private void handleReset(){
        resetInputFieldArea();

        //reset trường tìm kiếm
        employeeView.getComboBoxSearch().setSelectedIndex(0);
        employeeView.getTextFieldSearch().setText("");
        employeeView.getTextFieldSearchSalaryFrom().setText("");
        employeeView.getTextFieldSearchSalaryTo().setText("");

        updateEmployeeTable();
    }

    private void handleSearchEmployee(){
        String searchField = "";
        Object selectedItem = employeeView.getComboBoxSearch().getSelectedItem();
        if (selectedItem == null) {
            employeeView.showMessage("Vui lòng chọn tiêu chí tìm kiếm.");
            return;
        }

        String searchCriteria = selectedItem.toString();
        String searchValue = employeeView.getTextFieldSearch().getText();
        String salaryFrom=employeeView.getTextFieldSearchSalaryFrom().getText();
        String salaryTo=employeeView.getTextFieldSearchSalaryTo().getText();


        switch (searchCriteria){
            //"Mã NV", "Họ", "Tên" , "Chức vụ", "Email", "SĐT", "Lương", "Phái", "Ngày sinh"
            case "Mã NV": searchField = "customerID"; break;
            case "Họ": searchField = "lastName"; break;
            case "Tên": searchField = "firstName"; break;
            case "SĐT": searchField = "phoneNumber"; break;
            case "Chức vụ": searchField = "position"; break;
        }

        if (searchValue.trim().isEmpty() ) {
            employeeView.showMessage("Vui lòng nhập thông tin để tìm kiếm.");
            return;
        }

        ArrayList<Employees> employees = employeeService.searchEmployee(searchField,searchValue,salaryFrom,salaryTo);

        DefaultTableModel model =  employeeView.getTableModel();
        model.setRowCount(0);
        if(employees.size() > 0){
            for(Employees employee : employees){
                Date dob = employee.getDateOfBirth();
                String dobStr = (dob !=null) ? displayDateFormat.format(dob) : "";

                model.addRow(new Object[]{employee.getEmployeeID(),
                    employee.getFirstName(),
                    employee.getLastName(),
                    employee.getPosition(),
                        employee.getEmail(),
                        employee.getPhoneNumber(),
                        employee.getSalary(),
                        employee.getGender(),
                        dobStr
                });
            }
        }
        employeeView.showMessage("Tìm thấy " + (employees != null ? employees.size() : 0) + " nhân viên.");
        resetInputFieldArea(); // Reset input sau khi tìm kiếm
        // Clear nút radio tìm kiếm
        // if(customerView.getGroupSearch() != null) customerView.getGroupSearch().clearSelection();
    }

    private void showCalender(){
        Date selectedDate = DatePickerUtil.showDatePickerDialog(
                employeeView,
                "Chọn ngày sinh",
                employeeView.getTextFieldDob().getText(),
                parseDateFormat,
                "dd/MM/yyyy"
        );

        if(selectedDate != null){
            employeeView.getTextFieldDob().setText(displayDateFormat.format(selectedDate));
        }else {
            employeeView.getTextFieldDob().setText("");
        }
    }


    //Lấy dữ liệu từ input và xử lý validate
    private Employees createEmployeeFromInput() throws ParseException {
        String employeeID = employeeView.getTextFieldEmployeeId().getText().trim();
        String lastName = employeeView.getTextFieldLastName().getText().trim();
        String firstName = employeeView.getTextFieldFirstName().getText().trim();
        String position = employeeView.getTextFieldPosition().getText().trim();
        String email = employeeView.getTextFieldEmail().getText().trim();
        String phoneNumber = employeeView.getTextFieldPhone().getText().trim();
        double salary = Double.parseDouble(employeeView.getTextFieldSalary().getText().trim());
        String dobStr = employeeView.getTextFieldDob().getText().trim();

        String gender = null;
        if(employeeView.getRadioPositionNam().isSelected()){
            gender = "Nam";
        }else if(employeeView.getRadioPositionNu().isSelected()){
            gender="Nữ";
        }

        if(employeeID.isEmpty()) throw new IllegalArgumentException("Mã khách hàng không được để trống.");
        if (firstName.isEmpty()) throw new IllegalArgumentException("Tên không được để trống.");
        if (phoneNumber.isEmpty()) throw new IllegalArgumentException("Số điện thoại không được để trống.");

        // 2. Validate định dạng Số điện thoại (dùng Regex)
        Matcher phoneMatcher = PHONE_NUMBER_PATTERN.matcher(phoneNumber);
        if (!phoneMatcher.matches()) {
            throw new IllegalArgumentException("Số điện thoại không hợp lệ (cần 10-11 số, bắt đầu bằng 0).");
        }

        // 3. Validate định dạng Email (nếu người dùng có nhập)
        if (!email.isEmpty()) {
            Matcher emailMatcher = EMAIL_PATTERN.matcher(email);
            if (!emailMatcher.matches()) {
                throw new IllegalArgumentException("Địa chỉ email không hợp lệ.");
            }
        }

        // 4. Validate và Parse Ngày sinh
        Date dateOfBirth = null;
        if (!dobStr.isEmpty()) {
            try {
                // parseDateFormat đã setLenient(false) nên sẽ báo lỗi nếu ngày không tồn tại
                dateOfBirth = parseDateFormat.parse(dobStr);

                // 5. Validate logic: Ngày sinh không được là ngày trong tương lai
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
                // Ném lại lỗi ParseException với thông điệp rõ ràng hơn
                throw new ParseException("Định dạng ngày sinh không hợp lệ (cần dd/MM/yyyy)", e.getErrorOffset());
            }
        }

        return new Employees(firstName,lastName,dateOfBirth,phoneNumber,email,gender,employeeID,position,salary);


    }
}
