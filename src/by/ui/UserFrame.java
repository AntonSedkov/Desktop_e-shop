package by.ui;

import by.dao.DaoGoods;
import by.dao.DaoOrders;
import by.dao.DaoUsers;
import by.entity.Goods;
import by.entity.Orders;
import by.entity.Users;
import by.mysql.DB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class UserFrame extends JFrame {
    private DB db;
    private JPanel itemList;
    private JScrollPane scrollPane;
    private JButton logout;
    private JPanel userInfo;
    private JButton order;
    private LoginFrame login;
    private ArrayList<ShopItem> goodsList;
    private Users currentUser;
    private JLabel balanceLabel;

    public UserFrame(DB db, Users user, LoginFrame login) {
        this.db = db;
        this.currentUser = user;
        this.login = login;
        this.goodsList = new ArrayList<ShopItem>();
        setSize(600, 600);
        setTitle("UserFrame");
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        userInfo = new JPanel();
        userInfo.add(new JLabel("Hello, "));
        userInfo.add(new JLabel(user.getLogin() + "!"));
        balanceLabel = new JLabel("" + user.getBalance() + "$");
        userInfo.add(new JLabel("Balance: "));
        userInfo.add(balanceLabel);
        logout = new JButton("LogOut");
        logout.setSize(100, 100);
        userInfo.add(logout);
        add(userInfo, BorderLayout.NORTH);

        DaoGoods daoGoods = new DaoGoods(db);
        ArrayList<Goods> goods = null;
        int position = 100;
        try {
            goods = daoGoods.selectAll();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        itemList = new JPanel(new GridLayout(goods.size(), 1));
        itemList.setPreferredSize(new Dimension(500, goods.size() * 200));
        for (Goods i : goods) {
            ShopItem tmp = new ShopItem(i, position);
            itemList.add(tmp);
            goodsList.add(tmp);
        }
        scrollPane = new JScrollPane(itemList);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        add(scrollPane, BorderLayout.CENTER);
        order = new JButton("Order");
        order.setSize(100, 100);
        add(order, BorderLayout.SOUTH);
        setVisible(true);
        actions();
    }

    private void actions() {
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
                login.setVisible(true);
            }
        });

        order.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                HashMap<Goods, Integer> choosenGoods = new HashMap<Goods, Integer>();
                int totalCost = 0;
                for (ShopItem shopItem : goodsList) {
                    if (shopItem.isChoosen()) {
                        choosenGoods.put(shopItem.getCurrentItem(), shopItem.getAmount());
                        totalCost += shopItem.getCurrentItem().getPrice() * shopItem.getAmount();
                    }
                }
                if (currentUser.getBalance() >= totalCost) {
                    int lastInsertID = 0;
                    try {
                        new DaoOrders(db).insert(new Orders(currentUser.getUser_id(), "processing", 0, totalCost));
                        lastInsertID = new DaoOrders(db).getLastInsertId();
                    } catch (SQLException ex) {
                    }
                    StringBuilder sb = new StringBuilder("Insert into goods_in_orders values ");
                    for (Entry<Goods, Integer> entry : choosenGoods.entrySet()) {
                        sb.append("('" + lastInsertID + "','" + entry.getKey().getGoods_id() + "','" + entry.getValue() + "'),");
                    }
                    String result = sb.toString();
                    try {
                        db.update(result.substring(0, result.length() - 1));
                        balanceLabel.setText("" + (currentUser.getBalance() - totalCost) + "$");
                        currentUser.setBalance(currentUser.getBalance() - totalCost);
                        new DaoUsers(db).updateBalance(currentUser);
                        JOptionPane.showMessageDialog(null, "Total Cost: " + totalCost, "Success order", 1);
                    } catch (SQLException ex) {
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Total Cost " + totalCost +
                            "$ more than your balance", "Fail order", 1);
                }
            }
        });
    }
}