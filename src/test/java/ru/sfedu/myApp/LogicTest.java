package ru.sfedu.myApp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.sfedu.myApp.Entity.Cat;
import ru.sfedu.myApp.Entity.Fish;
import ru.sfedu.myApp.Entity.Owner;
import ru.sfedu.myApp.buisnesLogic.TreatmentSelection;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LogicTest {
    private static final Logger log = LogManager.getLogger(DataProviderXMLTest.class);
    private static DataProviderXML dataProvider;

    private static TreatmentSelection logic;

    static Cat test_pet1 = new Cat("Lilu", "female", 4.4, "meal", "cat", 2, "stomach_ache", false, true);

    static Owner test_owner1 = new Owner("Jerremy Clarckson", "+79876283820", "clarckson@gmail.com", 7299291816482104L);

    @BeforeAll
    static void init() throws Exception {
        dataProvider = new DataProviderXML();
        logic = new TreatmentSelection();
    }

    @Test
    void logicTest() throws Exception {
        dataProvider.saveOwnerRecord(test_owner1);
        //dataProvider.savePetRecord(test_pet1, test_owner1.getId());
        logic.animalTreatment(test_pet1);
        logic.calculatingExpenses(test_pet1);
        //logic.selectionFeed(test_pet1);
        //logic.selectionEnvironmentVariant(test_pet1);
        assertNotNull(dataProvider.getHistoryRecords(test_pet1.getId()));
    }
}
