package com.team10.fp_a2.data.model.person;

import com.team10.fp_a2.data.model.property.Property;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "owners")
@DiscriminatorValue("OWNER")
public class Owner extends Person{

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Property> ownedProperties = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "owner_host_relationships",
            joinColumns = @JoinColumn(name = "owner_id"),
            inverseJoinColumns = @JoinColumn(name = "host_id")
    )
    private List<Host> hosts = new ArrayList<>();

    public Owner() {}

    public Owner(String fullName, LocalDate dob, String email, String username, String password) {
        super(fullName, dob, email, username, password);
        this.ownedProperties = new ArrayList<>();
        this.hosts = new ArrayList<>();
    }

    public List<Property> getOwnedProperties() {
        return ownedProperties;
    }

    public void setOwnedProperties(List<Property> ownedProperties) {
        this.ownedProperties = ownedProperties;
    }

    public List<Host> getHosts() {
        return hosts;
    }

    public void setHosts(List<Host> hosts) {
        this.hosts = hosts;
    }

    //Additional method
    public void addHost(Host host) {
        hosts.add(host);
        host.getOwners().add(this);
    }

    public void removeHost(Host host) {
        hosts.remove(host);
        host.getOwners().remove(this);
    }
}
