package ru.sfedu.petclinic.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.petclinic.LoggingBeans;
import ru.sfedu.petclinic.model.*;
import ru.sfedu.petclinic.util.ConfigUtils;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.Date;

import static ru.sfedu.petclinic.Constans.*;

public class DataBaseProvider implements IDataProvider {
    private Connection connection;
    LoggingBeans logToHistory = new LoggingBeans();

    ConfigUtils config = new ConfigUtils();
    Logger log = LogManager.getLogger(DataBaseProvider.class);

    public DataBaseProvider() throws Exception {
        try {
            connection = DriverManager.getConnection(config.getConfigurationEntry(PATH_TO_DB), config.getConfigurationEntry(USER_TO_DB), config.getConfigurationEntry(PASS_TO_DB));
            log.info("connect to dataBase");
        } catch (SQLException e) {
            throw new Exception(e + "\nfailed to connect");
        }
    }

    public void closeConnection() throws Exception {
        try {
            connection.close();
            log.info("connection close");
        } catch (SQLException e) {
            log.info("connection already been closed");
            throw new Exception("connection already been closed");
        }
    }

    @Override
    public String createId() {
        String id = String.valueOf(UUID.randomUUID());
        return id;
    }

    @Override
    public void savePetRecord(Pet object, String ownerId) throws Exception {
        object.setOwnerId(ownerId);
        String sql = "INSERT INTO " + config.getConfigurationEntry(LINKTABLE_DB) + " (ownerId, petId, PetType) VALUES(?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, object.getOwnerId());
            statement.setString(2, object.getId());
            statement.setString(3, object.getType().name());
            if (statement.executeUpdate() == 0) {
                log.error("Impossible to save pet");
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception("Pet with this id has been saved previously");
        }
        switch (object.getType()) {
            case CAT:
                saveCat(object);
                break;
            case DOG:
                saveDog(object);
                break;
            case BIRD:
                saveBird(object);
                break;
            case FISH:
                saveFish(object);
                break;
            default:
                throw new Exception("undefined type of pet");
        }
    }

    public void saveCat(Pet obj) throws Exception {
        Cat cat = (Cat) obj;
        String sql = "INSERT INTO " + config.getConfigurationEntry(CAT_DB) + " (name, id, gender, weight, feedType, type, age, nameOfDisease, ownerId, afraidDogs, isCalm) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, cat.getName());
            statement.setString(2, cat.getId());
            statement.setString(3, cat.getGender());
            statement.setDouble(4, cat.getWeight());
            statement.setString(5, cat.getFeedType());
            statement.setString(6, cat.getType().name());
            statement.setInt(7, cat.getAge());
            statement.setString(8, cat.getNameOfDisease());
            statement.setString(9, cat.getOwnerId());
            statement.setBoolean(10, cat.getAfraidDogs());
            statement.setBoolean(11, cat.getIsCalm());
            log.info("save cat");
            if (statement.executeUpdate() == 0) {
                log.error("Impossible to save cat");
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception("Pet with this id has been saved previously");
        }
        try {
            logToHistory.logObjectChange(cat, config.getConfigurationEntry(LOG_SAVE_PET_DB), cat.getId());
        } catch (Exception e) {
            throw new Exception("Error at saving Mongo DB record");
        }
    }

    public void saveDog(Pet obj) throws Exception {
        Dog dog = (Dog) obj;
        String sql = "INSERT INTO " + config.getConfigurationEntry(DOG_DB) + " (name, id, gender, weight, feedType, type, age, nameOfDisease, ownerId, isAggressive) VALUES(?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, dog.getName());
            statement.setString(2, dog.getId());
            statement.setString(3, dog.getGender());
            statement.setDouble(4, dog.getWeight());
            statement.setString(5, dog.getFeedType());
            statement.setString(6, dog.getType().name());
            statement.setInt(7, dog.getAge());
            statement.setString(8, dog.getNameOfDisease());
            statement.setString(9, dog.getOwnerId());
            statement.setBoolean(10, dog.getIsAgressive());
            log.info("save dog");
            if (statement.executeUpdate() == 0) {
                log.error("Impossible to save dog");
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception("Pet with this id has been saved previously");
        }
        try {
            logToHistory.logObjectChange(dog, config.getConfigurationEntry(LOG_SAVE_PET_DB), dog.getId());
        } catch (Exception e) {
            throw new Exception("Error at saving Mongo DB record");
        }
    }

