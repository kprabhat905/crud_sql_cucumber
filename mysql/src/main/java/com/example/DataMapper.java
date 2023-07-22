package com.example;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface DataMapper<T> {
    void mapData(PreparedStatement statement, T data) throws SQLException;

    default String[] getColumnNames() {
        throw new UnsupportedOperationException("Not implemented.");
    }

    default String getPlaceholder() {
        throw new UnsupportedOperationException("Not implemented.");
    }

    T fromResultSet(ResultSet resultSet) throws SQLException;
}
