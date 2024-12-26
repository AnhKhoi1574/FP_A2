package com.team10.fp_a2.singleton;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DatabaseConnection {

    // The single instance of EntityManagerFactory
    private static EntityManagerFactory emf;

    // Private constructor to prevent instantiation
    private DatabaseConnection() {}

    // Thread-safe method to get the EntityManagerFactory instance
    public static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            synchronized (DatabaseConnection.class) {
                if (emf == null) {
                    emf = Persistence.createEntityManagerFactory("neonPU");
                }
            }
        }
        return emf;
    }

    // Method to get EntityManager instance
    public static EntityManager getEntityManager() {
        return getEntityManagerFactory().createEntityManager();
    }

    // Method to close the EntityManagerFactory when the application shuts down
    public static void close() {
        if (emf != null) {
            emf.close();
        }
    }

    // Test the connection (this is similar to your main method but now using this Singleton)
    public static void main(String[] args) {
        try {
            EntityManager em = getEntityManager();
            em.getTransaction().begin();
            System.out.println("Database connected.");
            em.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Database connection failed: " + e.getMessage());
        } finally {
            close(); // Ensure we close the EntityManagerFactory when done
        }
    }
}
