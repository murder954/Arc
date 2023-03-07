package ru.sfedu.myApp;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.sfedu.myApp.Model.*;

import static org.junit.jupiter.api.Assertions.*;

class DataProviderCSVTest {
    private static DataProviderCSV dataProvider;

    static Cat test_pet1 = new Cat("Vasiliy", "male", 3.4, "potato", "cat", 1, "stomach_ache", false, false);
    static Cat test_pet2 = new Cat("Vasilisa", "female", 6.07, "premium_feed_with_meat", "cat", 3, "ill", true, true);
    static Dog test_pet3 = new Dog("Muhtar", "male", 21.07, "feed_with_meat_taste", "dog", 1, "break_paw", true);
    static Fish test_pet4 = new Fish("Goldy", "female", 0.3, "bread", "fish", 2, "lepidorthosis", "fresh_water");
    static Bird test_pet5 = new Bird("Teo", "male", 0.7, "worms", "bird", 1, "brake_wing", false);
    static Dog test_pet6 = new Dog("Biba", "female", 33.1, "Meal", "dog", 6, "ill", false);
    static Fish test_pet7 = new Fish("Nemo", "male", 0.8, "worms", "fish", 2, "lepidorthosis", "sea");

    static Owner test_owner1 = new Owner("Ivan_Goydenko", "+79185534778", "goydenko2002@yandex.ru", 8888888888888888L);
    static Owner test_owner2 = new Owner("Matvei_Mishin", "+79287543554", "shatun1942@sfedu.ru", 1234123412341234L);
    static Owner test_owner3 = new Owner("Viktor_Anikeev", "+79289090400", "vanikeev@yandex.ru", 5675439427854321L);
    static Owner test_owner4 = new Owner("Valeri_Meladze", "+79246375153", "meladze@mail.ru", 8324827480954327L);
    static Owner test_owner5 = new Owner("Silvestor_Stalone", "+79823567216", "realman@mail.ru", 8492947164829103L);
    static Owner test_owner6 = new Owner("Jonny_Silverhand", "+79127348967", "afterlife@gmail.com", 6666666666666666L);
    static Owner test_owner7 = new Owner("Genadiy_Bukin", "+79884526715", "bukin@yandex.ru", 7283571098264538L);

    static Feed test_feed1 = new Feed("premium_feed_with_meat", 546.34, 500, "cat");
    static Feed test_feed2 = new Feed("seed", 203.0, 1000, "bird");
    static Feed test_feed3 = new Feed("porridge_with_meal", 563, 1000, "dog");

    static Drug test_drug1 = new Drug("Calcium", 1990.42, 60, 3);
    static Drug test_drug2 = new Drug("Aspirin", 295.34, 12, 1);
    static Drug test_drug3 = new Drug("antiglist", 495.34, 10, 2);

    static Disease test_disease1 = new Disease("brake_paw", 6, "calcium");
    static Disease test_disease2 = new Disease("stomach_ache", 2, "Aspirin");
    static Disease test_disease3 = new Disease("glists", 14, "antiglist");

    static EnvironmentVariant test_variant1 = new EnvironmentVariant("aquarium", true, "oxygen_pump", "decorative_castle", 2990.0, "fish");
    static EnvironmentVariant test_variant2 = new EnvironmentVariant("Cell", true, "Water tank", "perch", 499.0, "bird");
    static EnvironmentVariant test_variant3 = new EnvironmentVariant("Couch", true, "NONE", "pillow", 999.0, "cat");

    @BeforeAll
    static void init() throws Exception {
        dataProvider = new DataProviderCSV();
    }

