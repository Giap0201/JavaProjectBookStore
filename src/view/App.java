package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class App extends JFrame {

    private JPanel panelSale;
    private JPanel panelEmployee;
    private JPanel panelBooks;
    private JPanel panelCustomer;
    private CardLayout cardLayout;
    private Map<String, JPanel> viewMap;

    public App() {
        setTitle("Shops Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
//        setExtendedState(JFrame.MAXIMIZED_BOTH); // Full màn hình
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        setSize(width, height);
        // Gọi phương thức init để thiết lập sidebar chung
        init();
        setVisible(true);
    }

    private void init() {
        Font font = new Font("Tahoma", Font.BOLD, 18);
        JPanel panelSidebar = new JPanel(new BorderLayout());
        panelSidebar.setPreferredSize(new Dimension(250, getHeight())); // Cố định chiều rộng sidebar
        panelSidebar.setBackground(new Color(27, 53, 68));

        // Thêm ảnh logo vào sidebar
        JLabel labelImage = new JLabel(scaleImage("images/icon1.png", 200, 200));
        labelImage.setHorizontalAlignment(JLabel.CENTER);
        panelSidebar.add(labelImage, BorderLayout.NORTH);

        // Panel chứa các mục sidebar
        JPanel panelMenu = new JPanel(new GridBagLayout());
        panelMenu.setBackground(new Color(27, 53, 68));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;

        // Thêm các mục sidebar
        panelSale = createSidebarItem("images/icon2.png", "Bán Hàng", font);
        gbc.gridy = 0;
        panelMenu.add(panelSale, gbc);


        panelBooks = createSidebarItem("images/icon3.png", "Quản Lí Sách", font);
        gbc.gridy = 1;
        panelMenu.add(panelBooks, gbc);

        panelCustomer = createSidebarItem("images/icon4.png", "Quản Lí Khách Hàng", font);
        gbc.gridy = 2;
        panelMenu.add(panelCustomer, gbc);

        panelEmployee = createSidebarItem("images/icon5.png", "Quản Lí Nhân Viên", font);
        gbc.gridy = 3;
        panelMenu.add(panelEmployee, gbc);

        gbc.gridy = 4;
        gbc.weighty = 1; // Khoảng trống sẽ mở rộng để đẩy nội dung lên trên, chiem het khong gian ben duoi
        panelMenu.add(Box.createVerticalGlue(), gbc);//thanh phan tang hinh co kha nang gian no

        panelBooks.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new BookViewManagement().setVisible(true);
                dispose();//giai phong tai nguyen cu
            }
//            @Override
//            public void mouseEntered(MouseEvent e) {
//                panelBooks.setBackground(new Color(180, 220, 16));
//            }
            @Override
            public void mouseExited(MouseEvent e) {
                panelBooks.setBackground(new Color(27, 53, 68));
            }

        });

        panelSidebar.add(panelMenu, BorderLayout.CENTER);

        // Thêm sidebar vào frame
        add(panelSidebar, BorderLayout.WEST);
    }

    protected JPanel createSidebarItem(String iconPath, String text, Font font) {
        JPanel panelItem = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelItem.setBackground(new Color(27, 53, 68));

        JLabel iconLabel = new JLabel(scaleImage(iconPath, 40, 40));
        JLabel textLabel = new JLabel(text);
        textLabel.setFont(font);
        textLabel.setForeground(Color.WHITE);

        panelItem.add(iconLabel);
        panelItem.add(textLabel);
        return panelItem;
    }

    private ImageIcon scaleImage(String path, int width, int height) {
        try {
            ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource(path));
            Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception e) {
            System.err.println("No Image: " + path);
            return null;
        }
    }
    private void initContentPanel() {
        cardLayout = new CardLayout();
        JPanel panelContent = new JPanel(cardLayout);
        viewMap = new HashMap<>();

        // Thêm các View vào CardLayout
//        viewMap.put("BookView", new BookViewManagement()); // Thay vì mở JFrame mới
        viewMap.put("CustomerView", new JPanel()); // Sau này thay thế bằng CustomerView
        viewMap.put("EmployeeView", new JPanel()); // Sau này thay thế bằng EmployeeView
        viewMap.put("SaleView", new JPanel()); // Sau này thay thế bằng SaleView

        for (Map.Entry<String, JPanel> entry : viewMap.entrySet()) {
            panelContent.add(entry.getValue(), entry.getKey());
        }

        add(panelContent, BorderLayout.CENTER);
    }

    public JPanel getPanelSale() {
        return panelSale;
    }

    public JPanel getPanelEmployee() {
        return panelEmployee;
    }

    public JPanel getPanelBooks() {
        return panelBooks;
    }

    public JPanel getPanelCustomer() {
        return panelCustomer;
    }


    public static void main(String[] args) {
        new App();
    }


}
