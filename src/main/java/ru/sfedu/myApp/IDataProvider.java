package ru.sfedu.myApp;

import ru.sfedu.myApp.Entity.*;

import java.util.List;

public interface IDataProvider {

    String createId();

    void savePetRecord(Pet object, String ownerId) throws Exception;

    void saveOwnerRecord(Owner object) throws Exception;


    void saveHistoryRecord(Pet pet, Service serv) throws Exception;


    void saveFeedRecord(Feed object) throws Exception;

    void saveDrugRecord(Drug object) throws Exception;

    void saveDiseaseRecord(Disease object) throws Exception;

    void saveEnvironmentVariantRecord(EnvironmentVariant object) throws Exception;


    void deleteOwnerRecord(String id) throws Exception;

    void deletePetRecord(Pet pet) throws Exception;

    void deleteOnePetRecord(String id) throws Exception;


    void findForDelPetByOwner(String id) throws Exception;

    void deleteFeedRecord(String id) throws Exception;

    void deleteDrugRecord(String id) throws Exception;

    void deleteDiseaseRecord(String id) throws Exception;

    void deleteEnvironmentVariantRecord(String id) throws Exception;

    Pet findForGetPetRecordByOwnerId(String id) throws Exception;

    Pet getPet(Pet pet) throws Exception;

    Owner getOwnerRecordByID(String id) throws Exception;

    Pet getPetRecordByOwnerID(String id) throws Exception;

    Pet getPetRecordByID(String id) throws Exception;


    List<History> getHistoryRecords(String id) throws Exception;

    Feed getFeedRecordByID(String id) throws Exception;

    Drug getDrugRecordByID(String id) throws Exception;

    Disease getDiseaseRecordByID(String id) throws Exception;

    EnvironmentVariant getEnvironmentVariantRecordByID(String id) throws Exception;
}
