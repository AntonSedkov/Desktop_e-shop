package by.dao;

import by.entity.Users;
import by.mysql.DB;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoUsers implements DAOInterface<Users> {
    private DB db;

    public DaoUsers(DB db) {
        this.db = db;
    }

    @Override
    public void insert(Users obj) throws SQLException {
        PreparedStatement ps = (PreparedStatement) db.getCn().prepareStatement(
                "insert into " + obj.getClass().getSimpleName()
                        + " (login, password, role, balance, block_status, delete_status )"
                        + " values(?,?,?,?,?,?)");
        ps.setString(1, obj.getLogin());
        ps.setString(2, obj.getPassword());
        ps.setInt(3, obj.getRole());
        ps.setInt(4, obj.getBalance());
        ps.setInt(5, obj.getBlock_status());
        ps.setInt(6, obj.getDelete_status());
        ps.execute();
    }


    @Override
    public void update(Users obj) throws SQLException {
        PreparedStatement ps = (PreparedStatement) db.getCn().prepareStatement(
                "update " + obj.getClass().getSimpleName()
                        + " set login=?, password=?, role=?, balance=?, block_status=? "
                        + " where users_id=" + obj.getUser_id());
        ps.setString(1, obj.getLogin());
        ps.setString(2, obj.getPassword());
        ps.setInt(3, obj.getRole());
        ps.setInt(4, obj.getBalance());
        ps.setInt(5, obj.getBlock_status());
        ps.execute();
    }

    public void updateBalance(Users obj) throws SQLException {
        int balance = 0;
        ResultSet rs = db.query("select balance from users "
                + "where login='" + obj.getLogin() + "'");
        if (rs.next()) {
            balance = rs.getInt(1);
        }
        PreparedStatement ps = (PreparedStatement) db.getCn().prepareStatement(
                "update " + obj.getClass().getSimpleName()
                        + " set balance=? where login='"
                        + obj.getLogin() + "'");
        ps.setInt(1, balance + obj.getBalance());
        ps.execute();
    }

    @Override
    public void softDelete(Users obj) throws SQLException {
        db.update("delete from " + obj.getClass().getSimpleName()
                + " where users_id=" + obj.getUser_id());
    }

    @Override
    public void delete(Users obj) throws SQLException {
        db.update("update " + obj.getClass().getSimpleName()
                + " set delete_status=1 where users_id=" + obj.getUser_id());
    }

    public Users getUser(ResultSet rs) throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        return new Users(rs.getInt("users_id"), rs.getString("login"), rs.getString("password")
                , rs.getInt("role"), rs.getInt("balance"), rs.getInt("block_status"));
    }
}
