package com.thefreshlystore.daos;

import javax.inject.Inject;

import com.thefreshlystore.models.DeliveryAssociate;

public class DeliveryAssociateDAOImpl extends BaseDAOImpl<DeliveryAssociate> implements DeliveryAssociateDAO {
	
	@Inject
	public DeliveryAssociateDAOImpl() {
		super("id");
	}
}