    public void saveBird(Pet obj) throws Exception {
        Bird bird = (Bird) obj;
        String sql = "INSERT INTO " + config.getConfigurationEntry(BIRD_DB) + " (name, id, gender, weight, feedType, type, age, nameOfDisease, ownerId, isWaterFowl) VALUES(?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, bird.getName());
            statement.setString(2, bird.getId());
            statement.setString(3, bird.getGender());
            statement.setDouble(4, bird.getWeight());
            statement.setString(5, bird.getFeedType());
            statement.setString(6, bird.getType().name());
            statement.setInt(7, bird.getAge());
            statement.setString(8, bird.getNameOfDisease());
            statement.setString(9, bird.getOwnerId());
            statement.setBoolean(10, bird.getIsWaterFlow());
            log.info("save bird");
            if (statement.executeUpdate() == 0) {
                log.error("Impossible to save bird");
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception("Pet with this id has been saved previously");
        }
        try {
            logToHistory.logObjectChange(bird, config.getConfigurationEntry(LOG_SAVE_PET_DB), bird.getId());
        } catch (Exception e) {
            throw new Exception("Error at saving Mongo DB record");
        }
    }

    public void saveFish(Pet obj) throws Exception {
        Fish fish = (Fish) obj;
        String sql = "INSERT INTO " + config.getConfigurationEntry(FISH_DB) + " (name, id, gender, weight, feedType, type, age, nameOfDisease, ownerId, waterType) VALUES(?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, fish.getName());
            statement.setString(2, fish.getId());
            statement.setString(3, fish.getGender());
            statement.setDouble(4, fish.getWeight());
            statement.setString(5, fish.getFeedType());
            statement.setString(6, fish.getType().name());
            statement.setInt(7, fish.getAge());
            statement.setString(8, fish.getNameOfDisease());
            statement.setString(9, fish.getOwnerId());
            statement.setString(10, fish.getWaterType());
            log.info("save fish");
            if (statement.executeUpdate() == 0) {
                log.error("Impossible to save fish");
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception("Pet with this id has been saved previously");
        }
        try {
            logToHistory.logObjectChange(fish, config.getConfigurationEntry(LOG_SAVE_PET_DB), fish.getId());
        } catch (Exception e) {
            throw new Exception("Error at saving Mongo DB record");
        }
    }

    @Override
    public void saveOwnerRecord(Owner object) throws Exception {
        String sql = "INSERT INTO " + config.getConfigurationEntry(OWNER_DB) + " (id, ownerName, phoneNumber, email, bankAccount) VALUES(?,?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, object.getId());
            statement.setString(2, object.getOwnerName());
            statement.setString(3, object.getPhoneNumber());
            statement.setString(4, object.getEmail());
            statement.setLong(5, object.getBankAccount());
            log.info("save record");
            if (statement.executeUpdate() == 0) {
                log.error("record save error");
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception("record with this id has been saved previously");
        }
        try {
            logToHistory.logObjectChange(object, config.getConfigurationEntry(LOG_SAVE_OWNER_DB), object.getId());
        } catch (Exception e) {
            throw new Exception("Error at saving Mongo DB record");
        }
    }

