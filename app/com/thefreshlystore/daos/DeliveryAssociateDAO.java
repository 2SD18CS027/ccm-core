package com.thefreshlystore.daos;

import com.google.inject.ImplementedBy;
import com.thefreshlystore.models.DeliveryAssociate;

@ImplementedBy(DeliveryAssociateDAOImpl.class)
public interface DeliveryAssociateDAO extends BaseDAO<DeliveryAssociate> {

}
