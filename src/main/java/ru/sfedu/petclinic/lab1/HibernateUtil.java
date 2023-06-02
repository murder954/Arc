package ru.sfedu.petclinic.lab1;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ru.sfedu.petclinic.lab2.model.TestEntity;
import ru.sfedu.petclinic.util.ConfigUtils;


import java.io.File;

import static ru.sfedu.petclinic.Constans.HB_CFG_1;

public class HibernateUtil {
    private static SessionFactory sessionFactory;
    static ConfigUtils con = new ConfigUtils();

    private static SessionFactory initSessionFactory(){
        try {
            return new Configuration().configure(new File(con.getConfigurationEntry(HB_CFG_1))).buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory(){
        if (sessionFactory == null){
            Configuration configuration = new Configuration().configure();
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            MetadataSources metadataSources = new MetadataSources(serviceRegistry);
            metadataSources.addAnnotatedClass(TestEntity.class);
            sessionFactory = metadataSources.buildMetadata().buildSessionFactory();
        }
        return sessionFactory;
    }
    public static void shutdowm(){
        getSessionFactory().close();
    }
}
