package aadd2.javafxtest;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXML;

public class AppController {

    @FXML
    public Button addButton;
    @FXML
    public TextField nameField;
    @FXML
    public Button updateButton;
    @FXML
    public Button deleteButton;
    @FXML
    public TableColumn nameColumn;
    @FXML
    public TableColumn idColumn;
    @FXML
    public TableView tableView;

    @FXML
    public void handleAdd(ActionEvent actionEvent) {
    }
    @FXML
    public void handleUpdate(ActionEvent actionEvent) {
    }
    @FXML
    public void handleDelete(ActionEvent actionEvent) {
    }
}
