package ru.sfedu.petclinic.model;

import com.opencsv.bean.CsvBindByPosition;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.Date;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class History{
    @XmlElement(name = "petName")
    @CsvBindByPosition(position = 0)
    private String petName;

    @XmlElement(name = "petId")
    @CsvBindByPosition(position = 1)
    private String petId;

    @XmlElement(name = "ownerId")
    @CsvBindByPosition(position = 2)
    private String ownerId;

    @XmlElement(name = "serviceName")
    @CsvBindByPosition(position = 3)
    private String serviceName;

    @XmlElement(name = "servId")
    @CsvBindByPosition(position = 4)
    private String servId;

    @XmlElement(name = "priceForService")
    @CsvBindByPosition(position = 5)
    private double price;

    @XmlElement(name = "date")
    private Date date;

    public History(){

    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetId() {
        return petId;
    }

    public void setPetId(String petId) {
        this.petId = petId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServId(){
        return servId;
    }

    public void setServId(String servId){
        this.servId = servId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
