package ru.sfedu.petclinic.lab3.strat3.model;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import ru.sfedu.petclinic.Constans;

@Entity(name = "birds")
@DiscriminatorValue("bird")
public class Bird extends Pet {

    private boolean isWaterFowl;

    public Bird(String name, String gender, double weight, String feedType, int age, String nameOfDisease, String ownerId, boolean isWaterFowl) {
        super(name, gender, weight, feedType, Constans.TypePet.BIRD, age, nameOfDisease, ownerId);
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
