package ru.sfedu.petclinic.lab5.many_to_many.model;


import java.util.Date;


public class Environment extends Service {

    private boolean insideHouse;

    private String environmentFeatures;

    private String addition;

    private double price;

    public Environment() {

    }

    public Environment(double cost, Date date, String nameOfService, boolean insideHouse, String environmentFeatures, String addition, double price) {
        super(cost, date, nameOfService);
        this.insideHouse = insideHouse;
        this.environmentFeatures = environmentFeatures;
        this.addition = addition;
        this.price = price;
    }

    public void setInsideHouse(boolean insideHouse) {
        this.insideHouse = insideHouse;
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

    public boolean getInsideHouse() {
        return insideHouse;
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
