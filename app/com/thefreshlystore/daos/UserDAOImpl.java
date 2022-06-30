package com.thefreshlystore.daos;

import javax.inject.Inject;

import com.thefreshlystore.models.Users;

public class UserDAOImpl extends BaseDAOImpl<Users> implements UserDAO {
	
	@Inject
	public UserDAOImpl() {
		super("id");
	}
}
