package com.ben.tp_javafx_orm.dao;

import com.ben.tp_javafx_orm.dao.entities.Category;
import com.ben.tp_javafx_orm.dao.entities.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDaoImp  implements CategoryDao{
    @Override
    public Category find(long id) {
        Connection connection = ConnectionDBSingleton.getConnection();
        Category category = new Category();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM category WHERE idCategory=?");
            preparedStatement.setLong(1,id);
            ResultSet res = preparedStatement.executeQuery();
            if(res.next()){
                category.setId(res.getLong("idCategory"));
                category.setName(res.getString("categoryName"));
                }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return category;
    }

    @Override
    public List<Category> findAll() {
        Connection connection = ConnectionDBSingleton.getConnection();
        List<Category> categories = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM category");
            ResultSet res = preparedStatement.executeQuery();
            while (res.next()){
                Category category = new Category();
                category.setId(res.getLong("idCategory"));
                category.setName(res.getString("categoryName"));
                categories.add(category);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return categories;
    }

    @Override
    public void save(Category object) {
        Connection connection = ConnectionDBSingleton.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO category(categoryName)" +
                            "VALUES(?)"
            );
            preparedStatement.setString(1, object.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(long id) {
        Connection connection = ConnectionDBSingleton.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM category WHERE idCategory=?");
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Category object) {
        Connection connection = ConnectionDBSingleton.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE FROM category SET categoryName=?" +
                            "WHERE idCategory=?");
            preparedStatement.setString(1,object.getName());
            preparedStatement.setLong(2,object.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
