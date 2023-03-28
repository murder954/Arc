package ru.sfedu.petclinic.api;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlTransient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.petclinic.LoggingBeans;
import ru.sfedu.petclinic.model.*;
import ru.sfedu.petclinic.util.ConfigUtils;

import java.io.*;
import java.util.*;

import static ru.sfedu.petclinic.Constans.*;

public class DataProviderXML implements IDataProvider {

    @XmlTransient
    private static Logger log = LogManager.getLogger(DataProviderXML.class);

    private JAXBContext context;

    LoggingBeans logToHistory = new LoggingBeans();

    static ConfigUtils config = new ConfigUtils();

    public DataProviderXML() throws IOException {

    }

    public <T> List<T> getAllRecords(String path) throws IOException {
        File file = new File(path);
        file.createNewFile();
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader inputData = new InputStreamReader(fis);
        try {
            context = JAXBContext.newInstance(XmlWrapper.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            XmlWrapper wrap = (XmlWrapper) unmarshaller.unmarshal(inputData);
            log.debug("records initialization:" + wrap.getList());
            return new ArrayList<T>(wrap.getList());
        } catch (Exception e) {
            log.debug("SimpleTest " + e.getMessage());
        }
        return new ArrayList<>();
    }

    public void initRecord(List list, String path) {
        try {
            File f = new File(path);
            FileOutputStream output = new FileOutputStream(f);
            context = JAXBContext.newInstance(XmlWrapper.class);
            XmlWrapper wrap = new XmlWrapper();
            wrap.setList(list);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(wrap, output);
        } catch (Exception e) {
            log.error("Updating error" + e.getMessage());
        }
    }

    @Override
    public String createId() {
        String id = String.valueOf(UUID.randomUUID());
        return id;
    }

    @Override
    public void saveOwnerRecord(Owner object) throws Exception {
        List<Owner> list = getAllRecords(config.getConfigurationEntry(OWNER_XML));
        if (!list.stream().anyMatch(s -> ((Owner) s).getBankAccount() == object.getBankAccount())) {
            list.add(object);
            initRecord(list, config.getConfigurationEntry(OWNER_XML));
        } else {
            throw new Exception("Owner with those parameters has been created previously");
        }
        try {
            logToHistory.logObjectChange(object, config.getConfigurationEntry(LOG_SAVE_OWNER_XML), object.getId());
        }catch (Exception e) {
            throw new Exception("Error at saving Mongo DB record");
        }
    }

    @Override
    public void savePetRecord(Pet object, String ownerId) throws Exception {
        String path;
        switch (object.getType()) {
            case CAT:
                path = CAT_XML;
                break;
            case DOG:
                path = DOG_XML;
                break;
            case BIRD:
                path = BIRD_XML;
                break;
            case FISH:
                path = FISH_XML;
                break;
            default:
                throw new Exception("undefined type of pet");
        }
        List<Pet> list = getAllRecords(config.getConfigurationEntry(path));
        List<Pet> general = getAllRecords(config.getConfigurationEntry(PET_XML));
        object.setId(createId());
        object.setOwnerId(ownerId);
        if (list.stream().noneMatch(s -> ((((Pet) s).getNameOfDisease().equals(object.getNameOfDisease())) && (((Pet) s).getName().equals(object.getName())) && (((Pet) s).getType().equals(object.getType())) && (((Pet) s).getFeedType().equals(object.getFeedType()))))) {
            list.add(object);
            general.add(object);
            log.debug(list);
            initRecord(list, config.getConfigurationEntry(path));
            initRecord(general, config.getConfigurationEntry(PET_XML));
        } else {
            throw new Exception("Impossible to save pet, owner with this id not found, check owner's id");
        }
        try {
            logToHistory.logObjectChange(object, config.getConfigurationEntry(LOG_SAVE_PET_XML), object.getId());
        }catch (Exception e) {
            throw new Exception("Error at saving Mongo DB record");
        }

    }

    @Override
    public void saveHistoryRecord(Pet pet, Service serv) throws Exception {
        List<History> list = getAllRecords(config.getConfigurationEntry(PATH_TO_HISTORY) + pet.getId() + config.getConfigurationEntry(TYPE_XML));
        History historyObj = new History();
        try {
            historyObj.setPetId(pet.getId());
            historyObj.setPetName(pet.getName());
            historyObj.setOwnerId(pet.getOwnerId());
            historyObj.setServiceName(serv.getNameOfService());
            historyObj.setServId(serv.getId());
            historyObj.setPrice(serv.getCost());
            historyObj.setDate(serv.getDate());
            list.add(historyObj);
            initRecord(list, config.getConfigurationEntry(PATH_TO_HISTORY) + pet.getId() + config.getConfigurationEntry(TYPE_XML));
        }catch (Exception e){
            log.error("Data error...");
        }
        try {
            logToHistory.logObjectChange(historyObj, config.getConfigurationEntry(LOG_SAVE_HISTORY_XML), historyObj.getPetId());
        }catch (Exception e) {
            throw new Exception("Error at saving Mongo DB record");
        }
    }

    @Override
    public void saveFeedRecord(Feed object) throws Exception {
        List<Feed> list = getAllRecords(config.getConfigurationEntry(FEED_XML));
        object.setId(createId());
        if (!list.stream().anyMatch(s -> s.getFeedName().equals(object.getFeedName()))) {
            list.add(object);
            log.debug(list);
            initRecord(list, config.getConfigurationEntry(FEED_XML));
        } else {
            throw new Exception("Feed with those parameters has been created previously");
        }
        try {
            logToHistory.logObjectChange(object, config.getConfigurationEntry(LOG_SAVE_FEED_XML), object.getId());
        }catch (Exception e) {
            throw new Exception("Error at saving Mongo DB record");
        }
    }

    @Override
    public void saveDrugRecord(Drug object) throws Exception {
        List<Drug> list = getAllRecords(config.getConfigurationEntry(DRUG_XML));
        object.setId(createId());
        if (list.stream().noneMatch(s -> s.getDrugName().equals(object.getDrugName()))) {
            list.add(object);
            log.debug(list);
            initRecord(list, config.getConfigurationEntry(DRUG_XML));
        } else {
            throw new Exception("Drug with those parameters has been created previously");
        }
        try {
            logToHistory.logObjectChange(object, config.getConfigurationEntry(LOG_SAVE_DRUG_XML), object.getId());
        }catch (Exception e) {
            throw new Exception("Error at saving Mongo DB record");
        }
    }

    @Override
    public void saveDiseaseRecord(Disease object) throws Exception {
        List<Disease> list = getAllRecords(config.getConfigurationEntry(DISEASE_XML));
        object.setId(createId());
        if (!list.stream().anyMatch(s -> s.getNameOfDisease().equals(object.getNameOfDisease()))) {
            list.add(object);
            log.debug(list);
            initRecord(list, config.getConfigurationEntry(DISEASE_XML));
        } else {
            throw new Exception("Disease with those parameters has been created previously");
        }
        try {
            logToHistory.logObjectChange(object, config.getConfigurationEntry(LOG_SAVE_DISEASE_XML), object.getId());
        }catch (Exception e) {
            throw new Exception("Error at saving Mongo DB record");
        }
    }

    @Override
    public void saveEnvironmentVariantRecord(EnvironmentVariant object) throws Exception {
        List<EnvironmentVariant> list = getAllRecords(config.getConfigurationEntry(ENVVAR_XML));
        object.setId(createId());
        if (!list.stream().anyMatch(s -> ((s.getHouseName().equals(object.getHouseName())) && (s.getAddition().equals(object.getAddition())) && (s.getEnvironmentFeatures().equals(object.getEnvironmentFeatures()))))) {
            list.add(object);
            log.debug(list);
            initRecord(list, config.getConfigurationEntry(ENVVAR_XML));
        } else {
            throw new Exception("Environment Variant with those parameters has been created previously");
        }
        try {
            logToHistory.logObjectChange(object, config.getConfigurationEntry(LOG_SAVE_ENVIRONMENT_VARIANT_XML), object.getId());
        }catch (Exception e) {
            throw new Exception("Error at saving Mongo DB record");
        }
    }


    @Override
    public void findForDelPetByOwner(String id) throws Exception {
        List<Pet> list = getAllRecords(config.getConfigurationEntry(PET_XML));
        if (list.stream().noneMatch(p -> p.getOwnerId().equals(id))) {
            throw new Exception("WARNING: Owner doesn't have any pets. Only owner will be delete");
        }
        for (Pet pet : list) {
            if (pet.getOwnerId().equals(id)) {
                deletePetRecord(pet);
            }
        }
        if (list.removeIf(s -> s.getOwnerId().equals(id))) {
            log.info("Pet has been deleted");
        } else
            throw new Exception("Delete pet error, check ID");
        initRecord(list, config.getConfigurationEntry(PET_XML));
    }

    @Override
    public void deleteOwnerRecord(String id) throws Exception {
        try{
            logToHistory.logObjectChange(getOwnerRecordByID(id), config.getConfigurationEntry(LOG_DELETE_OWNER_XML), id);
        }catch (Exception e)
        {
            throw new Exception("Error at saving Mongo DB record");
        }
        List<Owner> list = getAllRecords(config.getConfigurationEntry(OWNER_XML));
        if (list.removeIf(ow -> ow.getId().equals(id))) {
            try {
                findForDelPetByOwner(id);
            } catch (Exception e) {
                log.warn("WARNING: Owner doesn't have any pets. Only owner will be delete");
            }
            log.info("Owner has been deleted");
        } else
            throw new Exception("Delete owner error, check ID");
        initRecord(list, config.getConfigurationEntry(OWNER_XML));
    }


    @Override
    public void deletePetRecord(Pet pet) throws Exception {
        try {
            logToHistory.logObjectChange(getPetRecordByID(pet.getId()), config.getConfigurationEntry(LOG_DELETE_PET_XML), pet.getId());
        }catch (Exception e){
            throw new Exception("Error at saving Mongo DB record");
        }
        String path;
        switch (pet.getType()) {
            case CAT:
                path = CAT_XML;
                break;
            case DOG:
                path = DOG_XML;
                break;
            case BIRD:
                path = BIRD_XML;
                break;
            case FISH:
                path = FISH_XML;
                break;
            default:
                throw new Exception("undefined type of pet");
        }
        List<Pet> list = getAllRecords(config.getConfigurationEntry(path));
        if (list.removeIf(s -> s.getId().equals(pet.getId()))) {
            log.info("Pet has been deleted");
        } else
            throw new Exception("Delete pet error, check ID");
        initRecord(list, config.getConfigurationEntry(path));
    }

    @Override
    public void deleteOnePetRecord(String id) throws Exception {
        List<Pet> list = getAllRecords(config.getConfigurationEntry(PET_XML));
        for (Pet pet : list) {
            if (pet.getId().equals(id)) {
                deletePetRecord(pet);
            }
        }
        if (list.removeIf(s -> s.getId().equals(id))) {
            log.info("Pet has been deleted");
        } else
            throw new Exception("Delete pet error, check ID");
        initRecord(list, config.getConfigurationEntry(PET_XML));
    }

    @Override
    public void deleteFeedRecord(String id) throws Exception {
        try {
            logToHistory.logObjectChange(getFeedRecordByID(id), config.getConfigurationEntry(LOG_DELETE_FEED_XML), id);
        }catch (Exception e){
            throw new Exception("Error at saving Mongo DB record");
        }
        List<Feed> list = getAllRecords(config.getConfigurationEntry(FEED_XML));
        if (list.removeIf(s -> s.getId().equals(id))) {
            log.info("Feed has been deleted");
        } else
            throw new Exception("Delete feed error, check ID");
        initRecord(list, config.getConfigurationEntry(FEED_XML));
    }

    @Override
    public void deleteDrugRecord(String id) throws Exception {
        try {
            logToHistory.logObjectChange(getDrugRecordByID(id), config.getConfigurationEntry(LOG_DELETE_DRUG_XML), id);
        }catch (Exception e){
            throw new Exception("Error at saving Mongo DB record");
        }
        List<Drug> list = getAllRecords(config.getConfigurationEntry(DRUG_XML));
        if (list.removeIf(s -> s.getId().equals(id))) {
            log.debug("Drug has been deleted");
        } else
            throw new Exception("Delete drug error, check ID");
        initRecord(list, config.getConfigurationEntry(DRUG_XML));
    }

    @Override
    public void deleteDiseaseRecord(String id) throws Exception {
        try {
            logToHistory.logObjectChange(getDiseaseRecordByID(id), config.getConfigurationEntry(LOG_DELETE_DISEASE_XML), id);
        }catch (Exception e){
            throw new Exception("Error at saving Mongo DB record");
        }
        List<Disease> list = getAllRecords(config.getConfigurationEntry(DISEASE_XML));
        if (list.removeIf(s -> s.getId().equals(id))) {
            log.debug("Disease has been deleted");
        } else
            throw new Exception("Delete disease error, check ID");
        initRecord(list, config.getConfigurationEntry(DISEASE_XML));
    }

    @Override
    public void deleteEnvironmentVariantRecord(String id) throws Exception {
        try {
            logToHistory.logObjectChange(getEnvironmentVariantRecordByID(id), config.getConfigurationEntry(LOG_DELETE_ENVIRONMENT_VARIANT_XML), id);
        }catch (Exception e){
            throw new Exception("Error at saving Mongo DB record");
        }
        List<EnvironmentVariant> list = getAllRecords(config.getConfigurationEntry(ENVVAR_XML));
        if (list.removeIf(s -> s.getId().equals(id))) {
            log.debug("Environment variant has been deleted");
        } else
            throw new Exception("Delete environment variant error, check ID");
        initRecord(list, config.getConfigurationEntry(ENVVAR_XML));
    }



    public Pet findForGetPetRecordByOwnerId(String id) throws Exception {
        List<Pet> list = getAllRecords(config.getConfigurationEntry(PET_XML));
        for (Pet pet : list) {
            if (pet.getOwnerId().equals(id)) {
                return getPet(pet);
            }
        }
        throw new Exception("Error at get owner's list of pets");
    }



    public Pet getPet(Pet pet) throws Exception {
        String path;
        switch (pet.getType()) {
            case CAT:
                path = CAT_XML;
                break;
            case DOG:
                path = DOG_XML;
                break;
            case BIRD:
                path = BIRD_XML;
                break;
            case FISH:
                path = FISH_XML;
                break;
            default:
                throw new Exception("undefined type of pet");
        }
        List<Pet> list = getAllRecords(config.getConfigurationEntry(path));
        for (Pet p : list) {
            if (p.getId().equals(pet.getId()))
                return p;
        }
        throw new Exception("Error in method getPet");
    }

    @Override
    public Owner getOwnerRecordByID(String id) throws Exception {
        List<Owner> list = getAllRecords(config.getConfigurationEntry(OWNER_XML));
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
        List<Pet> list = getAllRecords(config.getConfigurationEntry(PET_XML));
        for (Pet pet : list) {
            log.debug("get record with id " + id);
            if (pet.getOwnerId().equals(id)) {
                return findForGetPetRecordByOwnerId(id);
            }
        }
        throw new Exception("Record not found");
    }

    @Override
    public Pet getPetRecordByID(String id) throws Exception {
        List<Pet> list = getAllRecords(config.getConfigurationEntry(PET_XML));
        for (Pet pet : list) {
            log.debug("get record with id " + id);
            if (pet.getId().equals(id)) {
                return getPet(pet);
            }
        }
        throw new Exception("Record not found");
    }

    @Override
    public List<History> getHistoryRecords(String id) throws Exception {
        List<History> list = getAllRecords(config.getConfigurationEntry(PATH_TO_HISTORY) + id + config.getConfigurationEntry(TYPE_XML));
        if(list.isEmpty()){
            throw new Exception("No history records for this pet");
        }
        return list;
    }

    @Override
    public Feed getFeedRecordByID(String id) throws Exception {
        List<Feed> list = getAllRecords(config.getConfigurationEntry(FEED_XML));
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
        List<Drug> list = getAllRecords(config.getConfigurationEntry(DRUG_XML));

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
        List<Disease> list = getAllRecords(config.getConfigurationEntry(DISEASE_XML));

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
        List<EnvironmentVariant> list = getAllRecords(config.getConfigurationEntry(ENVVAR_XML));
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
        return getAllRecords(config.getConfigurationEntry(DISEASE_XML));
    }

    @Override
    public List<Drug> getAllDrugs() throws Exception {
        return getAllRecords(config.getConfigurationEntry(DRUG_XML));
    }

    @Override
    public List<Feed> getAllFeeds() throws Exception {
        return getAllRecords(config.getConfigurationEntry(FEED_XML));
    }

    @Override
    public List<EnvironmentVariant> getAllEnvironmentVariants() throws Exception {
        return getAllRecords(config.getConfigurationEntry(ENVVAR_XML));
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
