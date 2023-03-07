package ru.sfedu.myApp.buisnesLogic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.myApp.ConfigUtils;
import ru.sfedu.myApp.DataProviderXML;
import ru.sfedu.myApp.IDataProvider;
import ru.sfedu.myApp.Model.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static ru.sfedu.myApp.Constans.*;

public class BusinessLogic {

    Logger log = LogManager.getLogger(BusinessLogic.class);
    ConfigUtils config = new ConfigUtils();
    DataProviderXML data = new DataProviderXML();

    //SimpleDateFormat formatForDateNow = new SimpleDateFormat(DATE_FORMAT);
    public BusinessLogic() throws IOException {

    }

    public String createId() {
        String id = String.valueOf(UUID.randomUUID());
        return id;
    }

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

    public void createRecipe(Treatment obj, double packs) throws IOException {
        log.info("To treat " + obj.getDiseaseName() + ", you must buy " + packs + " packs of " + obj.getDrugName() + ". And you should give to your pet " + obj.getIntesityPerDay() + " tablets per day for " + obj.getTreatmentTime() + " days. It will be cost " + obj.getCost());
    }

    public void calculatingExpenses(Pet pet, IDataProvider dataProvider) throws Exception {
        double totalCost = Double.parseDouble(config.getConfigurationEntry(TOTAL));
        if (pet.getNameOfDisease().equals("NONE")) {
            totalCost += expensesFood(pet.getId(), dataProvider);
            totalCost += expensesEnvironment(pet.getId(), dataProvider);
        } else {
            totalCost += expensesMedicine(pet.getId(), dataProvider);
            totalCost += expensesFood(pet.getId(), dataProvider);
            totalCost += expensesEnvironment(pet.getId(), dataProvider);
        }
        log.info("Total cost of services for pet: " + totalCost);
    }

    public double expensesMedicine(String id, IDataProvider dataProvider) throws Exception {
        List<History> list = dataProvider.getHistoryRecords(id);
        double total = Double.parseDouble(config.getConfigurationEntry(TOTAL));
        for (History history : list) {
            if (history.getServiceName().equals(config.getConfigurationEntry(TREATMENT))) {
                total += history.getPrice();
            }
        }
        log.info("Medical expenses for pet: " + total);
        return total;
    }

    public double expensesFood(String id, IDataProvider dataProvider) throws Exception {
        List<History> list = dataProvider.getHistoryRecords(id);
        double total = Double.parseDouble(config.getConfigurationEntry(TOTAL));
        for (History history : list) {
            if (history.getServiceName().equals(config.getConfigurationEntry(DIET))) {
                total += history.getPrice();
            }
        }
        log.info("Food expenses for pet: " + total);
        return total;
    }

    public double expensesEnvironment(String id, IDataProvider dataProvider) throws Exception {
        List<History> list = dataProvider.getHistoryRecords(id);
        double total = Double.parseDouble(config.getConfigurationEntry(TOTAL));
        for (History history : list) {
            if (history.getServiceName().equals(config.getConfigurationEntry(ENVIRONMENT))) {
                total += history.getPrice();
            }
        }
        log.info("Environment expenses for pet: " + total);
        return total;
    }

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
            diet.setCost(99.0);
        dataProvider.saveHistoryRecord(pet, diet);
    }

    public double calculateFeedCost(Feed feed, int pieces) {
        double price = feed.getPriceForPack() * pieces;
        double weight = feed.getWeightOfPack() * pieces;
        log.info("Price for " + pieces + "feed packs: " + price + ". Total weight: " + weight);
        return price;
    }

    public void selectionEnvironmentVariant(Pet pet, List<EnvironmentVariant> list, boolean flag, IDataProvider dataProvider) throws Exception {
        //List<EnvironmentVariant> list = data.getAllRecords(config.getConfigurationEntry(ENVVAR_XML));
        //log.info("Your previously expenses for environment: " + expensesEnvironment(pet.getId()));
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
            env.setCost(99.00);
        dataProvider.saveHistoryRecord(pet, env);
    }

    public double calculateEnvironmentCost(EnvironmentVariant obj) {
        log.info("Environment variant with name " + obj.getHouseName() + " will be cost: " + obj.getPrice() + ". Addition: " + obj.getAddition());
        return obj.getPrice();
    }
}
