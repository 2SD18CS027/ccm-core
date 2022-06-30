package com.thefreshlystore.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import play.data.validation.Constraints;

public class AddDeliveryAssociateRequest {
	@Constraints.Required
	@JsonProperty("name")
	public String name;
	
	@Constraints.Required
	@JsonProperty("password")
	public String password;
	
	@Constraints.Required
	@JsonProperty("mobileNumber")
	public String mobileNumber;
}
