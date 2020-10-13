package org.yanwen.repository;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.yanwen.model.Brand;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BrandDaoImpl implements BrandDao {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Brand save(Brand brand) {
        Transaction transaction = null;
        Session session = sessionFactory.openSession();

        try{
            transaction = session.beginTransaction();
            session.save(brand);
            transaction.commit();
            session.close();
            return brand;

        }catch (Exception e){
            if(transaction != null) transaction.rollback();
            logger.error("fail to insert record",e);
            session.close();
            return null;
        }
    }

    @Override
    public List<Brand> getBrands() {
        String hql = "FROM Brand";
        Session s = sessionFactory.openSession();
        List<Brand> result = new ArrayList<>();

        try{
            Query query = s.createQuery(hql);
            result = query.list();
            s.close();
        }catch (HibernateException e){
            logger.error("session close exception try again...",e);
            s.close();
        }
        return result;
    }

    @Override
    public Brand getBy(long id) {
        String hql = "FROM Brand as b WHERE b.id = :Id";
        Session session = sessionFactory.openSession();
        try{
            Query<Brand> query = session.createQuery(hql);
            query.setParameter("Id",id);
            Brand result = query.uniqueResult();
            session.close();
            return result;
        }catch (HibernateException e){
            logger.error("failure to get data record",e);
            session.close();
            return null;}
    }

    @Override
    public Brand getBrandEagerBy(long id) {
        String hql = "FROM Brand as b LEFT JOIN FETCH b.products WHERE b.id=:Id";
        Session session = sessionFactory.openSession();
        try {
            Query<Brand> query = session.createQuery(hql);
            query.setParameter("Id",id);
            Brand result = query.uniqueResult();
            session.close();
            return result;
        }catch (HibernateException e){
            logger.error("failure to retrieve data record",e);
            session.close();
            return null;
        }
    }

    @Override
    public Brand update(Brand brand) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.saveOrUpdate(brand);
            transaction.commit();
            return brand;
        }catch (Exception e){
            if (transaction != null) transaction.rollback();
            logger.error("failure to update record", e.getMessage());
        }
        return null;
    }

    @Override
    public boolean delete(Brand brand) {
        String hql = "DELETE Brand as b WHERE b.id = :Id";
        int deletedCount = 0;
        Transaction transaction = null;
        Session session = sessionFactory.openSession();
        try{
            transaction = session.beginTransaction();
            Query<Brand> query = session.createQuery(hql);
            query.setParameter("Id",brand.getId());
            deletedCount = query.executeUpdate();
            transaction.commit();
            session.close();
            return deletedCount >= 1 ? true : false;
        }catch (HibernateException e){
            if (transaction != null) transaction.rollback();
            session.close();
            logger.error("unable to delete record",e);
        }
        return false;
    }
}
