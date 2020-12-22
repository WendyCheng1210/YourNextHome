package org.yanwen.core.repository;

import org.yanwen.core.domain.User;

import java.util.List;

public interface UserDao {
    User save(User user);
    User getByID(Long Id);
    User getUserByEmail(String email);
    User getEagerBy(Long Id);
    User getUserByCredentials(String email, String password);
    void delete(User u);
    List<User> getAllUsers();
}
