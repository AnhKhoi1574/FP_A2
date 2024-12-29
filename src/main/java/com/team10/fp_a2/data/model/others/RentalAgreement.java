package com.team10.fp_a2.data.model.others;

import jakarta.persistence.*;
import com.team10.fp_a2.data.model.person.Tenant;
import com.team10.fp_a2.data.model.person.Owner;
import com.team10.fp_a2.data.model.person.Host;
import com.team10.fp_a2.data.model.property.Property;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rental_agreements")
public class RentalAgreement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "rental_agreement_tenant_relationship",
            joinColumns = @JoinColumn(name = "agreement_id"), // FK to RentalAgreement
            inverseJoinColumns = @JoinColumn(name = "tenant_id")     // FK to Tenant
    )
    private List<Tenant> tenants = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @ManyToOne
    @JoinColumn(name = "host_id")
    private Host host;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    @Column(nullable = false)
    private int period;

    @Column(name = "contract_date")
    private LocalDate contractDate;

    @Column(name = "renting_fee")
    private double rentingFee;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RentalStatus status;

    @OneToMany(mappedBy = "rentalAgreement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> payments = new ArrayList<>();

    public RentalAgreement() {}

    public RentalAgreement(List<Tenant> tenants, Owner owner, Host host, Property property,
                           int period, LocalDate contractDate, double rentingFee, RentalStatus status) {
        this.tenants = tenants;
        this.owner = owner;
        this.host = host;
        this.property = property;
        this.period = period;
        this.contractDate = contractDate;
        this.rentingFee = rentingFee;
        this.status = status;
    }


    // Getters and Setters
    public Long getId() {
        return id;
    }

    public List<Tenant> getTenants() {
        return tenants;
    }

    public void setTenants(List<Tenant> tenants) {
        this.tenants = tenants;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public LocalDate getContractDate() {
        return contractDate;
    }

    public void setContractDate(LocalDate contractDate) {
        this.contractDate = contractDate;
    }

    public double getRentingFee() {
        return rentingFee;
    }

    public void setRentingFee(double rentingFee) {
        this.rentingFee = rentingFee;
    }

    public RentalStatus getStatus() {
        return status;
    }

    public void setStatus(RentalStatus status) {
        this.status = status;
    }


    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public enum RentalStatus {
        ACTIVE,
        COMPLETED,
        CANCELED
    }
}
