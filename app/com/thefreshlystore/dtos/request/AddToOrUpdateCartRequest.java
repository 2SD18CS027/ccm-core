package com.thefreshlystore.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import play.data.validation.Constraints;

public class AddToOrUpdateCartRequest {
	@Constraints.Required
	@JsonProperty("itemId")
	public String itemId;
	
	@Constraints.Required
	@JsonProperty("quantity")
	public Integer quantity;
	
	@Constraints.Required
	@JsonProperty("isDelete")
	public Boolean isDelete;
}
