package ru.sfedu.myApp.Entity;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.Date;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class EnvironmentVariant {
    @XmlElement(name = "houseName")
    private String houseName;
    @XmlElement(name = "variantId")
    private String id;
    @XmlElement(name = "addition")
    private String addition;
    @XmlElement(name = "insideHouseUsing")
    private boolean insideHouseUsing;
    @XmlElement(name = "environmentFeatures")
    private String environmentFeatures;
    @XmlElement(name = "price")
    private double price;

    public EnvironmentVariant() {

    }

    public EnvironmentVariant(String houseName, boolean insideHouseUsing, String environmentFeatures, String addition, double price) {
        this.houseName = houseName;
        this.insideHouseUsing = insideHouseUsing;
        this.environmentFeatures = environmentFeatures;
        this.addition = addition;
        this.price = price;
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
}
