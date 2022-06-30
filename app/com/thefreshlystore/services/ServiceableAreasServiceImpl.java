package com.thefreshlystore.services;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.thefreshlystore.daos.ServiceableAreasDAO;
import com.thefreshlystore.dtos.request.CreateAreaRequest;
import com.thefreshlystore.models.ServiceableAreas;

public class ServiceableAreasServiceImpl extends BaseServiceImpl<ServiceableAreas> implements ServiceableAreasService {
	
	private final ServiceableAreasDAO serviceableAreasDAO;
	
	@Inject
	public ServiceableAreasServiceImpl(ServiceableAreasDAO serviceableAreasDAO) {
		super(serviceableAreasDAO);
		this.serviceableAreasDAO = serviceableAreasDAO;
	}

	@Override
	public void createArea(CreateAreaRequest request) {
		ServiceableAreas serviceableArea = new ServiceableAreas(request.city, request.area);
		serviceableAreasDAO.save(serviceableArea);
	}

	@Override
	public Map<BigInteger, ServiceableAreas> getServiceableAreasById(List<BigInteger> areaIds) {
		Map<BigInteger, ServiceableAreas> result = new HashMap<>();
		getByFieldName("id", areaIds, null, null).forEach((record) -> {
			result.put(record.getId(), record);
		});
		return result;
	}
}
