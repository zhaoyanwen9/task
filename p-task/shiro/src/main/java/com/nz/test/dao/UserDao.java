package com.nz.test.dao;

import com.nz.test.model.User;

public interface UserDao extends BaseDao<User, Long> {
	
	User findByName(String name);
	
}