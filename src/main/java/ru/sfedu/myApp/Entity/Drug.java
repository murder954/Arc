package ru.sfedu.myApp.Entity;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Drug {
    @XmlElement(name = "drugName")
    private String drugName;
    @XmlElement(name = "drugId")
    private String id;
    @XmlElement(name = "priceForPack")
    private double priceForPack;
    @XmlElement(name = "piecesInPack")
    private int piecesInPack;
    @XmlElement(name = "intensityPerDay")
    private int intensityPerDay;

    public Drug() {

    }

    public Drug(String drugName, double priceForPack, int piecesInPack, int intensityPerDay) {
        this.drugName = drugName;
        this.priceForPack = priceForPack;
        this.piecesInPack = piecesInPack;
        this.intensityPerDay = intensityPerDay;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setPriceForPack(double priceForPack) {
        this.priceForPack = priceForPack;
    }

    public void setPiecesInPack(int piecesInPack) {
        this.piecesInPack = piecesInPack;
    }

    public void setIntesityPerDay(int intensityPerDay) {
        this.intensityPerDay = intensityPerDay;
    }

    public String getDrugName() {
        return drugName;
    }

    public String getId() {
        return id;
    }

    public double getPriceForPack() {
        return priceForPack;
    }

    public int getPiecesInPack() {
        return piecesInPack;
    }

    public int getIntesityPerDay() {
        return intensityPerDay;
    }
}
