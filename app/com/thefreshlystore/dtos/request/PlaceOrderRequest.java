package com.thefreshlystore.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import play.data.validation.Constraints;

public class PlaceOrderRequest {
	@Constraints.Required
	@JsonProperty("razorPaymentId")
	public String razorPaymentId;
	
	@Constraints.Required
	@JsonProperty("totalPrice")
	public Double totalPrice;
	
	@Constraints.Required
	@JsonProperty("addressId")
	public String addressId;
	
	@Constraints.Required
	@JsonProperty("fromSlotTime")
	public Long fromSlotTime;
	
	@Constraints.Required
	@JsonProperty("toSlotTime")
	public Long toSlotTime;
	
	@Constraints.Required
	@JsonProperty("areaId")
	public String areaId;
	
	@JsonProperty("promocode")
	public String promocode;
}