    @Override
    public void saveHistoryRecord(Pet pet, Service serv) throws Exception {
        History historyObj = new History();
        try {
            historyObj.setServId(serv.getId());
            historyObj.setOwnerId(pet.getOwnerId());
            historyObj.setPetId(pet.getId());
            historyObj.setPrice(serv.getCost());
            historyObj.setServiceName(serv.getNameOfService());
            historyObj.setPetName(pet.getName());
            historyObj.setDate(serv.getDate());
        } catch (Exception e) {
            log.error("Data error...");
        }
        String sql = "INSERT INTO " + config.getConfigurationEntry(HISTORY_DB) + " (petName, petid, ownerId, serviceName, price, date, servId) VALUES(?,?,?,?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, pet.getName());
            statement.setString(2, pet.getId());
            statement.setString(3, pet.getOwnerId());
            statement.setString(4, serv.getNameOfService());
            statement.setDouble(5, serv.getCost());
            Long longForSqlDate = serv.getDate().getTime();
            statement.setDate(6, new java.sql.Date(longForSqlDate));
            statement.setString(7, serv.getId());
            log.info("save history record");
            if (statement.executeUpdate() == 0) {
                log.error("record save error");
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception("record with this id has been saved previously");
        }
        try {
            logToHistory.logObjectChange(historyObj, config.getConfigurationEntry(LOG_SAVE_HISTORY_DB), historyObj.getPetId());
        } catch (Exception e) {
            throw new Exception("Error at saving Mongo DB record");
        }
    }

    @Override
    public void saveFeedRecord(Feed object) throws Exception {
        String sql = "INSERT INTO " + config.getConfigurationEntry(FEED_DB) + " (feedName, id, priceForPack, weightOfPack, forPetType) VALUES(?,?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, object.getFeedName());
            statement.setString(2, object.getId());
            statement.setDouble(3, object.getPriceForPack());
            statement.setDouble(4, object.getWeightOfPack());
            statement.setString(5, object.getForPetType().name());
            log.info("save record");
            if (statement.executeUpdate() == 0) {
                log.error("record save error");
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception("record with this id has been saved previously");
        }
        try {
            logToHistory.logObjectChange(object, config.getConfigurationEntry(LOG_SAVE_FEED_DB), object.getId());
        } catch (Exception e) {
            throw new Exception("Error at saving Mongo DB record");
        }
    }

    @Override
    public void saveDrugRecord(Drug object) throws Exception {
        String sql = "INSERT INTO " + config.getConfigurationEntry(DRUG_DB) + " (drugName, id, priceForPack, piecesInPack, intensityPerDay) VALUES(?,?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, object.getDrugName());
            statement.setString(2, object.getId());
            statement.setDouble(3, object.getPriceForPack());
            statement.setInt(4, object.getPiecesInPack());
            statement.setInt(5, object.getIntesityPerDay());
            log.info("save record");
            if (statement.executeUpdate() == 0) {
                log.error("record save error");
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception("record with this id has been saved previously");
        }
        try {
            logToHistory.logObjectChange(object, config.getConfigurationEntry(LOG_SAVE_DRUG_DB), object.getId());
        } catch (Exception e) {
            throw new Exception("Error at saving Mongo DB record");
        }
    }

    @Override
    public void saveDiseaseRecord(Disease object) throws Exception {
        String sql = "INSERT INTO " + config.getConfigurationEntry(DISEASE_DB) + " (nameOfDisease, id, treatmentTime, forTreatment) VALUES(?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, object.getNameOfDisease());
            statement.setString(2, object.getId());
            statement.setInt(3, object.getTreatmentTime());
            statement.setString(4, object.getForTreatment());
            log.info("save record");
            if (statement.executeUpdate() == 0) {
                log.error("record save error");
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception("record with this id has been saved previously");
        }
        try {
            logToHistory.logObjectChange(object, config.getConfigurationEntry(LOG_SAVE_DISEASE_DB), object.getId());
        } catch (Exception e) {
            throw new Exception("Error at saving Mongo DB record");
        }
    }

    @Override
    public void saveEnvironmentVariantRecord(EnvironmentVariant object) throws Exception {
        String sql = "INSERT INTO " + config.getConfigurationEntry(ENVVAR_DB) + " (houseName, id, addition, insideHouseUsing, environmentFeatures, price, forPetType) VALUES(?,?,?,?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, object.getHouseName());
            statement.setString(2, object.getId());
            statement.setString(3, object.getAddition());
            statement.setBoolean(4, object.getInsideHouseUsing());
            statement.setString(5, object.getEnvironmentFeatures());
            statement.setDouble(6, object.getPrice());
            statement.setString(7, object.getForPetType().name());
            log.info("save record");
            if (statement.executeUpdate() == 0) {
                log.error("record save error");
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception("record with this id has been saved previously");
        }
        try {
            logToHistory.logObjectChange(object, config.getConfigurationEntry(LOG_SAVE_ENVIRONMENT_VARIANT_DB), object.getId());
        } catch (Exception e) {
            throw new Exception("Error at saving Mongo DB record");
        }
    }

