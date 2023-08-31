package com.ben.tp_javafx_orm.dao;

import com.ben.tp_javafx_orm.dao.entities.Category;

import java.util.List;

public interface CategoryDao extends Dao<Category> {

    @Override
    Category find(long id);

    @Override
    List<Category> findAll();

    @Override
    void save(Category object);

    @Override
    void delete(long id);

    @Override
    void update(Category object);
}
