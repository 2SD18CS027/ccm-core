package com.thefreshlystore.daos;

import javax.inject.Inject;

import com.thefreshlystore.models.UserTempSessions;

public class UserTempSessionsDAOImpl extends BaseDAOImpl<UserTempSessions> implements UserTempSessionsDAO {
	
	@Inject
	public UserTempSessionsDAOImpl() {
		super("id");
	}
}
