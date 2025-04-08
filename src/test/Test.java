package test;

import view.App;
import view.BookView;
import view.ManageInvoiceView;

import javax.swing.*;
import java.awt.*;

public class Test {
    public static void main(String[] args) {
//        BookView view = new BookView();
//        JPanel panel = view.initBookView();
//        App app = new App();
//        app.add(panel, BorderLayout.CENTER);
//        app.setVisible(true);
        ManageInvoiceView manageInvoiceView = new ManageInvoiceView();
        JPanel panel = manageInvoiceView.initManageInvoiceView();
        App app = new App();
        app.add(panel,BorderLayout.CENTER);
        app.setVisible(true);
    }
}
