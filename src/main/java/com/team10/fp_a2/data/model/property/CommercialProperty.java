package com.team10.fp_a2.data.model.property;
import com.team10.fp_a2.data.model.person.Owner;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class CommercialProperty extends Property {

    @Column(name = "business_type")
    private String businessType;

    @Column(name = "has_parking_spaces")
    private boolean hasParkingSpaces;

    @Column(name = "square_footage", nullable = false)
    private double squareFootage;

    public CommercialProperty() {
    }

    public CommercialProperty(String address, double pricing, Owner owner, String businessType, boolean hasParkingSpaces, double squareFootage) {
        super(address, pricing, owner);
        this.businessType = businessType;
        this.hasParkingSpaces = hasParkingSpaces;
        this.squareFootage = squareFootage;
    }

    // Getters and setters
    public String getBusinessType() {
        return businessType;
    }

    public boolean isHasParkingSpaces() {
        return hasParkingSpaces;
    }

    public double getSquareFootage() {
        return squareFootage;
    }
}

