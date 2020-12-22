package org.yanwen.core.repository;

import org.yanwen.core.domain.Role;

import java.util.List;

public interface RoleDao {
    Role getRoleByName(String name);
    List<Role> getAllRoles();
}
