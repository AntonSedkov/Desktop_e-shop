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

public class RegistrationFrame extends JFrame {
    private JPanel panel;
    private JLabel labelLogin, labrlPassword;
    private JTextField tfLogin, tfPassword;
    private JButton registration;
    private DB db;

    public RegistrationFrame(DB db) {
        this.db = db;
        setSize(250, 230);
        setTitle("RegistrationFrame");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
        action();
        setResizable(false);
        setVisible(true);
    }

    private void initComponents() {
        panel = new JPanel();
        labelLogin = new JLabel("Login");
        labrlPassword = new JLabel("Password");
        tfLogin = new JTextField(20);
        tfPassword = new JTextField(20);
        registration = new JButton("Registration");
        panel.add(labelLogin);
        panel.add(tfLogin);
        panel.add(labrlPassword);
        panel.add(tfPassword);
        panel.add(registration);
        add(panel);
    }

    private void action() {
        registration.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (tfLogin.getText().equals("") || tfPassword.getText().equals("")) {
                    JOptionPane.showMessageDialog(panel, "Incorrect login or password", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        ResultSet rs = db.query("select * from users where login='" + tfLogin.getText() + "'");
                        if (rs.next()) {
                            JOptionPane.showMessageDialog(panel, "The username is not available", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            DaoUsers du = new DaoUsers(db);
                            try {
                                du.insert(new Users(tfLogin.getText(), tfPassword.getText(), 1, 0, 0, 0));
                                JOptionPane.showMessageDialog(panel, "Registration successful!", "Message", JOptionPane.INFORMATION_MESSAGE);
                                new LoginFrame(db);
                                dispose();
                            } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
                                ex.printStackTrace();
                            }
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(panel, "Error accessing database \n" + ex, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }
}