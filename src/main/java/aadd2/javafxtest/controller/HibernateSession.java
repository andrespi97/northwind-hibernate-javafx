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
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError("Error al crear el SessionFactory");
        }
    }

    public static HibernateSession getInstance() {
        if (instance == null) {
            instance = new HibernateSession();
        }
        return instance;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}