package com.company.dao;

import com.company.model.Role;

import java.util.List;

public interface RoleDao {

    List<String> getRoleNamesToList();
    Role getRoleByName(String name);
    List<Role> getAllRoles();
}
