package by.entity;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import static by.helpers.Sha1Encryption.encryptPassword;

public class Users {
    private int user_id;
    private String login;
    private String password;
    private int role;
    private int balance;
    private int block_status;
    private int delete_status;

    public Users(int user_id) {
        this.user_id = user_id;
        this.login = "";
        this.password = "";
        this.role = 1;
        this.balance = 0;
        this.block_status = 0;
        this.delete_status = 0;
    }

    public Users(int user_id, String login, String password, int role, int balance, int block_status)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        this.user_id = user_id;
        this.login = login;
        this.password = encryptPassword(password);
        this.role = role;
        this.balance = balance;
        this.block_status = block_status;
    }

    public Users(String login, String password, int role, int balance, int block_status, int delete_status)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        this.login = login;
        this.password = encryptPassword(password);
        this.role = role;
        this.balance = balance;
        this.block_status = block_status;
        this.delete_status = delete_status;
    }

    public Users(String login, int balance) {
        this.login = login;
        this.balance = balance;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getBlock_status() {
        return block_status;
    }

    public void setBlock_status(int block_status) {
        this.block_status = block_status;
    }

    public int getDelete_status() {
        return delete_status;
    }

    public void setDelete_status(int delete_status) {
        this.delete_status = delete_status;
    }
}
