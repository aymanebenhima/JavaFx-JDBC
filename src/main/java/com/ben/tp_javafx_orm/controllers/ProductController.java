package com.ben.tp_javafx_orm.controllers;

import com.ben.tp_javafx_orm.dao.CategoryDaoImp;
import com.ben.tp_javafx_orm.dao.ProductDaoImp;
import com.ben.tp_javafx_orm.dao.entities.Category;
import com.ben.tp_javafx_orm.dao.entities.Product;
import com.ben.tp_javafx_orm.service.CatalogueService;
import com.ben.tp_javafx_orm.service.CatalogueServiceImp;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.FocusModel;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class ProductController implements Initializable {

    @FXML
    private Button addBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private ComboBox<Category> productCategory;

    @FXML
    private TextField productName;

    @FXML
    private TextField productPrice;

    @FXML
    private TextField productSearch;

    @FXML
    private TextField referenceCode;

    @FXML
    private Button updateBtn;

    @FXML
    private TableView<Product> tableViewProduct;
    @FXML
    private TableColumn<String, Category> productColumnCategory;

    @FXML
    private TableColumn<Long, Product> productColumnId;

    @FXML
    private TableColumn<String, Product> productColumnName;

    @FXML
    private TableColumn<Float, Product> productColumnPrice;

    @FXML
    private TableColumn<String, Product> productColumnReference;

    private CatalogueService catalogueService;
    private ObservableList<Product> productObservableList = FXCollections.observableArrayList();
    private static final String SUCCESS = "Success Dialog";
    private static final String WARNING = "Warning Dialog";
    private Product selectedProduct;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        catalogueService = new CatalogueServiceImp(new ProductDaoImp(), new CategoryDaoImp());

        tableViewProduct.setItems(productObservableList);
        productColumnId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        productColumnName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productColumnReference.setCellValueFactory(new PropertyValueFactory<>("productReference"));
        productColumnPrice.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
        productColumnCategory.setCellValueFactory(new PropertyValueFactory<>("productCategory"));

        List<Category> categories = catalogueService.getAllCategories();
        productCategory.getItems().addAll(categories);

        loadData();

        productSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            productObservableList.clear();
            productObservableList.addAll(catalogueService.searchProductByQuery(newValue));
        });

        tableViewProduct.selectionModelProperty().addListener((observableValue, oldValue, newValue) -> {
            System.out.println(newValue);
        });
    }
    private void loadData(){
        productObservableList.clear();
        productObservableList.addAll(catalogueService.getAllProducts());
    }
    @FXML
    void addProduct(ActionEvent event) {
        String prdName = productName.getText();
        String prdReference = referenceCode.getText();
        String prdPrice = productPrice.getText();
        Category prdCategory = productCategory.getSelectionModel().getSelectedItem();
        if(verifyFields(prdName, prdReference, prdPrice) && prdCategory != null){
            Product product = new Product(
                    prdName,
                    prdReference,
                    Float.parseFloat(prdPrice),
                    prdCategory
            );
            catalogueService.addProduct(product);
            loadData();
            clearFields();
            displayPopup(SUCCESS, "Product added successfully.");
        }else{
            displayPopup(WARNING,"Please fill out all fields.");
        }
    }

    @FXML
    void deleteBtn(ActionEvent event) {
        Product product = tableViewProduct.getSelectionModel().getSelectedItem();
        catalogueService.deleteProduct(product.getProductId());
        loadData();
        displayPopup(SUCCESS, "Product deleted successfully.");
    }

    @FXML
    void editProduct(ActionEvent event) {
        selectedProduct = tableViewProduct.getSelectionModel().getSelectedItem();
        productName.setText(selectedProduct.getProductName());
        productPrice.setText(String.valueOf(selectedProduct.getProductPrice()));
        referenceCode.setText(selectedProduct.getProductReference());
        productCategory.selectionModelProperty().setValue(new SingleSelectionModel<Category>() {
            @Override
            protected Category getModelItem(int i) {
                return selectedProduct.getProductCategory();
            }

            @Override
            protected int getItemCount() {
                return 1;
            }
        });
        productCategory.setSelectionModel(new SingleSelectionModel<Category>() {
            @Override
            protected Category getModelItem(int i) {
                return selectedProduct.getProductCategory();
            }

            @Override
            protected int getItemCount() {
                return productCategory.getItems().size();
            }
        });
    }
    @FXML
    void updateBtn(ActionEvent event) {
        System.out.println(tableViewProduct.getSelectionModel().getSelectedItem());
        selectedProduct.setProductName(productName.getText());
        selectedProduct.setProductReference(referenceCode.getText());
        selectedProduct.setProductPrice(Float.parseFloat(productPrice.getText()));
        selectedProduct.setProductCategory(productCategory.getValue());
        catalogueService.updateProduct(selectedProduct);
        loadData();
        displayPopup("SUCCESS","Product Updated successfully.");
        clearFields();
    }

    private boolean verifyFields(String productName, String productReference, String productPrice){
        return !productName.isEmpty() && !productReference.isEmpty() && !productPrice.isEmpty();
    }

    private void displayPopup(String typeDialog, String text) {
        Alert alert = new Alert(
                Objects.equals(typeDialog, WARNING) ? Alert.AlertType.WARNING : Alert.AlertType.INFORMATION
        );
        alert.setTitle(typeDialog);
        alert.setHeaderText(text);
        alert.showAndWait();
    }

    private void clearFields(){
        productName.clear();
        productPrice.clear();
        referenceCode.clear();
    }



}