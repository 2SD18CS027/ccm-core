package com.thefreshlystore.services;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;
import com.thefreshlystore.dtos.request.CreateAreaRequest;
import com.thefreshlystore.models.ServiceableAreas;

@ImplementedBy(ServiceableAreasServiceImpl.class)
public interface ServiceableAreasService extends BaseService<ServiceableAreas> {
	public void createArea(CreateAreaRequest request);
	public Map<BigInteger, ServiceableAreas> getServiceableAreasById(List<BigInteger> areaIds);
}
