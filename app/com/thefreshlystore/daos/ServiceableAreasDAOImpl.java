package com.thefreshlystore.daos;

import javax.inject.Inject;

import com.thefreshlystore.models.ServiceableAreas;

public class ServiceableAreasDAOImpl extends BaseDAOImpl<ServiceableAreas> implements ServiceableAreasDAO {
	
	@Inject
	public ServiceableAreasDAOImpl() {
		super("id");
	}
}
