package test;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

/**
 *
 */
public class DateChooserExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("JDateChooser Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new FlowLayout());

        JLabel label = new JLabel("Chọn ngày:");
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDate(new Date()); // set ngày mặc định là hôm nay

        JButton button = new JButton("Lấy ngày");
        JLabel result = new JLabel("");

        button.addActionListener(e -> {
            Date selectedDate = dateChooser.getDate();
            if (selectedDate != null) {
                result.setText("Ngày đã chọn: " + selectedDate.toString());
            } else {
                result.setText("Chưa chọn ngày.");
            }
        });

        frame.add(label);
        frame.add(dateChooser);
        frame.add(button);
        frame.add(result);

        frame.setVisible(true);
    }
}
