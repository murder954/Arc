package ru.sfedu.myApp.Entity;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Dog extends Pet {

    @XmlElement(name = "aggressive")
    private boolean isAggressive;

    public Dog(String name, String gender, double weight, String feedType, String type, int age, String nameOfDisease, boolean isAggressive) {
        super(name, gender, weight, feedType, type, age, nameOfDisease);
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
