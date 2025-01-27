package aadd2.javafxtest.view;

import aadd2.javafxtest.controller.HibernateSession;
import aadd2.javafxtest.model.Product;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;

import java.net.URL;
import java.util.List;

public class MainApp extends Application {

    private static SessionFactory factory ;
    private static ProductDAO productDAO ;

    @FXML
    public Button addButton;
    @FXML
    public Button updateButton;
    @FXML
    public Button deleteButton;
    @FXML
    public TableView<Product> tableView; // Usa genéricos para evitar advertencias

    @Override
    public void start(Stage primaryStage) {
        try {
            // Verifica la ruta del archivo FXML
            URL fxmlUrl = getClass().getResource("/view/main.fxml");
            if (fxmlUrl == null) {
                System.err.println("No se pudo encontrar el archivo FXML. Verifica la ruta.");
                return;
            }
            System.out.println("Cargando FXML desde: " + fxmlUrl);
            // Cargar el archivo FXML
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            loader.setController(this); // Establece este controlador
            VBox root = loader.load();

            // Configurar columnas de la tabla
            configureTableColumns();

            // Obtener los datos de la base de datos
            showProductData();

            // Configurar la ventana
            Scene scene = new Scene(root, 1000, 600); // Ajusta el tamaño de la ventana
            primaryStage.setScene(scene);
            primaryStage.setTitle("Tabla de Productos");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void configureTableColumns() {
        // Crear columnas
        TableColumn<Product, Integer> productIDColumn = new TableColumn<>("Product ID");
        productIDColumn.setCellValueFactory(new PropertyValueFactory<>("productID"));

        TableColumn<Product, String> productNameColumn = new TableColumn<>("Product Name");
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));

//        TableColumn<Product, Integer> supplierIDColumn = new TableColumn<>("Supplier ID");
//        supplierIDColumn.setCellValueFactory(new PropertyValueFactory<>("supplierID"));
//
//        TableColumn<Product, Integer> categoryIDColumn = new TableColumn<>("Category ID");
//        categoryIDColumn.setCellValueFactory(new PropertyValueFactory<>("categoryID"));

        TableColumn<Product, String> quantityPerUnitColumn = new TableColumn<>("Quantity Per Unit");
        quantityPerUnitColumn.setCellValueFactory(new PropertyValueFactory<>("quantityPerUnit"));

        TableColumn<Product, Double> unitPriceColumn = new TableColumn<>("Unit Price");
        unitPriceColumn.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        TableColumn<Product, Short> unitsInStockColumn = new TableColumn<>("Units In Stock");
        unitsInStockColumn.setCellValueFactory(new PropertyValueFactory<>("unitsInStock"));

        TableColumn<Product, Short> unitsOnOrderColumn = new TableColumn<>("Units On Order");
        unitsOnOrderColumn.setCellValueFactory(new PropertyValueFactory<>("unitsOnOrder"));

        TableColumn<Product, Short> reorderLevelColumn = new TableColumn<>("Reorder Level");
        reorderLevelColumn.setCellValueFactory(new PropertyValueFactory<>("reorderLevel"));

        TableColumn<Product, Boolean> discontinuedColumn = new TableColumn<>("Discontinued");
        discontinuedColumn.setCellValueFactory(new PropertyValueFactory<>("discontinued"));

        // Añadir columnas a la tabla
        // supplierIDColumn, categoryIDColumn,
        tableView.getColumns().addAll(
                productIDColumn, productNameColumn,
                quantityPerUnitColumn, unitPriceColumn, unitsInStockColumn,
                unitsOnOrderColumn, reorderLevelColumn, discontinuedColumn
        );
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
        Product selectedProduct = tableView.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            // Lógica para actualizar valores desde la UI
            productDAO.update(selectedProduct);
            showProductData();  // Actualiza la tabla después de actualizar
        } else {
            System.out.println("No se ha seleccionado ningún producto.");
        }
    }

    @FXML
    public void handleDelete(ActionEvent actionEvent) {
        Product selectedProduct = tableView.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            productDAO.delete(selectedProduct);
            showProductData();  // Actualiza la tabla después de eliminar
        } else {
            System.out.println("No se ha seleccionado ningún producto.");
        }
    }

    private void showProductData() {
        List<Product> products = productDAO.getAll();
        Platform.runLater(() -> {
           tableView.setItems(FXCollections.observableList(products));
        });
    }

    public static void main(String[] args) {
        setFactory(HibernateSession.getInstance().getSessionFactory());
        setProductDAO( new ProductDAO(getFactory()));
        launch(args);
    }

    public static SessionFactory getFactory() {
        return factory;
    }

    public static void setFactory(SessionFactory factory) {
        MainApp.factory = factory;
    }

    public static ProductDAO getProductDAO() {
        return productDAO;
    }

    public static void setProductDAO(ProductDAO productDAO) {
        MainApp.productDAO = productDAO;
    }
}