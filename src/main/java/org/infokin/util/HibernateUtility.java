package org.infokin.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Helper class for creating a database session.
 */
public class HibernateUtility {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    public static void init() {
        buildSessionFactory();
    }

    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
