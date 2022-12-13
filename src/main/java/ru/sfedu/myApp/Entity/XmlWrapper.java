package ru.sfedu.myApp.Entity;

import jakarta.xml.bind.annotation.*;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({Owner.class, Pet.class, Cat.class, Dog.class, Fish.class, Bird.class, Feed.class, Drug.class, Disease.class, EnvironmentVariant.class})
public class XmlWrapper<T> implements Serializable {

    @XmlElementWrapper(name = "wrapper")
    @XmlElement
    private List<T> list = new ArrayList<>();

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
