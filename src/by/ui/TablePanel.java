package by.ui;

import by.mysql.DB;

import javax.swing.*;

public abstract class TablePanel extends JPanel {
    protected DB db;
    protected JScrollPane scroll;
    protected MyTable table;
    protected JButton delete, add, change;

    public TablePanel(DB db) {
        this.db = db;
        setSize(500, 500);
        setLayout(null);
    }

    public void initComponents() {
        createTable();
        scroll = new JScrollPane(table);
        scroll.setBounds(20, 20, 680, 300);
        delete = new JButton("Delete");
        add = new JButton("Add");
        change = new JButton("Change");
        add.setBounds(20, 350, 200, 20);
        change.setBounds(260, 350, 200, 20);
        delete.setBounds(495, 350, 200, 20);
        add(scroll);
        add(add);
        add(delete);
        add(change);
    }

    public abstract void action();

    public abstract void createTable();

    public void updateTable() {
        remove(scroll);
        createTable();
        scroll = new JScrollPane(table);
        scroll.setBounds(20, 20, 680, 300);
        add(scroll);
        updateUI();
    }

}