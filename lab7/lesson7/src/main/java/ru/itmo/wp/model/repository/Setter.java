package ru.itmo.wp.model.repository;

import java.sql.SQLException;

@FunctionalInterface
public interface Setter<T, U> {
    public void set(T t, U u) throws SQLException;
}
