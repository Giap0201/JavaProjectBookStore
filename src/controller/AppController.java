package controller;

import view.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AppController implements MouseListener {
    private App app;
    private JPanel viewBooks = new BookView().initBookView();
    private JPanel viewEmployee = new EmployeeView().initEmployeeView();
    private JPanel viewCustomers = new CustomerView().initCustomerView();
    private JPanel viewSales = new CreateInvoiceView().initCustomerInvoiceView();
    private JPanel viewManageInvoice = new ManageInvoiceView().initManageInvoiceView();
    private JPanel viewDiscount = new DiscountProgramView().initDiscountProgramView();
    private ArrayList<JPanel> listSidebar;

    public AppController(App app) {
        this.app = app;
        listSidebar = new ArrayList<>();
    }

    //phai them vao list de khi di chuot hay click moi tu dong  thay doi mau nen
    public void addJpanelList() {
        listSidebar.add(app.getPanelSale());
        listSidebar.add(app.getPanelBooks());
        listSidebar.add(app.getPanelEmployee());
        listSidebar.add(app.getPanelCustomer());
        listSidebar.add(app.getPanelInvoice());
        listSidebar.add(app.getPanelDiscount());
        for (int i = 0; i < listSidebar.size(); i++) {
            listSidebar.get(i).setBackground(new Color(27, 53, 68));
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        addJpanelList();
        if (e.getSource() == app.getPanelBooks()) {
            replacePanel(viewBooks);
            app.changeBackground(app.getPanelBooks());
        } else if (e.getSource() == app.getPanelEmployee()) {
            replacePanel(viewEmployee);
            app.changeBackground(app.getPanelEmployee());
        } else if (e.getSource() == app.getPanelCustomer()) {
            replacePanel(viewCustomers);
            app.changeBackground(app.getPanelCustomer());
        } else if (e.getSource() == app.getPanelInvoice()) {
            replacePanel(viewManageInvoice);
            app.changeBackground(app.getPanelInvoice());
        } else if (e.getSource() == app.getPanelDiscount()) {
            replacePanel(viewDiscount);
            app.changeBackground(app.getPanelDiscount());
        } else if (e.getSource() == app.getPanelSale()) {
            replacePanel(viewSales);
            app.changeBackground(app.getPanelSale());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    //ham thay the panel trong Jpanel
    private void replacePanel(JPanel panel) {
        //lay contenpane cua JFrame
        Container container = app.getContentPane();
        //lay component hien tai trong BorderLayout.Center
        Component currentPanel = ((BorderLayout) container.getLayout()).getLayoutComponent(BorderLayout.CENTER);
        if (currentPanel != null) {
            container.remove(currentPanel);
        }
        container.add(panel, BorderLayout.CENTER);
        container.revalidate();//cap nhat lai layout
        container.repaint();//ve lai UI
    }
}

//private void replacePanel(JPanel newPanel) {
//        Container contentPane = appView.getContentPane(); // Lấy content pane của JFrame
//        // Lấy component hiện tại trong BorderLayout.CENTER
//        Component currentCenterPanel = ((BorderLayout) contentPane.getLayout()).getLayoutComponent(BorderLayout.CENTER);
//        if (currentCenterPanel != null) {
//            contentPane.remove(currentCenterPanel); // Xóa panel hiện tại ở CENTER
//        }
//        contentPane.add(newPanel, BorderLayout.CENTER); // Thêm panel mới vào CENTER
//        contentPane.revalidate(); // Cập nhật layout
//        contentPane.repaint(); // Vẽ lại UI
//    }
/*
public class AppController {
    private App appView;
//    private JPanel panelBook = new BookView().initBookView();
//    private JPanel panelEmployee = new EmployeeView().Employee();

    public AppController(App appView) {
        this.appView = appView;
        initManageInvoiceView();
    }

    public void initManageInvoiceView() {
        appView.getPanelBooks().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                replacePanel(new BookView().initBookView());
                appView.changeBackground(appView.getPanelBooks());
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

}
*/
