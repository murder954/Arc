package ru.sfedu.petclinic.lab2.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.sfedu.petclinic.lab1.HibernateUtil;
import ru.sfedu.petclinic.lab1.api.HibernateDataProvider;
import ru.sfedu.petclinic.lab2.model.TestEntity;
import ru.sfedu.petclinic.util.ConfigUtils;

public class TestDataProvider {

    Logger log = LogManager.getLogger(HibernateDataProvider.class);
    ConfigUtils con = new ConfigUtils();

    public TestDataProvider() {
    }

    public void saveTestEntity(TestEntity entity) throws Exception {
        Session session;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        Transaction tx = session.beginTransaction();
        try {
            session.merge(entity);
            tx.commit();
            log.info("Entity has been saved");
        }catch (HibernateException ex) {
            throw new HibernateException(ex.getMessage());
        }
        session.close();
    }

    public void deleteTestEntity(Long id) throws Exception {
        Session session;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        Transaction tx = session.beginTransaction();
        TestEntity testEntity = session.get(TestEntity.class, id);
        if (testEntity != null) {
            session.delete(testEntity);
            tx.commit();
            log.info("Entity has been deleted");
        } else
            throw new Exception("Entity not found in data source");
        session.close();
    }

    public void updateTestEntity(TestEntity entity) throws Exception {
        Session session;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        Transaction tx = session.beginTransaction();
        try {
            session.saveOrUpdate(entity);
            tx.commit();
            log.info("Entity has been updated");
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
        session.close();
    }

    public void getTestEntity(Long id) throws Exception {
        Session session;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        TestEntity testEntity = session.get(TestEntity.class, id);
        if (testEntity != null) {
            log.info("Entity has been updated");
        } else
            throw new Exception("Entity not found in data source");
        session.close();
    }
}
