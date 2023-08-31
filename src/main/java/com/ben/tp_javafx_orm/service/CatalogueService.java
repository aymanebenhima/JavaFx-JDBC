package com.ben.tp_javafx_orm.service;

import com.ben.tp_javafx_orm.dao.entities.Category;
import com.ben.tp_javafx_orm.dao.entities.Product;

import java.util.List;

public interface CatalogueService {

    List<Product> getAllProducts();
    void addProduct(Product product);
    void updateProduct(Product product);
    void deleteProduct(long id);

    List<Category> getAllCategories();
    void addCategory(Category category);
    void updateCategory(Category category);
    void deleteCategory(long id);

    List<Product> searchProductByQuery(String key);

}
