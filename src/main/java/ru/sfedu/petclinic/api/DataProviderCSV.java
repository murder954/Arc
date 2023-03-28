package ru.sfedu.petclinic.api;

import com.opencsv.bean.*;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.petclinic.LoggingBeans;
import ru.sfedu.petclinic.model.*;
import ru.sfedu.petclinic.util.ConfigUtils;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static ru.sfedu.petclinic.Constans.*;

public class DataProviderCSV implements IDataProvider {

    private static Logger log = LogManager.getLogger(DataProviderCSV.class);
    LoggingBeans logToHistory = new LoggingBeans();
    static ConfigUtils config = new ConfigUtils();

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
        String path;
        Class cl;
        String[] header;
        switch (object.getType()) {
            case CAT:
                path = CAT_CSV;
                cl = CAT_CLASS;
                header = config.getConfigurationEntry(CAT_HEADER).split(SPLIT_REGEX);
                break;
            case DOG:
                path = DOG_CSV;
                cl = DOG_CLASS;
                header = config.getConfigurationEntry(DOG_HEADER).split(SPLIT_REGEX);
                break;
            case BIRD:
                path = BIRD_CSV;
                cl = BIRD_CLASS;
                header = config.getConfigurationEntry(BIRD_HEADER).split(SPLIT_REGEX);
                break;
            case FISH:
                path = FISH_CSV;
                cl = FISH_CLASS;
                header = config.getConfigurationEntry(FISH_HEADER).split(SPLIT_REGEX);
                break;
            default:
                throw new Exception("undefined type of pet");
        }
        List<Pet> list = getAllRecords(header, cl, config.getConfigurationEntry(path));
        List<Pet> general = getAllRecords(config.getConfigurationEntry(PET_HEADER).split(SPLIT_REGEX), PET_CLASS, config.getConfigurationEntry(PET_CSV));
        object.setOwnerId(ownerId);
        object.setId(createId());
        if (list.stream().noneMatch(s -> ((((Pet) s).getNameOfDisease().equals(object.getNameOfDisease())) && (((Pet) s).getName().equals(object.getName())) && (((Pet) s).getType().equals(object.getType())) && (((Pet) s).getFeedType().equals(object.getFeedType()))))) {
            list.add(object);
            general.add(object);
            log.debug(list);
            initRecord(list, header, cl, config.getConfigurationEntry(path));
            initRecord(general, config.getConfigurationEntry(PET_HEADER).split(SPLIT_REGEX), PET_CLASS, config.getConfigurationEntry(PET_CSV));
        } else {
            throw new Exception("Impossible to save pet, owner with this id not found, check owner's id");
        }
        try {
            logToHistory.logObjectChange(object, config.getConfigurationEntry(LOG_SAVE_PET_CSV), object.getId());
        }catch (Exception e) {
            throw new Exception("Error at saving Mongo DB record");
        }    }

    @Override
    public void saveOwnerRecord(Owner object) throws Exception {
        List<Owner> list = getAllRecords(config.getConfigurationEntry(OWNER_HEADER).split(SPLIT_REGEX), OWNER_CLASS, config.getConfigurationEntry(OWNER_CSV));
        if (!list.stream().anyMatch(s -> ((Owner) s).getBankAccount() == object.getBankAccount())) {
            list.add(object);
            initRecord(list, config.getConfigurationEntry(OWNER_HEADER).split(SPLIT_REGEX), OWNER_CLASS, config.getConfigurationEntry(OWNER_CSV));
        } else {
            throw new Exception("Owner with those parameters has been created previously");
        }
        try {
            logToHistory.logObjectChange(object, config.getConfigurationEntry(LOG_SAVE_OWNER_CSV), object.getId());
        }catch (Exception e) {
            throw new Exception("Error at saving Mongo DB record");
        }    }

    @Override
    public void saveHistoryRecord(Pet pet, Service serv) throws Exception {
        List<History> list = getAllRecords(config.getConfigurationEntry(HISTORY_HEADER).split(SPLIT_REGEX), HISTORY_CLASS, config.getConfigurationEntry(PATH_TO_HISTORY) + pet.getId() + config.getConfigurationEntry(TYPE_CSV));
        History historyObj = new History();
        try {
            historyObj.setPetId(pet.getId());
            historyObj.setPetName(pet.getName());
            historyObj.setOwnerId(pet.getOwnerId());
            historyObj.setServiceName(serv.getNameOfService());
            historyObj.setServId(serv.getId());
            historyObj.setPrice(serv.getCost());
            list.add(historyObj);
            initRecord(list, config.getConfigurationEntry(HISTORY_HEADER).split(SPLIT_REGEX), HISTORY_CLASS, config.getConfigurationEntry(PATH_TO_HISTORY) + pet.getId() + config.getConfigurationEntry(TYPE_CSV));
        } catch (Exception e) {
            log.error("Data error...");
        }
        try {
            logToHistory.logObjectChange(historyObj, config.getConfigurationEntry(LOG_SAVE_HISTORY_CSV), historyObj.getPetId());
        }catch (Exception e) {
            throw new Exception("Error at saving Mongo DB record");
        }    }

    @Override
    public void saveFeedRecord(Feed object) throws Exception {
        List<Feed> list = getAllRecords(config.getConfigurationEntry(FEED_HEADER).split(SPLIT_REGEX), FEED_CLASS, config.getConfigurationEntry(FEED_CSV));
        object.setId(createId());
        if (!list.stream().anyMatch(s -> s.getFeedName().equals(object.getFeedName()))) {
            list.add(object);
            log.debug(list);
            initRecord(list, config.getConfigurationEntry(FEED_HEADER).split(SPLIT_REGEX), FEED_CLASS, config.getConfigurationEntry(FEED_CSV));
        } else {
            throw new Exception("Feed with those parameters has been created previously");
        }
        try {
            logToHistory.logObjectChange(object, config.getConfigurationEntry(LOG_SAVE_FEED_CSV), object.getId());
        }catch (Exception e) {
            throw new Exception("Error at saving Mongo DB record");
        }    }

    @Override
    public void saveDrugRecord(Drug object) throws Exception {
        List<Drug> list = getAllRecords(config.getConfigurationEntry(DRUG_HEADER).split(SPLIT_REGEX), DRUG_CLASS, config.getConfigurationEntry(DRUG_CSV));
        object.setId(createId());
        if (list.stream().noneMatch(s -> s.getDrugName().equals(object.getDrugName()))) {
            list.add(object);
            log.debug(list);
            initRecord(list, config.getConfigurationEntry(DRUG_HEADER).split(SPLIT_REGEX), DRUG_CLASS, config.getConfigurationEntry(DRUG_CSV));
        } else {
            throw new Exception("Drug with those parameters has been created previously");
        }
        try {
            logToHistory.logObjectChange(object, config.getConfigurationEntry(LOG_SAVE_DRUG_CSV), object.getId());
        }catch (Exception e) {
            throw new Exception("Error at saving Mongo DB record");
        }    }

    @Override
    public void saveDiseaseRecord(Disease object) throws Exception {
        List<Disease> list = getAllRecords(config.getConfigurationEntry(DISEASE_HEADER).split(SPLIT_REGEX), DISEASE_CLASS, config.getConfigurationEntry(DISEASE_CSV));
        object.setId(createId());
        if (!list.stream().anyMatch(s -> s.getNameOfDisease().equals(object.getNameOfDisease()))) {
            list.add(object);
            log.debug(list);
            initRecord(list, config.getConfigurationEntry(DISEASE_HEADER).split(SPLIT_REGEX), DISEASE_CLASS, config.getConfigurationEntry(DISEASE_CSV));
        } else {
            throw new Exception("Disease with those parameters has been created previously");
        }
        try {
            logToHistory.logObjectChange(object, config.getConfigurationEntry(LOG_SAVE_DISEASE_CSV), object.getId());
        }catch (Exception e) {
            throw new Exception("Error at saving Mongo DB record");
        }    }

    @Override
    public void saveEnvironmentVariantRecord(EnvironmentVariant object) throws Exception {
        List<EnvironmentVariant> list = getAllRecords(config.getConfigurationEntry(ENVVAR_HEADER).split(SPLIT_REGEX), ENVVAR_CLASS, config.getConfigurationEntry(ENVVAR_CSV));
        object.setId(createId());
        if (!list.stream().anyMatch(s -> ((s.getHouseName().equals(object.getHouseName())) && (s.getAddition().equals(object.getAddition())) && (s.getEnvironmentFeatures().equals(object.getEnvironmentFeatures()))))) {
            list.add(object);
            log.debug(list);
            initRecord(list, config.getConfigurationEntry(ENVVAR_HEADER).split(SPLIT_REGEX), ENVVAR_CLASS, config.getConfigurationEntry(ENVVAR_CSV));
        } else {
            throw new Exception("Environment Variant with those parameters has been created previously");
        }
        try {
            logToHistory.logObjectChange(object, config.getConfigurationEntry(LOG_SAVE_ENVIRONMENT_VARIANT_CSV), object.getId());
        }catch (Exception e) {
            throw new Exception("Error at saving Mongo DB record");
        }    }


    @Override
    public void findForDelPetByOwner(String id) throws Exception {
        List<Pet> list = getAllRecords(config.getConfigurationEntry(PET_HEADER).split(SPLIT_REGEX), PET_CLASS, config.getConfigurationEntry(PET_CSV));
        for (Pet pet : list) {
            if (pet.getOwnerId().equals(id)) {
                deletePetRecord(pet);
            }
        }
        if (list.removeIf(s -> s.getOwnerId().equals(id))) {
            log.info("Pet has been deleted");
        } else
            throw new Exception("Delete pet error, check ID");
        initRecord(list, config.getConfigurationEntry(PET_HEADER).split(SPLIT_REGEX), PET_CLASS, config.getConfigurationEntry(PET_CSV));
    }

    @Override
    public void deleteOwnerRecord(String id) throws Exception {
        try{
            logToHistory.logObjectChange(getOwnerRecordByID(id), config.getConfigurationEntry(LOG_DELETE_OWNER_CSV), id);
        }catch (Exception e)
        {
            throw new Exception("Error at saving Mongo DB record");
        }
        List<Owner> list = getAllRecords(config.getConfigurationEntry(OWNER_HEADER).split(SPLIT_REGEX), OWNER_CLASS, config.getConfigurationEntry(OWNER_CSV));
        if (list.removeIf(ow -> ow.getId().equals(id))) {
            findForDelPetByOwner(id);
            log.info("Owner has been deleted");
        } else
            throw new Exception("Delete owner error, check ID");
        initRecord(list, config.getConfigurationEntry(OWNER_HEADER).split(SPLIT_REGEX), OWNER_CLASS, config.getConfigurationEntry(OWNER_CSV));
    }


    @Override
    public void deletePetRecord(Pet pet) throws Exception {
        try {
            logToHistory.logObjectChange(getPetRecordByID(pet.getId()), config.getConfigurationEntry(LOG_DELETE_PET_CSV), pet.getId());
        }catch (Exception e){
            throw new Exception("Error at saving Mongo DB record");
        }
        Class cl;
        String[] header;
        String path;
        switch (pet.getType()) {
            case CAT:
                cl = CAT_CLASS;
                header = config.getConfigurationEntry(CAT_HEADER).split(SPLIT_REGEX);
                path = CAT_CSV;
                break;
            case DOG:
                cl = DOG_CLASS;
                header = config.getConfigurationEntry(DOG_HEADER).split(SPLIT_REGEX);
                path = DOG_CSV;
                break;
            case BIRD:
                cl = Bird.class;
                header = config.getConfigurationEntry(BIRD_HEADER).split(SPLIT_REGEX);
                path = BIRD_CSV;
                break;
            case FISH:
                cl = FISH_CLASS;
                header = config.getConfigurationEntry(FISH_HEADER).split(SPLIT_REGEX);
                path = FISH_CSV;
                break;
            default:
                throw new Exception("undefined type of pet");
        }
        List<Pet> list = getAllRecords(header, cl, config.getConfigurationEntry(path));
        if (list.removeIf(s -> s.getId().equals(pet.getId()))) {
            log.info("Pet has been deleted");
        } else
            throw new Exception("Delete pet error, check ID");
        initRecord(list, header, cl, config.getConfigurationEntry(path));
    }

    @Override
    public void deleteOnePetRecord(String id) throws Exception {
        List<Pet> list = getAllRecords(config.getConfigurationEntry(PET_HEADER).split(SPLIT_REGEX), PET_CLASS, config.getConfigurationEntry(PET_CSV));
        for (Pet pet : list) {
            if (pet.getId().equals(id)) {
                deletePetRecord(pet);
            }
        }
        if (list.removeIf(s -> s.getId().equals(id))) {
            log.info("Pet has been deleted");
        } else
            throw new Exception("Delete pet error, check ID");
        initRecord(list, config.getConfigurationEntry(PET_HEADER).split(SPLIT_REGEX), PET_CLASS, config.getConfigurationEntry(PET_CSV));
    }

    @Override
    public void deleteFeedRecord(String id) throws Exception {
        try {
            logToHistory.logObjectChange(getFeedRecordByID(id), config.getConfigurationEntry(LOG_DELETE_FEED_CSV), id);
        }catch (Exception e){
            throw new Exception("Error at saving Mongo DB record");
        }
        List<Feed> list = getAllRecords(config.getConfigurationEntry(FEED_HEADER).split(SPLIT_REGEX), FEED_CLASS, config.getConfigurationEntry(FEED_CSV));
        if (list.removeIf(s -> s.getId().equals(id))) {
            log.info("Feed has been deleted");
        } else
            throw new Exception("Delete feed error, check ID");
        initRecord(list, config.getConfigurationEntry(FEED_HEADER).split(SPLIT_REGEX), FEED_CLASS, config.getConfigurationEntry(FEED_CSV));
    }

    @Override
    public void deleteDrugRecord(String id) throws Exception {
        try {
            logToHistory.logObjectChange(getDrugRecordByID(id), config.getConfigurationEntry(LOG_DELETE_DRUG_CSV), id);
        }catch (Exception e){
            throw new Exception("Error at saving Mongo DB record");
        }
        List<Drug> list = getAllRecords(config.getConfigurationEntry(DRUG_HEADER).split(SPLIT_REGEX), DRUG_CLASS, config.getConfigurationEntry(DRUG_CSV));
        if (list.removeIf(s -> s.getId().equals(id))) {
            log.debug("Drug has been deleted");
        } else
            throw new Exception("Delete drug error, check ID");
        initRecord(list, config.getConfigurationEntry(DRUG_HEADER).split(SPLIT_REGEX), DRUG_CLASS, config.getConfigurationEntry(DRUG_CSV));
    }

    @Override
    public void deleteDiseaseRecord(String id) throws Exception {
        try {
            logToHistory.logObjectChange(getDiseaseRecordByID(id), config.getConfigurationEntry(LOG_DELETE_DISEASE_CSV), id);
        }catch (Exception e){
            throw new Exception("Error at saving Mongo DB record");
        }
        List<Disease> list = getAllRecords(config.getConfigurationEntry(DISEASE_HEADER).split(SPLIT_REGEX), DISEASE_CLASS, config.getConfigurationEntry(DISEASE_CSV));
        if (list.removeIf(s -> s.getId().equals(id))) {
            log.debug("Disease has been deleted");
        } else
            throw new Exception("Delete disease error, check ID");
        initRecord(list, config.getConfigurationEntry(DISEASE_HEADER).split(SPLIT_REGEX), DISEASE_CLASS, config.getConfigurationEntry(DISEASE_CSV));
    }

    @Override
    public void deleteEnvironmentVariantRecord(String id) throws Exception {
        try {
            logToHistory.logObjectChange(getEnvironmentVariantRecordByID(id), config.getConfigurationEntry(LOG_DELETE_ENVIRONMENT_VARIANT_XML), id);
        }catch (Exception e){
            throw new Exception("Error at saving Mongo DB record");
        }
        List<EnvironmentVariant> list = getAllRecords(config.getConfigurationEntry(ENVVAR_HEADER).split(SPLIT_REGEX), ENVVAR_CLASS, config.getConfigurationEntry(ENVVAR_CSV));
        if (list.removeIf(s -> s.getId().equals(id))) {
            log.debug("Environment variant has been deleted");
        } else
            throw new Exception("Delete environment variant error, check ID");
        initRecord(list, config.getConfigurationEntry(ENVVAR_HEADER).split(SPLIT_REGEX), ENVVAR_CLASS, config.getConfigurationEntry(ENVVAR_CSV));
    }


    public Pet findForGetPetRecordByOwnerId(String id) throws Exception {
        List<Pet> list = getAllRecords(config.getConfigurationEntry(PET_HEADER).split(SPLIT_REGEX), PET_CLASS, config.getConfigurationEntry(PET_CSV));
        for (Pet pet : list) {
            if (pet.getOwnerId().equals(id)) {
                return getPet(pet);
            }
        }
        throw new Exception("Error at get owner's list of pets");
    }


    public Pet getPet(Pet pet) throws Exception {
        String path;
        Class cl;
        String[] header;
        switch (pet.getType()) {
            case CAT:
                path = CAT_CSV;
                cl = CAT_CLASS;
                header = config.getConfigurationEntry(CAT_HEADER).split(SPLIT_REGEX);
                break;
            case DOG:
                path = DOG_CSV;
                cl = DOG_CLASS;
                header = config.getConfigurationEntry(DOG_HEADER).split(SPLIT_REGEX);
                break;
            case BIRD:
                path = BIRD_CSV;
                cl = BIRD_CLASS;
                header = config.getConfigurationEntry(BIRD_HEADER).split(SPLIT_REGEX);
                break;
            case FISH:
                path = FISH_CSV;
                cl = FISH_CLASS;
                header = config.getConfigurationEntry(FISH_HEADER).split(SPLIT_REGEX);
                break;
            default:
                throw new Exception("undefined type of pet");
        }
        List<Pet> list = getAllRecords(header, cl, config.getConfigurationEntry(path));
        for (Pet p : list) {
            if (p.getId().equals(pet.getId()))
                return p;
        }
        throw new Exception("Error in method getPet");
    }

    @Override
    public Owner getOwnerRecordByID(String id) throws Exception {
        List<Owner> list = getAllRecords(config.getConfigurationEntry(OWNER_HEADER).split(SPLIT_REGEX), OWNER_CLASS, config.getConfigurationEntry(OWNER_CSV));
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
        List<Pet> list = getAllRecords(config.getConfigurationEntry(PET_HEADER).split(SPLIT_REGEX), PET_CLASS, config.getConfigurationEntry(PET_CSV));
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
        List<History> list = getAllRecords(config.getConfigurationEntry(HISTORY_HEADER).split(SPLIT_REGEX), HISTORY_CLASS, config.getConfigurationEntry(PATH_TO_HISTORY) + id + config.getConfigurationEntry(TYPE_CSV));
        if (list.isEmpty()) {
            throw new Exception("No history records for this pet");
        }
        return list;
    }

    @Override
    public Pet getPetRecordByID(String id) throws Exception {
        List<Pet> list = getAllRecords(config.getConfigurationEntry(PET_HEADER).split(SPLIT_REGEX), PET_CLASS, config.getConfigurationEntry(PET_CSV));
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
        List<Feed> list = getAllRecords(config.getConfigurationEntry(FEED_HEADER).split(SPLIT_REGEX), FEED_CLASS, config.getConfigurationEntry(FEED_CSV));
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
        List<Drug> list = getAllRecords(config.getConfigurationEntry(DRUG_HEADER).split(SPLIT_REGEX), DRUG_CLASS, config.getConfigurationEntry(DRUG_CSV));

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
        List<Disease> list = getAllRecords(config.getConfigurationEntry(DISEASE_HEADER).split(SPLIT_REGEX), DISEASE_CLASS, config.getConfigurationEntry(DISEASE_CSV));

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
        List<EnvironmentVariant> list = getAllRecords(config.getConfigurationEntry(ENVVAR_HEADER).split(SPLIT_REGEX), ENVVAR_CLASS, config.getConfigurationEntry(ENVVAR_CSV));

        for (EnvironmentVariant s : list) {
            log.debug("get record with id " + id);
            if (s.getId().equals(id)) {
                return s;
            }
        }
        throw new Exception("Record not found");
    }

    @Override
    public List<Disease> getAllDiseases() throws Exception {
        return getAllRecords(config.getConfigurationEntry(DISEASE_HEADER).split(SPLIT_REGEX), DISEASE_CLASS, config.getConfigurationEntry(DISEASE_CSV));
    }

    @Override
    public List<Drug> getAllDrugs() throws Exception {
        return getAllRecords(config.getConfigurationEntry(DRUG_HEADER).split(SPLIT_REGEX), DRUG_CLASS, config.getConfigurationEntry(DRUG_CSV));
    }

    @Override
    public List<Feed> getAllFeeds() throws Exception {
        return getAllRecords(config.getConfigurationEntry(FEED_HEADER).split(SPLIT_REGEX), FEED_CLASS, config.getConfigurationEntry(FEED_CSV));
    }

    @Override
    public List<EnvironmentVariant> getAllEnvironmentVariants() throws Exception {
        return getAllRecords(config.getConfigurationEntry(ENVVAR_HEADER).split(SPLIT_REGEX), ENVVAR_CLASS, config.getConfigurationEntry(ENVVAR_CSV));
    }


    /**
     * Determination of the necessary medicine for the treatment of the disease with calculating necessary packs of drug
     * @param object pet object
     * @param dataProvider used dataProvider
     * @param diseases list of diseases
     * @param drugs list of drugs
     * @throws Exception
     */
    @Override
    public void animalTreatment(Pet object, IDataProvider dataProvider, List<Disease> diseases, List<Drug> drugs) throws Exception {
        Disease dis = new Disease();
        for (Disease diseaseObj : diseases) {
            if (diseaseObj.getNameOfDisease().equals(object.getNameOfDisease())) {
                dis = diseaseObj;
            }
        }
        if (dis.getId() == null)
            throw new Exception("Disease record with this id not found");
        Drug dr = new Drug();
        for (Drug drugObj : drugs) {
            if (drugObj.getDrugName().equals(dis.getForTreatment())) {
                dr = drugObj;
            }
        }
        if (dr.getId() == null)
            throw new Exception("Drug record with this id not found");
        Treatment treat = new Treatment();
        treat.setId(createId());
        treat.setDate(new Date());
        treat.setNameOfService(config.getConfigurationEntry(TREATMENT));
        treat.setTreatmentTime(dis.getTreatmentTime());
        treat.setDrugName(dr.getDrugName());
        treat.setDiseaseName(dis.getNameOfDisease());
        treat.setPriceForPack(dr.getPriceForPack());
        treat.setPiecesInPack(dr.getPiecesInPack());
        treat.setIntesityPerDay(dr.getIntesityPerDay());
        double totalPacks = Math.ceil((double) dr.getIntesityPerDay() * (double) dis.getTreatmentTime() / (double) dr.getPiecesInPack());
        treat.setCost(totalPacks * dr.getPriceForPack());
        createRecipe(treat, totalPacks);
        dataProvider.saveHistoryRecord(object, treat);
        log.info("Service(treatment) record has been saved to history");
    }


    /**
     * Create recipe for treatment disease
     * @param obj treatment service object
     * @param packs quantity of total drug packs necessary for treatment
     * @throws IOException
     */
    @Override
    public void createRecipe(Treatment obj, double packs) throws IOException {
        log.info("To treat " + obj.getDiseaseName() + ", you must buy " + packs + " packs of " + obj.getDrugName() + ". And you should give to your pet " + obj.getIntesityPerDay() + " tablets per day for " + obj.getTreatmentTime() + " days. It will be cost " + obj.getCost());
    }


    /**
     * Calculate total expenses from history records
     * @param pet pet object
     * @param dataProvider used dataProvider
     * @throws Exception
     */
    @Override
    public void calculateExpensesAnimal(Pet pet, IDataProvider dataProvider) throws Exception {
        double totalCost = Double.parseDouble(config.getConfigurationEntry(TOTAL));
        if (pet.getNameOfDisease().equals(config.getConfigurationEntry(NO_DISEASE))) {
            totalCost += expensesFood(pet.getId(), dataProvider);
            totalCost += expensesEnvironment(pet.getId(), dataProvider);
        } else {
            totalCost += expensesMedicine(pet.getId(), dataProvider);
            totalCost += expensesFood(pet.getId(), dataProvider);
            totalCost += expensesEnvironment(pet.getId(), dataProvider);
        }
        log.info("Total cost of services for pet: " + totalCost);
    }


    /**
     * Calculate medicine expenses from history records
     * @param id pet id
     * @param dataProvider used dataProvider
     * @return cost of medical services from history
     * @throws Exception
     */
    @Override
    public double expensesMedicine(String id, IDataProvider dataProvider) throws Exception {
        List<History> list = dataProvider.getHistoryRecords(id);
        double total = Double.parseDouble(config.getConfigurationEntry(TOTAL));
        try {
            for (History history : list) {
                if (history.getServiceName().equals(config.getConfigurationEntry(TREATMENT))) {
                    total += history.getPrice();
                }
            }
        }catch (Exception e){
            throw new Exception("calculate expenses medicine error");
        }
        log.info("Medical expenses for pet: " + total);
        return total;
    }


    /**
     * Calculate food expenses from history records
     * @param id pet id
     * @param dataProvider used dataProvider
     * @return cost of food services from history
     * @throws Exception
     */
    @Override
    public double expensesFood(String id, IDataProvider dataProvider) throws Exception {
        List<History> list = dataProvider.getHistoryRecords(id);
        double total = Double.parseDouble(config.getConfigurationEntry(TOTAL));
        try {
            for (History history : list) {
                if (history.getServiceName().equals(config.getConfigurationEntry(DIET))) {
                    total += history.getPrice();
                }
            }
        }catch (Exception e){
            throw new Exception("calculate expenses food error");
        }
        log.info("Food expenses for pet: " + total);
        return total;
    }


    /**
     * Calculate environment expenses from history records
     * @param id pet id
     * @param dataProvider used dataProvider
     * @return cost of environment services from history
     * @throws Exception
     */
    @Override
    public double expensesEnvironment(String id, IDataProvider dataProvider) throws Exception {
        List<History> list = dataProvider.getHistoryRecords(id);
        double total = Double.parseDouble(config.getConfigurationEntry(TOTAL));
        try {
            for (History history : list) {
                if (history.getServiceName().equals(config.getConfigurationEntry(ENVIRONMENT))) {
                    total += history.getPrice();
                }
            }
        }catch (Exception e){
            throw new Exception("calculate expenses environment error");
        }
        log.info("Environment expenses for pet: " + total);
        return total;
    }


    /**
     * Selection suitable feed for pet
     * @param pet pet object
     * @param list list of feeds
     * @param flag bool value for choosing feed and calculate his cost
     * @param pieces quantity of feed packs
     * @param dataProvider used dataProvider
     * @throws Exception
     */
    @Override
    public void selectionFeed(Pet pet, List<Feed> list, boolean flag, int pieces, IDataProvider dataProvider) throws Exception {
        Feed obj = new Feed();
        for (Feed feed : list) {
            if (feed.getForPetType().equals(pet.getType())) {
                obj = feed;
            }
        }
        if (obj.getId() == null)
            throw new Exception("No feed for your pet");
        log.info("Selection result... Feed name: " + obj.getFeedName() + ", feed id: " + obj.getId() + ", pack weight: " + obj.getWeightOfPack() + ", variant price:" + obj.getPriceForPack());
        Diet diet = new Diet();
        diet.setId(createId());
        diet.setDate(new Date());
        diet.setNameOfService(config.getConfigurationEntry(DIET));
        diet.setFeedType(pet.getFeedType());
        diet.setWeightOfPack(obj.getWeightOfPack());
        diet.setPriceForPack(obj.getPriceForPack());
        if (flag)
            diet.setCost(calculateFeedCost(obj, pieces));
        else
            diet.setCost(Double.parseDouble(config.getConfigurationEntry(DEFAULT_PRICE)));
        dataProvider.saveHistoryRecord(pet, diet);
    }


    /**
     * Calculate cost of suitable feed dor pet
     * @param feed suitable feed
     * @param pieces quantity of feed packs
     * @return total price for some packs of suitable feed
     */
    @Override
    public double calculateFeedCost(Feed feed, int pieces) {
        double price = feed.getPriceForPack() * pieces;
        double weight = feed.getWeightOfPack() * pieces;
        log.info("Price for " + pieces + "feed packs: " + price + ". Total weight: " + weight);
        return price;
    }


    /**
     * Selection suitable environment variant for pet
     * @param pet pet object
     * @param list list of environment variants
     * @param flag bool value for choosing environment variant and calculate his cost
     * @param dataProvider used dataProvider
     * @throws Exception
     */
    @Override
    public void selectionEnvironmentVariant(Pet pet, List<EnvironmentVariant> list, boolean flag, IDataProvider dataProvider) throws Exception {
        EnvironmentVariant obj = new EnvironmentVariant();
        for (EnvironmentVariant var : list) {
            if (var.getForPetType().equals(pet.getType())) {
                obj = var;
            }
        }
        if (obj.getId() == null)
            throw new Exception("No environment for your pet");
        log.info("Selection result... Environment variant name: " + obj.getHouseName() + ", variant id: " + obj.getId() + ", variant addition: " + obj.getAddition() + ", variant features: " + obj.getEnvironmentFeatures() + ", variant price:" + obj.getPrice());
        Environment env = new Environment();
        env.setId(createId());
        env.setDate(new Date());
        env.setAddition(obj.getAddition());
        env.setEnvironmentFeatures(obj.getEnvironmentFeatures());
        env.setInsideHouse(obj.getInsideHouseUsing());
        env.setPrice(obj.getPrice());
        env.setNameOfService(config.getConfigurationEntry(ENVIRONMENT));
        if (flag)
            env.setCost(calculateEnvironmentCost(obj));
        else
            env.setCost(Double.parseDouble(config.getConfigurationEntry(DEFAULT_PRICE)));
        dataProvider.saveHistoryRecord(pet, env);
    }


    /**
     * Calculate environment variant cost
     * @param obj suitable environment variant for pet
     * @return total cost of environment variant
     */
    @Override
    public double calculateEnvironmentCost(EnvironmentVariant obj) {
        log.info("Environment variant with name " + obj.getHouseName() + " will be cost: " + obj.getPrice() + ". Addition: " + obj.getAddition());
        return obj.getPrice();
    }
}
