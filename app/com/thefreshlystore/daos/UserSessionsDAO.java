package com.thefreshlystore.daos;

import java.math.BigInteger;

import com.google.inject.ImplementedBy;
import com.thefreshlystore.models.UserSessions;

@ImplementedBy(UserSessionsDAOImpl.class)
public interface UserSessionsDAO extends BaseDAO<UserSessions> {
	public UserSessions getUserSessionByUserIdAndDeviceTypeId(BigInteger userId, Integer deviceTypeId);
	public void deleteUserSession(String token);
}
