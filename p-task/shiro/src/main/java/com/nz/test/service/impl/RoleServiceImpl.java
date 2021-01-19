package com.nz.test.service.impl;

import com.nz.test.dao.RoleDao;
import com.nz.test.model.Role;
import com.nz.test.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public Role findById(Long id) {
        return roleDao.findById(id).get();
    }

    @Override
    public List<Role> getAllRoles() {
        return roleDao.findAll();
    }
}
