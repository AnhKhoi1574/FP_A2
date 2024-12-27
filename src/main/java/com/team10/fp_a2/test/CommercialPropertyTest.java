package com.team10.fp_a2.test;

import com.team10.fp_a2.data.model.person.Owner;
import com.team10.fp_a2.data.model.property.CommercialProperty;
import com.team10.fp_a2.data.model.property.Property;
import com.team10.fp_a2.singleton.DatabaseConnection;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;

public class CommercialPropertyTest {
    public static void main(String[] args) {
        EntityManager em = DatabaseConnection.getEntityManagerFactory().createEntityManager();

        try {
            // Create and persist Owner
            Owner owner = new Owner();
            owner.setFullName("Jane Owner");
            owner.setDob(LocalDate.of(1990, 1, 1));
            owner.setEmail("owner@example.com");
            owner.setUsername("owner_user");
            owner.setPassword("password");
            em.persist(owner);

            // Create and persist Property
            Property property = new CommercialProperty("123 Main St", 1500.0, owner, "office", true, 20);
            em.persist(property);

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
            DatabaseConnection.close();
        }
    }
}
