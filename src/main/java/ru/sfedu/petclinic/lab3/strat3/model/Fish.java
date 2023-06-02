package ru.sfedu.petclinic.lab3.strat3.model;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import ru.sfedu.petclinic.Constans;


@Entity(name = "fishes")
@DiscriminatorValue("fish")
public class Fish extends Pet {

    private String waterType;

    public Fish(String name, String gender, double weight, String feedType, int age, String nameOfDisease, String ownerId, String waterType) {
        super(name, gender, weight, feedType, Constans.TypePet.FISH, age, nameOfDisease, ownerId);
        this.waterType = waterType;
    }

    public Fish() {

    }

    public void setWaterType(String waterType) {
        this.waterType = waterType;
    }

    public String getWaterType() {
        return waterType;
    }
}