    @Test
    void saveOwnerPositive() throws Exception {
        dataProvider.saveOwnerRecord(test_owner1);
        dataProvider.savePetRecord(test_pet1, test_owner1.getId());
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
    void savePetNegative() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> {
            dataProvider.savePetRecord(test_pet2, "29eda22f-c5b6-48a7-896c-7f6a02d134f1");
        });
        assertEquals("Impossible to save pet, owner with this id not found, check owner's id", exception.getMessage());
    }

    @Test
    void saveFeedPositive() throws Exception {
        dataProvider.saveFeedRecord(test_feed1);
        assertNotNull(dataProvider.getFeedRecordByID(test_feed1.getId()));
    }

    @Test
    void saveFeedNegative() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> {
            dataProvider.saveFeedRecord(test_feed1);
        });
        assertEquals("Feed with those parameters has been created previously", exception.getMessage());
    }

    @Test
    void saveDrugPositive() throws Exception {
        dataProvider.saveDrugRecord(test_drug1);
        assertNotNull(dataProvider.getDrugRecordByID(test_drug1.getId()));
    }

    @Test
    void saveDrugNegative() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> {
            dataProvider.saveDrugRecord(test_drug1);
        });
        assertEquals("Drug with those parameters has been created previously", exception.getMessage());
    }

    @Test
    void saveDiseasePositive() throws Exception {
        dataProvider.saveDiseaseRecord(test_disease1);
        assertNotNull(dataProvider.getDiseaseRecordByID(test_disease1.getId()));
    }

    @Test
    void saveDiseaseNegative() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> {
            dataProvider.saveDiseaseRecord(test_disease1);
        });
        assertEquals("Disease with those parameters has been created previously", exception.getMessage());
    }

    @Test
    void saveEnvironmentVariantPositive() throws Exception {
        dataProvider.saveEnvironmentVariantRecord(test_variant1);
        assertNotNull(dataProvider.getEnvironmentVariantRecordByID(test_variant1.getId()));
    }

    @Test
    void saveEnvironmentVariantNegative() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> {
            dataProvider.saveEnvironmentVariantRecord(test_variant1);
        });
        assertEquals("Environment Variant with those parameters has been created previously", exception.getMessage());
    }

    @Test
    void getOwnerRecordByIDPositive() throws Exception {
        dataProvider.saveOwnerRecord(test_owner4);
        assertNotNull(dataProvider.getOwnerRecordByID(test_owner4.getId()));
    }

    @Test
    void getOwnerRecordByIDNegative() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> {
            dataProvider.getOwnerRecordByID("kmsdfsdfsdf");
        });
        assertEquals("Record not found", exception.getMessage());
    }

    @Test
    void getPetRecordByOwnerIDPositive() throws Exception {
        dataProvider.saveOwnerRecord(test_owner5);
        dataProvider.savePetRecord(test_pet5, test_owner5.getId());
        assertNotNull(dataProvider.getPetRecordByOwnerID(test_pet5.getOwnerId()));
    }

    @Test
    void getPetRecordByOwnerIDNegative() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> {
            dataProvider.getPetRecordByOwnerID("kmsdfdsfsfsdfsdf");
        });
        assertEquals("Record not found", exception.getMessage());
    }

    @Test
    void getPetRecordByIDPositive() throws Exception {
        dataProvider.saveOwnerRecord(test_owner6);
        dataProvider.savePetRecord(test_pet6, test_owner6.getId());
        assertNotNull(dataProvider.getPetRecordByID(test_pet6.getId()));
    }

    @Test
    void getPetRecordByIDNegative() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> {
            dataProvider.getPetRecordByID("dfbnslkfmfsdiu");
        });
        assertEquals("Record not found", exception.getMessage());
    }

    @Test
    void getFeedRecordByIDPositive() throws Exception {
        dataProvider.saveFeedRecord(test_feed2);
        assertNotNull(dataProvider.getFeedRecordByID(test_feed2.getId()));
    }

    @Test
    void getFeedRecordByIDNegative() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> {
            dataProvider.getFeedRecordByID("doojfsdofodsjq");
        });
        assertEquals("Record not found", exception.getMessage());
    }

    @Test
    void getDrugRecordByIDPositive() throws Exception {
        dataProvider.saveDrugRecord(test_drug2);
        assertNotNull(dataProvider.getDrugRecordByID(test_drug2.getId()));
    }

    @Test
    void getDrugRecordByIDNegative() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> {
            dataProvider.getDrugRecordByID("doojfsdofodsjq");
        });
        assertEquals("Record not found", exception.getMessage());
    }

    @Test
    void getDiseaseRecordByIDPositive() throws Exception {
        dataProvider.saveDiseaseRecord(test_disease2);
        assertNotNull(dataProvider.getDiseaseRecordByID(test_disease2.getId()));
    }

    @Test
    void getDiseaseRecordByIDNegative() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> {
            dataProvider.getDiseaseRecordByID("qwesdvxvfwerdsfdafspd");
        });
        assertEquals("Record not found", exception.getMessage());
    }

    @Test
    void getEnvironmentVariantRecordByIDPositive() throws Exception {
        dataProvider.saveEnvironmentVariantRecord(test_variant2);
        assertNotNull(dataProvider.getEnvironmentVariantRecordByID(test_variant2.getId()));
    }

    @Test
    void getEnvironmentVariantRecordByIDNegative() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> {
            dataProvider.getEnvironmentVariantRecordByID("sdiewirhhdshfiu");
        });
        assertEquals("Record not found", exception.getMessage());
    }

    @Test
    void deleteOnePetRecordPositive() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> {
            dataProvider.saveOwnerRecord(test_owner7);
            dataProvider.savePetRecord(test_pet7, test_owner7.getId());
            dataProvider.deleteOnePetRecord(test_pet7.getId());
            dataProvider.getPetRecordByID(test_pet7.getId());
        });
        assertEquals("Record not found", exception.getMessage());
    }

    @Test
    void deleteOnePetRecordNegative() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> {
            dataProvider.deleteOnePetRecord("lersdfdsfretmn");
        });
        assertEquals("Delete pet error, check ID", exception.getMessage());
    }

    @Test
    void deleteOwnerRecordPositive() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> {
            dataProvider.saveOwnerRecord(test_owner3);
            dataProvider.savePetRecord(test_pet3, test_owner3.getId());
            dataProvider.deleteOwnerRecord(test_owner3.getId());
            dataProvider.getOwnerRecordByID(test_owner3.getId());
        });
        assertEquals("Record not found", exception.getMessage());
    }

    @Test
    void deleteOwnerRecordNegative() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> {
            dataProvider.deleteOwnerRecord("sdfsfsdferwrvc");
        });
        assertEquals("Delete owner error, check ID", exception.getMessage());
    }

    @Test
    void deleteFeedRecordPositive() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> {
            dataProvider.saveFeedRecord(test_feed3);
            dataProvider.deleteFeedRecord(test_feed3.getId());
            dataProvider.getFeedRecordByID(test_feed3.getId());
        });
        assertEquals("Record not found", exception.getMessage());
    }

    @Test
    void deleteFeedRecordNegative() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> {
            dataProvider.deleteFeedRecord("sdgaerihejdjs");
        });
        assertEquals("Delete feed error, check ID", exception.getMessage());
    }

    @Test
    void deleteDrugRecordPositive() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> {
            dataProvider.saveDrugRecord(test_drug3);
            dataProvider.deleteDrugRecord(test_drug3.getId());
            dataProvider.getDrugRecordByID(test_drug3.getId());
        });
        assertEquals("Record not found", exception.getMessage());
    }

    @Test
    void deleteDrugRecordNegative() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> {
            dataProvider.deleteDrugRecord("kpdsfpwe");
        });
        assertEquals("Delete drug error, check ID", exception.getMessage());
    }

    @Test
    void deleteDiseaseRecordPositive() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> {
            dataProvider.saveDiseaseRecord(test_disease3);
            dataProvider.deleteDiseaseRecord(test_disease3.getId());
            dataProvider.getDiseaseRecordByID(test_disease3.getId());
        });
        assertEquals("Record not found", exception.getMessage());
    }

    @Test
    void deleteDiseaseRecordNegative() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> {
            dataProvider.deleteDiseaseRecord("sfdewsvvbfds");
        });
        assertEquals("Delete disease error, check ID", exception.getMessage());
    }

    @Test
    void deleteEnvironmentVariantRecordPositive() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> {
            dataProvider.saveEnvironmentVariantRecord(test_variant3);
            dataProvider.deleteEnvironmentVariantRecord(test_variant3.getId());
            dataProvider.getEnvironmentVariantRecordByID(test_variant3.getId());
        });
        assertEquals("Record not found", exception.getMessage());
    }

    @Test
    void deleteEnvironmentVariantRecordNegative() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> {
            dataProvider.deleteEnvironmentVariantRecord("kmsdfsdfsdf");
        });
        assertEquals("Delete environment variant error, check ID", exception.getMessage());
    }
}