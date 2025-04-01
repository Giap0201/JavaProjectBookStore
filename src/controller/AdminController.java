package controller;

import service.UserService;
import view.SignInView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminController implements ActionListener {
    private SignInView signInView;
    private UserService userService;

    public AdminController(SignInView signInView) {
        this.signInView = signInView;
        this.userService = new UserService();

//        // Đăng ký sự kiện cho các nút trong SignInView
//        this.signInView.getBtnSignIn().addActionListener(this);
//        this.signInView.getBtnCancel().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == signInView.getBtnSignIn()) {
            handleLogin();
        } else if (e.getSource() == signInView.getBtnCancel()) {
            signInView.dispose();
        }
    }

    private void handleLogin() {
        String username = signInView.getjTextFieldName().getText();
        String password = new String(signInView.getjPasswordField().getPassword());

        if (userService.authenticateUser(username, password)) {
            JOptionPane.showMessageDialog(signInView, "Đăng nhập thành công!");
            signInView.dispose();
            // Mở màn hình chính
            new view.App();
        } else {
            JOptionPane.showMessageDialog(signInView, "Tài khoản hoặc mật khẩu sai!");
        }
    }
}
