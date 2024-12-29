package com.team10.fp_a2.data.model.person;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "managers")
public class Manager extends Person {
    public Manager() {
        // Default constructor
    }

    public Manager(String fullName, LocalDate dob, String email, String username, String password) {
        super(fullName, dob, email, username, password);
    }
}
