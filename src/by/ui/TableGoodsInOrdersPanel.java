package by.ui;

import by.mysql.DB;

import javax.swing.*;
import java.sql.SQLException;

public class TableGoodsInOrdersPanel extends TablePanel {
    private int orders_id;

    public TableGoodsInOrdersPanel(DB db, int users_id) {
        super(db);
        this.orders_id = users_id;
        initComponents();
        scroll.setSize(250, 200);
        remove(add);
        remove(delete);
        remove(change);
    }

    @Override
    public void action() {
    }

    @Override
    public void createTable() {
        try {
            table = new MyTable(db.query("Select goods.name, goods_in_orders.count from goods " +
                    "JOIN goods_in_orders ON goods.goods_id =goods_in_orders.goods_id where goods_in_orders.orders_id="
                    + orders_id)) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error in creating table \n" + ex,
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
