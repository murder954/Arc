package ru.sfedu.myApp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.myApp.Entity.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;

import static ru.sfedu.myApp.Constans.*;

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
        }catch(SQLException e){
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
            statement.setString(3, object.getType());
            if (statement.executeUpdate() == 0) {
                log.error("Impossible to save pet");
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception("Error at saving data in Link Table");
        }
        switch (object.getType()) {
            case "cat":
                saveCat(object);
                break;
            case "dog":
                saveDog(object);
                break;
            case "bird":
                saveBird(object);
                break;
            case "fish":
                saveFish(object);
                break;
            default:
                break;
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
            statement.setString(6, cat.getType());
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
        logToHistory.logObjectChange(obj, "savePetDB", obj.getId());
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
            statement.setString(6, dog.getType());
            statement.setInt(7, dog.getAge());
            statement.setString(8, dog.getNameOfDisease());
            statement.setString(9, dog.getOwnerId());
            statement.setBoolean(10, dog.getIsAgressive());
            log.info("save dog");
            if (statement.executeUpdate() == 0) {
                log.error("Impossible to save dog");
                throw new Exception();
            }
        }
        catch (Exception e) {
            throw new Exception("Pet with this id has been saved previously");
        }
        logToHistory.logObjectChange(obj, "savePetDB", obj.getId());
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
            statement.setString(6, bird.getType());
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
        logToHistory.logObjectChange(obj, "savePetDB", obj.getId());
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
            statement.setString(6, fish.getType());
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
        logToHistory.logObjectChange(obj, "savePetDB", obj.getId());
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
        logToHistory.logObjectChange(object, "saveOwnerDB", object.getId());
    }

    @Override
    public void saveHistoryRecord(Pet pet, Service serv) throws Exception {
        String sql = "INSERT INTO " + config.getConfigurationEntry(HISTORY_DB) + " (petName, petid, ownerId, serviceName, price, date) VALUES(?,?,?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, pet.getName());
            statement.setString(2, pet.getId());
            statement.setString(3, pet.getOwnerId());
            statement.setString(4, serv.getNameOfService());
            statement.setDouble(5, serv.getCost());
            statement.setDate(6, (Date) serv.getDate());
            log.info("save history record");
            if (statement.executeUpdate() == 0) {
                log.error("record save error");
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception("record with this id has been saved previously");
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
            statement.setString(5, object.getForPetType());
            log.info("save record");
            if (statement.executeUpdate() == 0) {
                log.error("record save error");
                throw new Exception();
            }
        }
        catch (Exception e) {
            throw new Exception("record with this id has been saved previously");
        }
        logToHistory.logObjectChange(object, "saveFeedDB", object.getId());
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
        logToHistory.logObjectChange(object, "saveDrugDB", object.getId());
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
        logToHistory.logObjectChange(object, "saveDiseaseDB", object.getId());
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
            statement.setString(7, object.getForPetType());
            log.info("save record");
            if (statement.executeUpdate() == 0) {
                log.error("record save error");
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception("record with this id has been saved previously");
        }
        logToHistory.logObjectChange(object, "saveVariantDB", object.getId());
    }

    @Override
    public void deleteOwnerRecord(String id) throws Exception {
        try {
            logToHistory.logObjectChange(getOwnerRecordByID(id), "deleteOwnerDB", id);
        }catch (Exception e){
            throw new Exception("Error at saving Mongo DB record");
        }
        String deleteSQL = "DELETE FROM " + config.getConfigurationEntry(OWNER_DB) + " WHERE id = \"" + id+"\"";
        try (Statement statement = connection.createStatement()){
            if(statement.executeUpdate(deleteSQL) == 0){
                throw new Exception();
            }
        }catch (Exception e){
            throw new Exception("Owner with this id not found");
        }
    }


    @Override
    public void deletePetRecord(Pet pet) throws Exception {
        switch (pet.getType()) {
            case "cat":
                 deletePet(CAT_DB, pet.getId());
                 break;
            case "dog":
                deletePet(DOG_DB, pet.getId());
                break;
            case "bird":
                deletePet(BIRD_DB, pet.getId());
                break;
            case "fish":
                deletePet(FISH_DB, pet.getId());
                break;
            default:
                throw new Exception("Incorrect pet type");
        }
    }

    @Override
    public void deleteOnePetRecord(String id) throws Exception {
        String selectSQL = "Select * FROM " + config.getConfigurationEntry(LINKTABLE_DB) + " WHERE petId = \"" + id+"\"";
        Map<String, String> type = new HashMap<String, String>();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(selectSQL);
            if (rs.next()) {
                type.put(rs.getString("petId"), rs.getString("petType"));
            }
        }
        if (type.isEmpty()) {
            log.error("Records not found");
            throw new Exception("No records with this id");
        }
        for (Map.Entry<String, String> entry : type.entrySet()) {
            deletePetRecord(findForGetPetRecord(entry.getKey(), entry.getValue()));
        }
    }

    public void findForDelPetByOwner(String id) throws Exception {
        String selectSQL = "Select * FROM " + config.getConfigurationEntry(LINKTABLE_DB) + " WHERE ownerId = \"" + id+"\"";
        Map<String, String> type = new HashMap<String, String>();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(selectSQL);
            if (rs.next()) {
                type.put(rs.getString("petId"), rs.getString("petType"));
            }
        }
        if (type.isEmpty()) {
            log.error("Records not found");
            throw new Exception("No records with this id");
        }
        for (Map.Entry<String, String> entry : type.entrySet()) {
            deletePetRecord(findForGetPetRecord(entry.getKey(), entry.getValue()));
        }
    }

    public void deletePet(String path, String id) throws Exception{
        logToHistory.logObjectChange(getPetRecordByID(id), "deletePetDB", id);
        String deleteSQL = "DELETE FROM " + config.getConfigurationEntry(path) + " WHERE id = \"" + id+"\"";
        try (Statement statement = connection.createStatement()){
            if(statement.executeUpdate(deleteSQL) == 0){
                throw new Exception();
            }
        } catch (Exception e){
            throw new Exception("record with this id not found");
        }
    }

    @Override
    public void deleteFeedRecord(String id) throws Exception {
        try {
            logToHistory.logObjectChange(getFeedRecordByID(id), "deleteFeedDb", id);
        }catch (Exception e){
            throw new Exception("Error at saving Mongo DB record");
        }
        String deleteSQL = "DELETE FROM " + config.getConfigurationEntry(FEED_DB) + " WHERE id = \"" + id+"\"";
        try (Statement statement = connection.createStatement()){
            if(statement.executeUpdate(deleteSQL) == 0){
                throw new Exception();
            }
        }catch (Exception e){
            throw new Exception("feed with this id not found");
        }
    }

    @Override
    public void deleteDrugRecord(String id) throws Exception {
        try {
            logToHistory.logObjectChange(getDrugRecordByID(id), "deleteDrugDB", id);
        }catch (Exception e){
            throw new Exception("Error at saving Mongo DB record");
        }
        String deleteSQL = "DELETE FROM " + config.getConfigurationEntry(DRUG_DB) + " WHERE id = \"" + id+"\"";
        try (Statement statement = connection.createStatement()){
            if(statement.executeUpdate(deleteSQL) == 0){
                throw new Exception();
            }
        }catch (Exception e){
            throw new Exception("drug with this id not found");
        }
    }

    @Override
    public void deleteDiseaseRecord(String id) throws Exception {
        try {
            logToHistory.logObjectChange(getDiseaseRecordByID(id), "deleteDiseaseDB", id);
        }catch (Exception e){
            throw new Exception("Error at saving Mongo DB record");
        }
        String deleteSQL = "DELETE FROM " + config.getConfigurationEntry(DISEASE_DB) + " WHERE id = \"" + id+"\"";
        try (Statement statement = connection.createStatement()){
            if(statement.executeUpdate(deleteSQL) == 0){
                throw new Exception();
            }
        }catch (Exception e){
            throw new Exception("disease with this id not found");
        }
    }

    @Override
    public void deleteEnvironmentVariantRecord(String id) throws Exception {
        try {
            logToHistory.logObjectChange(getEnvironmentVariantRecordByID(id), "deleteVariantDB", id);
        }catch (Exception e){
            throw new Exception("Error at saving Mongo DB record");
        }
        String deleteSQL = "DELETE FROM " + config.getConfigurationEntry(ENVVAR_DB) + " WHERE id = \"" + id+"\"";
        try (Statement statement = connection.createStatement()){
            if(statement.executeUpdate(deleteSQL) == 0){
                throw new Exception();
            }
        }catch (Exception e){
            throw new Exception("variant with this id not found");
        }
    }

    @Override
    public Owner getOwnerRecordByID(String id) throws Exception {
        String selectSQL = "Select * FROM " + config.getConfigurationEntry(OWNER_DB) + " WHERE id = \"" + id+"\"";
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
        String selectSQL = "Select * FROM " + config.getConfigurationEntry(CAT_DB) + " WHERE id = \"" + id+"\"";
        Cat cat = new Cat();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(selectSQL);
            if (rs.next()) {
                cat.setName(rs.getString("name"));
                cat.setId(rs.getString("id"));
                cat.setGender(rs.getString("gender"));
                cat.setWeight(rs.getDouble("weight"));
                cat.setFeedType(rs.getString("feedType"));
                cat.setType(rs.getString("type"));
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
        String selectSQL = "Select * FROM " + config.getConfigurationEntry(DOG_DB) + " WHERE id = \"" + id+"\"";
        Dog dog = new Dog();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(selectSQL);
            if (rs.next()) {
                dog.setName(rs.getString("name"));
                dog.setId(rs.getString("id"));
                dog.setGender(rs.getString("gender"));
                dog.setWeight(rs.getDouble("weight"));
                dog.setFeedType(rs.getString("feedType"));
                dog.setType(rs.getString("type"));
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
        String selectSQL = "Select * FROM " + config.getConfigurationEntry(BIRD_DB) + " WHERE id = \"" + id+"\"";
        Bird bird = new Bird();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(selectSQL);
            if (rs.next()) {
                bird.setName(rs.getString("name"));
                bird.setId(rs.getString("id"));
                bird.setGender(rs.getString("gender"));
                bird.setWeight(rs.getDouble("weight"));
                bird.setFeedType(rs.getString("feedType"));
                bird.setType(rs.getString("type"));
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
        String selectSQL = "Select * FROM " + config.getConfigurationEntry(FISH_DB) + " WHERE id = \"" + id+"\"";
        Fish fish = new Fish();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(selectSQL);
            if (rs.next()) {
                fish.setName(rs.getString("name"));
                fish.setId(rs.getString("id"));
                fish.setGender(rs.getString("gender"));
                fish.setWeight(rs.getDouble("weight"));
                fish.setFeedType(rs.getString("feedType"));
                fish.setType(rs.getString("type"));
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

    public Pet findForGetPetRecord(String id, String type) throws Exception {
        switch (type) {
            case "cat":
                return getCat(id);
            case "dog":
                return getDog(id);
            case "bird":
                return getBird(id);
            case "fish":
                return getFish(id);
            default:
                throw new Exception("Incorrect pet type");
        }
    }

    @Override
    public Pet getPetRecordByOwnerID(String id) throws Exception {
        String selectSQL = "Select * FROM " + config.getConfigurationEntry(LINKTABLE_DB) + " WHERE ownerId = \"" + id+"\"";
        Map<String, String> type = new HashMap<String, String>();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(selectSQL);
            if (rs.next()) {
                type.put(rs.getString("petId"), rs.getString("petType"));
            }
        }
        if (type.isEmpty()) {
            log.error("Records not found");
            throw new Exception("No records with this id");
        }
        for (Map.Entry<String, String> entry : type.entrySet()) {
            return findForGetPetRecord(entry.getKey(), entry.getValue());
        }
        throw new Exception("No record in link table");
    }

    @Override
    public Pet getPetRecordByID(String id) throws Exception {
        String selectSQL = "Select * FROM " + config.getConfigurationEntry(LINKTABLE_DB) + " WHERE petId = \"" + id+"\"";
        Map<String, String> type = new HashMap<String, String>();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(selectSQL);
            if (rs.next()) {
                type.put(rs.getString("petId"), rs.getString("petType"));
            }
        }
        if (type.isEmpty()) {
            log.error("Records not found");
            throw new Exception("No records with this id");
        }
        for (Map.Entry<String, String> entry : type.entrySet()) {
            return findForGetPetRecord(entry.getKey(), entry.getValue());
        }
        throw new Exception("No record in link table");
    }


    @Override
    public List<History> getHistoryRecords(String id) throws Exception {
        String selectSQL = "Select * FROM " + config.getConfigurationEntry(HISTORY_DB) + " WHERE petId = \"" + id+"\"";
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
                history.setDate(rs.getDate("date"));
                list.add(history);
            }
        }
        if (list.isEmpty()) {
            log.error("Not found history records with id=" + id);
            throw new Exception("there is no records with this id");
        }
        return list;
    }

    @Override
    public Feed getFeedRecordByID(String id) throws Exception {
        String selectSQL = "Select * FROM " + config.getConfigurationEntry(FEED_DB) + " WHERE id = \"" + id+"\"";
        Feed feed = new Feed();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(selectSQL);
            if (rs.next()) {
                feed.setFeedName(rs.getString("feedName"));
                feed.setId(rs.getString("id"));
                feed.setForPetType(rs.getString("forPetType"));
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
        String selectSQL = "Select * FROM " + config.getConfigurationEntry(DRUG_DB) + " WHERE id = \"" + id+"\"";
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
        String selectSQL = "Select * FROM " + config.getConfigurationEntry(DISEASE_DB) + " WHERE id = \"" + id+"\"";
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
        String selectSQL = "Select * FROM " + config.getConfigurationEntry(ENVVAR_DB) + " WHERE id = \"" + id+"\"";
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
            }
        }
        if (variant.getId() == null) {
            log.error("Not found disease with id=" + id);
            throw new Exception("there is no variant with this id");
        }
        return variant;
    }
    public List<Disease> getAllDiseases() throws Exception{
        String selectSQL = "Select * FROM " + config.getConfigurationEntry(DISEASE_DB);
        Disease dis = new Disease();
        List<Disease> list = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(selectSQL);
            if (rs.next()){
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

    public List<Drug> getAllDrugs() throws Exception{
        String selectSQL = "Select * FROM " + config.getConfigurationEntry(DRUG_DB);
        Drug dr = new Drug();
        List<Drug> list = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(selectSQL);
            if (rs.next()){
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
}
