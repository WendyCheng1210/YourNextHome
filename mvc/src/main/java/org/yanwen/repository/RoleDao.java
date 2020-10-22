package org.yanwen.repository;

import org.yanwen.model.Role;

import java.util.List;

public interface RoleDao {
    Role getRoleByName(String name);
    List<Role> getAllRoles();
}
