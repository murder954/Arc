package ru.sfedu.petclinic.lab3.strat1.api;

import org.junit.jupiter.api.Test;
import ru.sfedu.petclinic.Constans;
import ru.sfedu.petclinic.lab3.strat1.model.Feed;
import ru.sfedu.petclinic.lab3.strat1.model.Fish;


import static org.junit.jupiter.api.Assertions.*;

class HibernateDataProviderTest {

    private static HibernateDataProvider hibernateDataProvider = new HibernateDataProvider();


    @Test
    void testPetRecord() throws Exception {
        Fish nemo = new Fish("Nemo", "male", 0.8, "worms", 2, "lepidorthosis","sdfdshkfhsdghkds" ,  "sea");
        hibernateDataProvider.saveRecord(nemo);

        Object obj = hibernateDataProvider.getRecord(Fish.class, nemo.getId());
        assertNotNull(obj);

        nemo.setAge(3);
        hibernateDataProvider.updateRecord(nemo);
        assertEquals(hibernateDataProvider.getRecord(Fish.class, nemo.getId()), nemo);

        hibernateDataProvider.deleteRecord(nemo);
    }

    @Test
    void testFeedRecord() throws Exception {
        Feed chapi = new Feed("chapi", 2299.00, 15.00, Constans.TypePet.DOG);
        hibernateDataProvider.saveRecord(chapi);

        Object obj = hibernateDataProvider.getRecord(Feed.class, chapi.getId());
        assertNotNull(obj);

        chapi.setPriceForPack(2599.00);
        hibernateDataProvider.updateRecord(chapi);
        assertEquals(hibernateDataProvider.getRecord(Feed.class, chapi.getId()), chapi);

        hibernateDataProvider.deleteRecord(chapi);
    }
}