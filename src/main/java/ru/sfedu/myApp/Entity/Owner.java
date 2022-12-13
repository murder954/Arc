package ru.sfedu.myApp.Entity;

import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Owner {
    @XmlElement(name = "ownerId")
    private String id;

    @XmlElement(name = "ownerName")
    private String ownerName;

    @XmlElement(name = "phone")
    private String phoneNumber;

    @XmlElement(name = "email")
    private String email;

    @XmlElement(name = "bankAccount")
    private long bankAccount;

    @XmlTransient
    private List<Pet> ownerPets = new ArrayList<>();


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
        this.id = id.toString();
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

    public List<Pet> getOwnerPets() {
        return ownerPets;
    }

    public void setOwnerPets(List<Pet> ownerPets) {
        this.ownerPets = ownerPets;
    }

    public void addPetToOwner(Pet pet) throws Exception {
        if (!ownerPets.contains(pet)) {
            ownerPets.add(pet);
        } else {
            throw new Exception("This pet has already been added");
        }
    }

    public boolean checkOwner(Owner owner) throws Exception {
        if(bankAccount == owner.getBankAccount()){
            return true;
        }else return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Owner owner = (Owner) o;
        return bankAccount == owner.bankAccount && id.equals(owner.id) && ownerName.equals(owner.ownerName) && phoneNumber.equals(owner.phoneNumber) && email.equals(owner.email) && Objects.equals(ownerPets, owner.ownerPets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ownerName, phoneNumber, email, bankAccount, ownerPets);
    }
}
