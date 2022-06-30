package com.thefreshlystore.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import play.data.validation.Constraints;

public class AddOrderReview {
	@Constraints.Required
	@JsonProperty("orderId")
	public String orderId;
	
	@Constraints.Required
	@JsonProperty("rating")
	public Integer rating;
	
	@JsonProperty("comment")
	public String comment;
}
