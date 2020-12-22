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
import org.yanwen.core.domain.Category;
import org.yanwen.core.repository.CategoryDao;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoryDaoImpl implements CategoryDao {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Category save(Category category) {
        Transaction transaction = null;
        Session session = sessionFactory.openSession();
        try{
            transaction = session.beginTransaction();
            session.save(category);
            transaction.commit();
            session.close();
            return category;
        }catch (Exception e){
            if(transaction != null) transaction.rollback();
            logger.error("fail to insert record", e);
            session.close();
            return null;
        }
    }

    @Override
    public List<Category> getCategories() {
        String hql = "From Category";
        Session session = sessionFactory.openSession();
        List<Category> result = new ArrayList<>();

        try{
            Query query = session.createQuery(hql);
            result = query.list();
            session.close();
        }catch (HibernateException e){
            logger.error("session close exception try again...",e);
            session.close();
        }
        return result;
    }

    @Override
    public Category getBy(long id) {
        String hql = "From Category as c Left Join Fetch c.products where c.id = :Id";
        Session session = sessionFactory.openSession();
        try{
            Query<Category> query = session.createQuery(hql);
            query.setParameter("Id",id);
            Category result = query.uniqueResult();
            session.close();
            return result;
        }catch (HibernateException e){
            logger.error("failure to retrieve data record",e);
            session.close();
            return null;
        }
    }

    @Override
    public Category getCategoryEagerBy(long id) {
        String hql = "From Category as c Left Join Fetch c.products where c.id = :Id";
        Session session = sessionFactory.openSession();
        try {
            Query<Category> query = session.createQuery(hql);
            query.setParameter("Id", id);
            Category result = query.uniqueResult();
            session.close();
            return result;
        } catch (HibernateException e) {
            logger.error("failure to retrieve data record", e);
            session.close();
            return null;
        }
    }

    @Override
    public Category update(Category category) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.saveOrUpdate(category);
            transaction.commit();
            return category;
        }catch (Exception e){
            if (transaction != null) transaction.rollback();
            logger.error("failure to update record", e.getMessage());
        }
        return null;
    }

    @Override
    public boolean delete(Category category) {
        String hql = "Delete Category as c where c.id = :Id";
        int deleteCount = 0;
        Transaction transaction = null;
        Session session = sessionFactory.openSession();
        try{
            transaction = session.beginTransaction();
            Query<Category> query = session.createQuery(hql);
            query.setParameter("Id", category.getId());
            deleteCount = query.executeUpdate();
            transaction.commit();
            session.close();
            return deleteCount >= 1 ? true : false;
        }catch (HibernateException e){
            if (transaction != null) transaction.rollback();
            session.close();
            logger.error("unable to delete record",e);
        }
        return false;
    }
}
