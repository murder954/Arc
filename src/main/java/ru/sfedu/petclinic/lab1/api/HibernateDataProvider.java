package ru.sfedu.petclinic.lab1.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import ru.sfedu.petclinic.lab1.HibernateUtil;
import ru.sfedu.petclinic.model.Bird;
import ru.sfedu.petclinic.util.ConfigUtils;


import java.io.IOException;
import java.util.List;

import static ru.sfedu.petclinic.Constans.BIRD_DB;

public class HibernateDataProvider {

    Logger log = LogManager.getLogger(HibernateDataProvider.class);
    ConfigUtils con = new ConfigUtils();

    public void startHibernate() throws Exception{
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.close();
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
        HibernateUtil.shutdowm();
    }

    public List<Object> getListBird() throws IOException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        NativeQuery query = session.createNativeQuery("SELECT * FROM " + con.getConfigurationEntry(BIRD_DB));
        List<Object> result = query.list();
        for(Object bird : result){
            log.info(bird);
        }
        return result;
    }

    public List<String> getListTable(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        NativeQuery query = session.createNativeQuery("SHOW TABLES");
        List<String> result = query.list();
        for (String s : result){
            log.info(s);
        }
        return result;
    }

    public List<Bird> getInfoBird() throws IOException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        NativeQuery query = session.createNativeQuery("DESCRIBE "+con.getConfigurationEntry(BIRD_DB));
        List<Bird> result = query.list();
        for (Object s : result){
            log.info(s);
        }
        return result;
    }

    public List<String> getDataBases(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        NativeQuery query = session.createNativeQuery("SHOW DATABASES");
        List<String> result = query.list();
        for (String s : result){
            log.info(s);
        }
        return result;
    }


}
