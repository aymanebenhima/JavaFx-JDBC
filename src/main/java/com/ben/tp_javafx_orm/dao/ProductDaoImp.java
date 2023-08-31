package com.ben.tp_javafx_orm.dao;

import com.ben.tp_javafx_orm.dao.entities.Category;
import com.ben.tp_javafx_orm.dao.entities.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImp implements ProductDao{
    @Override
    public Product find(long id) {
        Connection connection = ConnectionDBSingleton.getConnection();
        Product product = new Product();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM products WHERE idProduct=?");
            preparedStatement.setLong(1,id);
            ResultSet res = preparedStatement.executeQuery();
            if(res.next()){
                product.setProductId(res.getLong("idProduct"));
                product.setProductName(res.getString("productName"));
                product.setProductReference(res.getString("productReference"));
                product.setProductPrice(res.getFloat("productPrice"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return product;
    }

    @Override
    public List<Product> findAll() {
        Connection connection = ConnectionDBSingleton.getConnection();
        List<Product> products = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM products");
            ResultSet res = preparedStatement.executeQuery();
            while (res.next()){
                Product product = new Product();
                product.setProductId(res.getLong("idProduct"));
                product.setProductName(res.getString("productName"));
                product.setProductReference(res.getString("productReference"));
                product.setProductPrice(res.getFloat("productPrice"));
                PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT * FROM category WHERE idCategory=?");
                preparedStatement1.setLong(1, res.getLong("categoryId"));
                ResultSet res1 = preparedStatement1.executeQuery();
                Category category = new Category();
                if(res1.next()) category.setName(res1.getString("categoryName"));
                product.setProductCategory(category);
                products.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }

    @Override
    public void save(Product object) {
        Connection connection = ConnectionDBSingleton.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO products(productName,productReference,productPrice,categoryId)" +
                            "VALUES(?,?,?,?)"
            );
            preparedStatement.setString(1, object.getProductName());
            preparedStatement.setString(2,object.getProductReference());
            preparedStatement.setFloat(3, object.getProductPrice());
            preparedStatement.setLong(4, object.getProductCategory().getId());
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
                    "DELETE FROM products WHERE idProduct=?");
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Product object) {
        Connection connection = ConnectionDBSingleton.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE products SET productName=?, productReference=?, productPrice=?" +
                            "WHERE idProduct=?");
            preparedStatement.setString(1,object.getProductName());
            preparedStatement.setString(2,object.getProductReference());
            preparedStatement.setFloat(3,object.getProductPrice());
            preparedStatement.setLong(4,object.getProductId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> findByQuery(String query) {
        Connection connection = ConnectionDBSingleton.getConnection();
        List<Product> products = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM products WHERE productName Like ? " +
                            "OR productReference LIKE ?" +
                            "OR productPrice LIKE ?");
            preparedStatement.setString(1,"%"+query+"%");
            preparedStatement.setString(2,"%"+query+"%");
            preparedStatement.setString(3,"%"+query+"%");
            ResultSet res = preparedStatement.executeQuery();
            while (res.next()){
                Product product = new Product();
                product.setProductId(res.getLong("idProduct"));
                product.setProductName(res.getString("productName"));
                product.setProductReference(res.getString("productReference"));
                product.setProductPrice(res.getFloat("productPrice"));
                products.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }
}
