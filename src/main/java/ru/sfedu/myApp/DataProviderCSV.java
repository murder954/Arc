package ru.sfedu.myApp;

import com.opencsv.bean.*;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.myApp.Entity.*;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static ru.sfedu.myApp.Constans.*;

public class DataProviderCSV implements IDataProvider{

    private static Logger log = LogManager.getLogger(DataProviderCSV.class);
    LoggingBeans logToHistory = new LoggingBeans();
    static ConfigUtils config = new ConfigUtils();

    private String[] ownerHeader = {"ownerId", "ownerName", "phone", "email", "bankAccount"};
    private String[] catHeader = {"name", "id", "gender", "weight", "feetType", "type", "age", "nameOfDisease", "ownerId", "isAfraidDogs", "isCalm"};
    private String[] dogHeader = {"name", "id", "gender", "weight", "feetType", "type", "age", "nameOfDisease", "ownerId", "isAggressive"};
    private String[] birdHeader = {"name", "id", "gender", "weight", "feetType", "type", "age", "nameOfDisease", "ownerId", "isWaterFowl"};
    private String[] fishHeader = {"name", "id", "gender", "weight", "feetType", "type", "age", "nameOfDisease", "ownerId","waterType"};
    private String[] petHeader = {"name", "id", "gender", "weight", "feetType", "type", "age", "nameOfDisease", "ownerId"};

    private String[] historyHeader = {"petName", "petId", "ownerId", "serviceName", "priceForService", "date"};

    private String[] feedHeader = {"name", "id", "price", "weight", "forPet"};
    private String[] drugHeader = {"name", "id", "price", "pieces", "intensity"};
    private String[] diseaseHeader = {"name", "id", "treatmentTime", "forTreatment"};
    private String[] envVarHeader = {"name", "id", "insideHouse", "features", "addition", "price", "forPet"};


    public DataProviderCSV() throws IOException {
        log.debug("create dataProviderCSV...");
    }

    public <T> List<T> getAllRecords(String[] columns, Class<T> tClass, String path) {
        try {
            log.debug("deserialize object...");
            FileReader reader = new FileReader(path);
            ColumnPositionMappingStrategy<T> strat = new ColumnPositionMappingStrategyBuilder<T>().build();
            strat.setType(tClass);
            strat.setColumnMapping(columns);
            CsvToBean csv = new CsvToBeanBuilder(reader).withMappingStrategy(strat).build();
            List list = csv.parse();
            log.debug("records initialization:" + list);
            return list;
        } catch (IOException e) {
            log.info("file is empty");
        }
        return new ArrayList<>();
    }


