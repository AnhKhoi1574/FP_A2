package com.team10.fp_a2.data.model.person;

import com.team10.fp_a2.data.model.others.Payment;
import com.team10.fp_a2.data.model.others.RentalAgreement;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tenants")
@DiscriminatorValue("TENANT")
public class Tenant extends Person {

    @ManyToMany(mappedBy = "tenants")
    private List<RentalAgreement> rentalAgreements = new ArrayList<>();

    @OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> payments = new ArrayList<>();

    public Tenant() {}

    public Tenant(String fullName, LocalDate dob, String email, String username, String password) {
        super(fullName, dob, email, username, password);
    }

    public List<RentalAgreement> getRentalAgreements() {
        return rentalAgreements;
    }

    public void setRentalAgreements(List<RentalAgreement> rentalAgreements) {
        this.rentalAgreements = rentalAgreements;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }
}
