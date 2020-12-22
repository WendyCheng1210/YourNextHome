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
import org.yanwen.core.domain.Order;
import org.yanwen.core.repository.OrderDao;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Order save(Order order) {
        Transaction transaction = null;
        Session session = sessionFactory.openSession();

        try{
            transaction = session.beginTransaction();
            session.save(order);
            transaction.commit();
            session.close();
            return order;

        }catch (Exception e){
            if(transaction != null) transaction.rollback();
            logger.error("fail to insert record",e);
            session.close();
            return null;
        }
    }

    @Override
    public List<Order> getOrders() {
        String hql = "FROM Order";
        Session s = sessionFactory.openSession();
        List<Order> result = new ArrayList<>();

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
    public Order getBy(long id) {
        String hql = "FROM Order as o WHERE o.id = :Id";
        Session session = sessionFactory.openSession();
        try{
            Query<Order> query = session.createQuery(hql);
            query.setParameter("Id",id);
            Order result = query.uniqueResult();
            session.close();
            return result;
        }catch (HibernateException e){
            logger.error("failure to get data record",e);
            session.close();
            return null;}
    }

    @Override
    public Order getOrderEagerBy(long id) {
        String hql = "FROM Order as o LEFT JOIN FETCH o.products WHERE o.id=:Id";
        Session session = sessionFactory.openSession();
        try {
            Query<Order> query = session.createQuery(hql);
            query.setParameter("Id",id);
            Order result = query.uniqueResult();
            session.close();
            return result;
        }catch (HibernateException e){
            logger.error("failure to retrieve data record",e);
            session.close();
            return null;
        }
    }

    @Override
    public Order update(Order order) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.saveOrUpdate(order);
            transaction.commit();
            return order;
        }catch (Exception e){
            if (transaction != null) transaction.rollback();
            logger.error("failure to update record", e.getMessage());
        }
        return null;
    }

    @Override
    public boolean delete(Order order) {
        String hql = "DELETE Order as o WHERE o.id = :Id";
        int deletedCount = 0;
        Transaction transaction = null;
        Session session = sessionFactory.openSession();
        try{
            transaction = session.beginTransaction();
            Query<Order> query = session.createQuery(hql);
            query.setParameter("Id",order.getId());
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
