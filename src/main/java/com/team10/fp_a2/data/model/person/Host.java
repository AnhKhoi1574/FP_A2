package com.team10.fp_a2.data.model.person;

import com.team10.fp_a2.data.model.others.RentalAgreement;
import com.team10.fp_a2.data.model.property.Property;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hosts")
@DiscriminatorValue("HOST")
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

    public Host(String fullName, LocalDate dob, String email, String username, String password) {
        super(fullName, dob, email, username, password);
        this.managedProperties = new ArrayList<>();
        this.owners = new ArrayList<>();
        this.rentalAgreements = new ArrayList<>();
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

    // Additional method
    public void addOwner(Owner owner) {
        owners.add(owner);
        owner.getHosts().add(this);
    }

    public void removeOwner(Owner owner) {
        owners.remove(owner);
        owner.getHosts().remove(this);
    }

    public void addProperty (Property property){
        managedProperties.add(property);
        property.getHosts().add(this);
    }

    public void removeProperty (Property property){
        managedProperties.remove(property);
        property.getHosts().remove(this);
    }
}
