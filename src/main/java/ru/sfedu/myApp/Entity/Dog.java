package ru.sfedu.myApp.Entity;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import ru.sfedu.myApp.PetTypes;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Dog extends Pet {

    @XmlElement(name = "aggressive")
    private boolean isAggressive;

    public Dog(String name, String gender, double weight, String feedType, PetTypes type, int age, String nameOfDisease, boolean isAgressive) {
        super(name, gender, weight, feedType, type, age, nameOfDisease);
        this.isAggressive = isAgressive;
    }

    public Dog(){

    }


    public void enterIsAgressive(boolean isAgressive) {
        this.isAggressive = isAgressive;
    }

    public boolean returnIsAgressive() {
        return isAggressive;
    }
}
