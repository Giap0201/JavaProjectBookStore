package controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import utils.CommonView;
import view.App;
import view.BookView;
import view.CreateInvoiceView;
import view.CustomerView;
import view.DiscountProgramView;
import view.EmployeeView;
import view.ManageInvoiceView;

public class AppController implements MouseListener {
    private App app;
    private JPanel viewBooks = new BookView().initBookView();
    private JPanel viewEmployee = new EmployeeView().initEmployeeView();
    private JPanel viewCustomers = new CustomerView().initCustomerView();
    private JPanel viewSales = new CreateInvoiceView().initCustomerInvoiceView();
    private JPanel viewManageInvoice = new ManageInvoiceView(app).initManageInvoiceView();
    private JPanel viewDiscount = new DiscountProgramView().initDiscountProgramView();
    private ArrayList<JPanel> listSidebar;

    public AppController(App app) {
        this.app = app;
        listSidebar = new ArrayList<>();
    }

    // phai them vao list de khi di chuot hay click moi tu dong thay doi mau nen
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
            CommonView.replacePanel(app, viewBooks);
            app.changeBackground(app.getPanelBooks());
        } else if (e.getSource() == app.getPanelEmployee()) {
            CommonView.replacePanel(app,viewEmployee);
            app.changeBackground(app.getPanelEmployee());
        } else if (e.getSource() == app.getPanelCustomer()) {
            CommonView.replacePanel(app,viewCustomers);
            app.changeBackground(app.getPanelCustomer());
        } else if (e.getSource() == app.getPanelInvoice()) {
            CommonView.replacePanel(app,viewManageInvoice);
            app.changeBackground(app.getPanelInvoice());
        } else if (e.getSource() == app.getPanelDiscount()) {
            CommonView.replacePanel(app,viewDiscount);
            app.changeBackground(app.getPanelDiscount());
        } else if (e.getSource() == app.getPanelSale()) {
            CommonView.replacePanel(app,viewSales);
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
