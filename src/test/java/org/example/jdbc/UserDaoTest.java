package org.example.jdbc;

import org.example.jdbc_model_dao.User;
import org.example.jdbc_model_dao.UserDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserDaoTest {

    private UserDao userJDBCDao;

    @Before
    public void setUp(){
        userJDBCDao = new UserDao();
    }

    @After
    public void tearDown(){
        userJDBCDao = null;
    }

    @Test
    public void getUserTest(){
        List<User> userList = userJDBCDao.getUsers();
        assertEquals(0, userList.size());
    }

}
