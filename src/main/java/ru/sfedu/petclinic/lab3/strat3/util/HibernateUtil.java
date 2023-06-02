package ru.sfedu.petclinic.lab3.strat3.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ru.sfedu.petclinic.lab3.strat3.model.*;
import ru.sfedu.petclinic.util.ConfigUtils;

import java.io.File;
import java.io.IOException;

import static ru.sfedu.petclinic.Constans.HB_CFG_33;

public class HibernateUtil {
    private static SessionFactory sessionFactory;
    static ConfigUtils con = new ConfigUtils();

    private static String configPath;

    static {
        try {
            configPath = con.getConfigurationEntry(HB_CFG_33);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public HibernateUtil() throws IOException {
        String str = System.getProperty("hibernate");
        if (str != null){
            configPath = str;
        }
    }
    public static SessionFactory getSessionFactory(){
        if(sessionFactory == null){
            File file = new File(configPath);
            Configuration configuration = new Configuration();
            configuration.configure(file);
            configuration.addAnnotatedClass(Fish.class);
            configuration.addAnnotatedClass(Bird.class);
            configuration.addAnnotatedClass(Cat.class);
            configuration.addAnnotatedClass(Dog.class);
            configuration.addAnnotatedClass(Drug.class);
            configuration.addAnnotatedClass(Disease.class);
            configuration.addAnnotatedClass(EnvironmentVariant.class);
            configuration.addAnnotatedClass(Feed.class);
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }
        return sessionFactory;
    }
}
