package ru.sfedu.lab1;

import org.apache.log4j.Logger;

import java.io.IOException;

import static ru.sfedu.lab1.Constans.PLANET;
import static ru.sfedu.lab1.Planet.config;

public class Main {
    private static Logger log = Logger.getLogger(Lab1LogsClient.class);

    public static void main(String[] args) throws IOException {
        Planet pl = new Planet();
        pl.nameOfPlanet();
        pl.listOfPlanets();
        pl.nameOfMonth();
        log.info("Планета: " + ConfigUtils.getConfigurationEntry(PLANET));
    }
}