    public <T> void initRecord(List list, String[] headers, Class<T> tClass, String path) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        log.info("update info");
        new FileOutputStream(path).close();
        FileWriter writer = new FileWriter(path, false);
        ColumnPositionMappingStrategy<T> strategy = new ColumnPositionMappingStrategyBuilder<T>().build();
        strategy.setType(tClass);
        strategy.setColumnMapping(headers);
        StatefulBeanToCsvBuilder<T> builder = new StatefulBeanToCsvBuilder(writer);
        StatefulBeanToCsv beanToCsv = builder.withMappingStrategy(strategy).build();
        beanToCsv.write(list);
        writer.close();
    }


    @Override
    public String createId() {
        String id = String.valueOf(UUID.randomUUID());
        return id;
    }

    @Override
    public void savePetRecord(Pet object, String ownerId) throws Exception {
        String path = PET_CSV;
        Class cl = Pet.class;
        String[] header = petHeader;
        switch (object.getType()){
            case "cat":
                path = CAT_CSV;
                cl = Cat.class;
                header=catHeader;
                break;
            case "dog":
                path = DOG_CSV;
                cl = Dog.class;
                header=dogHeader;
                break;
            case "bird":
                path = BIRD_CSV;
                cl = Bird.class;
                header=birdHeader;
                break;
            case "fish":
                path = FISH_CSV;
                cl = Fish.class;
                header=fishHeader;
                break;
            default:
                break;
        }
        List<Pet> list = getAllRecords(header, cl, config.getConfigurationEntry(path));
        List<Pet> general = getAllRecords(petHeader, Pet.class, config.getConfigurationEntry(PET_CSV));
        object.setOwnerId(ownerId);
        object.setId(createId());
        if (list.stream().noneMatch(s -> ((((Pet) s).getNameOfDisease().equals(object.getNameOfDisease())) && (((Pet) s).getName().equals(object.getName())) && (((Pet) s).getType().equals(object.getType())) && (((Pet) s).getFeedType().equals(object.getFeedType()))))) {
            list.add(object);
            general.add(object);
            log.debug(list);
            initRecord(list, header, cl, config.getConfigurationEntry(path));
            initRecord(general, petHeader, Pet.class, config.getConfigurationEntry(PET_CSV));
        } else {
            throw new Exception("Impossible to save pet, owner with this id not found, check owner's id");
        }
        logToHistory.logObjectChange(object, "savePetCSV", object.getId());
    }

    @Override
    public void saveOwnerRecord(Owner object) throws Exception {
        List<Owner> list = getAllRecords(ownerHeader, Owner.class, config.getConfigurationEntry(OWNER_CSV));
        if (!list.stream().anyMatch(s -> ((Owner) s).getBankAccount() == object.getBankAccount())) {
            list.add(object);
            initRecord(list, ownerHeader, Owner.class, config.getConfigurationEntry(OWNER_CSV));
        } else {
            throw new Exception("Owner with those parameters has been created previously");
        }
        logToHistory.logObjectChange(object, "saveOwnerCSV", object.getId());
    }

    @Override
    public void saveHistoryRecord(Pet pet, Service serv) throws Exception {
        List<History> list = getAllRecords(historyHeader, History.class, "src/main/resources/HistoryFiles/" + pet.getId() + ".csv");
        History historyObj = new History();
        try {
            historyObj.setPetId(pet.getId());
            historyObj.setPetName(pet.getName());
            historyObj.setOwnerId(pet.getOwnerId());
            historyObj.setServiceName(serv.getNameOfService());
            historyObj.setPrice(serv.getCost());
            historyObj.setDate(serv.getDate());
            list.add(historyObj);
            initRecord(list, historyHeader, History.class, "src/main/resources/HistoryFiles/" + pet.getId() + ".csv");
        }catch (Exception e){
            log.error("Data error...");
        }

    }
    @Override
    public void saveFeedRecord(Feed object) throws Exception {
        List<Feed> list = getAllRecords(feedHeader, Feed.class, config.getConfigurationEntry(FEED_CSV));
        object.setId(createId());
        if (!list.stream().anyMatch(s -> s.getFeedName().equals(object.getFeedName()))) {
            list.add(object);
            log.debug(list);
            initRecord(list, feedHeader, Feed.class, config.getConfigurationEntry(FEED_CSV));
        } else {
            throw new Exception("Feed with those parameters has been created previously");
        }
        logToHistory.logObjectChange(object, "saveFeedCSV", object.getId());
    }

    @Override
    public void saveDrugRecord(Drug object) throws Exception {
        List<Drug> list = getAllRecords(drugHeader, Drug.class, config.getConfigurationEntry(DRUG_CSV));
        object.setId(createId());
        if (list.stream().noneMatch(s -> s.getDrugName().equals(object.getDrugName()))) {
            list.add(object);
            log.debug(list);
            initRecord(list, drugHeader, Drug.class, config.getConfigurationEntry(DRUG_CSV));
        } else {
            throw new Exception("Drug with those parameters has been created previously");
        }
        logToHistory.logObjectChange(object, "saveDrugCSV", object.getId());
    }

    @Override
    public void saveDiseaseRecord(Disease object) throws Exception {
        List<Disease> list = getAllRecords(diseaseHeader, Disease.class, config.getConfigurationEntry(DISEASE_CSV));
        object.setId(createId());
        if (!list.stream().anyMatch(s -> s.getNameOfDisease().equals(object.getNameOfDisease()))) {
            list.add(object);
            log.debug(list);
            initRecord(list, drugHeader, Disease.class, config.getConfigurationEntry(DISEASE_CSV));
        } else {
            throw new Exception("Disease with those parameters has been created previously");
        }
        logToHistory.logObjectChange(object, "saveDiseaseCSV", object.getId());
    }

    @Override
    public void saveEnvironmentVariantRecord(EnvironmentVariant object) throws Exception {
        List<EnvironmentVariant> list = getAllRecords(envVarHeader, EnvironmentVariant.class, config.getConfigurationEntry(ENVVAR_CSV));
        object.setId(createId());
        if (!list.stream().anyMatch(s -> ((s.getHouseName().equals(object.getHouseName())) && (s.getAddition().equals(object.getAddition())) && (s.getEnvironmentFeatures().equals(object.getEnvironmentFeatures()))))) {
            list.add(object);
            log.debug(list);
            initRecord(list, envVarHeader, EnvironmentVariant.class, config.getConfigurationEntry(ENVVAR_CSV));
        } else {
            throw new Exception("Environment Variant with those parameters has been created previously");
        }
        logToHistory.logObjectChange(object, "saveVariantCSV", object.getId());
    }


    @Override
    public void findForDelPetByOwner(String id) throws Exception {
        List<Pet> list = getAllRecords(petHeader, Pet.class, config.getConfigurationEntry(PET_CSV));
        for (Pet pet : list) {
            if (pet.getOwnerId().equals(id)) {
                deletePetRecord(pet);
            }
        }
        if (list.removeIf(s -> s.getOwnerId().equals(id))) {
            log.info("Pet has been deleted");
        } else
            throw new Exception("Delete pet error, check ID");
        initRecord(list, petHeader, Pet.class, config.getConfigurationEntry(PET_CSV));
    }
    @Override
    public void deleteOwnerRecord(String id) throws Exception {
        List<Owner> list = getAllRecords(ownerHeader, Owner.class, config.getConfigurationEntry(OWNER_CSV));
        if (list.removeIf(ow -> ow.getId().equals(id))) {
            findForDelPetByOwner(id);
            log.info("Owner has been deleted");
        } else
            throw new Exception("Delete owner error, check ID");
        logToHistory.logObjectChange(getOwnerRecordByID(id), "deleteOwnerCSV", id);
        initRecord(list, ownerHeader, Owner.class, config.getConfigurationEntry(OWNER_CSV));
    }


    @Override
    public void deletePetRecord(Pet pet) throws Exception {
        Class cl = Pet.class;
        String[] header =petHeader;
        String path = PET_CSV;
        switch (pet.getType()) {
            case "cat":
                cl = Cat.class;
                header = catHeader;
                path = CAT_CSV;
                break;
            case "dog":
                cl = Dog.class;
                header =dogHeader;
                path = DOG_CSV;
                break;
            case "bird":
                cl = Bird.class;
                header = birdHeader;
                path = BIRD_CSV;
                break;
            case "fish":
                cl = Fish.class;
                header = fishHeader;
                path = FISH_CSV;
                break;
            default:
                break;
        }
        List<Pet> list = getAllRecords(header, cl, config.getConfigurationEntry(path));
        if (list.removeIf(s -> s.getId().equals(pet.getId()))) {
            log.info("Pet has been deleted");
        } else
            throw new Exception("Delete pet error, check ID");
        logToHistory.logObjectChange(pet, "deletePetCSV", pet.getId());
        initRecord(list, header, cl, config.getConfigurationEntry(path));
    }

    @Override
    public void deleteOnePetRecord(String id) throws Exception {
        List<Pet> list = getAllRecords(petHeader, Pet.class, config.getConfigurationEntry(PET_CSV));
        for (Pet pet : list){
            if(pet.getId().equals(id)){
                deletePetRecord(pet);
            }
        }
        if (list.removeIf(s -> s.getId().equals(id))) {
            log.info("Pet has been deleted");
        } else
            throw new Exception("Delete pet error, check ID");
        initRecord(list, petHeader, Pet.class, config.getConfigurationEntry(PET_CSV));
    }

    @Override
    public void deleteFeedRecord(String id) throws Exception {
        List<Feed> list = getAllRecords(feedHeader, Feed.class, config.getConfigurationEntry(FEED_CSV));
        if (list.removeIf(s -> s.getId().equals(id))) {
            log.info("Feed has been deleted");
        } else
            throw new Exception("Delete feed error, check ID");
        logToHistory.logObjectChange(getFeedRecordByID(id), "deleteFeedCSV", id);
        initRecord(list, feedHeader, Feed.class, config.getConfigurationEntry(FEED_CSV));
    }

    @Override
    public void deleteDrugRecord(String id) throws Exception {
        List<Drug> list = getAllRecords(drugHeader, Drug.class, config.getConfigurationEntry(DRUG_CSV));
        if (list.removeIf(s -> s.getId().equals(id))) {
            log.debug("Drug has been deleted");
        } else
            throw new Exception("Delete drug error, check ID");
        logToHistory.logObjectChange(getDrugRecordByID(id), "deleteDrugCSV", id);
        initRecord(list, drugHeader, Drug.class, config.getConfigurationEntry(DRUG_CSV));
    }

    @Override
    public void deleteDiseaseRecord(String id) throws Exception {
        List<Disease> list = getAllRecords(diseaseHeader, Disease.class, config.getConfigurationEntry(DISEASE_CSV));
        if (list.removeIf(s -> s.getId().equals(id))) {
            log.debug("Disease has been deleted");
        } else
            throw new Exception("Delete disease error, check ID");
        logToHistory.logObjectChange(getDiseaseRecordByID(id), "deleteDiseaseCSV", id);
        initRecord(list, diseaseHeader, Disease.class, config.getConfigurationEntry(DISEASE_CSV));
    }

    @Override
    public void deleteEnvironmentVariantRecord(String id) throws Exception {
        List<EnvironmentVariant> list = getAllRecords(envVarHeader, EnvironmentVariant.class, config.getConfigurationEntry(ENVVAR_CSV));
        if (list.removeIf(s -> s.getId().equals(id))) {
            log.debug("Environment variant has been deleted");
        } else
            throw new Exception("Delete environment variant error, check ID");
        logToHistory.logObjectChange(getEnvironmentVariantRecordByID(id), "deleteVariantCSV", id);
        initRecord(list, envVarHeader, EnvironmentVariant.class, config.getConfigurationEntry(ENVVAR_CSV));
    }


    public Pet findForGetPetRecordByOwnerId(String id) throws Exception {
        List<Pet> list = getAllRecords(petHeader, Pet.class, config.getConfigurationEntry(PET_CSV));
        if(list.stream().noneMatch(p -> p.getOwnerId().equals(id))) {
            return null;
        }
        for (Pet pet : list){
            if (pet.getOwnerId().equals(id)) {
                return getPet(pet);
            }
        }
        throw new Exception("Error at get owner's list of pets");
    }


    public Pet getPet(Pet pet) throws Exception {
        String path = PET_CSV;
        Class cl = Pet.class;
        String[] header = petHeader;
        switch (pet.getType()){
            case "cat":
                path = CAT_CSV;
                cl = Cat.class;
                header=catHeader;
                break;
            case "dog":
                path = DOG_CSV;
                cl = Dog.class;
                header=dogHeader;
                break;
            case "bird":
                path = BIRD_CSV;
                cl = Bird.class;
                header=birdHeader;
                break;
            case "fish":
                path = FISH_CSV;
                cl = Fish.class;
                header=fishHeader;
                break;
            default:
                break;
        }
        List<Pet> list = getAllRecords(header, cl, config.getConfigurationEntry(path));
        for (Pet p : list){
            if(p.getId().equals(pet.getId()))
                return p;
        }
        throw new Exception("Error in method getPet");
    }

    @Override
    public Owner getOwnerRecordByID(String id) throws Exception {
        List<Owner> list = getAllRecords(ownerHeader, Owner.class, config.getConfigurationEntry(OWNER_CSV));
        for (Owner owner : list) {
            log.debug("get record with id " + id);
            if (owner.getId().equals(id)) {
                return owner;
            }
        }
        throw new Exception("Record not found");
    }

    @Override
    public Pet getPetRecordByOwnerID(String id) throws Exception {
        List<Pet> list = getAllRecords(petHeader, Pet.class, config.getConfigurationEntry(PET_CSV));
        for (Pet pet : list) {
            log.debug("get record with id " + id);
            if (pet.getOwnerId().equals(id)) {
                return findForGetPetRecordByOwnerId(id);
            }
        }
        throw new Exception("Record not found");
    }


    @Override
    public List<History> getHistoryRecords(String id) throws Exception {
        List<History> list = getAllRecords(historyHeader, History.class, "src/main/resources/HistoryFiles/" + id + ".csv");
        if(list.isEmpty()){
            throw new Exception("File is empty");
        }
        return list;
    }
    @Override
    public Pet getPetRecordByID(String id) throws Exception {
        List<Pet> list = getAllRecords(petHeader, Pet.class, config.getConfigurationEntry(PET_CSV));
        for (Pet pet : list) {
            log.debug("get record with id " + id);
            if (pet.getId().equals(id)) {
                return getPet(pet);
            }
        }
        throw new Exception("Record not found");
    }

    @Override
    public Feed getFeedRecordByID(String id) throws Exception {
        List<Feed> list = getAllRecords(feedHeader, Feed.class, config.getConfigurationEntry(FEED_CSV));
        for (Feed s : list) {
            log.debug("get record with id " + id);
            if (s.getId().equals(id)) {
                return s;
            }
        }
        throw new Exception("Record not found");
    }

    @Override
    public Drug getDrugRecordByID(String id) throws Exception {
        List<Drug> list = getAllRecords(drugHeader, Drug.class, config.getConfigurationEntry(DRUG_CSV));

        for (Drug s : list) {
            log.debug("get record with id " + id);
            if (s.getId().equals(id)) {
                return s;
            }
        }
        throw new Exception("Record not found");
    }

    @Override
    public Disease getDiseaseRecordByID(String id) throws Exception {
        List<Disease> list = getAllRecords(diseaseHeader, Disease.class, config.getConfigurationEntry(DISEASE_CSV));

        for (Disease s : list) {
            log.debug("get record with id " + id);
            if (s.getId().equals(id)) {
                return s;
            }
        }
        throw new Exception("Record not found");
    }

    @Override
    public EnvironmentVariant getEnvironmentVariantRecordByID(String id) throws Exception {
        List<EnvironmentVariant> list = getAllRecords(envVarHeader, EnvironmentVariant.class, config.getConfigurationEntry(ENVVAR_CSV));

        for (EnvironmentVariant s : list) {
            log.debug("get record with id " + id);
            if (s.getId().equals(id)) {
                return s;
            }
        }
        throw new Exception("Record not found");
    }
}
