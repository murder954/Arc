package ru.sfedu.petclinic.lab3.strat2.model;


import jakarta.persistence.Entity;
import ru.sfedu.petclinic.Constans;


@Entity(name = "dogs")
public class Dog extends Pet {

    private boolean isAggressive;

    public Dog(String name, String gender, double weight, String feedType, int age, String nameOfDisease, String ownerId, boolean isAggressive) {
        super(name, gender, weight, feedType, Constans.TypePet.DOG, age, nameOfDisease, ownerId);
        this.isAggressive = isAggressive;
    }

    public Dog(){

    }


    public void setIsAgressive(boolean isAgressive) {
        this.isAggressive = isAgressive;
    }

    public boolean getIsAgressive() {
        return isAggressive;
    }
}
