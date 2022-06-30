package com.thefreshlystore.services;

import java.math.BigInteger;
import java.util.Date;

import javax.inject.Inject;

import com.thefreshlystore.daos.PromocodesDAO;
import com.thefreshlystore.daos.PromocodesUsageDAO;
import com.thefreshlystore.dtos.request.CreatePromocodeRequest;
import com.thefreshlystore.dtos.response.CheckPromocodeResponse;
import com.thefreshlystore.exceptions.TheFreshlyStoreException;
import com.thefreshlystore.models.Promocodes;
import com.thefreshlystore.models.PromocodesUsage;
import com.thefreshlystore.utilities.TheFreshlyStoreConstants.ApiFailureMessages;

public class PromocodesServiceImpl extends BaseServiceImpl<Promocodes> implements PromocodesService {
	
	private final PromocodesDAO promocodesDAO;
	private final PromocodesUsageDAO promocodesUsageDAO;
	
	@Inject
	public PromocodesServiceImpl(PromocodesDAO promocodesDAO, PromocodesUsageDAO promocodesUsageDAO) {
		super(promocodesDAO);
		this.promocodesDAO = promocodesDAO;
		this.promocodesUsageDAO = promocodesUsageDAO;
	}

	@Override
	public void createPromocode(CreatePromocodeRequest request) {
		Date currentDate = new Date();
		if(request.fromTime >= request.toTime) {
			throw new TheFreshlyStoreException(ApiFailureMessages.CANNOT_PERFORM_OPERATION);
		}
		if(request.toTime < currentDate.getTime()) {
			throw new TheFreshlyStoreException(ApiFailureMessages.CANNOT_PERFORM_OPERATION);
		}
		if(getByFieldName("promocode", request.promocode, false) != null) {
			throw new TheFreshlyStoreException(ApiFailureMessages.Promocodes.PROMOCODE_ALREADY_EXISTS);
		}
		save( new Promocodes(request.promocode, new Date(request.fromTime), new Date(request.toTime), request.isPercentage, 
				request.value, request.maxValue, request.reuseCount) );
	}

	@Override
	public CheckPromocodeResponse checkPromocode(String promocode, BigInteger userId, Double totalPrice) {
		Promocodes promocodeObj = getByFieldName("promocode", promocode, false);
		if(promocodeObj == null) {
			throw new TheFreshlyStoreException(ApiFailureMessages.Promocodes.PROMOCODE_NOT_FOUND);
		}
		long currentDate = new Date().getTime();
		if(promocodeObj.getFromTime().getTime() > currentDate) {
			throw new TheFreshlyStoreException(ApiFailureMessages.Promocodes.PROMOCODE_NOT_FOUND);
		}
		if(promocodeObj.getToTime().getTime() < currentDate) {
			throw new TheFreshlyStoreException(ApiFailureMessages.Promocodes.PROMOCODE_NOT_FOUND);
		}
		int promocodeUsageCount = promocodesUsageDAO.checkPromocodeUsageCount(promocodeObj.getId(), userId);
		if(promocodeUsageCount >= promocodeObj.getReuseCount()) {
			throw new TheFreshlyStoreException(ApiFailureMessages.Promocodes.PROMOCODE_ALREADY_USED);
		}
		Double discount = 0d;
		if(promocodeObj.getIsPercentage()) {
			discount = ((totalPrice * promocodeObj.getValue()) / 100);
			discount = (discount > promocodeObj.getMaxValue()) ? promocodeObj.getMaxValue() : discount;
		} else {
			discount = promocodeObj.getValue();
		}
		return new CheckPromocodeResponse(discount, promocodeObj.getId());
	}

	@Override
	public void addEntryToPromocodeUsage(BigInteger userId, BigInteger promocodeId) {
		promocodesUsageDAO.save( new PromocodesUsage(promocodeId, userId) );
	}
}
