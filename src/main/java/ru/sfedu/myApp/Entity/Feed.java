package ru.sfedu.myApp.Entity;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.Objects;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Feed {
    @XmlElement(name = "feedName")
    private String feedName;

    @XmlElement(name = "feedId")
    private String id;
    @XmlElement(name = "priceForPack")
    private double priceForPack;
    @XmlElement(name = "weightOfPack")
    private double weightOfPack;

    public Feed() {

    }

    public Feed(String feedName, double priceForPack, double weightOfPack) {
        this.feedName = feedName;
        this.priceForPack = priceForPack;
        this.weightOfPack = weightOfPack;
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
