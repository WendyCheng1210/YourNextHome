package org.yanwen.service;

import org.yanwen.model.User;
import org.yanwen.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public User save(User user){
        return userDao.save(user);
    }

    public User getByID(Long Id){
        return userDao.getByID(Id);
    };

    public User getUserByEmail(String email){
        return userDao.getUserByEmail(email);
    };

    public User getUserByCredentials(String email, String password){
        return userDao.getUserByCredentials(email,password);
    }

    public List<User> getAllUsers(){
        List<User> userList = userDao.getAllUsers();
        return userList;
    }
}
