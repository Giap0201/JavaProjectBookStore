//package view;
//
//import controller.CustomerController;
//import model.Customers;
//
//import javax.swing.*;
//import java.awt.*;
//import java.text.ParseException;
//
//public class InputCustomerView extends JDialog {
//    public class InputCustomerDialog extends JDialog {
//
//        private Customers customerResult = null;
//
//        public InputCustomerDialog(CustomerView customerView) {
//            super((Frame) null, "Nhập thông tin khách hàng", true);
//            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//
//            JPanel inputPanel = customerView.inputPanel();
//            getContentPane().add(inputPanel, BorderLayout.CENTER);
//
//            JButton btnLuu = new JButton("Lưu");
//            btnLuu.addActionListener(e -> {
//                CustomerController customerController = null;
//                try {
//                    customerResult = customerController.createCustomerFromInput();
//                } catch (ParseException ex) {
//                    throw new RuntimeException(ex);
//                }
//                dispose();
//            });
//
//            JButton btnHuy = new JButton("Huỷ");
//            btnHuy.addActionListener(e -> dispose());
//
//            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//            buttonPanel.add(btnHuy);
//            buttonPanel.add(btnLuu);
//            getContentPane().add(buttonPanel, BorderLayout.SOUTH);
//
//            pack();
//            setLocationRelativeTo(null);
//        }
//
//        public Customers showDialogAndGetCustomer() {
//            setVisible(true); // Chặn luồng cho đến khi dialog đóng
//            return customerResult; // Trả về sau khi người dùng nhấn "Lưu" hoặc null nếu "Huỷ"
//        }
//    }
//
//}
