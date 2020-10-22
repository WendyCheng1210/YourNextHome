package org.yanwen.jdbc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.yanwen.jdbc.UserJDBC;
import org.yanwen.jdbc.UserJDBCDao;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserJDBCDaoTest {

    private UserJDBCDao userJDBCDao;

    @Before
    public void setUp(){
        userJDBCDao = new UserJDBCDao();
    }

    @After
    public void tearDown(){
        userJDBCDao = null;
    }

    @Test
    public void getUserTest(){
        List<UserJDBC> userJDBCList = userJDBCDao.getUsers();
        assertEquals(0, userJDBCList.size());
    }

}
