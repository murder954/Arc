package ru.sfedu.petclinic.lab5.one_to_one.model;

public class Expencces {
    private int calculatingPeriod;
    private Double cost;

    public Expencces(){

    }

    public int getCalculatingPeriod() {
        return calculatingPeriod;
    }

    public void setCalculatingPeriod(int calculatingPeriod) {
        this.calculatingPeriod = calculatingPeriod;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
}
