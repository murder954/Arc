package ru.sfedu.petclinic.lab3.strat3.model;



import java.util.Objects;
import java.util.UUID;


public class Owner {

    private String id;


    private String ownerName;


    private String phoneNumber;


    private String email;


    private long bankAccount;


    public Owner() {

    }

    public Owner(String ownerName, String phoneNumber, String email, long bankAccount) {
        this.id = UUID.randomUUID().toString();
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
