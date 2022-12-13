package ru.sfedu.myApp;


import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import ru.sfedu.myApp.Entity.*;

import java.io.IOException;
import java.util.Optional;

public interface IDataProvider {

    String createId();

    void savePetRecord(Pet object, String ownerId) throws Exception;

    void saveOwnerRecord(Owner object) throws Exception;


    void saveFeedRecord(Feed object) throws Exception;

    void saveDrugRecord(Drug object) throws Exception;

    void saveDiseaseRecord(Disease object) throws Exception;

    void saveEnvironmentVariantRecord(EnvironmentVariant object) throws Exception;

    void deleteOwnerRecord(String id) throws Exception;

    void deletePetRecordByOwnerId(String id) throws Exception;

    void deletePetRecordById(String id) throws Exception;


    void deleteFeedRecord(String id) throws Exception;

    void deleteDrugRecord(String id) throws Exception;

    void deleteDiseaseRecord(String id) throws Exception;

    void deleteEnvironmentVariantRecord(String id) throws Exception;

    Owner getOwnerRecordByID(String id) throws Exception;

    Pet getPetRecordByOwnerID(String id) throws Exception;

    Pet getPetRecordByID(String id) throws Exception;


    Feed getFeedRecordByID(String id) throws Exception;

    Drug getDrugRecordByID(String id) throws Exception;

    Disease getDiseaseRecordByID(String id) throws Exception;

    EnvironmentVariant getEnvironmentVariantRecordByID(String id) throws Exception;
}
