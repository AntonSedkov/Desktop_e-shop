package by.dao;

import by.entity.Orders;
import by.mysql.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoOrders implements DAOInterface<Orders> {
    private DB db;

    public DaoOrders(DB db) {
        this.db = db;
    }

    @Override
    public void insert(Orders obj) throws SQLException {
        PreparedStatement ps = db.getCn().prepareStatement(
                "insert into " + obj.getClass().getSimpleName()
                        + " (users_id, payment, delete_status, total_cost)"
                        + " values (?,?,?,?)");
        ps.setInt(1, obj.getUsers_id());
        ps.setString(2, obj.getPayment());
        ps.setInt(3, obj.getDelete_status());
        ps.setInt(4, obj.getTotal_cost());
        ps.execute();
    }

    @Override
    public void update(Orders obj) throws SQLException {
        PreparedStatement ps = db.getCn().prepareStatement(
                "update " + obj.getClass().getSimpleName()
                        + " set payment=? where orders_id=" + obj.getOrders_id());
        ps.setString(1, obj.getPayment());
        ps.execute();
    }

    @Override
    public void softDelete(Orders obj) throws SQLException {
        db.update("delete from " + obj.getClass().getSimpleName()
                + " where order_id=" + obj.getOrders_id());
    }

    @Override
    public void delete(Orders obj) throws SQLException {
        db.update("update " + obj.getClass().getSimpleName()
                + " set delete_status=1 where orders_id=" + obj.getOrders_id());
    }

    public int getLastInsertId() throws SQLException {
        ResultSet rs = db.query("select last_insert_id() from orders");
        rs.next();
        return rs.getInt(1);
    }

}
