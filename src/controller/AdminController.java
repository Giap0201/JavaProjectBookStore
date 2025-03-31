package controller;

import model.User;
import view.SignInView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminController implements ActionListener {
    private SignInView signInView;
    private User user;

    public  AdminController(SignInView signInView) {
        this.signInView = signInView;
        this.user = new User();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == signInView.getBtnSignIn()){
            try {
                //co the chinh sua them phan database sau
                String name = signInView.getjTextFieldName().getText();
                char [] temp = signInView.getjPasswordField().getPassword();//vi getpassword tra ve mang char
                String pass = new String(temp);
                this.user.setPassword(pass);
                this.user.setName(name);
                if(this.user.getPassword().equals("admin") && this.user.getName().equals("admin")){
                    JOptionPane.showMessageDialog(null,"Đăng nhập thành công!!");
//                    new BaseView();
                    signInView.setVisible(false);
                }else {
                    JOptionPane.showMessageDialog(null,"Tài khoản hoặc mật khẩu sai!!");
                }

            }catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        else if(e.getSource() == signInView.getBtnCancel()){
            this.signInView.setVisible(false);
        }
    }
}

