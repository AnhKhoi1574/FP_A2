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
import java.util.List;

public class DataTest {
    public static void main(String[] args) {
        EntityManagerFactory emf = DatabaseConnection.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            // 1. Create Owners
            Owner owner1 = new Owner(
                    "Nguyễn Văn An",
                    LocalDate.of(1980, 6, 15),
                    "nguyenvanan@example.com",
                    "nguyenvanan",
                    "matkhau123"
            );
            Owner owner2 = new Owner(
                    "Trần Thị Bích",
                    LocalDate.of(1985, 12, 5),
                    "tranthibich@example.com",
                    "tranbich",
                    "bichmatkhau"
            );

            // 2. Create Hosts
            Host host1 = new Host(
                    "Phạm Quốc Hưng",
                    LocalDate.of(1988, 7, 19),
                    "phamquochung@example.com",
                    "quochung",
                    "hungmatkhau"
            );
            Host host2 = new Host(
                    "Lê Thị Hồng",
                    LocalDate.of(1992, 11, 30),
                    "lethihong@example.com",
                    "hongle",
                    "hongmatkhau"
            );

            // Persist Owners and Hosts
            em.persist(owner1);
            em.persist(owner2);
            em.persist(host1);
            em.persist(host2);

            // Establish Relationships Between Owners and Hosts
            owner1.getHosts().add(host1);
            owner1.getHosts().add(host2);
            host1.getOwners().add(owner1);
            host2.getOwners().add(owner1);

            em.merge(owner1);
            em.merge(host1);
            em.merge(host2);

            // 3. Create Tenants
            Tenant tenant1 = new Tenant("Nguyễn Hữu Minh", LocalDate.of(1995, 4, 25),
                    "nguyenhuuminh@example.com", "huuminh", "minhmatkhau");
            Tenant tenant2 = new Tenant("Hoàng Anh Tú", LocalDate.of(1993, 9, 14),
                    "hoanganhtu@example.com", "anhtu", "tumathau");

            em.persist(tenant1);
            em.persist(tenant2);

            // 4. Create Properties
            ResidentialProperty resProp1 = new ResidentialProperty("123 Đường Hoa Phượng, Quận 1",
                    250000.00, owner1, 3, true, false);
            ResidentialProperty resProp2 = new ResidentialProperty("456 Đường Lê Văn Sỹ, Quận 3",
                    300000.00, owner2, 4, true, true);

            CommercialProperty commProp1 = new CommercialProperty("789 Đường Nguyễn Huệ, Quận 1",
                    500000.00, owner1, "Bán lẻ", true, 1500.00);
            CommercialProperty commProp2 = new CommercialProperty("321 Đường Cách Mạng Tháng 8, Quận 10",
                    450000.00, owner2, "Văn phòng", false, 2000.00);

            em.persist(resProp1);
            em.persist(resProp2);
            em.persist(commProp1);
            em.persist(commProp2);

            // Assign Hosts to Properties
            resProp1.getHosts().add(host1);
            host1.getManagedProperties().add(resProp1);
            commProp1.getHosts().add(host2);
            host2.getManagedProperties().add(commProp1);

            em.merge(resProp1);
            em.merge(commProp1);
            em.merge(host1);
            em.merge(host2);

            // 5. Create Rental Agreements
            RentalAgreement agreement1 = new RentalAgreement();
            agreement1.setOwner(owner1);
            agreement1.setHost(host1);
            agreement1.setProperty(resProp1);
            agreement1.setPeriod(12);
            agreement1.setContractDate(LocalDate.now());
            agreement1.setRentingFee(24000.00); // Example total rent for 12 months
            agreement1.setStatus(RentalAgreement.RentalStatus.ACTIVE);
            agreement1.getTenants().add(tenant1);
            agreement1.getTenants().add(tenant2);

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
        System.out.println("\n--- Owners ---");
        TypedQuery<Owner> ownerQuery = em.createQuery("SELECT o FROM Owner o", Owner.class);
        for (Owner owner : ownerQuery.getResultList()) {
            System.out.println("Chủ sở hữu ID: " + owner.getId() + ", Tên: " + owner.getFullName());
        }

        System.out.println("\n--- Hosts ---");
        TypedQuery<Host> hostQuery = em.createQuery("SELECT h FROM Host h", Host.class);
        for (Host host : hostQuery.getResultList()) {
            System.out.println("Chủ nhà ID: " + host.getId() + ", Tên: " + host.getFullName());
        }

        System.out.println("\n--- Tenants ---");
        TypedQuery<Tenant> tenantQuery = em.createQuery("SELECT t FROM Tenant t", Tenant.class);
        for (Tenant tenant : tenantQuery.getResultList()) {
            System.out.println("Người thuê ID: " + tenant.getId() + ", Tên: " + tenant.getFullName());
        }

        System.out.println("\n--- Properties ---");
        TypedQuery<Property> propertyQuery = em.createQuery("SELECT p FROM Property p", Property.class);
        for (Property property : propertyQuery.getResultList()) {
            System.out.println("Bất động sản ID: " + property.getId() + ", Địa chỉ: " + property.getAddress());
        }

        System.out.println("\n--- Rental Agreements ---");
        TypedQuery<RentalAgreement> agreementQuery = em.createQuery("SELECT ra FROM RentalAgreement ra", RentalAgreement.class);
        for (RentalAgreement agreement : agreementQuery.getResultList()) {
            System.out.println("Hợp đồng ID: " + agreement.getId() + ", Bất động sản: " + agreement.getProperty().getAddress());
        }

        System.out.println("\n--- Payments ---");
        TypedQuery<Payment> paymentQuery = em.createQuery("SELECT p FROM Payment p", Payment.class);
        for (Payment payment : paymentQuery.getResultList()) {
            System.out.println("Thanh toán ID: " + payment.getId() + ", Số tiền: " + payment.getAmount());
        }
    }
}
