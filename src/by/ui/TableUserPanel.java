package by.ui;

import by.dao.DaoUsers;
import by.entity.Users;
import by.mysql.DB;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TableUserPanel extends TablePanel {
    private JPanel panel = this;

    public TableUserPanel(DB db) {
        super(db);
        super.initComponents();
        action();
    }

    @Override
    public void action() {
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if (table.getSelectedRow() == -1) {
                        JOptionPane.showMessageDialog(panel, "Select the line you want to remove");
                    } else {
                        db.update("update users set delete_status=1 where users_id="
                                + table.getValueAt(table.getSelectedRow(), 0));
                        updateTable();
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(panel, "Error in delete " + ex);
                }
            }
        });

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    int maxId = 0;
                    ResultSet resultSet = db.query("select max(users_id) from users");
                    if (resultSet.next()) {
                        maxId = resultSet.getInt(1);
                    }
                    DaoUsers daoUsers = new DaoUsers(db);
                    daoUsers.insert(new Users(maxId));
                    updateTable();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(panel, "Error in add " + ex);
                }
            }
        });

        change.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (table.getSelectedRow() != -1) {
                    try {
                        int role = 0;
                        if (table.getValueAt(table.getSelectedRow(), 4).toString()
                                .equalsIgnoreCase("user")) {
                            role = 1;
                        } else {
                            if (table.getValueAt(table.getSelectedRow(), 4).toString()
                                    .equalsIgnoreCase("admin")) {
                                role = 0;
                            } else {
                                throw new SQLException("Error role");
                            }
                        }
                        int block_status = 0;
                        if (table.getValueAt(table.getSelectedRow(), 5).toString()
                                .equalsIgnoreCase("active")) {
                            block_status = 0;
                        } else {
                            if (table.getValueAt(table.getSelectedRow(), 5).toString()
                                    .equalsIgnoreCase("blocked")) {
                                block_status = 1;
                            } else {
                                throw new SQLException("Error block_status");
                            }
                        }
                        DaoUsers daoUsers = new DaoUsers(db);
                        daoUsers.update(
                                new Users(Integer.valueOf(table.getValueAt(table.getSelectedRow(), 0).toString()),
                                        table.getValueAt(table.getSelectedRow(), 1).toString(),
                                        table.getValueAt(table.getSelectedRow(), 2).toString(),
                                        role,
                                        Integer.valueOf(table.getValueAt(table.getSelectedRow(), 3).toString()),
                                        block_status
                                ));
                        updateTable();
                    } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
                        Logger.getLogger(TablePanel.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (NumberFormatException | SQLException ex) {
                        JOptionPane.showMessageDialog(panel, "Incorrect data " + ex);
                        updateTable();
                    }
                } else {
                    JOptionPane.showMessageDialog(panel, "Select the line you want to change");
                }
            }
        });
    }

    @Override
    public void createTable() {
        try {
            table = new MyTable(db.query("select users_id, login,password,balance, case role when 1 then 'user' when 0 " +
                    "then 'admin' end as role, if (block_status=1, 'blocked','active')as blocked_status from users " +
                    "where delete_status=0")) {

                @Override
                public boolean isCellEditable(int row, int column) {
                    if (column == 0) {
                        return false;
                    } else {
                        return true;
                    }
                }
            };
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error creating table \n" + ex,
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
