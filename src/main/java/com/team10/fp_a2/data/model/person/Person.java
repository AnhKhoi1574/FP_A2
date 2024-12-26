package com.team10.fp_a2.data.model.person;

import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * Represents a generic person in the system.
 * This is an abstract class that can be extended for specific roles (e.g., Host, Tenant).
 *
 * @author <Phan Anh Khoi - S3980639>
 */

@Entity
public abstract class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    /**
     * Default constructor for initializing a person with default values.
     */
    public Person() {
    }

    /**
     * Parameterized constructor for initializing a person with specific details.
     *
     * @param id        The unique identifier for the person.
     * @param email     The email address of the person.
     * @param dob       The date of birth of the person.
     * @param fullName  The full name of the person.
     */

    public Person(String id, String email, LocalDate dob, String fullName, String username, String password) {
        this.id = id;
        this.email = email;
        this.dob = dob;
        this.fullName = fullName;
        this.username = username;
        this.password = password;
    }

    /**
     * Retrieves the email address of the person.
     *
     * @return The email as a String.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Retrieves the date of birth of the person.
     *
     * @return The date of birth as a LocalDate object.
     */
    public LocalDate getDob() {
        return dob;
    }

    /**
     * Retrieves the full name of the person.
     *
     * @return The full name as a String.
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Retrieves the unique identifier of the person.
     *
     * @return The ID as a String.
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
