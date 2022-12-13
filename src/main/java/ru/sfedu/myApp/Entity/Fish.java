package ru.sfedu.myApp.Entity;

import com.opencsv.bean.CsvBindByPosition;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import ru.sfedu.myApp.PetTypes;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Fish extends Pet {
    @XmlElement(name = "waterType")
    private String waterType;

    public Fish(String name, String gender, double weight, String feedType, PetTypes type, int age, String nameOfDisease, String waterType) {
        super(name, gender, weight, feedType, type, age, nameOfDisease);
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
