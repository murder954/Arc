package ru.sfedu.petclinic.model;

import com.opencsv.bean.CsvBindByPosition;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import ru.sfedu.petclinic.Constans;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Bird extends Pet {
    @XmlElement(name = "waterFowl")
    @CsvBindByPosition(position = 9)
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
