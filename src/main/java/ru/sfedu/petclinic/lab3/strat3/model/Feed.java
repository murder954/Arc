package ru.sfedu.petclinic.lab3.strat3.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.GenericGenerator;
import ru.sfedu.petclinic.Constans;

import java.util.Objects;
import java.util.UUID;


@Entity(name = "feeds")
public class Feed {

    private String feedName;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private double priceForPack;

    private double weightOfPack;


    private Constans.TypePet forPetType;

    public Feed() {

    }

    public Feed(String feedName, double priceForPack, double weightOfPack, Constans.TypePet forPetType) {
        this.feedName = feedName;
        this.id = UUID.randomUUID().toString();
        this.priceForPack = priceForPack;
        this.weightOfPack = weightOfPack;
        this.forPetType = forPetType;
    }

    public void setFeedName(String feedName) {
        this.feedName = feedName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPriceForPack(double priceForPack) {
        this.priceForPack = priceForPack;
    }

    public void setWeightOfPack(double weightOfPack) {
        this.weightOfPack = weightOfPack;
    }

    public void setForPetType(Constans.TypePet forPetType) {
        this.forPetType = forPetType;
    }

    public String getFeedName() {
        return feedName;
    }

    public String getId() {
        return id;
    }

    public double getPriceForPack() {
        return priceForPack;
    }

    public double getWeightOfPack() {
        return weightOfPack;
    }

    public Constans.TypePet getForPetType() {
        return forPetType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feed feed = (Feed) o;
        return Double.compare(feed.priceForPack, priceForPack) == 0 && Double.compare(feed.weightOfPack, weightOfPack) == 0 && feedName.equals(feed.feedName) && id.equals(feed.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedName, id, priceForPack, weightOfPack);
    }
}
