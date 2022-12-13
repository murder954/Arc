package ru.sfedu.myApp.Entity;

import jakarta.xml.bind.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.myApp.PetTypes;

import java.util.Objects;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Pet {

    private PetTypes petTypes;
    @XmlElement(name = "name")
    private String name;
    @XmlElement(name = "id")
    private String id;
    @XmlElement(name = "gender")
    private String gender;
    @XmlElement(name = "weight")
    private double weight;
    @XmlElement(name = "feedType")
    private String feedType;
    @XmlElement(name = "type")
    private PetTypes type;
    @XmlElement(name = "age")
    private int age;
    @XmlElement(name = "disease")
    private String nameOfDisease;
    @XmlElement(name = "ownerId")
    private String ownerId;

    @XmlTransient
    Logger log = LogManager.getLogger(Pet.class);

    public Pet() {

    }

    public Pet(String name, String gender, double weight, String feedType, PetTypes type, int age, String nameOfDisease) {
        this.name = name;
        this.gender = gender;
        this.weight = weight;
        this.feedType = feedType;
        this.type = type;
        this.age = age;
        this.nameOfDisease = nameOfDisease;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setFeedType(String feedType) {
        this.feedType = feedType;
    }

    public void setType(PetTypes type) {
        this.type = type;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setNameOfDisease(String nameOfDisease) {
        this.nameOfDisease = nameOfDisease;
    }

    public void setOwnerId(String ownerId){
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public double getWeight() {
        return weight;
    }

    public String getGender() {
        return gender;
    }

    public String getFeedType() {
        return feedType;
    }

    public PetTypes getType() {
        return type;
    }

    public int getAge() {
        return age;
    }

    public String getNameOfDisease() {
        return nameOfDisease;
    }

    public String getOwnerId(){
        return ownerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return Double.compare(pet.weight, weight) == 0 && age == pet.age && name.equals(pet.name) && id.equals(pet.id) && gender.equals(pet.gender) && feedType.equals(pet.feedType) && type == pet.type && nameOfDisease.equals(pet.nameOfDisease) && ownerId.equals(pet.ownerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id, gender, weight, feedType, type, age, nameOfDisease, ownerId);
    }
}
