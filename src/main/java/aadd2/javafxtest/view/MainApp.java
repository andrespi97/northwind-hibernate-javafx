package aadd2.javafxtest.view;

import aadd2.javafxtest.controller.HibernateSession;
import aadd2.javafxtest.model.Category;
import aadd2.javafxtest.model.Product;
import aadd2.javafxtest.model.Supplier;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;

import java.net.URL;
import java.util.List;

public class MainApp extends Application {

    private static SessionFactory factory;
    private static ProductDAO productDAO;

    @FXML
    public Button addButton;
    @FXML
    public Button deleteButton;
    @FXML
    public TableView<Product> tableView; // Usa genéricos para evitar advertencias
    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;
    @FXML
    private void onSearch() {
        String idText = searchField.getText();
        if (idText.isEmpty()) return;

        try {
            int id = Integer.parseInt(idText);
            for (Product product : tableView.getItems()) {
                if (product.getProductID() == id) {
                    tableView.getSelectionModel().select(product);
                    tableView.scrollTo(product);
                    return;
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Introduce un ID válido");
        }
    }
    @FXML
    private void initialize() {
        searchButton.setOnAction(event -> onSearch());
    }

    public static void main(String[] args) {
        setFactory(HibernateSession.getInstance().getSessionFactory());
        setProductDAO(new ProductDAO(getFactory()));
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

    @Override
    public void start(Stage primaryStage) {
        try {
            // Verifica la ruta del archivo FXML
            URL fxmlUrl = getClass().getResource("/view/main.fxml");
            if (fxmlUrl == null) {
                System.err.println("No se pudo encontrar el archivo FXML. Verifica la ruta.");
            } else {
                System.out.println("Cargando FXML desde: " + fxmlUrl);// Cargar el archivo FXML
                FXMLLoader loader = new FXMLLoader(fxmlUrl);
                loader.setController(this); // Establece este controlador
                VBox root = loader.load();// Configurar columnas de la tabla
                configureTableColumns();// Obtener los datos de la base de datos
                showProductData();// Configurar la ventana
                Scene scene = new Scene(root, 1000, 600); // Ajusta el tamaño de la ventana
                primaryStage.setScene(scene);
                primaryStage.setTitle("Tabla de Productos");
                primaryStage.show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            addButton.setOnAction((ActionEvent event) -> {
                handleAdd(event);
            });

            deleteButton.setOnAction((ActionEvent event) -> {
                handleDelete(event);
            });
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
        productNameColumn.setCellFactory(TextFieldTableCell.forTableColumn()); // Hace la celda editable
        productNameColumn.setOnEditCommit(event -> {
            Product product = event.getRowValue();
            product.setProductName(event.getNewValue());
            productDAO.update(product); // Guarda el cambio en la base de datos

            showProductData();
        });

//        TableColumn<Product, Integer> supplierIDColumn = new TableColumn<>("Supplier ID");
//        supplierIDColumn.setCellValueFactory(new PropertyValueFactory<>("supplierID"));
//
//        TableColumn<Product, Integer> categoryIDColumn = new TableColumn<>("Category ID");
//        categoryIDColumn.setCellValueFactory(new PropertyValueFactory<>("categoryID"));

        TableColumn<Product, String> quantityPerUnitColumn = new TableColumn<>("Quantity Per Unit");
        quantityPerUnitColumn.setCellValueFactory(new PropertyValueFactory<>("quantityPerUnit"));
        quantityPerUnitColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        quantityPerUnitColumn.setOnEditCommit(event -> {
            Product product = event.getRowValue();
            product.setQuantityPerUnit(event.getNewValue());
            productDAO.update(product);
        });
        TableColumn<Product, Double> unitPriceColumn = new TableColumn<>("Unit Price");
        unitPriceColumn.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        unitPriceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new javafx.util.converter.DoubleStringConverter()));
        unitPriceColumn.setOnEditCommit(event -> {
            Product product = event.getRowValue();
            product.setUnitPrice(event.getNewValue());
            productDAO.update(product);
        });
        TableColumn<Product, Short> unitsInStockColumn = new TableColumn<>("Units In Stock");
        unitsInStockColumn.setCellValueFactory(new PropertyValueFactory<>("unitsInStock"));
        unitsInStockColumn.setCellFactory(TextFieldTableCell.forTableColumn(new javafx.util.converter.ShortStringConverter()));
        unitsInStockColumn.setOnEditCommit(event -> {
            Product product = event.getRowValue();
            product.setUnitsInStock(event.getNewValue());
            productDAO.update(product);
        });
        TableColumn<Product, Short> unitsOnOrderColumn = new TableColumn<>("Units On Order");
        unitsOnOrderColumn.setCellValueFactory(new PropertyValueFactory<>("unitsOnOrder"));

        TableColumn<Product, Short> reorderLevelColumn = new TableColumn<>("Reorder Level");
        reorderLevelColumn.setCellValueFactory(new PropertyValueFactory<>("reorderLevel"));

        TableColumn<Product, Boolean> discontinuedColumn = new TableColumn<>("Discontinued");
        discontinuedColumn.setCellValueFactory(new PropertyValueFactory<>("discontinued"));

        TableColumn<Product, Integer> supplierIDColumn = new TableColumn<>("Supplier ID");
        supplierIDColumn.setCellValueFactory(cellData -> {
            Supplier supplier = cellData.getValue().getSupplierID();
            return (supplier != null) ? new SimpleIntegerProperty(supplier.getId()).asObject() : null;
        });

        TableColumn<Product, Integer> categoryIDColumn = new TableColumn<>("Category ID");
        categoryIDColumn.setCellValueFactory(cellData -> {
            Category category = cellData.getValue().getCategoryID();
            return (category != null) ? new SimpleIntegerProperty(category.getId()).asObject() : null;
        });
        tableView.setEditable(true);
        // Añadir columnas a la tabla
        // supplierIDColumn, categoryIDColumn,
        tableView.getColumns().addAll(
                productIDColumn, productNameColumn,
                quantityPerUnitColumn, unitPriceColumn, unitsInStockColumn,
                unitsOnOrderColumn, reorderLevelColumn, discontinuedColumn,supplierIDColumn,categoryIDColumn
        );
    }

    /*    public void handleAdd(ActionEvent actionEvent) {
            Product product = new Product();
            // Lógica para llenar el producto con valores desde la UI

            productDAO.save(product);
            showProductData();  // Actualiza la tabla después de guardar
        }*/
    public void handleAdd(ActionEvent actionEvent) {
        // Crear un nuevo producto vacío con valores por defecto
        Product newProduct = new Product();
        newProduct.setProductName("");  // Nombre vacío para que el usuario lo edite
        newProduct.setQuantityPerUnit("");
        newProduct.setUnitPrice(0.0);
        newProduct.setUnitsInStock((short) 0);

        // Agregarlo a la tabla
        tableView.getItems().add(newProduct);
        tableView.scrollTo(newProduct);
        // Iniciar edición en la primera columna editable (Product Name)
        Platform.runLater(() -> {
            tableView.requestFocus();
            tableView.getSelectionModel().select(newProduct);
            tableView.getFocusModel().focus(tableView.getItems().size() - 1, tableView.getColumns().get(1)); // Columna "Product Name"
            tableView.edit(tableView.getItems().size() - 1, tableView.getColumns().get(1)); // Activar edición
        });
    }

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
}