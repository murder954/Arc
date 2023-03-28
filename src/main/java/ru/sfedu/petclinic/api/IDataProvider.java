package ru.sfedu.petclinic.api;

import ru.sfedu.petclinic.model.*;

import java.io.IOException;
import java.util.List;

public interface IDataProvider {

    void animalTreatment(Pet object, IDataProvider dataProvider, List<Disease> diseases, List<Drug> drugs) throws Exception;

    void createRecipe(Treatment obj, double packs) throws IOException;
    void calculateExpensesAnimal(Pet pet, IDataProvider dataProvider) throws Exception;
    double expensesMedicine(String id, IDataProvider dataProvider) throws Exception;
    double expensesFood(String id, IDataProvider dataProvider) throws Exception;
    double expensesEnvironment(String id, IDataProvider dataProvider) throws Exception;
    void selectionFeed(Pet pet, List<Feed> list, boolean flag, int pieces, IDataProvider dataProvider) throws Exception;
    double calculateFeedCost(Feed feed, int pieces);
    void selectionEnvironmentVariant(Pet pet, List<EnvironmentVariant> list, boolean flag, IDataProvider dataProvider) throws Exception;
    double calculateEnvironmentCost(EnvironmentVariant obj);

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

    Owner getOwnerRecordByID(String id) throws Exception;

    Pet getPetRecordByOwnerID(String id) throws Exception;

    Pet getPetRecordByID(String id) throws Exception;


    List<History> getHistoryRecords(String id) throws Exception;

    Feed getFeedRecordByID(String id) throws Exception;

    Drug getDrugRecordByID(String id) throws Exception;

    Disease getDiseaseRecordByID(String id) throws Exception;

    EnvironmentVariant getEnvironmentVariantRecordByID(String id) throws Exception;

    List<Disease> getAllDiseases() throws Exception;

    List<Drug> getAllDrugs() throws Exception;

    List<Feed> getAllFeeds() throws Exception;

    List<EnvironmentVariant> getAllEnvironmentVariants() throws Exception;
}
