package ru.sfedu.petclinic.lab2.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import ru.sfedu.petclinic.lab2.model.Operation;
import ru.sfedu.petclinic.lab2.model.TestEntity;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TestDataProviderTest {

    private static final Logger log = LogManager.getLogger(TestDataProvider.class);
    private static TestDataProvider dataProvider = new TestDataProvider();

    static Operation operation = new Operation("type", 500L);
    static TestEntity entity = new TestEntity("name", "description", new Date(), false, operation);

    @Test
    void saveTestEntity() throws Exception {
        dataProvider.saveTestEntity(entity);
    }

    @Test
    void deleteTestEntity() throws Exception {
        dataProvider.deleteTestEntity(3L);
    }

    @Test
    void updateTestEntity() throws Exception {
        dataProvider.updateTestEntity(entity);
    }

    @Test
    void getTestEntity() throws Exception {
        dataProvider.getTestEntity(4L);
    }
}