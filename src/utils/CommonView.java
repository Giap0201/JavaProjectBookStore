package utils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class CommonView {
    public static JButton createButton(String title, Color color){
        Font font = new Font("Tahoma", Font.BOLD, 15);
        JButton button = new JButton(title);
        button.setFont(font);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setHorizontalAlignment(JButton.CENTER);
        return button;
    }

//    **
//            * Hàm hỗ trợ scale ảnh
//     */
    public static ImageIcon scaleImage(String path, int width, int height) {
        try {
            ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource(path));
            Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception e) {
            System.err.println("No Image: " + path);
            return new ImageIcon(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)); // Trả về ảnh trống
        }
    }
}
