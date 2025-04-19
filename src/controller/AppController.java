package controller;

import utils.CommonView;
import view.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class AppController implements MouseListener {
    private App app;
    private JPanel viewBooks;
    private JPanel viewEmployee;
    private JPanel viewCustomers;
    private JPanel viewSales;
    private JPanel viewManageInvoice;
    private JPanel viewDiscount;
    private ArrayList<JPanel> listSidebar;

    public AppController(App app) {
        this.app = app;
        listSidebar = new ArrayList<>();
        initialize();
    }

    private void initialize() {
        viewBooks = new BookView().initBookView();
        viewEmployee = new EmployeeView().initEmployeeView();
        viewCustomers = new CustomerView().initCustomerView();
        viewSales = new CreateInvoiceView().initCustomerInvoiceView();
        viewManageInvoice = new ManageInvoiceView(app).initManageInvoiceView();
        viewDiscount = new DiscountProgramView().initDiscountProgramView();
    }

    // phai them vao list de khi di chuot hay click moi tu dong thay doi mau nen
    public void addJpanelList() {
        listSidebar.clear();
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
            CommonView.replacePanel(app, viewBooks);
            app.changeBackground(app.getPanelBooks());
        } else if (e.getSource() == app.getPanelEmployee()) {
            CommonView.replacePanel(app, viewEmployee);
            app.changeBackground(app.getPanelEmployee());
        } else if (e.getSource() == app.getPanelCustomer()) {
            CommonView.replacePanel(app, viewCustomers);
            app.changeBackground(app.getPanelCustomer());
        } else if (e.getSource() == app.getPanelInvoice()) {
            CommonView.replacePanel(app, viewManageInvoice);
            app.changeBackground(app.getPanelInvoice());
        } else if (e.getSource() == app.getPanelDiscount()) {
            CommonView.replacePanel(app, viewDiscount);
            app.changeBackground(app.getPanelDiscount());
        } else if (e.getSource() == app.getPanelSale()) {
            CommonView.replacePanel(app, viewSales);
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

}
