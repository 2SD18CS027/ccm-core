package com.thefreshlystore.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.ALWAYS)
public class PlaceOrderResponse {
	public String orderId;
	
	public PlaceOrderResponse(String orderId) {
		this.orderId = orderId;
	}
}
