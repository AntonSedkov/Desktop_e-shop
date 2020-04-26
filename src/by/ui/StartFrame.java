package by.ui;

import by.mysql.DB;
import by.mysql.WorkDB;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class StartFrame extends JFrame {
    private JPanel panel;
    private JLabel labelUrl, labelLogin, labelPassword;
    private JTextField tfUrl, tfLogin;
    private JPasswordField pfPassword;
    private JButton create, delete, connect;

    public StartFrame() {
        setSize(250, 230);       //размер фрейма
        setTitle("StartFrame");             //заголовок
        setLocationRelativeTo(null);        //центрирование
        setDefaultCloseOperation(EXIT_ON_CLOSE);            // закрытие окна
        initComponents();                   //создание и добавление всех компонент
        action();                           //обработка событий
        setResizable(false);
        setVisible(true);                   //прорисовка
    }

    private void initComponents() {
        panel = new JPanel();
        labelUrl = new JLabel("URL");
        labelLogin = new JLabel("Login");
        labelPassword = new JLabel("Password");
        tfUrl = new JTextField("localhost:3306", 20);
        tfLogin = new JTextField("root", 20);
        pfPassword = new JPasswordField(20);
        create = new JButton("Create");
        delete = new JButton("Delete");
        connect = new JButton("Connect");
        panel.add(labelUrl);
        panel.add(tfUrl);
        panel.add(labelLogin);
        panel.add(tfLogin);
        panel.add(labelPassword);
        panel.add(pfPassword);
        panel.add(create);
        panel.add(delete);
        panel.add(connect);
        add(panel);
    }

    private void action() {
        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    try {
                        WorkDB.create("jdbc:mysql://" + tfUrl.getText()
                                        + "/", tfLogin.getText(),
                                String.valueOf(pfPassword.getPassword()));
                    } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                    }
                    JOptionPane.showMessageDialog(panel,
                            "Database installing successfully",
                            "Message", JOptionPane.INFORMATION_MESSAGE);
                } catch (ClassNotFoundException | SQLException ex) {
                    System.out.println(ex);
                    JOptionPane.showMessageDialog(panel,
                            "Error installing database \n" + ex,
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    WorkDB.delete("jdbc:mysql://" + tfUrl.getText()
                                    + "/", tfLogin.getText(),
                            String.valueOf(pfPassword.getPassword()));
                    JOptionPane.showMessageDialog(panel,
                            "Database drop successfully",
                            "Message", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException | ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(panel,
                            "Error drop database \n" + ex,
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        connect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    DB db = new DB("jdbc:mysql://" + tfUrl.getText()
                            + "/" + WorkDB.NAME_DB, tfLogin.getText(),              //mysql?useSSL=false
                            String.valueOf(pfPassword.getPassword()));
                    new LoginFrame(db);
                    dispose();
                } catch (SQLException | ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(panel,
                            "the database is not installed \n" + ex,
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

}