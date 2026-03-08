package ma.projet.util;


import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Utilitaire pour la gestion de la SessionFactory Hibernate.
 */
public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            // Print l'exception pour faciliter le débogage
            System.err.println("Échec de la création de la SessionFactory : " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

     // Retourne l'instance unique de la SessionFactory.
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }


     // Ferme proprement les caches et les pools de connexions
    public static void shutdown() {
        getSessionFactory().close();
    }
}
