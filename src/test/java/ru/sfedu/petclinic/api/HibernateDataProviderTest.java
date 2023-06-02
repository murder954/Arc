package ru.sfedu.petclinic.api;

import org.junit.jupiter.api.Test;
import ru.sfedu.petclinic.lab1.api.HibernateDataProvider;

import java.io.IOException;


class HibernateDataProviderTest {
    HibernateDataProvider hDp = new HibernateDataProvider();
    

    @Test
    void getListBird() throws IOException {
        hDp.getListBird();
    }

    @Test
    void getListTables(){
        hDp.getListTable();
    }

    @Test
    void getInfoBird() throws IOException {
        hDp.getInfoBird();
    }

    @Test
    void getDatabases(){
        hDp.getDataBases();
    }
}