package ru.sfedu.petclinic.lab4.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.GenericGenerator;
import ru.sfedu.petclinic.Constans;

import java.util.UUID;


@Entity(name = "environmentVariants")
public class EnvironmentVariant {

    private String houseName;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String addition;

    private boolean insideHouseUsing;

    private String environmentFeatures;

    private double price;

    private Constans.TypePet forPetType;

    public EnvironmentVariant() {

    }

    public EnvironmentVariant(String houseName, boolean insideHouseUsing, String environmentFeatures, String addition, double price, Constans.TypePet forPetType) {
        this.houseName = houseName;
        this.id = UUID.randomUUID().toString();
        this.insideHouseUsing = insideHouseUsing;
        this.environmentFeatures = environmentFeatures;
        this.addition = addition;
        this.price = price;
        this.forPetType = forPetType;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setInsideHouseUsing(boolean insideHouseUsing) {
        this.insideHouseUsing = insideHouseUsing;
    }

    public void setEnvironmentFeatures(String environmentFeatures) {
        this.environmentFeatures = environmentFeatures;
    }

    public void setAddition(String addition) {
        this.addition = addition;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setForPetType(Constans.TypePet forPetType) {
        this.forPetType = forPetType;
    }

    public String getHouseName() {
        return houseName;
    }

    public String getId() {
        return id;
    }

    public boolean getInsideHouseUsing() {
        return insideHouseUsing;
    }

    public String getEnvironmentFeatures() {
        return environmentFeatures;
    }

    public String getAddition() {
        return addition;
    }

    public double getPrice() {
        return price;
    }

    public Constans.TypePet getForPetType() {
        return forPetType;
    }
}
