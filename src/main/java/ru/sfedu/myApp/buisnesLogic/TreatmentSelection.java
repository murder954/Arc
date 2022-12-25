package ru.sfedu.myApp.buisnesLogic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.myApp.ConfigUtils;
import ru.sfedu.myApp.DataProviderXML;
import ru.sfedu.myApp.Entity.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static ru.sfedu.myApp.Constans.*;

public class TreatmentSelection {

    Logger log = LogManager.getLogger(TreatmentSelection.class);
    ConfigUtils config = new ConfigUtils();
    DataProviderXML data = new DataProviderXML();

    //SimpleDateFormat formatForDateNow = new SimpleDateFormat(DATE_FORMAT);
    public TreatmentSelection() {

    }

    public String createId() {
        String id = String.valueOf(UUID.randomUUID());
        return id;
    }

    public void animalTreatment(Pet object) throws Exception {
        List<Disease> diseases = data.getAllRecords(config.getConfigurationEntry(DISEASE_XML));
        Disease dis = new Disease();
        for (Disease diseaseObj : diseases) {
            if (diseaseObj.getNameOfDisease().equals(object.getNameOfDisease())) {
                dis = diseaseObj;
            }
        }
        if (dis.getId() == null)
            throw new Exception("Disease record with this id not found");
        List<Drug> drugs = data.getAllRecords(config.getConfigurationEntry(DRUG_XML));
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
        data.saveHistoryRecord(object, treat);
    }

    public void createRecipe(Treatment obj, double packs) throws IOException {
        log.info("To treat %s, you must buy %s packs of %s. And you should give to your pet %s tablets per day for %s days. It will be cost %s", obj.getDiseaseName(), packs, obj.getDrugName(), obj.getIntesityPerDay(), obj.getTreatmentTime(), obj.getCost());
    }

    public void calculatingExpenses(Pet pet) throws Exception {
        double totalCost = Double.parseDouble(config.getConfigurationEntry(TOTAL));
        if (pet.getNameOfDisease().isEmpty()) {
            totalCost += expensesFood(pet.getId());
            totalCost += expensesEnvironment(pet.getId());
        } else {
            totalCost += expensesMedicine(pet.getId());
            totalCost += expensesFood(pet.getId());
            totalCost += expensesEnvironment(pet.getId());
        }
        log.info("Total cost of services for pet: " + totalCost);
    }

    public double expensesMedicine(String id) throws Exception {
        List<History> list = data.getHistoryRecords(id);
        double total = Double.parseDouble(config.getConfigurationEntry(TOTAL));
        for (History history : list) {
            if (history.getServiceName().equals(config.getConfigurationEntry(TREATMENT))) {
                total += history.getPrice();
            }
        }
        if (total == 0.0)
            log.info("No medical expenses for this pet");
        log.info("Medical expenses for pet: " + total);
        return total;
    }

    public double expensesFood(String id) throws Exception {
        List<History> list = data.getHistoryRecords(id);
        double total = Double.parseDouble(config.getConfigurationEntry(TOTAL));
        for (History history : list) {
            if (history.getServiceName().equals(config.getConfigurationEntry(DIET))) {
                total += history.getPrice();
            }
        }
        if (total == 0.0)
            log.info("No food expenses for this pet");
        log.info("Food expenses for pet: " + total);
        return total;
    }

    public double expensesEnvironment(String id) throws Exception {
        List<History> list = data.getHistoryRecords(id);
        double total = Double.parseDouble(config.getConfigurationEntry(TOTAL));
        for (History history : list) {
            if (history.getServiceName().equals(config.getConfigurationEntry(ENVIRONMENT))) {
                total += history.getPrice();
            }
        }
        if (total == 0.0)
            log.info("No environment expenses for this pet");
        log.info("Environment expenses for pet: " + total);
        return total;
    }

    public void selectionFeed(Pet pet) throws Exception {
        List<Feed> list = data.getAllRecords(config.getConfigurationEntry(FEED_XML));
        log.info("Your previously expenses for feed: " + expensesFood(pet.getId()));
        Feed obj = new Feed();
        for (Feed feed : list) {
            if (feed.getForPetType().equals(pet.getType())) {
                obj = feed;
            }
        }
        if(obj.getId() == null)
            throw new Exception("No feed for your pet");
        Diet diet = new Diet();
        diet.setId(createId());
        diet.setDate(new Date());
        diet.setCost(obj.getPriceForPack());
        diet.setNameOfService(config.getConfigurationEntry(DIET));
        diet.setFeedType(pet.getFeedType());
        diet.setWeightOfPack(obj.getWeightOfPack());
        diet.setPriceForPack(obj.getPriceForPack());
        data.saveHistoryRecord(pet, diet);
    }

    public void selectionEnvironmentVariant(Pet pet) throws Exception {
        List<EnvironmentVariant> list = data.getAllRecords(config.getConfigurationEntry(ENVVAR_XML));
        log.info("Your previously expenses for environment: " + expensesEnvironment(pet.getId()));
        EnvironmentVariant obj = new EnvironmentVariant();
        for (EnvironmentVariant var : list) {
            if (var.getForPetType().equals(pet.getType())) {
                obj = var;
            }
        }
        if(obj.getId() == null)
            throw new Exception("No environment for your pet");
        Environment env = new Environment();
        env.setId(createId());
        env.setDate(new Date());
        env.setCost(obj.getPrice());
        env.setAddition(obj.getAddition());
        env.setEnvironmentFeatures(obj.getEnvironmentFeatures());
        env.setInsideHouse(obj.getInsideHouseUsing());
        env.setPrice(obj.getPrice());
        env.setNameOfService(config.getConfigurationEntry(ENVIRONMENT));
        data.saveHistoryRecord(pet, env);
    }
}
