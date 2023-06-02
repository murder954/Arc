package ru.sfedu.petclinic.lab5.many_to_many.model;


import jakarta.persistence.Entity;
import ru.sfedu.petclinic.Constans;


@Entity(name = "fishes")
public class Fish extends Pet {

    private String waterType;

    public Fish(String name, String gender, double weight, String feedType, int age, String nameOfDisease, String waterType) {
        super(name, gender, weight, feedType, Constans.TypePet.FISH, age, nameOfDisease);
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
