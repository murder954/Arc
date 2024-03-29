package ru.sfedu.petclinic.lab3.strat4.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.UUID;

public class Service {

    private double cost;

    private String id;

    private Date date;

    private String nameOfService;


    Logger log = LogManager.getLogger(Service.class);

    public Service() {

    }

    public Service(double cost, Date date, String nameOfService) {
        this.cost = cost;
        this.id = String.valueOf(UUID.randomUUID());
        //this.id = id;
        this.date = date;
        this.nameOfService = nameOfService;
        log.info("Service with name: " + nameOfService + " has been created");
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setNameOfService(String nameOfService) {
        this.nameOfService = nameOfService;
    }

    public double getCost() {
        return cost;
    }

    public String getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getNameOfService() {
        return nameOfService;
    }

}
