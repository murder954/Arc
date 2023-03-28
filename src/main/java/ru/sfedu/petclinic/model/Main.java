package ru.sfedu.petclinic.model;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.Option;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.petclinic.*;
import ru.sfedu.petclinic.api.DataBaseProvider;
import ru.sfedu.petclinic.api.DataProviderCSV;
import ru.sfedu.petclinic.api.DataProviderXML;
import ru.sfedu.petclinic.api.IDataProvider;
import ru.sfedu.petclinic.util.ConfigUtils;

import java.util.List;


public class Main {
    private static final Logger log = LogManager.getLogger(ru.sfedu.petclinic.model.Main.class);
    private static IDataProvider dataProvider;

    private static ConfigUtils config = new ConfigUtils();

    public static void main(String[] args) throws Exception {
        log.info("Logger configuration");

        Options options = new Options();
        Option optionTreatment = new Option("treatment", true, "create treatment");
        Option optionExpenses = new Option("expenses", true, "calculate expenses animal");
        Option optionSelFeed = new Option("selectFeed", true, "selection Feed");
        Option optionSelVariant = new Option("selectVariant", true, "selection Environment Variant");
        Option optionPet = new Option("pet", true, "create Pet record");
        Option optionOwner = new Option("owner", true, "create Owner record");
        Option optionFeed = new Option("feed", true, "create feed record");
        Option optionDisease = new Option("disease", true, "create Disease record");
        Option optionDrug = new Option("drug", true, "create Drug record");
        Option optionVariant = new Option("variant", true, "create Variant record");
        Option optionDelete = new Option("delete", true, "delete record");

        options.addOption(optionTreatment);
        options.addOption(optionExpenses);
        options.addOption(optionSelFeed);
        options.addOption(optionSelVariant);
        options.addOption(optionVariant);
        options.addOption(optionDisease);
        options.addOption(optionDrug);
        options.addOption(optionFeed);
        options.addOption(optionOwner);
        options.addOption(optionPet);
        options.addOption(optionDelete);


        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        if (cmd.hasOption("pet")){
            createDataProvider(args[1]);

            switch (args[2]){
                case "cat":
                    Cat cat = new Cat(args[4], args[5], Double.parseDouble(args[6]), args[7], Integer.parseInt(args[8]), args[9], Boolean.parseBoolean(args[10]), Boolean.parseBoolean(args[11]));
                    dataProvider.savePetRecord(cat, args[3]);
                    break;
                case "dog":
                    Dog dog = new Dog(args[4], args[5], Double.parseDouble(args[6]), args[7], Integer.parseInt(args[8]), args[9], Boolean.parseBoolean(args[10]));
                    dataProvider.savePetRecord(dog, args[3]);
                    break;
                case "fish":
                    Fish fish = new Fish(args[4], args[5], Double.parseDouble(args[6]), args[7], Integer.parseInt(args[8]), args[9], args[10]);
                    dataProvider.savePetRecord(fish, args[3]);
                    break;
                case "bird":
                    Bird bird = new Bird(args[4], args[5], Double.parseDouble(args[6]), args[7], Integer.parseInt(args[8]), args[9], Boolean.parseBoolean(args[10]));
                    dataProvider.savePetRecord(bird, args[3]);
                    break;
                default:
                    throw new Exception("Undefined pet type");
            }
        }

        if (cmd.hasOption("owner")){
            createDataProvider(args[1]);
            Owner owner = new Owner(args[2], args[3], args[4], Long.parseLong(args[5]));
            dataProvider.saveOwnerRecord(owner);
        }

        if (cmd.hasOption("feed")){
            createDataProvider(args[1]);
            Feed feed = new Feed(args[2], Double.parseDouble(args[3]), Double.parseDouble(args[4]), Constans.TypePet.valueOf(args[5]));
            dataProvider.saveFeedRecord(feed);
        }

        if (cmd.hasOption("drug")){
            createDataProvider(args[1]);
            Drug drug = new Drug(args[2], Double.parseDouble(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]));
            dataProvider.saveDrugRecord(drug);
        }

        if (cmd.hasOption("disease")){
            createDataProvider(args[1]);
            Disease disease = new Disease(args[2], Integer.parseInt(args[3]), args[4]);
            dataProvider.saveDiseaseRecord(disease);
        }

        if (cmd.hasOption("variant")){
            createDataProvider(args[1]);
            EnvironmentVariant variant = new EnvironmentVariant(args[2], Boolean.parseBoolean(args[3]), args[4], args[5], Double.parseDouble(args[6]), Constans.TypePet.valueOf(args[7]));
            dataProvider.saveEnvironmentVariantRecord(variant);
        }

        if (cmd.hasOption("delete")){
            createDataProvider(args[1]);
                switch(args[2]) {
                    case "pet":
                        dataProvider.deleteOnePetRecord(args[3]);
                        break;
                    case "owner":
                        dataProvider.deleteOwnerRecord(args[3]);
                        break;
                    case "drug":
                        dataProvider.deleteDrugRecord(args[3]);
                        break;
                    case "feed":
                        dataProvider.deleteFeedRecord(args[3]);
                        break;
                    case "disease":
                        dataProvider.deleteDiseaseRecord(args[3]);
                        break;
                    case "envvar":
                        dataProvider.deleteEnvironmentVariantRecord(args[3]);
                        break;
            }
        }

        if (cmd.hasOption("treatment")){
            createDataProvider(args[1]);
            List<Disease> dis = dataProvider.getAllDiseases();
            List<Drug> drug = dataProvider.getAllDrugs();
            Pet pet = dataProvider.getPetRecordByID(args[2]);
            dataProvider.animalTreatment(pet,dataProvider,dis,drug);
        }

        if (cmd.hasOption("expenses")){
            createDataProvider(args[1]);
            Pet pet = dataProvider.getPetRecordByID(args[2]);
            dataProvider.calculateExpensesAnimal(pet, dataProvider);
        }

        if (cmd.hasOption("selectFeed")){
            createDataProvider(args[1]);
            Pet pet = dataProvider.getPetRecordByID(args[2]);
            List<Feed> list = dataProvider.getAllFeeds();
            dataProvider.selectionFeed(pet, list, Boolean.parseBoolean(args[3]), Integer.parseInt(args[4]), dataProvider);
        }

        if (cmd.hasOption("selectVariant")){
            createDataProvider(args[1]);
            Pet pet = dataProvider.getPetRecordByID(args[2]);
            List<EnvironmentVariant> list = dataProvider.getAllEnvironmentVariants();
            dataProvider.selectionEnvironmentVariant(pet, list, Boolean.parseBoolean(args[3]), dataProvider);
        }

    }

    private static void createDataProvider(String dataProviderName) throws Exception {
        switch(dataProviderName){
            case "DB": dataProvider = new DataBaseProvider();break;
            case "CSV": dataProvider = new DataProviderCSV();break;
            case "XML": dataProvider = new DataProviderXML();break;
        }
    }
}