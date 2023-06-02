package ru.sfedu.petclinic.lab5.one_to_many.api;

import org.junit.jupiter.api.Test;
import ru.sfedu.petclinic.lab5.one_to_many.model.Cat;
import ru.sfedu.petclinic.lab5.one_to_many.model.Fish;
import ru.sfedu.petclinic.lab5.one_to_many.model.Owner;

import static org.junit.jupiter.api.Assertions.*;

class HibernateDataProviderTest {

    private static HibernateDataProvider hibernateDataProvider = new HibernateDataProvider();

    @Test
    void testPetRecord() throws Exception {
        Owner owner = new Owner("Viktor_Osimhen", "+79995554040", "napoli@sui.com", 4352743388483840L);

        Fish nemo = new Fish("Nemo", "male", 0.8, "worms", 2, "lepidorthosis",  "sea");
        Cat cat = new Cat("Bebra", "female", 5.00, "snitch", 3, "spring", true, true);
        nemo.setOwner(owner);
        owner.addPets(cat);
        cat.setOwner(owner);
        owner.addPets(nemo);
        hibernateDataProvider.saveRecord(owner);
    }

}