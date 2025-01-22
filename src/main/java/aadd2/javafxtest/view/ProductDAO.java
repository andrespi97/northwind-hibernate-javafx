package aadd2.javafxtest.view;

import aadd2.javafxtest.model.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import aadd2.javafxtest.controller.HibernateSession;
import java.util.List;

public class ProductDAO {
    private SessionFactory sessionFactory;

    public ProductDAO() {
        sessionFactory = HibernateSession.getInstance().getSessionFactory();
    }

    public void save(Product product) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(product);
        session.getTransaction().commit();
        session.close();
    }

    public List<Product> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Product", Product.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching products", e);
        }

    }

    public void update(Product product) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.merge(product);
        session.getTransaction().commit();
        session.close();
    }

    public void delete(Product product) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.remove(product);
        session.getTransaction().commit();
        session.close();
    }
}
