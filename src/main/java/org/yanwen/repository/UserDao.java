package org.yanwen.repository;

import org.yanwen.model.User;

import java.util.List;

public interface UserDao {
    User save(User user);
    User getByID(Long Id);
    User getUserByEmail(String email);
    User getUserByCredentials(String email, String password);
    void delete(User u);
    List<User> getAllUsers();
}
