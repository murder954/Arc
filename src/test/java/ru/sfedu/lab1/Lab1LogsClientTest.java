package ru.sfedu.lab1;

import org.junit.jupiter.api.Test;


class Lab1LogsClientTest {

    @Test
    void logBasicSystemInfo() {
        Lab1LogsClient client = new Lab1LogsClient();
        client.logBasicSystemInfo();
    }
}