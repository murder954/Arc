package ru.sfedu.myApp.Entity;

import java.util.Date;


public class Diet extends Service {

    private String feedType;

    private double priceForPack;

    private double weightOfPack;

    public Diet(){

    }

    public Diet(double cost, String id, Date date, String nameOfService, String feedType, double priceForPack, double weightOfPack) {
        super(cost, id, date, nameOfService);
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
