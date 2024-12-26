package com.team10.fp_a2.data.model.person;

import com.team10.fp_a2.data.model.others.*;
import com.team10.fp_a2.data.model.property.*;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an owner in the rental system.
 * An owner can have properties and collaborations with other owners.
 *
 * @author <Phan Anh Khoi - S3980639>
 */

@Entity
@Table(name = "owners")
public class Owner extends Person {

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Property> ownedProperties;

    private List<Host> collaboratedHosts;

    @ManyToMany
    @JoinTable(
            name = "owner_collaborations",
            joinColumns = @JoinColumn(name = "owner_id"),
            inverseJoinColumns = @JoinColumn( name = "host_id")
    )

    @OneToMany( mappedBy = "")
    private List<RentalAgreement> rentalAgreements;


    /**
     * Default constructor initializing an owner with empty lists for properties and collaborations.
     */
    public Owner() {
        this.collaboratedHosts = new ArrayList<>();
        this.ownedProperties = new ArrayList<>();
        this.rentalAgreements = new ArrayList<>();
    }

    /**
     * Parameterized constructor to create an owner with specified details.
     *
     * @param id                The unique ID of the owner.
     * @param email             The email address of the owner.
     * @param dob               The date of birth of the owner.
     * @param fullName          The full name of the owner.
     */
    public Owner(String id, String email, LocalDate dob, String fullName, String username, String password) {
        super(id, email, dob, fullName, username, password);
        this.collaboratedHosts = new ArrayList<>();
        this.ownedProperties = new ArrayList<>();
    }

    /**
     * Retrieves the list of property IDs owned by the owner.
     *
     * @return A list of property IDs.
     */
    public List<Property> getOwnedProperties() {
        return ownedProperties;
    }

    /**
     * Retrieves the list of owner IDs collaborated with this owner.
     *
     * @return A list of collaborated owner IDs.
     */
    public List<Host> getCollaboratedOwner() {
        return collaboratedHosts;
    }

    /**
     * Sets the list of owner IDs collaborated with this owner.
     *
     * @param collaboratedHosts A list of host objects.
     */
    public void setCollaboratedHosts(List<Host> collaboratedHosts) {
        this.collaboratedHosts = collaboratedHosts;
    }

    public String ownedPropertiesString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ownedProperties.size(); i++) {
            sb.append(ownedProperties.get(i).toString());
            if (i<ownedProperties.size() - 1){
                sb.append(",");
            }
        }
        return sb.toString();
    }

    public String collaboratedHostsString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < collaboratedHosts.size(); i++) {
            sb.append(collaboratedHosts.get(i).toString());
            if (i<collaboratedHosts.size() - 1){
                sb.append(",");
            }
        }
        return sb.toString();
    }


    /**
     * Returns a string representation of the Owner object, formatted for CSV.
     *
     * @return A formatted CSV string representing the `Owner`.
     */
    @Override
    public String toString() {
        // Use the helper methods to get the CSV-style string representations for the properties and hosts
        String ownedPropertiesString = ownedPropertiesString();
        String collaboratedOwnerString = collaboratedHostsString();

        // Return the formatted CSV string
        return String.format("%s,%s,%s,%s,%s,%s",
                this.getId(),
                this.getFullName(),
                this.getDob(),
                this.getEmail(),
                collaboratedOwnerString,
                ownedPropertiesString
        );
    }


}
