package aadd2.javafxtest.controller;

import aadd2.javafxtest.model.Product;
import aadd2.javafxtest.view.ProductDAO;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import org.hibernate.Session;

import java.util.List;

public class AppController {
    private ProductDAO productDAO;
    @FXML
    public Button addButton;
    @FXML
    public TextField nameField;
    @FXML
    public Button updateButton;
    @FXML
    public Button deleteButton;
    @FXML
    public TableColumn <Product, String> nameColumn;
    @FXML
    public TableColumn<Product, Long> idColumn;
    @FXML
    public TableView tableView;

    public void initialize() {
        productDAO = new ProductDAO();
        idColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getId()).asObject());
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProductName()));
        showProductData();
    }

    @FXML
    public void handleAdd(ActionEvent actionEvent) {
        Product product = new Product();
        // Lógica para llenar el producto con valores desde la UI
        productDAO.save(product);
        showProductData();  // Actualiza la tabla después de guardar
    }
    @FXML
    public void handleUpdate(ActionEvent actionEvent) {

//            Product p = (Product) session.get(Product.class, numProducto);
//            if (p != null) {
//                p.setProductName("Tarta de Vigo");
//                productDAO.update(p);
//            } else {
//                System.out.println("No existe el producto");
//            }

    }
    @FXML
    public void handleDelete(ActionEvent actionEvent) {
        Product selectedProduct =(Product) tableView.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            productDAO.delete(selectedProduct);
            showProductData();  // Actualiza la tabla después de eliminar
        }
    }


    private void showProductData() {
        List<Product> products = productDAO.getAll();
        tableView.setItems(FXCollections.observableList(products));
    }

}
