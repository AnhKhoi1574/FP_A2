package com.team10.fp_a2.data.model.property;

import com.team10.fp_a2.data.model.person.Owner;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class ResidentialProperty extends Property {

    @Column(name = "num_of_rooms", nullable = false)
    private int numOfRooms;

    @Column(name = "has_garden")
    private boolean hasGarden;

    @Column(name = "has_swimming_pool")
    private boolean hasSwimmingPool;

    public ResidentialProperty() {
        // Default constructor
    }

    public ResidentialProperty(String address, double pricing, Owner owner, int numOfRooms, boolean hasGarden, boolean hasSwimmingPool) {
        super(address, pricing, owner);
        this.numOfRooms = numOfRooms;
        this.hasGarden = hasGarden;
        this.hasSwimmingPool = hasSwimmingPool;
    }

    // Getters and setters
    public int getNumOfRooms() {
        return numOfRooms;
    }

    public void setNumOfRooms(int numOfRooms) {
        this.numOfRooms = numOfRooms;
    }

    public boolean isHasGarden() {
        return hasGarden;
    }

    public void setHasGarden(boolean hasGarden) {
        this.hasGarden = hasGarden;
    }

    public boolean isHasSwimmingPool() {
        return hasSwimmingPool;
    }

    public void setHasSwimmingPool(boolean hasSwimmingPool) {
        this.hasSwimmingPool = hasSwimmingPool;
    }
}
