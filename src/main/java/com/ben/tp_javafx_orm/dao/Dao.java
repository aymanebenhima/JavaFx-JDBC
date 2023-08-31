package com.ben.tp_javafx_orm.dao;

import java.util.List;

public interface Dao <T>{

    T find(long id);
    List<T> findAll();
    void save(T object);
    void delete(long id);
    void update(T object);
}
