package com.team10.fp_a2.test;

import com.team10.fp_a2.data.model.person.*;
import com.team10.fp_a2.data.model.property.*;
import com.team10.fp_a2.data.model.others.*;
import com.team10.fp_a2.singleton.DatabaseConnection;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class DataTesting {

    public static void main(String[] args) {
        EntityManagerFactory emf = DatabaseConnection.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            // 1. Create Managers
            Manager manager1 = new Manager("Laura Williams", LocalDate.of(1978, 2, 28),
                    "laura.williams@example.com", "lauraw", "laurapass");
            Manager manager2 = new Manager("Michael Brown", LocalDate.of(1982, 11, 11),
                    "michael.brown@example.com", "mikeb", "mikesecure");

            em.persist(manager1);
            em.persist(manager2);

            // 2. Create Owners
            Owner owner1 = new Owner("Natalie Portman", LocalDate.of(1987, 6, 17),
                    "natalie.portman@example.com", "nataliep", "nataliepwd");
            Owner owner2 = new Owner("Oscar Isaac", LocalDate.of(1985, 9, 24),
                    "oscar.isaac@example.com", "oscari", "oscariPWD");

            // Assign Hosts to Owners
            Host host1 = new Host("Paul Walker", LocalDate.of(1990, 4, 3),
                    "paul.walker@example.com", "paulw", "paulwpwd");
            Host host2 = new Host("Quincy Adams", LocalDate.of(1989, 10, 16),
                    "quincy.adams@example.com", "quincya", "quincyaPWD");

            owner1.addHost(host1);
            owner2.addHost(host2);

            em.persist(owner1);
            em.persist(owner2);
            em.persist(host1);
            em.persist(host2);

            // 3. Create Tenants
            Tenant tenant1 = new Tenant("Rachel Green", LocalDate.of(1994, 1, 8),
                    "rachel.green@example.com", "rachelg", "rachelgpwd");
            Tenant tenant2 = new Tenant("Samuel Jackson", LocalDate.of(1991, 7, 19),
                    "samuel.jackson@example.com", "samj", "samjPWD");

            em.persist(tenant1);
            em.persist(tenant2);

            // 4. Create Properties
            ResidentialProperty resProp1 = new ResidentialProperty("789 Birch Lane, Riverside",
                    275000.00, owner1, 2, true, true);
            ResidentialProperty resProp2 = new ResidentialProperty("321 Elm Street, Lakeside",
                    325000.00, owner2, 5, false, false);

            CommercialProperty commProp1 = new CommercialProperty("654 Spruce Avenue, Hilltown",
                    550000.00, owner1, "Restaurant", true, 1800.00);
            CommercialProperty commProp2 = new CommercialProperty("987 Willow Drive, Brookfield",
                    475000.00, owner2, "Retail", true, 2200.00);

            // Assign Hosts to Properties
            host1.addProperty(resProp1);
            host1.addProperty(commProp1);
            host2.addProperty(resProp2);
            host2.addProperty(commProp2);

            em.persist(resProp1);
            em.persist(resProp2);
            em.persist(commProp1);
            em.persist(commProp2);

            // 5. Create Rental Agreements
            RentalAgreement agreement1 = new RentalAgreement();
            agreement1.setOwner(owner1);
            agreement1.setHost(host1);
            agreement1.setProperty(resProp1);
            agreement1.setPeriod(24);
            agreement1.setContractDate(LocalDate.now().minusDays(30));
            agreement1.setRentingFee(48000.00); // Example total rent for 24 months
            agreement1.setStatus(RentalAgreement.RentalStatus.ACTIVE);

            agreement1.addTenant(tenant1);
            agreement1.addTenant(tenant2);

            em.persist(agreement1);

            // 6. Create Payments for Rental Agreement
            double paymentPerTenant = agreement1.getRentingFee() / agreement1.getTenants().size();
            for (Tenant tenant : agreement1.getTenants()) {
                Payment payment = new Payment();
                payment.setTenant(tenant);
                payment.setRentalAgreement(agreement1);
                payment.setAmount(paymentPerTenant / 24); // Monthly payment
                payment.setDate(LocalDate.now().minusDays(15));
                payment.setPaymentMethod(Payment.PaymentMethod.CREDIT_CARD);
                payment.setStatus(Payment.PaymentStatus.COMPLETED);

                agreement1.addPayment(payment);
                em.persist(payment);
            }

            em.getTransaction().commit();
            System.out.println("New sample data has been successfully persisted to the database.");

            // 7. Retrieve and Display Data for Verification
            displayData(em);

        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("An error occurred while persisting sample data: " + e.getMessage());
            e.printStackTrace();
        } finally {
            em.close();
            DatabaseConnection.close();
        }
    }

    private static void displayData(EntityManager em) {
        // Fetch Owners
        TypedQuery<Owner> ownerQuery = em.createQuery("SELECT o FROM Owner o", Owner.class);
        List<Owner> owners = ownerQuery.getResultList();
        System.out.println("\n--- Owners ---");
        for (Owner owner : owners) {
            System.out.println("Owner ID: " + owner.getId() + ", Name: " + owner.getFullName());
            System.out.println("Managed Hosts:");
            for (Host host : owner.getHosts()) {
                System.out.println("\tHost ID: " + host.getId() + ", Name: " + host.getFullName());
            }
            System.out.println("Owned Properties:");
            for (Property property : owner.getOwnedProperties()) {
                System.out.println("\tProperty ID: " + property.getId() + ", Address: " + property.getAddress()
                        + ", Type: " + property.getClass().getSimpleName() + ", Status: " + property.getStatus());
            }
            System.out.println();
        }

        // Fetch Tenants
        TypedQuery<Tenant> tenantQuery = em.createQuery("SELECT t FROM Tenant t", Tenant.class);
        List<Tenant> tenants = tenantQuery.getResultList();
        System.out.println("\n--- Tenants ---");
        for (Tenant tenant : tenants) {
            System.out.println("Tenant ID: " + tenant.getId() + ", Name: " + tenant.getFullName());
            System.out.println("Rental Agreements:");
            for (RentalAgreement agreement : tenant.getRentalAgreements()) {
                System.out.println("\tAgreement ID: " + agreement.getId() + ", Property: " + agreement.getProperty().getAddress()
                        + ", Status: " + agreement.getStatus());
            }
            System.out.println();
        }

        // Fetch Properties
        TypedQuery<Property> propertyQuery = em.createQuery("SELECT p FROM Property p", Property.class);
        List<Property> properties = propertyQuery.getResultList();
        System.out.println("\n--- Properties ---");
        for (Property property : properties) {
            System.out.println("Property ID: " + property.getId() + ", Address: " + property.getAddress()
                    + ", Type: " + property.getClass().getSimpleName() + ", Status: " + property.getStatus());
            System.out.println("\tOwner: " + property.getOwner().getFullName());
            System.out.println("\tHosts Managing: ");
            for (Host host : property.getHosts()) {
                System.out.println("\t\tHost ID: " + host.getId() + ", Name: " + host.getFullName());
            }
            System.out.println();
        }

        // Fetch Rental Agreements
        TypedQuery<RentalAgreement> agreementQuery = em.createQuery("SELECT ra FROM RentalAgreement ra", RentalAgreement.class);
        List<RentalAgreement> agreements = agreementQuery.getResultList();
        System.out.println("\n--- Rental Agreements ---");
        for (RentalAgreement ra : agreements) {
            System.out.println("Agreement ID: " + ra.getId() + ", Property: " + ra.getProperty().getAddress()
                    + ", Owner: " + ra.getOwner().getFullName() + ", Host: " + ra.getHost().getFullName()
                    + ", Status: " + ra.getStatus());
            System.out.println("\tTenants:");
            for (Tenant tenant : ra.getTenants()) {
                System.out.println("\t\tTenant ID: " + tenant.getId() + ", Name: " + tenant.getFullName());
            }
            System.out.println("\tPayments:");
            for (Payment payment : ra.getPayments()) {
                System.out.println("\t\tPayment ID: " + payment.getId() + ", Tenant: " + payment.getTenant().getFullName()
                        + ", Amount: " + payment.getAmount() + ", Status: " + payment.getStatus());
            }
            System.out.println();
        }

        // Fetch Payments
        TypedQuery<Payment> paymentQuery = em.createQuery("SELECT p FROM Payment p", Payment.class);
        List<Payment> payments = paymentQuery.getResultList();
        System.out.println("\n--- Payments ---");
        for (Payment payment : payments) {
            System.out.println("Payment ID: " + payment.getId() + ", Tenant: " + payment.getTenant().getFullName()
                    + ", Amount: " + payment.getAmount() + ", Date: " + payment.getDate()
                    + ", Method: " + payment.getPaymentMethod() + ", Status: " + payment.getStatus());
        }
    }
}
