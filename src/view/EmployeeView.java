package view;

import javax.swing.*;
import java.awt.*;

public class EmployeeView extends JPanel {

    public JPanel Employee(){
        JPanel panelContent = new JPanel();
        panelContent.setLayout(null);
        panelContent.setBackground(new Color(157, 239, 227));
        JLabel labeltitle = new JLabel("QUẢN LÍ NHÂN VIÊN");
        labeltitle.setBounds(600, 10, 250, 30);
        labeltitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        panelContent.add(labeltitle);
        return panelContent;

    }

}
