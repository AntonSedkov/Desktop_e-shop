package by.dao;

import by.entity.Goods;
import by.mysql.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DaoGoods implements DAOInterface<Goods> {
    private DB db;

    public DaoGoods(DB db) {
        this.db = db;
    }

    @Override
    public void insert(Goods obj) throws SQLException {
        PreparedStatement ps = db.getCn().prepareStatement(
                "insert into " + obj.getClass().getSimpleName()
                        + " (name, image_path, price, description, delete_status)"
                        + " values(?,?,?,?,?)");
        ps.setString(1, obj.getName());
        ps.setString(2, obj.getImagePath());
        ps.setInt(3, obj.getPrice());
        ps.setString(4, obj.getDescription());
        ps.setInt(5, obj.getDelete_status());
        ps.execute();
    }

    @Override
    public void update(Goods obj) throws SQLException {
        PreparedStatement ps = db.getCn().prepareStatement(
                "update " + obj.getClass().getSimpleName()
                        + " set name=?, price=?, description=?"
                        + " where goods_id=" + obj.getGoods_id());
        ps.setString(1, obj.getName());
        ps.setInt(2, obj.getPrice());
        ps.setString(3, obj.getDescription());
        ps.execute();
    }

    @Override
    public void softDelete(Goods obj) throws SQLException {
        db.update("delete from " + obj.getClass().getSimpleName()
                + " where goods_id=" + obj.getGoods_id());
    }

    @Override
    public void delete(Goods obj) throws SQLException {
        db.update("update " + obj.getClass().getSimpleName()
                + " set delete_status=1 where goods_id=" + obj.getGoods_id());
    }

    public ArrayList<Goods> selectAll() throws SQLException {
        ArrayList<Goods> tmp = new ArrayList<Goods>();
        ResultSet rs = this.db.query("select * from goods");
        while (rs.next()) {
            tmp.add(new Goods(
                    rs.getInt("goods_id"), rs.getString("name"), rs.getString("image_path"),
                    rs.getInt("price"), rs.getString("description"), rs.getInt("delete_status")));
        }
        return tmp;
    }

    public void setImagepath(Goods obj) throws SQLException {
        System.out.println(obj.getImagePath());
        db.update("update " + obj.getClass().getSimpleName()
                + " set image_path='" + obj.getImagePath().replace("\\", "\\/")
                + "' where goods_id=" + obj.getGoods_id());
    }

}
