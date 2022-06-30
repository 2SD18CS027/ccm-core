package com.thefreshlystore.daos;

import com.google.inject.ImplementedBy;
import com.thefreshlystore.models.UserTempSessions;

@ImplementedBy(UserTempSessionsDAOImpl.class)
public interface UserTempSessionsDAO extends BaseDAO<UserTempSessions> {

}
