package ru.sfedu.myApp;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlTransient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.myApp.Entity.*;

import java.io.*;
import java.util.*;

import static ru.sfedu.myApp.Constans.*;

public class DataProviderXML implements IDataProvider {

    @XmlTransient
    private static Logger log = LogManager.getLogger(DataProviderXML.class);

    private JAXBContext context;

    static ConfigUtils config = new ConfigUtils();

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
            log.info("SimpleTest " + e.getMessage());
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
            object.setId(createId());
            list.add(object);
            initRecord(list, config.getConfigurationEntry(OWNER_XML));
        } else {
            throw new Exception("Owner with those parameters has been created previously");
        }
    }

    @Override
    public void savePetRecord(Pet object, String ownerId) throws Exception {
        String path = PET_XML;
        switch (object.getType()) {
            case "cat":
                path = CAT_XML;
                break;
            case "dog":
                path = DOG_XML;
                break;
            case "bird":
                path = BIRD_XML;
                break;
            case "fish":
                path = FISH_XML;
                break;
            default:
                break;
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
    }

    @Override
    public void saveHistoryRecord(Pet pet, Service serv) throws Exception {
        List<History> list = getAllRecords("src/main/resources/HistoryFiles/" + pet.getId() + ".xml");
        History historyObj = new History();
        try {
            historyObj.setPetId(pet.getId());
            historyObj.setPetName(pet.getName());
            historyObj.setOwnerId(pet.getOwnerId());
            historyObj.setServiceName(serv.getNameOfService());
            historyObj.setPrice(serv.getCost());
            historyObj.setDate(serv.getDate());
            list.add(historyObj);
            initRecord(list, "src/main/resources/HistoryFiles/" + pet.getId() + ".xml");
        }catch (Exception e){
            log.error("Data error...");
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
        String path = PET_XML;
        switch (pet.getType()) {
            case "cat":
                path = CAT_XML;
                break;
            case "dog":
                path = DOG_XML;
                break;
            case "bird":
                path = BIRD_XML;
                break;
            case "fish":
                path = FISH_XML;
                break;
            default:
                break;
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
        List<Feed> list = getAllRecords(config.getConfigurationEntry(FEED_XML));
        if (list.removeIf(s -> s.getId().equals(id))) {
            log.info("Feed has been deleted");
        } else
            throw new Exception("Delete feed error, check ID");
        initRecord(list, config.getConfigurationEntry(FEED_XML));
    }

    @Override
    public void deleteDrugRecord(String id) throws Exception {
        List<Drug> list = getAllRecords(config.getConfigurationEntry(DRUG_XML));
        if (list.removeIf(s -> s.getId().equals(id))) {
            log.debug("Drug has been deleted");
        } else
            throw new Exception("Delete drug error, check ID");
        initRecord(list, config.getConfigurationEntry(DRUG_XML));
    }

    @Override
    public void deleteDiseaseRecord(String id) throws Exception {
        List<Disease> list = getAllRecords(config.getConfigurationEntry(DISEASE_XML));
        if (list.removeIf(s -> s.getId().equals(id))) {
            log.debug("Disease has been deleted");
        } else
            throw new Exception("Delete disease error, check ID");
        initRecord(list, config.getConfigurationEntry(DISEASE_XML));
    }

    @Override
    public void deleteEnvironmentVariantRecord(String id) throws Exception {
        List<EnvironmentVariant> list = getAllRecords(config.getConfigurationEntry(ENVVAR_XML));
        if (list.removeIf(s -> s.getId().equals(id))) {
            log.debug("Environment variant has been deleted");
        } else
            throw new Exception("Delete environment variant error, check ID");
        initRecord(list, config.getConfigurationEntry(ENVVAR_XML));
    }


    @Override
    public Pet findForGetPetRecordByOwnerId(String id) throws Exception {
        List<Pet> list = getAllRecords(config.getConfigurationEntry(PET_XML));
        if (list.stream().noneMatch(p -> p.getOwnerId().equals(id))) {
            return null;
        }
        for (Pet pet : list) {
            if (pet.getOwnerId().equals(id)) {
                return getPet(pet);
            }
        }
        throw new Exception("Error at get owner's list of pets");
    }


    @Override
    public Pet getPet(Pet pet) throws Exception {
        String path = PET_XML;
        switch (pet.getType()) {
            case "cat":
                path = CAT_XML;
                break;
            case "dog":
                path = DOG_XML;
                break;
            case "bird":
                path = BIRD_XML;
                break;
            case "fish":
                path = FISH_XML;
                break;
            default:
                break;
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
        List<History> list = getAllRecords("src/main/resources/HistoryFiles/" + id + ".xml");
        if(list.isEmpty()){
            throw new Exception("File is empty");
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
}
