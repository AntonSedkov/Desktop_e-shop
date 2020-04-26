package by.dao;

import java.sql.SQLException;

public interface DAOInterface<T> {
    public void insert(T obj) throws SQLException;

    public void delete(T obj) throws SQLException;

    public void update(T obj) throws SQLException;

    public void softDelete(T obj) throws SQLException;
}
