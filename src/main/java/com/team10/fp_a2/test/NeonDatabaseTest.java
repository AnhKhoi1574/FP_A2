package com.team10.fp_a2.test;

import com.team10.fp_a2.data.model.person.Host;
import com.team10.fp_a2.data.model.person.Owner;
import com.team10.fp_a2.data.model.person.Tenant;
import com.team10.fp_a2.data.model.property.CommercialProperty;
import com.team10.fp_a2.data.model.property.Property;
import com.team10.fp_a2.data.model.others.RentalAgreement;
import com.team10.fp_a2.data.model.property.ResidentialProperty;
import com.team10.fp_a2.singleton.DatabaseConnection;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.util.List;

public class NeonDatabaseTest {
    public static void main(String[] args) {
        EntityManager em = DatabaseConnection.getEntityManagerFactory().createEntityManager();

        try {
            em.getTransaction().begin();

            // Create and persist Host
            Host host = new Host();
            host.setFullName("Hoang Host");
            host.setDob(LocalDate.of(1999, 1, 1));
            host.setEmail("host@example1.com");
            host.setUsername("host_user1");
            host.setPassword("password1");
            em.persist(host);

            // Create and persist Owner
            Owner owner = new Owner();
            owner.setFullName("Ha Owner");
            owner.setDob(LocalDate.of(1980, 1, 1));
            owner.setEmail("owner@example1.com");
            owner.setUsername("owner_user1");
            owner.setPassword("password1");
            em.persist(owner);

            // Create and persist Property
            Property property = new ResidentialProperty("890 Main St", 1900.0, owner, 2, true, true);
            em.persist(property);

            // Create and persist Property
            Property property2 = new CommercialProperty("456 Main St", 500.0, owner, "office", true, 20);
            em.persist(property2);

            // Create and persist Tenant
            Tenant tenant = new Tenant("Yen Tenant", LocalDate.of(2003, 1, 1), "tenant1@example.com", "tenant_user1", "password1");
            em.persist(tenant);

            // Create and persist RentalAgreement
            RentalAgreement rentalAgreement = new RentalAgreement();
            rentalAgreement.setHost(host); // Assign Host
            rentalAgreement.setOwner(owner); // Assign Owner
            rentalAgreement.setProperty(property2); // Assign Property
            rentalAgreement.setTenants(List.of(tenant)); // Assign Tenants
            rentalAgreement.setPeriod(20);
            rentalAgreement.setContractDate(LocalDate.now());
            rentalAgreement.setRentingFee(2000.0);
            rentalAgreement.setStatus("Active");

            em.persist(rentalAgreement);

            em.getTransaction().commit();

            // Retrieve and validate data
            System.out.println("RentalAgreement persisted with ID: " + rentalAgreement.getId());

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
