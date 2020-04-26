package by.ui;

import by.dao.DaoUsers;
import by.helpers.Sha1Encryption;
import by.mysql.DB;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFrame extends JFrame {
    private JPanel panel;
    private JLabel labelLogin, labelPassword;
    private JTextField tfLogin;
    private JPasswordField pfPassword;
    private JButton enter, registration;
    private DB db;
    private LoginFrame login;

    public LoginFrame(DB db) {
        this.db = db;
        setSize(250, 230);
        setTitle("LoginFrame");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponent();
        action();
        setResizable(false);
        setVisible(true);
        login = this;
    }

    private void initComponent() {
        panel = new JPanel();
        labelLogin = new JLabel("Login");
        labelPassword = new JLabel("Password");
        tfLogin = new JTextField("admin", 20);
        pfPassword = new JPasswordField("1234", 20);
        enter = new JButton("Enter");
        registration = new JButton("Registration");
        panel.add(labelLogin);
        panel.add(tfLogin);
        panel.add(labelPassword);
        panel.add(pfPassword);
        panel.add(enter);
        panel.add(registration);
        add(panel);
    }

    private void action() {
        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    ResultSet rs = db.query("select * from users where login = '"
                            + tfLogin.getText() + "' and delete_status=0");
                    if (rs.next()) {
                        try {
                            if (Sha1Encryption.encryptPassword(String.valueOf(pfPassword.getPassword())).equals(rs.getString("password"))) {
                                DaoUsers du = new DaoUsers(db);
                                if (rs.getInt("role") == 1) {
                                    new UserFrame(db, du.getUser(rs), login);
                                    dispose();
                                } else if (rs.getInt("role") == 0) {
                                    new AdminFrame(db);
                                    dispose();
                                }
                            } else {
                                JOptionPane.showMessageDialog(panel, "Incorrect password", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        JOptionPane.showMessageDialog(panel, "Incorrect login", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(panel, "Error in database \n" + ex, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        registration.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new RegistrationFrame(db);
                dispose();
            }
        });

    }


}
