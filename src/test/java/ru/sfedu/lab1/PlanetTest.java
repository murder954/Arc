package ru.sfedu.lab1;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class PlanetTest {

    @Test
    void nameOfPlanet() throws IOException {
        Planet planet = new Planet();
        planet.nameOfPlanet();
    }

    @Test
    void listOfPlanets() throws IOException{
        Planet planet = new Planet();
        planet.listOfPlanets();
    }

    @Test
    void nameOfMonth() throws IOException{
        Planet planet = new Planet();
        planet.nameOfMonth();
    }
}