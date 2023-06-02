package ru.sfedu.petclinic.lab5.many_to_many.model;


import java.util.Date;


public class Treatment extends Service {

    private int treatmentTime;

    private String drugName;

    private String diseaseName;

    private double priceForPack;

    private int piecesInPack;

    private int intensityPerDay;

    public Treatment() {

    }

    public Treatment(double cost, Date date, String nameOfService, int treatmentTime, String drugName, String diseaseName, double priceForPack, int piecesInPack, int intensityPerDay) {
        super(cost, date, nameOfService);
        this.treatmentTime = treatmentTime;
        this.drugName = drugName;
        this.diseaseName = diseaseName;
        this.priceForPack = priceForPack;
        this.piecesInPack = piecesInPack;
        this.intensityPerDay = intensityPerDay;
    }

    public void setTreatmentTime(int treatmentTime) {
        this.treatmentTime = treatmentTime;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
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

    public int getTreatmentTime() {
        return treatmentTime;
    }

    public String getDrugName() {
        return drugName;
    }

    public String getDiseaseName() {
        return diseaseName;
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
