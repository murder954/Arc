package ru.sfedu.petclinic.lab5.one_to_one.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "owners")
public class Owner {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;


    @Column(name = "ownerName")
    private String ownerName;


    @Column(name = "phoneNumber")
    private String phoneNumber;


    @Column(name = "email")
    private String email;


    @Column(name = "bankAccount")
    private long bankAccount;

    @OneToOne(mappedBy = "owner", cascade = CascadeType.ALL)
    private Pet pet;


    public Owner() {

    }

    public Owner(String ownerName, String phoneNumber, String email, long bankAccount) {
        this.ownerName = ownerName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.bankAccount = bankAccount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(long bankAccount) {
        this.bankAccount = bankAccount;
    }

    public Pet getPets() {
        return pet;
    }

    public void setPets(Pet pets) {
        this.pet = pets;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Owner owner = (Owner) o;
        return bankAccount == owner.bankAccount && id.equals(owner.id) && ownerName.equals(owner.ownerName) && phoneNumber.equals(owner.phoneNumber) && email.equals(owner.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ownerName, phoneNumber, email, bankAccount);
    }
}