    @Override
    public void deleteOwnerRecord(String id) throws Exception {
        try{
            logToHistory.logObjectChange(getOwnerRecordByID(id), config.getConfigurationEntry(LOG_DELETE_OWNER_DB), id);
        }catch (Exception e)
        {
            throw new Exception("Error at saving Mongo DB record");
        }
        String deleteSQL = "DELETE FROM " + config.getConfigurationEntry(OWNER_DB) + " WHERE id = \"" + id + "\"";
        try (Statement statement = connection.createStatement()) {
            if (statement.executeUpdate(deleteSQL) == 0) {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception("Owner with this id not found");
        }
    }


    @Override
    public void deletePetRecord(Pet pet) throws Exception {
        try {
            logToHistory.logObjectChange(getPetRecordByID(pet.getId()), config.getConfigurationEntry(LOG_DELETE_PET_DB), pet.getId());
        }catch (Exception e){
            throw new Exception("Error at saving Mongo DB record");
        }
        switch (pet.getType()) {
            case CAT:
                deletePet(CAT_DB, pet.getId());
                break;
            case DOG:
                deletePet(DOG_DB, pet.getId());
                break;
            case BIRD:
                deletePet(BIRD_DB, pet.getId());
                break;
            case FISH:
                deletePet(FISH_DB, pet.getId());
                break;
            default:
                throw new Exception("Incorrect pet type");
        }
    }

    @Override
    public void deleteOnePetRecord(String id) throws Exception {
        String selectSQL = "Select * FROM " + config.getConfigurationEntry(LINKTABLE_DB) + " WHERE petId = \"" + id + "\"";
        Map<String, TypePet> type = new HashMap<String, TypePet>();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(selectSQL);
            if (rs.next()) {
                type.put(rs.getString("petId"), TypePet.valueOf(rs.getString("petType")));
            }
        }
        if (type.isEmpty()) {
            log.error("Records not found");
            throw new Exception("No records with this id");
        }
        for (Map.Entry<String, TypePet> entry : type.entrySet()) {
            deletePetRecord(findForGetPetRecord(entry.getKey(), entry.getValue()));
        }
    }

