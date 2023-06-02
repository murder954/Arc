package ru.sfedu.petclinic.lab2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Operation {
    @Column(name = "testType")
    private String type;
    @Column(name = "testTotal")
    private Long total;

    public Operation(String type, Long total) {
        this.type = type;
        this.total = total;
    }

    public Operation() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
