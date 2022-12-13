package ru.sfedu.myApp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import ru.sfedu.myApp.Entity.*;


class DataProviderXMLTest {

    private static final Logger log = LogManager.getLogger(DataProviderXMLTest.class);
    private static DataProviderXML dataProvider;

    static Cat test_pet2 = new Cat("Vasilisa", "female", 6.07, "premium_feed_with_meat", PetTypes.CAT, 3, "ill", true, true);
    static Dog test_pet3 = new Dog("Muhtar", "male", 21.07, "feed_with_meat_taste", PetTypes.DOG, 1, "break paw", true);
    static Fish test_pet4 = new Fish("Goldy", "female", 0.3, "bread", PetTypes.FISH, 2, "lepidorthosis", "fresh_water");;
    static Bird test_pet5 = new Bird("Teo", "male", 0.7, "worms", PetTypes.BIRD, 1, "brake wing", false);


    static Owner test_owner1 = new Owner("Ivan_Goydenko", "+79185534778", "goydenko2002@yandex.ru", 8888888888888888L);

    static Owner test_owner2 = new Owner("Matvei_Mishin", "+79287543554", "shatun1942@sfedu.ru", 1234123412341234L);

    static Owner test_owner3 = new Owner("Viktor_Anikeev", "+79289090400", "vanikeev@yandex.ru", 5675439427854321L);
    static Feed test_feed1 = new Feed("premium_feed_with_meat", 546.34, 500);

    static Feed test_feed2 = new Feed("potato", 63, 1000);
    static Drug test_drug1 = new Drug("Calcium", 1990.42, 60, 3);

    static Drug test_drug2 = new Drug("Aspirin", 295.34, 12, 1);
    static Disease test_disease1 = new Disease("brake_paw", 6, "calcium");

    static Disease test_disease2 = new Disease("stomach_ache", 2, "Aspirin");
    static EnvironmentVariant test_variant1 = new EnvironmentVariant("aquarium", true, "oxygen_pump", "decorative_castle", 2990.0);

    static EnvironmentVariant test_variant2 = new EnvironmentVariant("Cell", true, "Water tank", "perch", 499.0);
    @BeforeAll
    static void init() throws Exception {
        dataProvider = new DataProviderXML();
        Cat test_pet1 = new Cat("Vasiliy", "male", 3.4, "potato", PetTypes.CAT, 1, "stomach_ache", false, false);
        test_owner1.addPetToOwner(test_pet1);
        test_owner3.addPetToOwner(test_pet4);
    }

    @Test
    void saveOwnerPositive() throws Exception {
        dataProvider.saveOwnerRecord(test_owner3);
        dataProvider.saveOwnerRecord(test_owner1);
        assertNotNull(dataProvider.getOwnerRecordByID(test_owner1.getId()));
    }

