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
public class RentalAgreement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "main_tenant_id", nullable = false)
    private Tenant mainTenant;

    @ManyToMany
    @JoinTable(
            name = "rental_agreement_sub_tenants",
            joinColumns = @JoinColumn(name = "agreement_id"),
            inverseJoinColumns = @JoinColumn(name = "tenant_id")
    )
    private List<Tenant> subTenants = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "owner", nullable = false)
    private Owner owner;

    @ManyToOne
    @JoinColumn(name = "host", nullable = false)
    private Host host;

    @ManyToOne
    @JoinColumn(name = "property", nullable = false)
    private Property property;

    @Column(nullable = false)
    private int period;

    @Column(name = "contract_date", nullable = false)
    private LocalDate contractDate;

    @Column(name = "renting_fee", nullable = false)
    private double rentingFee;

    @Column(nullable = false)
    private String status;

    public RentalAgreement() {}

    public RentalAgreement(Tenant mainTenant, List<Tenant> subTenants, Owner owner, Host host, Property property,
                           int period, LocalDate contractDate, double rentingFee, String status) {
        this.mainTenant = mainTenant;
        this.subTenants = subTenants;
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

    public Tenant getMainTenant() {
        return mainTenant;
    }

    public void setMainTenant(Tenant mainTenant) {
        this.mainTenant = mainTenant;
    }

    public List<Tenant> getSubTenants() {
        return subTenants;
    }

    public void setSubTenants(List<Tenant> subTenants) {
        this.subTenants = subTenants;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
