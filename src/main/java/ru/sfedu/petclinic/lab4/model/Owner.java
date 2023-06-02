package ru.sfedu.petclinic.lab4.model;



import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.*;


@Entity(name = "owners")
public class Owner {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;


    private String ownerName;


    private String phoneNumber;


    private String email;


    private long bankAccount;

    @ElementCollection
    @Column(name="addresses")
    private Set<String> addresses = new HashSet<>();

    @ElementCollection
    @OrderColumn
    @Column(name = "pets")
    private List<String> pets = new ArrayList<>();

    @ElementCollection
    @MapKeyColumn(name = "telephone")
    @Column(name = "contact")
    @CollectionTable(name = "map", joinColumns = @JoinColumn(name = "OWNER_ID"))
    private Map<String, String> emergencyContacts= new HashMap<>();


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

    public Map<String, String> getEmergencyContacts() {
        return emergencyContacts;
    }

    public void setEmergencyContacts(Map<String, String> emergencyContacts) {
        this.emergencyContacts = emergencyContacts;
    }

    public List<String> getPets() {
        return pets;
    }

    public void setPets(List<String> pets) {
        this.pets = pets;
    }

    public Set<String> getAddresses(){
        return addresses;
    }

    public void setAddresses(String adsress){
        this.addresses = addresses;
    }

    public void addAddresses(String adress){
        addresses.add(adress);
    }

    public void addPets(String pet){
        pets.add(pet);
    }

    public void addEmergencyContacts(String contact, String phone){
        emergencyContacts.put(phone, contact);
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
