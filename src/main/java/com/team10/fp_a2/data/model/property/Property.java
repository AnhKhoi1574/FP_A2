package com.team10.fp_a2.data.model.property;

import com.team10.fp_a2.data.model.person.*;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private double price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PropertyStatus status = PropertyStatus.AVAILABLE;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner owner;

    @ManyToMany(mappedBy = "managedProperties")
    private List<Host> hosts = new ArrayList<>();


    public Property() {
        // Default constructor
    }

    public Property(String address, double price, Owner owner) {
        this.address = address;
        this.price = price;
        this.owner = owner;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getPricing() {
        return price;
    }

    public void setPricing(double price) {
        this.price = price;
    }

    public PropertyStatus getStatus() {
        return status;
    }

    public void setStatus(PropertyStatus status) {
        this.status = status;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public List<Host> getHosts() {
        return hosts;
    }

    public void setHosts(List<Host> hosts) {
        this.hosts = hosts;
    }

    public enum PropertyStatus {
        AVAILABLE,
        RENTED,
        MAINTENANCE,
        UNDER_CONSTRUCTION
    }
}
