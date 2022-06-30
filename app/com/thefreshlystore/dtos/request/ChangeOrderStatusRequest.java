package com.thefreshlystore.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import play.data.validation.Constraints;

public class ChangeOrderStatusRequest {
	@Constraints.Required
	@JsonProperty("orderId")
	public String orderId;
	
	@Constraints.Required
	@Constraints.Min(2)
	@JsonProperty("newOrderStatus")
	public Integer newOrderStatus;
}
