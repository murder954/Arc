package ru.sfedu.petclinic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.sfedu.petclinic.model.*;
import ru.sfedu.petclinic.api.DataBaseProvider;
import ru.sfedu.petclinic.api.DataProviderCSV;
import ru.sfedu.petclinic.api.DataProviderXML;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LogicTest {
    private static final Logger log = LogManager.getLogger(DataProviderXMLTest.class);
    private static DataProviderXML dpXML;
    private static DataBaseProvider dpDB;
    private static DataProviderCSV dpSCV;

    static Cat test_pet1 = new Cat("Lilu", "female", 4.4, "meal", 2, "stomach_ache", false, true);

    static Owner test_owner1 = new Owner("Jerremy Clarckson", "+79876283820", "clarckson@gmail.com", 7299291816482104L);

    @BeforeAll
    static void init() throws Exception {
        dpXML = new DataProviderXML();
        dpDB = new DataBaseProvider();
        dpSCV = new DataProviderCSV();
    }

    @Test
    void animalTreatmentTestPositive() throws Exception{
        Pet pet = dpDB.getPetRecordByID("3e7ed8c1-3212-4850-8afc-c801e0a75a22");
        List<Disease> dis = dpDB.getAllDiseases();
        List<Drug> dr = dpDB.getAllDrugs();
        dpDB.animalTreatment(pet, dpDB, dis, dr);
        assertNotNull(dpDB.getHistoryRecords("3e7ed8c1-3212-4850-8afc-c801e0a75a22"));
    }

    @Test
    void animalTreatmentTestNegative() throws Exception{
        Exception exception = assertThrows(Exception.class, () -> {
            Pet pet = dpDB.getPetRecordByID("478c99e7-e208-449a-bc0d-d51751776fb6");
            List<Disease> dis = dpDB.getAllDiseases();
            List<Drug> dr = dpDB.getAllDrugs();
            dpDB.animalTreatment(pet, dpDB, dis, dr);
        });
        assertEquals("Disease record with this id not found", exception.getMessage());
    }

    @Test
    void selectionEnvironmentTestPositive() throws Exception{
        Pet pet = dpSCV.getPetRecordByID("ea588e46-615d-4def-a5f1-8702f350a78d");
        List<EnvironmentVariant> env = dpSCV.getAllEnvironmentVariants();
        dpSCV.selectionEnvironmentVariant(pet, env, true,  dpSCV);
        assertNotNull(dpSCV.getHistoryRecords("ea588e46-615d-4def-a5f1-8702f350a78d"));
    }

    @Test
    void selectionEnvironmentTestNegative() throws Exception{
        Exception exception = assertThrows(Exception.class, () -> {
            Pet pet = dpSCV.getPetRecordByID("7dd91e7a-fa19-4773-b760-2c1dcd80f2cf");
            List<EnvironmentVariant> env = dpSCV.getAllEnvironmentVariants();
            dpSCV.selectionEnvironmentVariant(pet, env, true,  dpSCV);
        });
        assertEquals("No environment for your pet", exception.getMessage());
    }

    @Test
    void selectionFeedTestPositive() throws Exception{
        Pet pet = dpXML.getPetRecordByID("e9ebb541-44ef-441a-abe3-4b2f32aa4274");
        List<Feed> fd = dpXML.getAllFeeds();
        dpXML.selectionFeed(pet, fd, true, 2, dpXML);
        assertNotNull(dpXML.getHistoryRecords("e9ebb541-44ef-441a-abe3-4b2f32aa4274"));
    }

    @Test
    void selectionFeedTestNegative() throws Exception{
        Exception exception = assertThrows(Exception.class, () -> {
            Pet pet = dpXML.getPetRecordByID("ba7d5951-a30c-48a3-b8e9-64c8eccb1485");
            List<Feed> fd = dpXML.getAllFeeds();
            dpXML.selectionFeed(pet, fd, true, 2, dpXML);
        });
        assertEquals("No feed for your pet", exception.getMessage());
    }

    @Test
    void calculatingExpensesTestPositive() throws Exception{
        Pet pet = dpXML.getPetRecordByID("e9ebb541-44ef-441a-abe3-4b2f32aa4274");
        dpXML.calculateExpensesAnimal(pet, dpXML);
        assertNotNull(dpXML.getHistoryRecords("e9ebb541-44ef-441a-abe3-4b2f32aa4274"));
    }

    @Test
    void calculatingExpensesTestNegative() throws Exception{
        Exception exception = assertThrows(Exception.class, () -> {
            Pet pet = dpXML.getPetRecordByID("ba7d5951-a30c-48a3-b8e9-64c8eccb1485");
            dpXML.calculateExpensesAnimal(pet, dpXML);
        });
        assertEquals("No history records for this pet", exception.getMessage());
    }
}
