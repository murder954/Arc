package ru.sfedu.petclinic.model;

import com.opencsv.bean.CsvBindByPosition;
import jakarta.xml.bind.annotation.*;
import ru.sfedu.petclinic.Constans;

import java.util.Objects;
import java.util.UUID;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Pet {
    @XmlElement(name = "name")
    @CsvBindByPosition(position = 0)
    private String name;
    @XmlElement(name = "id")
    @CsvBindByPosition(position = 1)
    private String id;
    @XmlElement(name = "gender")
    @CsvBindByPosition(position = 2)
    private String gender;
    @XmlElement(name = "weight")
    @CsvBindByPosition(position = 3)
    private double weight;
    @XmlElement(name = "feedType")
    @CsvBindByPosition(position = 4)
    private String feedType;
    @XmlElement(name = "type")
    @CsvBindByPosition(position = 5)
    private Constans.TypePet type;
    @XmlElement(name = "age")
    @CsvBindByPosition(position = 6)
    private int age;
    @XmlElement(name = "disease")
    @CsvBindByPosition(position = 7)
    private String nameOfDisease;
    @XmlElement(name = "ownerId")
    @CsvBindByPosition(position = 8)
    private String ownerId;


    public Pet() {

    }

    public Pet(String name, String gender, double weight, String feedType, Constans.TypePet type, int age, String nameOfDisease) {
        this.name = name;
        this.id = UUID.randomUUID().toString();
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

    public void setType(Constans.TypePet type) {
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

    public Constans.TypePet getType() {
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
        return Double.compare(pet.weight, weight) == 0 && age == pet.age && name.equals(pet.name) && id.equals(pet.id) && gender.equals(pet.gender) && feedType.equals(pet.feedType) && type.equals(pet.type) && nameOfDisease.equals(pet.nameOfDisease) && ownerId.equals(pet.ownerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id, gender, weight, feedType, type, age, nameOfDisease, ownerId);
    }
}