    public void findForDelPetByOwner(String id) throws Exception {
        String selectSQL = "Select * FROM " + config.getConfigurationEntry(LINKTABLE_DB) + " WHERE ownerId = \"" + id + "\"";
        Map<String, TypePet> type = new HashMap<String, TypePet>();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(selectSQL);
            if (rs.next()) {
                type.put(rs.getString("petId"), TypePet.valueOf(rs.getString("petType")));
            }
        }
        if (type.isEmpty()) {
            log.error("Records not found");
            throw new Exception("No records with this id");
        }
        for (Map.Entry<String, TypePet> entry : type.entrySet()) {
            deletePetRecord(findForGetPetRecord(entry.getKey(), entry.getValue()));
        }
    }

    public void deletePet(String path, String id) throws Exception {
        String deleteSQL = "DELETE FROM " + config.getConfigurationEntry(path) + " WHERE id = \"" + id + "\"";
        try (Statement statement = connection.createStatement()) {
            if (statement.executeUpdate(deleteSQL) == 0) {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception("record with this id not found");
        }
    }

    @Override
    public void deleteFeedRecord(String id) throws Exception {
        try {
            logToHistory.logObjectChange(getFeedRecordByID(id), config.getConfigurationEntry(LOG_DELETE_FEED_DB), id);
        }catch (Exception e){
            throw new Exception("Error at saving Mongo DB record");
        }
        String deleteSQL = "DELETE FROM " + config.getConfigurationEntry(FEED_DB) + " WHERE id = \"" + id + "\"";
        try (Statement statement = connection.createStatement()) {
            if (statement.executeUpdate(deleteSQL) == 0) {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception("feed with this id not found");
        }
    }

    @Override
    public void deleteDrugRecord(String id) throws Exception {
        try {
            logToHistory.logObjectChange(getDrugRecordByID(id), config.getConfigurationEntry(LOG_DELETE_DRUG_DB), id);
        }catch (Exception e){
            throw new Exception("Error at saving Mongo DB record");
        }
        String deleteSQL = "DELETE FROM " + config.getConfigurationEntry(DRUG_DB) + " WHERE id = \"" + id + "\"";
        try (Statement statement = connection.createStatement()) {
            if (statement.executeUpdate(deleteSQL) == 0) {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception("drug with this id not found");
        }
    }

    @Override
    public void deleteDiseaseRecord(String id) throws Exception {
        try {
            logToHistory.logObjectChange(getDiseaseRecordByID(id), config.getConfigurationEntry(LOG_DELETE_DISEASE_DB), id);
        }catch (Exception e){
            throw new Exception("Error at saving Mongo DB record");
        }
        String deleteSQL = "DELETE FROM " + config.getConfigurationEntry(DISEASE_DB) + " WHERE id = \"" + id + "\"";
        try (Statement statement = connection.createStatement()) {
            if (statement.executeUpdate(deleteSQL) == 0) {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception("disease with this id not found");
        }
    }

    @Override
    public void deleteEnvironmentVariantRecord(String id) throws Exception {
        try {
            logToHistory.logObjectChange(getEnvironmentVariantRecordByID(id), config.getConfigurationEntry(LOG_DELETE_ENVIRONMENT_VARIANT_DB), id);
        }catch (Exception e){
            throw new Exception("Error at saving Mongo DB record");
        }
        String deleteSQL = "DELETE FROM " + config.getConfigurationEntry(ENVVAR_DB) + " WHERE id = \"" + id + "\"";
        try (Statement statement = connection.createStatement()) {
            if (statement.executeUpdate(deleteSQL) == 0) {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception("variant with this id not found");
        }
    }

    @Override
    public Owner getOwnerRecordByID(String id) throws Exception {
        String selectSQL = "Select * FROM " + config.getConfigurationEntry(OWNER_DB) + " WHERE id = \"" + id + "\"";
        Owner owner = new Owner();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(selectSQL);
            if (rs.next()) {
                owner.setId(rs.getString("id"));
                owner.setOwnerName(rs.getString("ownerName"));
                owner.setPhoneNumber(rs.getString("phoneNumber"));
                owner.setEmail(rs.getString("email"));
                owner.setBankAccount(rs.getLong("bankAccount"));
            }
        }
        if (owner.getId() == null) {
            log.error("Not found owner with id=" + id);
            throw new Exception("there is no record with this id");
        }
        return owner;
    }

    public Pet getCat(String id) throws Exception {
        String selectSQL = "Select * FROM " + config.getConfigurationEntry(CAT_DB) + " WHERE id = \"" + id + "\"";
        Cat cat = new Cat();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(selectSQL);
            if (rs.next()) {
                cat.setName(rs.getString("name"));
                cat.setId(rs.getString("id"));
                cat.setGender(rs.getString("gender"));
                cat.setWeight(rs.getDouble("weight"));
                cat.setFeedType(rs.getString("feedType"));
                cat.setType(TypePet.valueOf(rs.getString("type")));
                cat.setAge(rs.getInt("age"));
                cat.setNameOfDisease(rs.getString("nameOfDisease"));
                cat.setOwnerId(rs.getString("ownerId"));
                cat.setAfraidDogs(rs.getBoolean("afraidDogs"));
                cat.setIsCalm(rs.getBoolean("isCalm"));
            }
        }
        if (cat.getId() == null) {
            log.error("Not found cat with id=" + id);
            throw new Exception("there is no record with this id");
        }
        return cat;
    }

    public Pet getDog(String id) throws Exception {
        String selectSQL = "Select * FROM " + config.getConfigurationEntry(DOG_DB) + " WHERE id = \"" + id + "\"";
        Dog dog = new Dog();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(selectSQL);
            if (rs.next()) {
                dog.setName(rs.getString("name"));
                dog.setId(rs.getString("id"));
                dog.setGender(rs.getString("gender"));
                dog.setWeight(rs.getDouble("weight"));
                dog.setFeedType(rs.getString("feedType"));
                dog.setType(TypePet.valueOf(rs.getString("type")));
                dog.setAge(rs.getInt("age"));
                dog.setNameOfDisease(rs.getString("nameOfDisease"));
                dog.setOwnerId(rs.getString("ownerId"));
                dog.setIsAgressive(rs.getBoolean("isAggressive"));
            }
        }
        if (dog.getId() == null) {
            log.error("Not found dog with id=" + id);
            throw new Exception("there is no record with this id");
        }
        return dog;
    }

    public Pet getBird(String id) throws Exception {
        String selectSQL = "Select * FROM " + config.getConfigurationEntry(BIRD_DB) + " WHERE id = \"" + id + "\"";
        Bird bird = new Bird();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(selectSQL);
            if (rs.next()) {
                bird.setName(rs.getString("name"));
                bird.setId(rs.getString("id"));
                bird.setGender(rs.getString("gender"));
                bird.setWeight(rs.getDouble("weight"));
                bird.setFeedType(rs.getString("feedType"));
                bird.setType(TypePet.valueOf(rs.getString("type")));
                bird.setAge(rs.getInt("age"));
                bird.setNameOfDisease(rs.getString("nameOfDisease"));
                bird.setOwnerId(rs.getString("ownerId"));
                bird.setIsWaterFlow(rs.getBoolean("isWaterFowl"));
            }
        }
        if (bird.getId() == null) {
            log.error("Not found bird with id=" + id);
            throw new Exception("there is no record with this id");
        }
        return bird;
    }

    public Pet getFish(String id) throws Exception {
        String selectSQL = "Select * FROM " + config.getConfigurationEntry(FISH_DB) + " WHERE id = \"" + id + "\"";
        Fish fish = new Fish();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(selectSQL);
            if (rs.next()) {
                fish.setName(rs.getString("name"));
                fish.setId(rs.getString("id"));
                fish.setGender(rs.getString("gender"));
                fish.setWeight(rs.getDouble("weight"));
                fish.setFeedType(rs.getString("feedType"));
                fish.setType(TypePet.valueOf(rs.getString("type")));
                fish.setAge(rs.getInt("age"));
                fish.setNameOfDisease(rs.getString("nameOfDisease"));
                fish.setOwnerId(rs.getString("ownerId"));
                fish.setWaterType(rs.getString("waterType"));
            }
        }
        if (fish.getId() == null) {
            log.error("Not found fish with id=" + id);
            throw new Exception("there is no record with this id");
        }
        return fish;
    }

    public Pet findForGetPetRecord(String id, TypePet type) throws Exception {
        switch (type) {
            case CAT:
                return getCat(id);
            case DOG:
                return getDog(id);
            case BIRD:
                return getBird(id);
            case FISH:
                return getFish(id);
            default:
                throw new Exception("Incorrect pet type");
        }
    }

    @Override
    public Pet getPetRecordByOwnerID(String id) throws Exception {
        String selectSQL = "Select * FROM " + config.getConfigurationEntry(LINKTABLE_DB) + " WHERE ownerId = \"" + id + "\"";
        Map<String, TypePet> type = new HashMap<String, TypePet>();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(selectSQL);
            if (rs.next()) {
                type.put(rs.getString("petId"), TypePet.valueOf(rs.getString("petType")));
            }
        }
        if (type.isEmpty()) {
            log.error("Records not found");
            throw new Exception("No records with this id");
        }
        for (Map.Entry<String, TypePet> entry : type.entrySet()) {
            return findForGetPetRecord(entry.getKey(), entry.getValue());
        }
        throw new Exception("No record in link table");
    }

    @Override
    public Pet getPetRecordByID(String id) throws Exception {
        String selectSQL = "Select * FROM " + config.getConfigurationEntry(LINKTABLE_DB) + " WHERE petId = \"" + id + "\"";
        Map<String, TypePet> type = new HashMap<String, TypePet>();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(selectSQL);
            if (rs.next()) {
                type.put(rs.getString("petId"), TypePet.valueOf(rs.getString("petType")));
            }
        }
        if (type.isEmpty()) {
            log.error("Records not found");
            throw new Exception("No records with this id");
        }
        for (Map.Entry<String, TypePet> entry : type.entrySet()) {
            return findForGetPetRecord(entry.getKey(), entry.getValue());
        }
        throw new Exception("No record in link table");
    }


    @Override
    public List<History> getHistoryRecords(String id) throws Exception {
        String selectSQL = "Select * FROM " + config.getConfigurationEntry(HISTORY_DB) + " WHERE petId = \"" + id + "\"";
        History history = new History();
        List<History> list = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(selectSQL);
            if (rs.next()) {
                history.setPetName(rs.getString("petName"));
                history.setPetId(rs.getString("petId"));
                history.setOwnerId(rs.getString("ownerId"));
                history.setServiceName(rs.getString("serviceName"));
                history.setPrice(rs.getDouble("price"));
                Date date = new Date(rs.getDate("date").getTime());
                history.setDate(date);
                history.setServId(rs.getString("servId"));
                list.add(history);
            }
        }
        if (list.isEmpty()) {
            log.error("Not found history records with id=" + id);
            throw new Exception("No history records for this pet");
        }
        return list;
    }

    @Override
    public Feed getFeedRecordByID(String id) throws Exception {
        String selectSQL = "Select * FROM " + config.getConfigurationEntry(FEED_DB) + " WHERE id = \"" + id + "\"";
        Feed feed = new Feed();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(selectSQL);
            if (rs.next()) {
                feed.setFeedName(rs.getString("feedName"));
                feed.setId(rs.getString("id"));
                feed.setForPetType(TypePet.valueOf(rs.getString("forPetType")));
                feed.setPriceForPack(rs.getDouble("priceForPack"));
                feed.setWeightOfPack(rs.getDouble("weightOfPack"));
            }
        }
        if (feed.getId() == null) {
            log.error("Not found feed with id=" + id);
            throw new Exception("there is no feed with this id");
        }
        return feed;
    }

    @Override
    public Drug getDrugRecordByID(String id) throws Exception {
        String selectSQL = "Select * FROM " + config.getConfigurationEntry(DRUG_DB) + " WHERE id = \"" + id + "\"";
        Drug drug = new Drug();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(selectSQL);
            if (rs.next()) {
                drug.setDrugName(rs.getString("drugName"));
                drug.setId(rs.getString("id"));
                drug.setPriceForPack(rs.getDouble("priceForPack"));
                drug.setPiecesInPack(rs.getInt("piecesInPack"));
                drug.setIntesityPerDay(rs.getInt("intensityPerDay"));
            }
        }
        if (drug.getId() == null) {
            log.error("Not found drug with id=" + id);
            throw new Exception("there is no drug with this id");
        }
        return drug;
    }

    @Override
    public Disease getDiseaseRecordByID(String id) throws Exception {
        String selectSQL = "Select * FROM " + config.getConfigurationEntry(DISEASE_DB) + " WHERE id = \"" + id + "\"";
        Disease disease = new Disease();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(selectSQL);
            if (rs.next()) {
                disease.setNameOfDisease(rs.getString("nameOfDisease"));
                disease.setId(rs.getString("id"));
                disease.setForTreatment(rs.getString("forTreatment"));
                disease.setTreatmentTime(rs.getInt("treatmentTime"));
            }
        }
        if (disease.getId() == null) {
            log.error("Not found disease with id=" + id);
            throw new Exception("there is no disease with this id");
        }
        return disease;
    }

    @Override
    public EnvironmentVariant getEnvironmentVariantRecordByID(String id) throws Exception {
        String selectSQL = "Select * FROM " + config.getConfigurationEntry(ENVVAR_DB) + " WHERE id = \"" + id + "\"";
        EnvironmentVariant variant = new EnvironmentVariant();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(selectSQL);
            if (rs.next()) {
                variant.setHouseName(rs.getString("houseName"));
                variant.setId(rs.getString("id"));
                variant.setAddition(rs.getString("addition"));
                variant.setInsideHouseUsing(rs.getBoolean("insideHouseUsing"));
                variant.setEnvironmentFeatures(rs.getString("environmentFeatures"));
                variant.setPrice(rs.getDouble("price"));
                variant.setForPetType(TypePet.valueOf(rs.getString("forPetType")));
            }
        }
        if (variant.getId() == null) {
            log.error("Not found disease with id=" + id);
            throw new Exception("there is no variant with this id");
        }
        return variant;
    }

    @Override
    public List<Disease> getAllDiseases() throws Exception {
        String selectSQL = "Select * FROM " + config.getConfigurationEntry(DISEASE_DB);
        Disease dis = new Disease();
        List<Disease> list = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(selectSQL);
            if (rs.next()) {
                dis.setNameOfDisease(rs.getString("nameOfDisease"));
                dis.setId(rs.getString("id"));
                dis.setForTreatment(rs.getString("forTreatment"));
                dis.setTreatmentTime(rs.getInt("treatmentTime"));
                list.add(dis);
            }
        }
        if (list.isEmpty()) {
            log.error("No diseases");
            throw new Exception("No diseases at all");
        }
        return list;
    }

    @Override
    public List<Drug> getAllDrugs() throws Exception {
        String selectSQL = "Select * FROM " + config.getConfigurationEntry(DRUG_DB);
        Drug dr = new Drug();
        List<Drug> list = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(selectSQL);
            if (rs.next()) {
                dr.setDrugName(rs.getString("drugName"));
                dr.setId(rs.getString("id"));
                dr.setPriceForPack(rs.getDouble("priceForPack"));
                dr.setPiecesInPack(rs.getInt("piecesInPack"));
                dr.setIntesityPerDay(rs.getInt("intensityPerDay"));
                list.add(dr);
            }
        }
        if (list.isEmpty()) {
            log.error("No drugs");
            throw new Exception("No drugs at all");
        }
        return list;
    }

    @Override
    public List<Feed> getAllFeeds() throws Exception {
        String selectSQL = "Select * FROM " + config.getConfigurationEntry(FEED_DB);
        Feed fd = new Feed();
        List<Feed> list = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(selectSQL);
            if (rs.next()) {
                fd.setFeedName(rs.getString("feedName"));
                fd.setId(rs.getString("id"));
                fd.setForPetType(TypePet.valueOf(rs.getString("forPetType")));
                fd.setPriceForPack(rs.getDouble("priceForPack"));
                fd.setWeightOfPack(rs.getDouble("weightOfPack"));
                list.add(fd);
            }
        }
        if (list.isEmpty()) {
            log.info("No feed.");
            throw new Exception(("No feed at all"));
        }
        return list;
    }

    @Override
    public List<EnvironmentVariant> getAllEnvironmentVariants() throws Exception {
        String selectSQL = "Select * FROM " + config.getConfigurationEntry(ENVVAR_DB);
        EnvironmentVariant var = new EnvironmentVariant();
        List<EnvironmentVariant> list = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(selectSQL);
            if (rs.next()) {
                var.setHouseName(rs.getString("houseName"));
                var.setId(rs.getString("id"));
                var.setAddition(rs.getString("addition"));
                var.setInsideHouseUsing(rs.getBoolean("insideHouseUsing"));
                var.setEnvironmentFeatures(rs.getString("environmentFeatures"));
                var.setPrice(rs.getDouble("price"));
                var.setForPetType(TypePet.valueOf(rs.getString("forPetType")));
                list.add(var);
            }
        }
        if (list.isEmpty()) {
            log.info("No environment variants");
            throw new Exception("No variants at all");
        }
        return list;
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
