package view;

import controller.AdminController;
import model.User;

import javax.swing.*;
import java.awt.*;

public class SignInView extends JFrame {
    private User user;
    private JTextField jTextFieldName;
    private JPasswordField jPasswordField;
    private JButton btnSignIn;
    private JButton btnCancel;

    public SignInView() {
        this.user = new User();
        this.init();
        this.setVisible(true);
    }

    public void init() {
        this.setSize(500, 350);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Sign In");
        this.setLocationRelativeTo(null);

        //su kien
        AdminController action = new AdminController(this);
        // Tiêu đề
        JLabel title = new JLabel("ĐĂNG NHẬP", JLabel.CENTER);
        title.setForeground(new Color(10, 72, 208));
        title.setFont(new Font("Tahoma", Font.BOLD, 25));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0)); // Khoảng cách trên/dưới

        // Nhãn và ô nhập tài khoản
        JLabel jLabelName = new JLabel("Tên tài khoản:");
        jLabelName.setFont(new Font("Tahoma", Font.BOLD, 16));
        jTextFieldName = new JTextField(15);
        jTextFieldName.setFont(new Font("Tahoma", Font.PLAIN, 16));

        // Nhãn và ô nhập mật khẩu
        JLabel jLabelPassword = new JLabel("Mật khẩu:");
        jLabelPassword.setFont(new Font("Tahoma", Font.BOLD, 16));
        jPasswordField = new JPasswordField(15);
        jPasswordField.setFont(new Font("Tahoma", Font.PLAIN, 16));

        // Giới hạn kích thước của ô nhập
        jTextFieldName.setPreferredSize(new Dimension(200, 40));
        jPasswordField.setPreferredSize(new Dimension(200, 40));

        // Panel chứa các ô nhập liệu (Dùng GridBagLayout)
        JPanel panel1 = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();//Dinh nghia cac thanh phan duoc hien thi tren luoi
        gbc.insets = new Insets(10, 10, 10, 10); // Khoảng cách giữa các thành phần
//        gbc.fill = GridBagConstraints.HORIZONTAL; // Ô nhập sẽ không bị kéo dài quá mức

        // Hàng 1 - Tên tài khoản
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;//can trai
        panel1.add(jLabelName, gbc);

        gbc.gridx = 1;
        panel1.add(jTextFieldName, gbc);

        // Hàng 2 - Mật khẩu
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel1.add(jLabelPassword, gbc);

        gbc.gridx = 1;
        panel1.add(jPasswordField, gbc);

        // Nút đăng nhập
        btnSignIn = new JButton("Đăng nhập");
        btnSignIn.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnSignIn.setForeground(Color.WHITE);
        btnSignIn.setBackground(new Color(10, 72, 208));

        btnCancel = new JButton("Hủy bỏ");
        btnCancel.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setBackground(new Color(10, 72, 208));


        //them su kien vao cac nut bam
        btnSignIn.addActionListener(action);
        btnCancel.addActionListener(action);

        // Panel chứa nút đăng nhập
        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridBagLayout());
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.insets = new Insets(0, 20, 30, 20);
        gbc2.gridy = 0;//cung 1 hang
        gbc2.gridx = 0;
        panel2.add(btnSignIn, gbc2);
        gbc2.gridx = 1;
        panel2.add(btnCancel,gbc2);

        // Panel chính chứa tất cả thành phần
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(title, BorderLayout.NORTH);
        panel.add(panel1, BorderLayout.CENTER);
        panel.add(panel2, BorderLayout.SOUTH);

        this.setLayout(new BorderLayout());
        this.add(panel, BorderLayout.CENTER);
        this.setResizable(false);//khong cho thay doi kich thuoc hien thi
    }

    public JTextField getjTextFieldName() {
        return jTextFieldName;
    }

    public void setjTextFieldName(JTextField jTextFieldName) {
        this.jTextFieldName = jTextFieldName;
    }

    public JPasswordField getjPasswordField() {
        return jPasswordField;
    }

    public void setjPasswordField(JPasswordField jPasswordField) {
        this.jPasswordField = jPasswordField;
    }

    public JButton getBtnSignIn() {
        return btnSignIn;
    }

    public void setBtnSignIn(JButton btnSignIn) {
        this.btnSignIn = btnSignIn;
    }

    public JButton getBtnCancel() {
        return btnCancel;
    }

    public void setBtnCancel(JButton btnCancel) {
        this.btnCancel = btnCancel;
    }

    public static void main(String[] args) {
        new SignInView();
    }

}
