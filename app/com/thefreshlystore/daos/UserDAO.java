package com.thefreshlystore.daos;

import com.google.inject.ImplementedBy;
import com.thefreshlystore.models.Users;

@ImplementedBy(UserDAOImpl.class)
public interface UserDAO extends BaseDAO<Users> {

}
