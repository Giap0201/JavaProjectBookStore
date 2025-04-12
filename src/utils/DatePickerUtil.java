package utils; // Hoặc package bạn muốn đặt tiện ích

import com.toedter.calendar.JDateChooser; // Import JDateChooser

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Lớp tiện ích để hiển thị hộp thoại chọn ngày sử dụng JDateChooser.
 */
public class DatePickerUtil {

    /**
     * Hiển thị hộp thoại chọn ngày và trả về ngày được chọn.
     */
    public static Date showDatePickerDialog(Component parent, String title, String initialDateString, SimpleDateFormat parseFormat, String displayFormatStr) {
        // Tạo một đối tượng JDateChooser
        JDateChooser dateChooser = new JDateChooser();

        // Thiết lập định dạng hiển thị ngày tháng *trong* JDateChooser
        // Quan trọng: định dạng này quyết định cách ngày hiển thị cho người dùng chọn
        if (displayFormatStr != null && !displayFormatStr.isEmpty()) {
            dateChooser.setDateFormatString(displayFormatStr);
        } else {
            dateChooser.setDateFormatString("dd/MM/yyyy"); // Mặc định nếu không cung cấp
        }

        // Cố gắng đặt ngày ban đầu nếu có và hợp lệ
        if (initialDateString != null && !initialDateString.trim().isEmpty() && parseFormat != null) {
            try {
                // Parse chuỗi ngày ban đầu sử dụng định dạng parseFormat (đã setLenient(false) ở controller)
                Date initialDate = parseFormat.parse(initialDateString.trim());
                // Đặt ngày ban đầu cho JDateChooser
                dateChooser.setDate(initialDate);
            } catch (ParseException ex) {
                // Nếu chuỗi ngày ban đầu không hợp lệ, bỏ qua và không đặt ngày ban đầu
                System.err.println("DatePickerUtil: Lỗi parse ngày ban đầu '" + initialDateString + "': " + ex.getMessage());
                // Không cần thông báo cho người dùng ở đây, chỉ cần không set ngày
            }
        }

        // Tạo một thông điệp tùy chỉnh chứa JDateChooser
        // (Có thể thêm Label nếu muốn: JPanel panel = new JPanel(); panel.add(new JLabel("Chọn ngày:")); panel.add(dateChooser);)
        Object[] message = {dateChooser};

        // Hiển thị hộp thoại JOptionPane chứa JDateChooser
        int result = JOptionPane.showConfirmDialog(
                parent,           // Component cha
                message,          // Component hiển thị (JDateChooser)
                title,            // Tiêu đề hộp thoại
                JOptionPane.OK_CANCEL_OPTION, // Cung cấp nút OK và Cancel
                JOptionPane.PLAIN_MESSAGE     // Kiểu thông báo đơn giản
        );

        // Kiểm tra xem người dùng đã nhấn OK chưa
        if (result == JOptionPane.OK_OPTION) {
            // Lấy ngày được chọn từ JDateChooser
            Date selectedDate = dateChooser.getDate();
            return selectedDate; // Trả về ngày đã chọn (có thể là null nếu người dùng xóa ngày trong chooser)
        } else {
            // Nếu người dùng nhấn Cancel hoặc đóng hộp thoại
            return null; // Trả về null
        }
    }

    /**
     * Phương thức tiện ích đơn giản hơn nếu bạn luôn dùng cùng định dạng parse/display.
     */
    public static Date showDatePickerDialog(Component parent, String title, String initialDateString, String dateFormatStr) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormatStr);
        format.setLenient(false); // Đảm bảo parse nghiêm ngặt
        return showDatePickerDialog(parent, title, initialDateString, format, dateFormatStr);
    }

}