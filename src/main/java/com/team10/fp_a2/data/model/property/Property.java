package com.team10.fp_a2.data.model.property;

import com.team10.fp_a2.data.model.person.Host;
import com.team10.fp_a2.data.model.person.Owner;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String id;

    @Column(name = "address")
    private String address;

    @Column(name = "pricing")
    private double pricing;

    @Column(name = "status")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Owner owner;
    
    @ManyToMany
    @JoinColumn(name = "property_id")
    @Column(name = "host")
    private List<Host> hosts;

    /**
     * Default constructor initializing the property with default values.
     * - Status is set to "available."
     * - Hosts list is initialized as an empty list.
     */
    public Property() {
        this.hosts = new ArrayList<>();
        this.status = "available";
    }

    /**
     * Parameterized constructor to initialize the property with specific values.
     */
    public Property(String id, String address, double pricing, Owner owner) {
        this.id = id;
        this.address = address;
        this.pricing = pricing;
        this.owner = owner;
        this.hosts = new ArrayList<>();
        this.status = "available";
    }

    // Getters and setters for all fields
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getPricing() {
        return pricing;
    }

    public void setPricing(double pricing) {
        this.pricing = pricing;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public List<String> getHosts() {
        return hosts;
    }

    public void setHosts(List<String> hosts) {
        this.hosts = hosts;
    }
}
