package com.thefreshlystore.services;

import java.math.BigInteger;

import com.google.inject.ImplementedBy;
import com.thefreshlystore.dtos.request.CreatePromocodeRequest;
import com.thefreshlystore.dtos.response.CheckPromocodeResponse;
import com.thefreshlystore.models.Promocodes;

@ImplementedBy(PromocodesServiceImpl.class)
public interface PromocodesService extends BaseService<Promocodes> {
	public void createPromocode(CreatePromocodeRequest request);
	public CheckPromocodeResponse checkPromocode(String promocode, BigInteger userId, Double totalPrice);
	public void addEntryToPromocodeUsage(BigInteger userId, BigInteger promocodeId);
}
