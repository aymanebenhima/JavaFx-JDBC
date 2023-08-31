package com.ben.tp_javafx_orm.dao.entities;

public class Product {

    private long productId;
    private String productName;
    private String productReference;
    private float productPrice;

    private Category productCategory;

    public Product() {
    }

    public Product(String productName, String productReference, float productPrice, Category category) {
        this.productName = productName;
        this.productReference = productReference;
        this.productPrice = productPrice;
        this.productCategory = category;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductReference() {
        return productReference;
    }

    public void setProductReference(String productReference) {
        this.productReference = productReference;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    public Category getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(Category productCategory) {
        this.productCategory = productCategory;
    }

    @Override
    public String toString() {
        return "productId:" + productId +
                ", productName:" + productName +
                ", productReference:" + productReference +
                ", productPrice:" + productPrice;
    }
}
