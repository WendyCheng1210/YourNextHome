package org.yanwen.repository;

import org.yanwen.model.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public Role getRoleByName(String name) {
        String hql = "From Role as r where lower(r.name) = :name";
        try(Session session = sessionFactory.openSession()){
            Query<Role> query = session.createQuery(hql);
            query.setParameter("name", name.toLowerCase());
            return query.uniqueResult();
        }
    }

    @Override
    public List<Role> getAllRoles() {
        String hql = "From Role";
        try(Session s = sessionFactory.openSession()){
            Query<Role> query= s.createQuery(hql);
            return query.list();
        }

    }

}
