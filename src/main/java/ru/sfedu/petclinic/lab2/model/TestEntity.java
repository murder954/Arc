package ru.sfedu.petclinic.lab2.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "test")
public class TestEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "testId")
    private Long id;
    @Column(name = "testName")
    private String name;
    @Column(name = "testDescription")
    private String description;
    @Column(name = "testDateCreated")
    private Date dateCreated;
    @Column(name = "testCheck")
    private Boolean check;
    @Embedded
    private Operation operation;

    public TestEntity(String name, String description, Date dateCreated, Boolean check, Operation operation) {
        this.name = name;
        this.description = description;
        this.dateCreated = dateCreated;
        this.check = check;
        this.operation = operation;
    }

    public TestEntity() {
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    public Long getId() {
        return id;
    }
}