    @Test
    void saveOwnerNegative() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> {
            dataProvider.saveOwnerRecord(test_owner1);
        });
        assertEquals("Owner with those parameters has been created previously", exception.getMessage());
    }

    @Test
    void savePetPositive() throws Exception {
        dataProvider.saveOwnerRecord(test_owner2);
        dataProvider.savePetRecord(test_pet2, test_owner2.getId());
        assertNotNull(dataProvider.getPetRecordByOwnerID(test_owner2.getId()));
    }

    @Test
    void savePetNegative() throws Exception{
        Exception exception = assertThrows(Exception.class, () -> {
            dataProvider.savePetRecord(test_pet2, test_owner2.getId());
        });
        assertEquals("Impossible to save pet, owner with this id not found, check owner's id", exception.getMessage());
    }

    @Test
    void saveFeedPositive() throws Exception{
        dataProvider.saveFeedRecord(test_feed1);
        dataProvider.saveFeedRecord(test_feed2);
        assertNotNull(dataProvider.getFeedRecordByID(test_feed1.getId()));
    }

    @Test
    void saveFeedNegative() throws Exception{
        Exception exception = assertThrows(Exception.class, () -> {
            dataProvider.saveFeedRecord(test_feed1);
        });
        assertEquals("Feed with those parameters has been created previously", exception.getMessage());
    }

    @Test
    void saveDrugPositive() throws  Exception{
        dataProvider.saveDrugRecord(test_drug1);
        dataProvider.saveDrugRecord(test_drug2);
        assertNotNull(dataProvider.getDrugRecordByID(test_drug1.getId()));
    }

    @Test
    void saveDrugNegative() throws Exception{
        Exception exception = assertThrows(Exception.class, () -> {
            dataProvider.saveDrugRecord(test_drug1);
        });
        assertEquals("Drug with those parameters has been created previously", exception.getMessage());
    }

    @Test
    void saveDiseasePositive() throws Exception{
        dataProvider.saveDiseaseRecord(test_disease1);
        dataProvider.saveDiseaseRecord(test_disease2);
        assertNotNull(dataProvider.getDiseaseRecordByID(test_disease1.getId()));
    }

    @Test
    void saveDiseaseNegative() throws Exception{
        Exception exception = assertThrows(Exception.class, () -> {
            dataProvider.saveDiseaseRecord(test_disease1);
        });
        assertEquals("Disease with those parameters has been created previously", exception.getMessage());
    }

    @Test
    void saveEnvironmentVariantPositive() throws Exception{
        dataProvider.saveEnvironmentVariantRecord(test_variant1);
        dataProvider.saveEnvironmentVariantRecord(test_variant2);
        assertNotNull(dataProvider.getEnvironmentVariantRecordByID(test_variant1.getId()));
    }

    @Test
    void saveEnvironmentVariantNegative() throws Exception{
        Exception exception = assertThrows(Exception.class, () -> {
            dataProvider.saveEnvironmentVariantRecord(test_variant1);
        });
        assertEquals("Environment Variant with those parameters has been created previously", exception.getMessage());
    }

    @Test
    void deleteOwnerRecordPositive() throws Exception{
        dataProvider.deleteOwnerRecord("0c0e1a00-233c-4587-9f86-7a4fef58e65d");
    }

    @Test
    void deleteOwnerRecordNegative() throws Exception{
        Exception exception = assertThrows(Exception.class, () -> {
            dataProvider.deleteOwnerRecord("sfdkdfbkdsf");
        });
        assertEquals("Delete owner error, check ID", exception.getMessage());
    }

    @Test
    void deletePetRecordByIdPositive() throws Exception{
        dataProvider.deletePetRecordById("c31127bb-8f09-41c9-a5e5-9a6ec96477fc");
    }

    @Test
    void deletePetRecordByIdNegative() throws Exception{
        Exception exception = assertThrows(Exception.class, () -> {
            dataProvider.deletePetRecordById("sfdhiwhfowe");
        });
        assertEquals("Delete pet error, check ID", exception.getMessage());

    }

    @Test
    void deleteFeedRecordPositive() throws Exception{
        dataProvider.deleteFeedRecord("b334c9c5-d4e9-4203-b6d7-65668c4740ec");
    }

    @Test
    void deleteFeedRecordNegative() throws Exception{
        Exception exception = assertThrows(Exception.class, () -> {
            dataProvider.deleteFeedRecord("sdgaerihejdjs");
        });
        assertEquals("Delete feed error, check ID", exception.getMessage());
    }

    @Test
    void deleteDrugRecordPositive() throws Exception{
        dataProvider.deleteDrugRecord("5be559ce-16b9-41cd-990a-b088ae7ebd8f");
    }

    @Test
    void deleteDrugRecordNegative() throws Exception{
        Exception exception = assertThrows(Exception.class, () -> {
            dataProvider.deleteDrugRecord("kpdsfpwe");
        });
        assertEquals("Delete drug error, check ID", exception.getMessage());
    }

    @Test
    void deleteDiseaseRecordPositive() throws Exception{
        dataProvider.deleteDiseaseRecord("0aa436e2-0fc0-40bf-82c8-8ee7054c867e");
    }

    @Test
    void deleteDiseaseRecordNegative() throws Exception{
        Exception exception = assertThrows(Exception.class, () -> {
            dataProvider.deleteDiseaseRecord("sfdewsvvbfds");
        });
        assertEquals("Delete disease error, check ID", exception.getMessage());
    }

    @Test
    void deleteEnvironmentVariantRecordPositive() throws Exception{
        dataProvider.deleteEnvironmentVariantRecord("fd96cdf6-a830-4ae2-b5f2-f7521cc9c3a2");
    }

    @Test
    void deleteEnvironmentVariantRecordNegative() throws Exception{
        Exception exception = assertThrows(Exception.class, () -> {
            dataProvider.deleteEnvironmentVariantRecord("kmsdfsdfsdf");
        });
        assertEquals("Delete environment variant error, check ID", exception.getMessage());
    }

    @Test
    void getOwnerRecordByIDPositive() throws Exception{
        assertNotNull(dataProvider.getOwnerRecordByID("c847863c-7860-4dc4-93ff-449b935c4df3"));
    }

    @Test
    void getOwnerRecordByIDNegative() throws Exception{
        Exception exception = assertThrows(Exception.class, () -> {
            dataProvider.getOwnerRecordByID("kmsdfsdfsdf");
        });
        assertEquals("Record not found", exception.getMessage());
    }

    @Test
    void getPetRecordByIDPositive() throws Exception{
        assertNotNull(dataProvider.getPetRecordByID("f5e73753-478e-4402-9be4-35dbf49d26ab"));
    }

    @Test
    void getPetRecordByIDNegative() throws Exception{
        Exception exception = assertThrows(Exception.class, () -> {
            dataProvider.getPetRecordByID("dfbnslkfmfsdiu");
        });
        assertEquals("Record not found", exception.getMessage());
    }

    @Test
    void getFeedRecordByIDPositive() throws Exception{
        assertNotNull(dataProvider.getFeedRecordByID("54a74b25-9080-4a1c-a22f-fccc0ebe3dcd"));
    }

    @Test
    void getFeedRecordByIDNegative() throws Exception{
        Exception exception = assertThrows(Exception.class, () -> {
            dataProvider.getFeedRecordByID("doojfsdofodsjq");
        });
        assertEquals("Record not found", exception.getMessage());
    }

    @Test
    void getDrugRecordByIDPositive() throws Exception{
        assertNotNull(dataProvider.getDrugRecordByID("2f780458-3c5c-4d5f-9085-cbeb1e42f93e"));
    }

    @Test
    void getDrugRecordByIDNegative() throws Exception{
        Exception exception = assertThrows(Exception.class, () -> {
            dataProvider.getDrugRecordByID("doojfsdofodsjq");
        });
        assertEquals("Record not found", exception.getMessage());
    }

    @Test
    void getDiseaseRecordByIDPositive() throws Exception{
        assertNotNull(dataProvider.getDiseaseRecordByID("9711b5e0-f520-4129-973c-59fc5ee5a5f4"));
    }

    @Test
    void getDiseaseRecordByIDNegative() throws Exception{
        Exception exception = assertThrows(Exception.class, () -> {
            dataProvider.getDiseaseRecordByID("qwesdvxvfwerdsfdafspd");
        });
        assertEquals("Record not found", exception.getMessage());
    }

    @Test
    void getEnvironmentVariantRecordByIDPositive() throws Exception{
        assertNotNull(dataProvider.getEnvironmentVariantRecordByID("e7e561a4-eea1-4522-ae3c-517b143a6205"));
    }

    @Test
    void getEnvironmentVariantRecordByIDNegative() throws Exception{
        Exception exception = assertThrows(Exception.class, () -> {
            dataProvider.getEnvironmentVariantRecordByID("sdiewirhhdshfiu");
        });
        assertEquals("Record not found", exception.getMessage());
    }

}