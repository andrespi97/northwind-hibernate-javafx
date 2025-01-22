package aadd2.javafxtest;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateSession {

    // Instancia única de la clase (Singleton)
    private static HibernateSession instance;

    // SessionFactory de Hibernate
    private SessionFactory sessionFactory;

    // Constructor privado para evitar instanciación externa
    private HibernateSession() {
        try {
            // Configura Hibernate y construye el SessionFactory
            sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        } catch (Exception e) {
            System.err.println("Error al inicializar Hibernate: " + e.getMessage());
            throw new ExceptionInInitializerError(e);
        }
    }

    // Método para obtener la instancia única (Singleton)
    public static HibernateSession getInstance() {
        if (instance == null) {
            synchronized (HibernateSession.class) { // Bloque para garantizar seguridad en aplicaciones multihilo
                if (instance == null) {
                    instance = new HibernateSession();
                }
            }
        }
        return instance;
    }

    // Método para obtener el SessionFactory
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    // Método para cerrar el SessionFactory
    public void closeSessionFactory() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }
}