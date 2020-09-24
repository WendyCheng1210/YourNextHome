package org.yanwen.service;

import org.yanwen.model.Role;
import org.yanwen.repository.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleDao roleDao;

    public Role getRoleByName(String name){return roleDao.getRoleByName(name);}

    public List<Role> getAllRoles(){
        List<Role> roleList = roleDao.getAllRoles();
        return roleList;
    }
}