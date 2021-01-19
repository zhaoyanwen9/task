package com.nz.test.service;

import com.nz.test.model.Role;
import com.nz.test.model.User;

public interface LoginService {

	User addUser(User user);

	Role addRole(Role role);

	User findByName(String name);
	
}