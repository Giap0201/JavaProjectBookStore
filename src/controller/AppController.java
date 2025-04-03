package controller;

import view.App;
import view.BookView;
import view.EmployeeView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AppController {
    private App appView;
//    private JPanel panelBook = new BookView().initBookView();
//    private JPanel panelEmployee = new EmployeeView().Employee();

    public AppController(App appView) {
        this.appView = appView;
        init();
    }

    public void init() {
        appView.getPanelBooks().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                replacePanel(new BookView().initBookView());
            }
        });

        appView.getPanelEmployee().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                replacePanel(new EmployeeView().initEmployeeView());
            }
        });
    }

    // Hàm thay thế panel trong JFrame
    private void replacePanel(JPanel newPanel) {
        Container contentPane = appView.getContentPane(); // Lấy content pane của JFrame
        // Lấy component hiện tại trong BorderLayout.CENTER
        Component currentCenterPanel = ((BorderLayout) contentPane.getLayout()).getLayoutComponent(BorderLayout.CENTER);
        if (currentCenterPanel != null) {
            contentPane.remove(currentCenterPanel); // Xóa panel hiện tại ở CENTER
        }
        contentPane.add(newPanel, BorderLayout.CENTER); // Thêm panel mới vào CENTER
        contentPane.revalidate(); // Cập nhật layout
        contentPane.repaint(); // Vẽ lại UI
    }
}
