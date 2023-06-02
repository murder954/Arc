package ru.sfedu.petclinic.lab5.one_to_one.api;

import org.junit.jupiter.api.Test;
import ru.sfedu.petclinic.lab5.one_to_one.api.HibernateDataProvider;
import ru.sfedu.petclinic.lab5.one_to_one.model.Fish;
import ru.sfedu.petclinic.lab5.one_to_one.model.Owner;

import static org.junit.jupiter.api.Assertions.*;

class HibernateDataProviderTest {
    private static HibernateDataProvider hibernateDataProvider = new HibernateDataProvider();

    @Test
    void testPetRecord() throws Exception {
        Owner owner = new Owner("Viktor_Osimhen", "+79995554040", "napoli@sui.com", 4352743388483840L);

        Fish nemo = new Fish("Nemo", "male", 0.8, "worms", 2, "lepidorthosis",  "sea");
        owner.setPets(nemo);
        nemo.setOwner(owner);
        hibernateDataProvider.saveRecord(owner);
    }

}