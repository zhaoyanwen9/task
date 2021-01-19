package com.nz.test.service;

import com.nz.test.model.Role;

import java.util.List;

public interface RoleService {

    Role findById(Long id);

    List<Role> getAllRoles();
}
