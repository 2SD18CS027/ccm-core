package com.thefreshlystore.daos;

import java.math.BigInteger;

import javax.inject.Inject;

import com.thefreshlystore.models.UserSessions;

import io.ebean.Ebean;

public class UserSessionsDAOImpl extends BaseDAOImpl<UserSessions> implements UserSessionsDAO {
	
	@Inject
	public UserSessionsDAOImpl() {
		super("id");
	}

	@Override
	public UserSessions getUserSessionByUserIdAndDeviceTypeId(BigInteger userId, Integer deviceTypeId) {
		return Ebean.find(UserSessions.class).where().eq("user_id", userId).eq("device_type_id", deviceTypeId).findOne();
	}

	@Override
	public void deleteUserSession(String token) {
		Ebean.createUpdate(UserSessions.class, "delete from user_sessions where token=\"" + token + "\"").execute();
	}
}
