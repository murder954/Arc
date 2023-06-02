package ru.sfedu.petclinic.lab5.many_to_many.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity(name = "drugs")
public class Drug {
    private String drugName;
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private double priceForPack;

    private int piecesInPack;

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
