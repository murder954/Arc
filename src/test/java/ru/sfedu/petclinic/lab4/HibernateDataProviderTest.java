package ru.sfedu.petclinic.lab4;

import org.junit.jupiter.api.Test;
import ru.sfedu.petclinic.lab4.api.HibernateDataProvider;
import ru.sfedu.petclinic.lab4.model.Fish;
import ru.sfedu.petclinic.lab4.model.Owner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class HibernateDataProviderTest {
    private static HibernateDataProvider hibernateDataProvider = new HibernateDataProvider();


    @Test
    void testPetRecord() throws Exception {
        Owner vasya = new Owner("Vasya_Pupkin", "+79998836754", "pupkin@mail.ru", 1111777788889999L);
        vasya.addAddresses("Stabilnaya3s2");
        vasya.addEmergencyContacts("mom", "+79885763452");
        hibernateDataProvider.saveRecord(vasya);

        Fish nemo = new Fish("Nemo", "male", 0.8, "worms", 2, "lepidorthosis", vasya.getId(),  "sea");
        vasya.addPets(nemo.getName());
        hibernateDataProvider.saveRecord(nemo);

        Object obj = hibernateDataProvider.getRecord(Owner.class, vasya.getId());
        assertNotNull(obj);

        vasya.addAddresses("Krasnoarmeiskaya100");
        vasya.addEmergencyContacts("father", "+79195682239");

        hibernateDataProvider.updateRecord(vasya);
        assertEquals(hibernateDataProvider.getRecord(Owner.class, vasya.getId()), vasya);

        //hibernateDataProvider.deleteRecord(nemo);
        //hibernateDataProvider.deleteRecord(vasya);
    }

}