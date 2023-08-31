package com.ben.tp_javafx_orm.service;

import com.ben.tp_javafx_orm.dao.CategoryDao;
import com.ben.tp_javafx_orm.dao.ProductDao;
import com.ben.tp_javafx_orm.dao.entities.Category;
import com.ben.tp_javafx_orm.dao.entities.Product;

import java.util.List;

public class CatalogueServiceImp implements CatalogueService{

    private ProductDao productDao;
    private CategoryDao categoryDao;

    public CatalogueServiceImp(ProductDao productDao, CategoryDao categoryDao) {
        this.productDao = productDao;
        this.categoryDao = categoryDao;
    }

    @Override
    public List<Product> getAllProducts() {
        return productDao.findAll();
    }

    @Override
    public void addProduct(Product product) {
        productDao.save(product);
    }

    @Override
    public void updateProduct(Product product) {
        productDao.update(product);
    }

    @Override
    public void deleteProduct(long id) {
        productDao.delete(id);
    }


    @Override
    public List<Product> searchProductByQuery(String key) {
        return productDao.findByQuery(key);
    }
    @Override
    public List<Category> getAllCategories() {
        return categoryDao.findAll();
    }

    @Override
    public void addCategory(Category category) {
        categoryDao.save(category);
    }

    @Override
    public void updateCategory(Category category) {
        categoryDao.update(category);
    }

    @Override
    public void deleteCategory(long id) {
        categoryDao.delete(id);
    }

}
