package aadd2.javafxtest.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;

public class MainApp extends Application {
    private static SessionFactory factory;

    @Override
    public void start(Stage stage) throws Exception {
        // Cargar el archivo FXML
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/aadd2/javafxtest/app.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    public static void main(String[] args) {
        // Iniciar la aplicación JavaFX
        launch(args);
    }
}