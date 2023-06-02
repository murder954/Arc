package ru.sfedu.petclinic.lab5.many_to_many.api;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Test;
import ru.sfedu.petclinic.lab5.many_to_many.api.HibernateDataProvider;
import ru.sfedu.petclinic.lab5.many_to_many.model.Cat;
import ru.sfedu.petclinic.lab5.many_to_many.model.Fish;
import ru.sfedu.petclinic.lab5.many_to_many.model.Owner;
import ru.sfedu.petclinic.lab5.many_to_many.util.HibernateUtil;

import static org.junit.jupiter.api.Assertions.*;

class HibernateDataProviderTest {

    private static HibernateDataProvider hibernateDataProvider = new HibernateDataProvider();

    @Test
    void testPetRecord() throws Exception {
        Owner owner = new Owner("Viktor_Osimhen", "+79995554040", "napoli@sui.com", 4352743388483840L);
        Owner owner1 = new Owner("Elring_Haaland", "+79119899100", "perdun@city.com", 6838239349349433L);
        Fish nemo = new Fish("Nemo", "male", 0.8, "worms", 2, "lepidorthosis",  "sea");
        Cat cat = new Cat("Bebra", "female", 5.00, "snitch", 3, "spring", true, true);

        owner1.addPets(nemo);
        owner1.addPets(cat);
        owner.addPets(nemo);
        owner.addPets(cat);
        hibernateDataProvider.saveRecord(owner);
        hibernateDataProvider.saveRecord(owner1);
    }

    @Test
    void testNativeSQL() throws Exception{
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        String sql = "SELECT * FROM owners LEFT JOIN owners_pets ON owners.id = owners_pets.owner_id";
        long start = System.currentTimeMillis();
        session.createNativeQuery(sql).getResultList();
        long finish = System.currentTimeMillis();
        session.close();
        System.out.println(finish-start);//~34
    }

    @Test
    void testCriteriaQuery() throws Exception{
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Owner> criteriaQuery = builder.createQuery(Owner.class);
        Root<Owner> root = criteriaQuery.from(Owner.class);
        root.join("pets", JoinType.LEFT);

        criteriaQuery.select(root).where(builder.equal(root.get("id"), "580f48bd-cdf5-486d-b9bf-5f5aaa76716f"));

        long start = System.currentTimeMillis();
        session.createQuery(criteriaQuery.select(root)).getResultList();
        long finish = System.currentTimeMillis();
        session.close();
        System.out.println(finish-start);//~76
    }

    @Test
    void testHQL() throws Exception{
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        Query query = session.createQuery("FROM Owner o JOIN Pet p WHERE o.id = :id")
                .setParameter("id", "580f48bd-cdf5-486d-b9bf-5f5aaa76716f");

        long start = System.currentTimeMillis();
        query.list();
        long finish = System.currentTimeMillis();
        session.close();
        System.out.println(finish-start);//~66
    }

}