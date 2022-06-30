package com.thefreshlystore.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import play.data.validation.Constraints;

public class ChangeItemAvailabilityStatusRequest {
	@Constraints.Required
	@JsonProperty("itemId")
	public String itemId;
	
	@Constraints.Required
	@JsonProperty("status")
	public Boolean status;
}
