package aadd2.javafxtest.view;

import aadd2.javafxtest.controller.HibernateSession;
import aadd2.javafxtest.model.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

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
        // Iniciar Hibernate
        factory = HibernateSession.getInstance().getSessionFactory();
        mostrarProductos();
        // Iniciar la aplicación JavaFX
        launch(args);
    }
    // Método para realizar la consulta HQL (puedes llamarlo desde cualquier parte)
    public static void mostrarProductos() {
        // Abre la sesión
        try (Session session = factory.openSession()) {
            session.beginTransaction();

            // Consulta HQL para listar todos los productos
            List<Product> productos = session.createQuery("FROM Product", Product.class).list();

            // Muestra los productos en consola
            for (Product p : productos) {
                System.out.println("ID: " + p.getId() + ", Nombre: " + p.getProductName());
                System.out.println(p.getCategoryID());
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}