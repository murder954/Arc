package ru.sfedu.petclinic.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import ru.sfedu.petclinic.Constans;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Fish extends Pet {
    @XmlElement(name = "waterType")
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
