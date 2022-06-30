package com.thefreshlystore.daos;

import com.google.inject.ImplementedBy;
import com.thefreshlystore.models.ServiceableAreas;

@ImplementedBy(ServiceableAreasDAOImpl.class)
public interface ServiceableAreasDAO extends BaseDAO<ServiceableAreas> {

}
