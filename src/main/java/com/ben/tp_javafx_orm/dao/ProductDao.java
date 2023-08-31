package com.ben.tp_javafx_orm.dao;

import com.ben.tp_javafx_orm.dao.entities.Product;

import java.util.List;

public interface ProductDao extends Dao<Product> {
    List<Product> findByQuery(String query);
}
