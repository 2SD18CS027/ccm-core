package com.thefreshlystore.daos;

import javax.inject.Inject;

import com.thefreshlystore.models.Administrator;

public class AdministratorDAOImpl extends BaseDAOImpl<Administrator> implements AdministratorDAO {

	@Inject
	public AdministratorDAOImpl() {
		super("id");
	}
}
