package ru.sfedu.myApp.Model;

import com.opencsv.bean.CsvBindByPosition;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.UUID;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Drug {
    @XmlElement(name = "drugName")
    @CsvBindByPosition(position = 0)
    private String drugName;
    @XmlElement(name = "drugId")
    @CsvBindByPosition(position = 1)
    private String id;
    @XmlElement(name = "priceForPack")
    @CsvBindByPosition(position = 2)
    private double priceForPack;
    @XmlElement(name = "piecesInPack")
    @CsvBindByPosition(position = 3)
    private int piecesInPack;
    @XmlElement(name = "intensityPerDay")
    @CsvBindByPosition(position = 4)
    private int intensityPerDay;

    public Drug() {

    }

    public Drug(String drugName, double priceForPack, int piecesInPack, int intensityPerDay) {
        this.drugName = drugName;
        this.id = String.valueOf(UUID.randomUUID());
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
