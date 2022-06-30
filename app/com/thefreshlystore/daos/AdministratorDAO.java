package com.thefreshlystore.daos;

import com.google.inject.ImplementedBy;
import com.thefreshlystore.models.Administrator;

@ImplementedBy(AdministratorDAOImpl.class)
public interface AdministratorDAO extends BaseDAO<Administrator> {

}
