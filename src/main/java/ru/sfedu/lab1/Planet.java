package ru.sfedu.lab1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.*;

import static ru.sfedu.lab1.Constans.*;

public class Planet {
    private static Logger log = LogManager.getLogger(Lab1LogsClient.class);
    //List<String> Planets = new ArrayList<String>();
    static ConfigUtils config = new ConfigUtils();


    public void nameOfPlanet() throws IOException {
        log.info("Планета: " + config.getConfigurationEntry(PLANET));
    }

    public void listOfPlanets() throws IOException {
        List<String> list = List.of(config.getConfigurationEntry(PLANETS).split(","));
        log.info("Планета: " + list);
    }

    public void nameOfMonth() throws IOException {
        Map<Integer, String> mapMonth = new HashMap<Integer, String>();
        String mon[] = config.getConfigurationEntry(MONTH).split(",");
        List.of(mon).forEach(m -> {
            int id;

            String value;
            String mass[] = m.split(":");
            id = Integer.parseInt(mass[0]);
            value = mass[1];
            mapMonth.put(id,value);
        });
        log.info("Месяцы: " + mapMonth);

    }
}


