package ru.sfedu.lab1;

import org.apache.log4j.Logger;

public class Lab1LogsClient {

    private static Logger log = Logger.getLogger(Lab1LogsClient.class);

    public void Lab1LogsClient(){
        log.debug("Lab1LogsClient[0]: starting application.........");
    }

    public void logBasicSystemInfo() {
        log.info("Launching the application...");
        log.info("Operating System: " + System.getProperty("os.name") + " " + System.getProperty("os.version"));
        log.info("JRE: " + System.getProperty("java.version"));
        log.info("Java Launched From: " + System.getProperty("java.home"));
        log.info("Class Path: " + System.getProperty("java.class.path"));
        log.info("Library Path: " + System.getProperty("java.library.path"));
        log.info("User Home Directory: " + System.getProperty("user.home"));
        log.info("User Working Directory: " + System.getProperty("user.dir"));
        log.info("Test INFO logging.");
        log.warn("Test warn: ");
        log.error("Test error: ");
        log.debug("Test debug: ");
    }

}
