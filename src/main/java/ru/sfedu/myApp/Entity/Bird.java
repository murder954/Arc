package ru.sfedu.myApp.Entity;

import com.opencsv.bean.CsvBindByPosition;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import ru.sfedu.myApp.PetTypes;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Bird extends Pet {
    @XmlElement(name = "bird")
    @CsvBindByPosition(position = 9)
    private boolean isWaterFowl;

    public Bird(String name, String gender, double weight, String feedType, PetTypes type, int age, String nameOfDisease, boolean isWaterFowl) {
        super(name, gender, weight, feedType, type, age, nameOfDisease);
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
