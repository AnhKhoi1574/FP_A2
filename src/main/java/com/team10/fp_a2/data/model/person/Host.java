package com.team10.fp_a2.data.model.person;

import com.team10.fp_a2.data.model.property.Property;
import com.team10.fp_a2.data.model.others.RentalAgreement;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hosts")
public class Host extends Person {

    @ManyToMany
    @JoinTable(
            name = "host_managed_properties",
            joinColumns = @JoinColumn(name = "host_id"),
            inverseJoinColumns = @JoinColumn(name = "property_id")
    )
    private List<Property> managedProperties = new ArrayList<>();

    @OneToMany(mappedBy = "host", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RentalAgreement> rentalAgreements = new ArrayList<>();

    @ManyToMany(mappedBy = "hosts")
    private List<Owner> owners = new ArrayList<>();

    public Host() {
        // Default constructor
    }

    public List<Property> getManagedProperties() {
        return managedProperties;
    }

    public void setManagedProperties(List<Property> managedProperties) {
        this.managedProperties = managedProperties;
    }

    public List<RentalAgreement> getRentalAgreements() {
        return rentalAgreements;
    }

    public void setRentalAgreements(List<RentalAgreement> rentalAgreements) {
        this.rentalAgreements = rentalAgreements;
    }

    public List<Owner> getOwners() {
        return owners;
    }

    public void setOwners(List<Owner> owners) {
        this.owners = owners;
    }
}
