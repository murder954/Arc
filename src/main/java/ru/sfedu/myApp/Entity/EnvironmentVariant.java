package ru.sfedu.myApp.Entity;

import com.opencsv.bean.CsvBindByPosition;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.Date;
import java.util.UUID;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class EnvironmentVariant {
    @XmlElement(name = "houseName")
    @CsvBindByPosition(position = 0)
    private String houseName;
    @XmlElement(name = "variantId")
    @CsvBindByPosition(position = 1)
    private String id;
    @XmlElement(name = "addition")
    @CsvBindByPosition(position = 2)
    private String addition;
    @XmlElement(name = "insideHouseUsing")
    @CsvBindByPosition(position = 3)
    private boolean insideHouseUsing;
    @XmlElement(name = "environmentFeatures")
    @CsvBindByPosition(position = 4)
    private String environmentFeatures;
    @XmlElement(name = "price")
    @CsvBindByPosition(position = 5)
    private double price;
    @XmlElement(name = "forPet")
    @CsvBindByPosition(position = 6)
    private String forPetType;

    public EnvironmentVariant() {

    }

    public EnvironmentVariant(String houseName, boolean insideHouseUsing, String environmentFeatures, String addition, double price, String forPetType) {
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

    public void setForPetType(String forPetType) {
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

    public String getForPetType() {
        return forPetType;
    }
}
