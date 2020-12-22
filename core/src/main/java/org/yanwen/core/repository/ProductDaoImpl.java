package org.yanwen.core.repository;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.yanwen.core.domain.Product;
import org.yanwen.core.repository.ProductDao;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductDaoImpl implements ProductDao {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Product save(Product product) {
        Transaction transaction = null;
        Session session = sessionFactory.openSession();

        try{
            transaction = session.beginTransaction();
            session.save(product);
            transaction.commit();
            session.close();
            return product;

        }catch (Exception e){
            if(transaction != null) transaction.rollback();
            logger.error("fail to insert record",e);
            session.close();
            return null;
        }
    }

    @Override
    public List<Product> getProducts() {
        String hql = "From Product";
        Session s = sessionFactory.openSession();
        List<Product> result = new ArrayList<>();

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
    public Product getBy(long id) {
        String hql = "From Product as p where p.id = :Id";
        Session session = sessionFactory.openSession();
        try{
            Query<Product> query = session.createQuery(hql);
            query.setParameter("Id",id);
            Product result = query.uniqueResult();
            session.close();
            return result;
        }catch (HibernateException e){
            logger.error("failure to get data record",e);
            session.close();
            return null;}
    }

    @Override
    public Product update(Product product) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.saveOrUpdate(product);
            transaction.commit();
            return product;
        }catch (Exception e){
            if (transaction != null) transaction.rollback();
            logger.error("failure to update record", e.getMessage());
        }
        return null;
    }

    @Override
    public boolean delete(Product product) {
        String hql = "DELETE Product as p where p.id = :Id";
        int deletedCount = 0;
        Transaction transaction = null;
        Session session = sessionFactory.openSession();
        try{
            transaction = session.beginTransaction();
            Query<Product> query = session.createQuery(hql);
            query.setParameter("Id",product.getId());
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
