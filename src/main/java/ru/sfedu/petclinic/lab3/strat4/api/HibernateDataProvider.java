package ru.sfedu.petclinic.lab3.strat4.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import ru.sfedu.petclinic.api.DataBaseProvider;
import ru.sfedu.petclinic.lab3.strat4.model.Pet;
import ru.sfedu.petclinic.lab3.strat4.util.HibernateUtil;
import ru.sfedu.petclinic.util.ConfigUtils;

import java.util.List;

import static ru.sfedu.petclinic.Constans.BIRD_DB;

public class HibernateDataProvider {

    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private Session session;
    private Transaction transaction;

    ConfigUtils con = new ConfigUtils();

    Logger log = LogManager.getLogger(DataBaseProvider.class);

    public void saveRecord(Object object) throws Exception {
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        session.save(object);
        transaction.commit();
        session.close();
    }

    public void deleteRecord(Object object) throws Exception {
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        session.delete(object);
        transaction.commit();
        session.close();
    }

    public void updateRecord(Object object) throws Exception {
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        session.update(object);
        transaction.commit();
        session.close();
    }
    public Object getRecord(Class cl, String id) throws Exception {
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        return session.get(cl, id);
    }

    public List getAll() throws Exception {
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        Query query = session.createQuery("FROM Pet", Pet.class);
        List<Pet> result = query.list();
        for(Object bird : result){
            log.info(bird);
        }
        return result;
    }
}