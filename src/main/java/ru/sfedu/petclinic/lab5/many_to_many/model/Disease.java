package ru.sfedu.petclinic.lab5.many_to_many.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;


@Entity(name = "diseases")
public class Disease {

    private String nameOfDisease;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private int treatmentTime;

    private String forTreatment;

    public Disease() {

    }

    public Disease(String nameOfDisease, int treatmentTime, String forTreatment) {
        this.nameOfDisease = nameOfDisease;
        this.id = String.valueOf(UUID.randomUUID());
        this.treatmentTime = treatmentTime;
        this.forTreatment = forTreatment;
    }

    public void setNameOfDisease(String nameOfDisease) {
        this.nameOfDisease = nameOfDisease;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTreatmentTime(int treatmentTime) {
        this.treatmentTime = treatmentTime;
    }

    public void setForTreatment(String forTreatment) {
        this.forTreatment = forTreatment;
    }

    public String getNameOfDisease() {
        return nameOfDisease;
    }

    public String getId() {
        return id;
    }

    public int getTreatmentTime() {
        return treatmentTime;
    }

    public String getForTreatment() {
        return forTreatment;
    }
}
