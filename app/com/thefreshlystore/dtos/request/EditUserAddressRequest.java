package com.thefreshlystore.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import play.data.validation.Constraints;

public class EditUserAddressRequest {
	@Constraints.Required
	@JsonProperty("address")
	public String address;
	
	@Constraints.Required
	@JsonProperty("addressId")
	public String addressId;
}
