package ru.sfedu.petclinic.lab3.strat1.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.GenericGenerator;
import ru.sfedu.petclinic.Constans;

import java.util.Objects;



@MappedSuperclass
public class Pet {

    private String name;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String gender;

    private double weight;

    private String feedType;

    private Constans.TypePet type;

    private int age;

    private String nameOfDisease;

    private String ownerId;


    public Pet() {

    }

    public Pet(String name, String gender, double weight, String feedType, Constans.TypePet type, int age, String nameOfDisease, String ownerId) {
        this.name = name;
        this.gender = gender;
        this.weight = weight;
        this.feedType = feedType;
        this.type = type;
        this.age = age;
        this.nameOfDisease = nameOfDisease;
        this.ownerId = ownerId;
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
