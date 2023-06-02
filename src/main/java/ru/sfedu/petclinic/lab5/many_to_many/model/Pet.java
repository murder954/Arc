package ru.sfedu.petclinic.lab5.many_to_many.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import ru.sfedu.petclinic.Constans;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Pet {

    @Column(name = "name")
    private String name;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(name = "gender")
    private String gender;

    @Column(name = "weight")
    private double weight;

    @Column(name = "feedType")
    private String feedType;

    @Column(name = "type")
    private Constans.TypePet type;

    @Column(name = "age")
    private int age;

    @Column(name = "nameOfDisease")
    private String nameOfDisease;


    @ManyToMany(mappedBy = "pets", fetch = FetchType.EAGER)
    private List<Owner> owners = new ArrayList<>();


    public Pet() {

    }

    public Pet(String name, String gender, double weight, String feedType, Constans.TypePet type, int age, String nameOfDisease) {
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

    public void setType(Constans.TypePet type) {
        this.type = type;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setNameOfDisease(String nameOfDisease) {
        this.nameOfDisease = nameOfDisease;
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


    public List<Owner> getOwners() {
        return owners;
    }

    public void setOwners(List<Owner> owners) {
        this.owners = owners;
    }

    public void addOwners(Owner owner){
        owners.add(owner);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return Double.compare(pet.weight, weight) == 0 && age == pet.age && name.equals(pet.name) && id.equals(pet.id) && gender.equals(pet.gender) && feedType.equals(pet.feedType) && type.equals(pet.type) && nameOfDisease.equals(pet.nameOfDisease);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id, gender, weight, feedType, type, age, nameOfDisease);
    }
}
