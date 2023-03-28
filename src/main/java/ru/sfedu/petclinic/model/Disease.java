package ru.sfedu.petclinic.model;

import com.opencsv.bean.CsvBindByPosition;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.UUID;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Disease {
    @XmlElement(name = "disease")
    @CsvBindByPosition(position = 0)
    private String nameOfDisease;
    @XmlElement(name = "diseaseId")
    @CsvBindByPosition(position = 1)
    private String id;
    @XmlElement(name = "treatment_time")
    @CsvBindByPosition(position = 2)
    private int treatmentTime;
    @XmlElement(name = "forTreatment")
    @CsvBindByPosition(position = 3)
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
