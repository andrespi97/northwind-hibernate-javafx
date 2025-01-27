package aadd2.javafxtest.controller;
import aadd2.javafxtest.model.Product;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateSession {
    private static HibernateSession instance;
    private SessionFactory sessionFactory;

    private HibernateSession() {
        // Inicialización del SessionFactory
        try {
            sessionFactory = new Configuration().configure().addAnnotatedClass(Product.class).buildSessionFactory();
        } catch  (Throwable ex) {
            System.err.println("Error al crear el SessionFactory: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static synchronized HibernateSession getInstance() {
        if (instance == null) {
            instance = new HibernateSession();
        }
        return instance;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    public void close() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}