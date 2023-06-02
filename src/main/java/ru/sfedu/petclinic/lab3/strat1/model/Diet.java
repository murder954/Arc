package ru.sfedu.petclinic.lab3.strat1.model;

import java.util.Date;


public class Diet extends Service {

    private String feedType;

    private double priceForPack;

    private double weightOfPack;

    public Diet(){

    }

    public Diet(double cost, Date date, String nameOfService, String feedType, double priceForPack, double weightOfPack) {
        super(cost, date, nameOfService);
        this.feedType = feedType;
        this.priceForPack = priceForPack;
        this.weightOfPack = weightOfPack;
    }

    public void setFeedType(String feedType) {
        this.feedType = feedType;
    }

    public void setPriceForPack(double priceForPack) {
        this.priceForPack = priceForPack;
    }

    public void setWeightOfPack(double weightOfPack) {
        this.weightOfPack = weightOfPack;
    }

    public String getFeedType() {
        return feedType;
    }

    public double getPriceForPack() {
        return priceForPack;
    }

    public double getWeightOfPack() {
        return weightOfPack;
    }
}
