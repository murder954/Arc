package ru.sfedu.petclinic.lab3.strat2.model;

import jakarta.persistence.Entity;
import ru.sfedu.petclinic.Constans;

import java.util.Objects;


@Entity(name = "cats")
public class Cat extends Pet {


    protected boolean afraidDogs;

    protected boolean isCalm;

    public Cat(String name, String gender, double weight, String feedType, int age, String nameOfDisease, String ownerId, boolean afraidDogs, boolean isCalm) {
        super(name, gender, weight, feedType, Constans.TypePet.CAT, age, nameOfDisease, ownerId);
        this.afraidDogs = afraidDogs;
        this.isCalm = isCalm;
    }

    public Cat() {

    }


    public void setAfraidDogs(boolean afraidDogs) {
        this.afraidDogs = afraidDogs;
    }

    public void setIsCalm(boolean isCalm) {
        this.isCalm = isCalm;
    }

    public boolean getAfraidDogs() {
        return afraidDogs;
    }

    public boolean getIsCalm() {
        return isCalm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Cat cat = (Cat) o;
        return afraidDogs == cat.afraidDogs && isCalm == cat.isCalm;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), afraidDogs, isCalm);
    }
}
