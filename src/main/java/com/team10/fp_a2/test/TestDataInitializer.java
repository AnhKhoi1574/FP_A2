package com.team10.fp_a2.test;

import com.team10.fp_a2.data.model.person.*;
import com.team10.fp_a2.data.model.property.*;
import com.team10.fp_a2.data.model.others.*;
import com.team10.fp_a2.singleton.DatabaseConnection;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestDataInitializer {

    public static void main(String[] args) {
        EntityManagerFactory emf = DatabaseConnection.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            // 1. Create Managers
            Manager manager1 = new Manager(
                    "Khoi Phan",
                    LocalDate.of(1980, 5, 15),
                    "khoiphan123456@example.com",
                    "khoiphan12343546",
                    "password1235435"
            );
            Manager manager2 = new Manager(
                    "Hoang Tran",
                    LocalDate.of(1980, 5, 15),
                    "hoang.tran@example.com",
                    "alicej",
                    "password12345433"
            );

            em.persist(manager1);
            em.persist(manager2);

            // 2. Create Owners
            Owner owner1 = new Owner(
                    "Khoi Phan",
                    LocalDate.of(1980, 5, 15),
                    "khoiphan56788@example.com",
                    "khoiphan1232348888899",
                    "password124354"
            );

            Owner owner2 = new Owner("Diana Prince", LocalDate.of(1985, 12, 5),
                    "diana.prince@example.com", "dianap", "dianapwd");

            // Assign Hosts to Owners
            Host host1 = new Host("Ethan Hunt", LocalDate.of(1988, 7, 19),
                    "ethan.hunt@example.com", "ethanh", "ethanhpwd");
            Host host2 = new Host("Fiona Gallagher", LocalDate.of(1992, 11, 30),
                    "fiona.gallagher@example.com", "fionag", "fionagpwd");

            em.persist(owner1);
            em.persist(owner2);
            em.persist(host1);
            em.persist(host2);



            // 3. Create Tenants
            Tenant tenant1 = new Tenant("George Martin", LocalDate.of(1995, 4, 25),
                    "george.martin@example.com", "georgem", "georgempwd");
            Tenant tenant2 = new Tenant("Hannah Lee", LocalDate.of(1993, 9, 14),
                    "hannah.lee@example.com", "hannahl", "hannahlpwd");

            em.persist(tenant1);
            em.persist(tenant2);

            // 4. Create Properties
            ResidentialProperty resProp1 = new ResidentialProperty("123 Maple Street, Springfield",
                    250000.00, owner1, 3, true, false);
            ResidentialProperty resProp2 = new ResidentialProperty("456 Oak Avenue, Shelbyville",
                    300000.00, owner2, 4, true, true);

            CommercialProperty commProp1 = new CommercialProperty("789 Pine Road, Capital City",
                    500000.00, owner1, "Retail", true, 1500.00);
            CommercialProperty commProp2 = new CommercialProperty("321 Cedar Boulevard, Ogdenville",
                    450000.00, owner2, "Office", false, 2000.00);

            // Assign Hosts to Properties

            em.persist(resProp1);
            em.persist(resProp2);
            em.persist(commProp1);
            em.persist(commProp2);

            // 5. Create Rental Agreements
            RentalAgreement agreement1 = new RentalAgreement();
            agreement1.setOwner(owner1);
            agreement1.setHost(host1);
            agreement1.setProperty(resProp1);
            agreement1.setPeriod(12);
            agreement1.setContractDate(LocalDate.now());
            agreement1.setRentingFee(24000.00); // Example total rent for 12 months
            agreement1.setStatus(RentalAgreement.RentalStatus.ACTIVE);

            ArrayList<Tenant> tenants = new ArrayList<>();
            tenants.add(tenant1);
            tenants.add(tenant2);
            agreement1.setTenants(tenants);

            em.persist(agreement1);

            // 6. Create Payments for Rental Agreement
            for (Tenant tenant : agreement1.getTenants()) {
                Payment payment = new Payment();
                payment.setTenant(tenant);
                payment.setRentalAgreement(agreement1);
                payment.setAmount(1000);
                payment.setDate(LocalDate.now());
                payment.setPaymentMethod(Payment.PaymentMethod.BANK_TRANSFER);
                payment.setStatus(Payment.PaymentStatus.PENDING);

                ArrayList<Payment> payments = new ArrayList<>();
                payments.add(payment);
                agreement1.setPayments(payments);
                em.persist(payment);
            }

            em.getTransaction().commit();
            System.out.println("Sample data has been successfully persisted to the database.");

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