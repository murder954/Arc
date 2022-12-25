package ru.sfedu.myApp.Entity;

import com.opencsv.bean.CsvBindByPosition;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.Objects;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Cat extends Pet {

    @XmlElement(name = "afraidDogs")
    @CsvBindByPosition(position = 9)
    protected boolean afraidDogs;
    @XmlElement(name = "calm")
    @CsvBindByPosition(position = 10)
    protected boolean isCalm;

    public Cat(String name, String gender, double weight, String feedType, String type, int age, String nameOfDisease, boolean afraidDogs, boolean isCalm) {
        super(name, gender, weight, feedType, type, age, nameOfDisease);
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
