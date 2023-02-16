package ru.sfedu.myApp;

import org.junit.jupiter.api.Test;
import ru.sfedu.myApp.Entity.Cat;
import ru.sfedu.myApp.Entity.Feed;
import ru.sfedu.myApp.Entity.Owner;

class LoggingBeansTest {

    @Test
    public void testMongoDB() throws Exception{
        LoggingBeans logToHistory = new LoggingBeans();

        Cat test_hist1 = new Cat("Kuzia", "male", 6.1, "porridge", "cat", 2, "break paw", false, true);
        logToHistory.logObjectChange(test_hist1, "testPetHistory", test_hist1.getId());

        Owner test_hist2 = new Owner("Aleksandr_Goydenko", "+79185115496", "takto7288@mail.ru", 7283910364571821L);
        logToHistory.logObjectChange(test_hist2, "testOwnerHistory", test_hist2.getId());

        Feed test_hist3 = new Feed("porridge", 78.99, 500.00, "cat");
        logToHistory.logObjectChange(test_hist3, "testFeedHistory", test_hist3.getId());
    }
}