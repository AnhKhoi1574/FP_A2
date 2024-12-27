package com.team10.fp_a2.singleton;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DatabaseConnection {

    private static EntityManagerFactory emf;

    private DatabaseConnection() {}

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

    public static void close() {
        if (emf != null) {
            emf.close();
        }
    }

    public static void main(String[] args) {
        try (EntityManager em = getEntityManagerFactory().createEntityManager()) {
            em.getTransaction().begin();
            System.out.println("Connected to Database.");
            em.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Connection Failed: " + e.getMessage());
        } finally {
            close();
        }
    }
}
