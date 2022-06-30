package com.thefreshlystore.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import play.data.validation.Constraints;

public class AddAddressRequestDTO {
	@Constraints.Required
	@Constraints.MaxLength(300)
	@JsonProperty("address")
	public String address;
	
	@Constraints.Required
	@JsonProperty("areaId")
	public String areaId;
}
