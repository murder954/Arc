package ru.sfedu.petclinic.lab5.many_to_many.model;


import jakarta.persistence.Entity;
import ru.sfedu.petclinic.Constans;

@Entity(name = "birds")
public class Bird extends Pet {

    private boolean isWaterFowl;

    public Bird(String name, String gender, double weight, String feedType, int age, String nameOfDisease, boolean isWaterFowl) {
        super(name, gender, weight, feedType, Constans.TypePet.BIRD, age, nameOfDisease);
        this.isWaterFowl = isWaterFowl;
    }

    public Bird(){

    }

    public void setIsWaterFlow(boolean isWaterFowl) {
        this.isWaterFowl = isWaterFowl;
    }

    public boolean getIsWaterFlow() {
        return isWaterFowl;
    }

}
