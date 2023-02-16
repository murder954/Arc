package ru.sfedu.myApp.Entity;

import com.opencsv.bean.CsvBindByPosition;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.Objects;
import java.util.UUID;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Feed {
    @XmlElement(name = "feedName")
    @CsvBindByPosition(position = 0)
    private String feedName;
    @XmlElement(name = "feedId")
    @CsvBindByPosition(position = 1)
    private String id;
    @XmlElement(name = "priceForPack")
    @CsvBindByPosition(position = 2)
    private double priceForPack;
    @XmlElement(name = "weightOfPack")
    @CsvBindByPosition(position = 3)
    private double weightOfPack;

    @XmlElement(name = "forPet")
    @CsvBindByPosition(position = 4)
    private String forPetType;

    public Feed() {

    }

    public Feed(String feedName, double priceForPack, double weightOfPack, String forPetType) {
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

    public void setForPetType(String forPetType) {
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

    public String getForPetType() {
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
