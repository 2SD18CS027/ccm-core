package com.thefreshlystore.dtos.response;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CheckPromocodeResponse {
	public Double discount;
	@JsonIgnore
	public BigInteger promocodeId;
	
	public CheckPromocodeResponse(Double discount, BigInteger promocodeId) {
		this.discount = discount;
		this.promocodeId = promocodeId;
	}
}
