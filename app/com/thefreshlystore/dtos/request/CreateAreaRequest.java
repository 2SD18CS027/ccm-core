package com.thefreshlystore.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import play.data.validation.Constraints;

public class CreateAreaRequest {
	@Constraints.Required
	@JsonProperty("city")
	public String city;
	
	@Constraints.Required
	@JsonProperty("area")
	public String area;
}
