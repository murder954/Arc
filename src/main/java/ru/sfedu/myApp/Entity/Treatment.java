package ru.sfedu.myApp.Entity;


import java.util.Date;


public class Treatment extends Service {

    private double treatmentTime;

    private String drugName;

    private String forDisease;

    private double priceForPack;

    private int piecesInPack;

    private int intensityPerDay;

    public Treatment() {

    }

    public Treatment(double cost, String id, Date date, String nameOfService, double treatmentTime, String drugName, String forDisease, double priceForPack, int piecesInPack, int intensityPerDay) {
        super(cost, id, date, nameOfService);
        this.treatmentTime = treatmentTime;
        this.drugName = drugName;
        this.forDisease = forDisease;
        this.priceForPack = priceForPack;
        this.piecesInPack = piecesInPack;
        this.intensityPerDay = intensityPerDay;
    }

    public void setTreatmentTime(double treatmentTime) {
        this.treatmentTime = treatmentTime;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public void setForDisease(String forDisease) {
        this.forDisease = forDisease;
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

    public double getTreatmentTime() {
        return treatmentTime;
    }

    public String getDrugName() {
        return drugName;
    }

    public String getForDisease() {
        return forDisease;
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
