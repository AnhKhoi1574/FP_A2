package com.team10.fp_a2.data.model.others;

import jakarta.persistence.*;
import com.team10.fp_a2.data.model.person.Tenant;

import java.time.LocalDate;

/**
 * Represents a payment made by a tenant for rental services.
 * Includes details such as payment ID, tenant ID, amount, date, and payment method.
 */
@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tenant", nullable = false)
    private Tenant tenant;

    @ManyToOne
    @JoinColumn(name = "rentalAgreement", nullable = false)
    private RentalAgreement rentalAgreement;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private LocalDate date;

    @Column(name = "paymentMethod", nullable = false)
    private String paymentMethod;

    @Column(name = "status")
    private String status;

    public Payment() {
    }

    public Payment(Tenant tenant, RentalAgreement rentalAgreement, double amount, String paymentMethod, LocalDate date) {
        this.tenant = tenant;
        this.rentalAgreement = rentalAgreement;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.date = date;
        this.status = status;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public RentalAgreement getRentalAgreement() {
        return rentalAgreement;
    }

    public void setRentalAgreement(RentalAgreement rentalAgreement) {
        this.rentalAgreement = rentalAgreement;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("Payment{id=%d, tenant=%s, rentalAgreement=%s, amount=%.2f, date=%s, paymentMethod='%s'}",
                id, tenant.getId(), rentalAgreement.getId(), amount, date, paymentMethod);
    }
}
