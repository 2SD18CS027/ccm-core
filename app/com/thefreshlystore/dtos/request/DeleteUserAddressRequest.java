package com.thefreshlystore.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import play.data.validation.Constraints;

public class DeleteUserAddressRequest {
	@Constraints.Required
	@JsonProperty("addressId")
	public String addressId;
}
