package com.team10.fp_a2.data.model.person;

import com.team10.fp_a2.data.model.others.*;
import com.team10.fp_a2.data.model.property.*;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a host in the rental system.
 * Inherits from the Person class and manages host-specific data like managed properties, rental agreements, and owners.
 *
 * @author <Phan Anh Khoi - S3980639>
 */
@Entity
@Table(name = "hosts")
public class Host extends Person {
    @ManyToMany(mappedBy = "hosts")
    private List<Property> managedProperties;

    private List<Owner> managedOwners;

    @OneToMany(mappedBy = "hosts", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RentalAgreement> managedRentalAgreements;

    /**
     * Default constructor initializing empty lists for properties, rental agreements, and owners.
     */
    public Host() {
        this.managedProperties = new ArrayList<>();
        this.managedRentalAgreements = new ArrayList<>();
        this.managedOwners = new ArrayList<>();
    }

    /**
     * Creates a host with the specified details.
     *
     * @param id                     The host's ID.
     * @param email                  The host's email address.
     * @param dob                    The host's date of birth.
     * @param fullName               The host's full name.
     */
    public Host(String id, String email, LocalDate dob, String fullName, String username, String password) {
        super(id, email, dob, fullName, username, password);
        this.managedProperties = new ArrayList<>();
        this.managedRentalAgreements = new ArrayList<>();
        this.managedOwners = new ArrayList<>();
    }

    /**
     * Creates a host with the specified details.
     *
     * @param id                     The host's ID.
     * @param email                  The host's email address.
     * @param dob                    The host's date of birth.
     * @param fullName               The host's full name.
     * @param managedProperties      A list of property IDs managed by the host.
     * @param managedRentalAgreements A list of rental agreement IDs managed by the host.
     * @param managedOwners          A list of owner IDs managed by the host.
     */
    public Host(String id, String email, LocalDate dob, String fullName, String username, String password, List<Property> managedProperties, List<Owner> managedOwners, List<RentalAgreement> managedRentalAgreements) {
        super(id, email, dob, fullName, username, password);
        this.managedProperties = managedProperties;
        this.managedOwners = managedOwners;
        this.managedRentalAgreements = managedRentalAgreements;
    }

    /**
     * Returns a string representation of the Host object, formatted for CSV.
     *
     * @return A CSV-formatted string representing the Host.
     */
    @Override
    public String toString() {
        String managedPropertiesString = String.join(";", this.managedProperties);
        String managedRentalAgreementsString = String.join(";", this.managedRentalAgreements);
        String managedOwnersString = String.join(";", this.managedOwners);
        return String.format("%s,%s,%s,%s,%s,%s,%s",
                this.getId(),
                this.getFullName(),
                this.getDob(),
                this.getEmail(),
                managedPropertiesString,
                managedRentalAgreementsString,
                managedOwnersString
        );
    }

    /**
     * Retrieves all hosts who manage a specific property.
     *
     * @param propertyId The ID of the property to search for.
     * @return A list of hosts managing the specified property.
     */
    public static List<Host> getHostsForProperty(String propertyId) {
        List<String[]> hostsData = Helper.readData(FILEPATH);
        List<Host> hostsForProperty = new ArrayList<>();
        for (String[] data : hostsData) {
            String id = data[0];
            String fullName = data[1];
            String email = data[3];
            List<String> managedProperties = new ArrayList<>(List.of(data[4].split(";")));
            List<String> managedRentalAgreements = new ArrayList<>(List.of(data[5].split(";")));
            List<String> managedOwners = new ArrayList<>(List.of(data[6].split(";")));

            if (managedProperties.contains(propertyId)) {
                hostsForProperty.add(new Host(id, email, null, fullName, managedProperties, managedRentalAgreements, managedOwners));
            }
        }
        return hostsForProperty;
    }

    /**
     * Finds and retrieves a host by their unique ID.
     *
     * @param hostId The ID of the host to retrieve.
     * @return The `Host` object if found; null otherwise.
     */
    public static Host getHostById(String hostId) {
        List<Host> hosts = getAllHosts(true);
        for (Host host : hosts) {
            if (host.getId().equals(hostId)) {
                return host;
            }
        }
        return null;
    }

    /**
     * Adds a rental agreement ID to the host's managed rental agreements list.
     *
     * @param rentalAgreementId The ID of the rental agreement to add.
     */
    public void addRentalAgreement(String rentalAgreementId) {
        if (!this.managedRentalAgreements.contains(rentalAgreementId)) {
            this.managedRentalAgreements.add(rentalAgreementId);
        }
    }

    // Getter and Setter methods
    public List<String> getManagedRentalAgreements() {
        return managedRentalAgreements;
    }
}

