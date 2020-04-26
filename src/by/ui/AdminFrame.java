package by.ui;

import by.mysql.DB;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class AdminFrame extends JFrame {
    private DB db;
    private TablePanel panelUsers, panelGoods, panelOrders;
    private JTabbedPane tabbedPane;

    public AdminFrame(DB db) {
        this.db = db;
        setSize(750, 480);
        setTitle("AdminFrame");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
        action();
        setVisible(true);
    }

    private void initComponents() {
        panelUsers = new TableUserPanel(db);
        panelGoods = new TableGoodsPanel(db);
        panelOrders = new TableOrdersPanel(db);
        tabbedPane = new JTabbedPane();
        tabbedPane.add("Users", panelUsers);
        tabbedPane.add("Goods", panelGoods);
        tabbedPane.add("Orders", panelOrders);
        add(tabbedPane);
    }

    private void action() {
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                panelUsers.updateTable();
                panelGoods.updateTable();
                panelOrders.updateTable();
            }
        });
    }
}
