package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class BaseView extends JFrame {
    private JPanel panelSidebar;
    private JPanel panelContent;
    private CardLayout cardLayout;
    private Map<String, JPanel> viewMap;

    public BaseView() {
        setTitle("Shops Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Khởi tạo sidebar và nội dung chính
        initSidebar();
        initContentPanel();

        setVisible(true);
    }

    /**
     * Khởi tạo Sidebar
     */
    private void initSidebar() {
        Font font = new Font("Tahoma", Font.BOLD, 18);
        panelSidebar = new JPanel(new BorderLayout());
        panelSidebar.setPreferredSize(new Dimension(250, getHeight())); // Cố định chiều rộng
        panelSidebar.setBackground(new Color(27, 53, 68));

        // Thêm logo
        JLabel labelImage = new JLabel(scaleImage("images/icon1.png", 200, 200));
        labelImage.setHorizontalAlignment(JLabel.CENTER);
        panelSidebar.add(labelImage, BorderLayout.NORTH);

        // Panel chứa các mục menu
        JPanel panelMenu = new JPanel(new GridBagLayout());
        panelMenu.setBackground(new Color(27, 53, 68));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;

        // Các mục menu
        gbc.gridy = 0;
        panelMenu.add(createSidebarItem("images/icon2.png", "Bán Hàng", font, "SaleView"), gbc);

        gbc.gridy = 1;
        panelMenu.add(createSidebarItem("images/icon3.png", "Quản Lí Sách", font, "BookView"), gbc);

        gbc.gridy = 2;
        panelMenu.add(createSidebarItem("images/icon4.png", "Quản Lí Khách Hàng", font, "CustomerView"), gbc);

        gbc.gridy = 3;
        panelMenu.add(createSidebarItem("images/icon5.png", "Quản Lí Nhân Viên", font, "EmployeeView"), gbc);

        gbc.gridy = 4;
        gbc.weighty = 1;
        panelMenu.add(Box.createVerticalGlue(), gbc); // Đẩy menu lên trên

        panelSidebar.add(panelMenu, BorderLayout.CENTER);
        add(panelSidebar, BorderLayout.WEST);
    }

    /**
     * Khởi tạo Panel chứa nội dung
     */
    private void initContentPanel() {
        cardLayout = new CardLayout();
        panelContent = new JPanel(cardLayout);
        viewMap = new HashMap<>();

        // Thêm các View vào CardLayout
//        viewMap.put("BookView", new BookView()); // Thay vì mở JFrame mới
        viewMap.put("CustomerView", new JPanel()); // Sau này thay thế bằng CustomerView
        viewMap.put("EmployeeView", new JPanel()); // Sau này thay thế bằng EmployeeView
        viewMap.put("SaleView", new JPanel()); // Sau này thay thế bằng SaleView

        for (Map.Entry<String, JPanel> entry : viewMap.entrySet()) {
            panelContent.add(entry.getValue(), entry.getKey());
        }

        add(panelContent, BorderLayout.CENTER);
    }

    /**
     * Tạo một mục Sidebar
     */
    private JPanel createSidebarItem(String iconPath, String text, Font font, String viewName) {
        JPanel panelItem = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelItem.setBackground(new Color(27, 53, 68));

        JLabel iconLabel = new JLabel(scaleImage(iconPath, 40, 40));
        JLabel textLabel = new JLabel(text);
        textLabel.setFont(font);
        textLabel.setForeground(Color.WHITE);

        panelItem.add(iconLabel);
        panelItem.add(textLabel);

        // Xử lý sự kiện khi nhấn vào mục menu
        panelItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showView(viewName);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                panelItem.setBackground(new Color(40, 73, 92));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panelItem.setBackground(new Color(27, 53, 68));
            }
        });

        return panelItem;
    }

    /**
     * Hiển thị View theo tên
     */
    private void showView(String viewName) {
        if (viewMap.containsKey(viewName)) {
            cardLayout.show(panelContent, viewName);
        } else {
            System.err.println("Không tìm thấy view: " + viewName);
        }
    }

    /**
     * Hỗ trợ scale ảnh
     */
    private ImageIcon scaleImage(String path, int width, int height) {
        try {
            ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource(path));
            Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception e) {
            System.err.println("Không tìm thấy ảnh: " + path);
            return null;
        }
    }

    public static void main(String[] args) {
        new BaseView();
    }
}
