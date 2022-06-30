package com.thefreshlystore.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import play.data.validation.Constraints;

public class AssociateLoginRequest {
	@Constraints.Required
	@JsonProperty("mobileNumber")
	public String mobileNumber;
	
	@Constraints.Required
	@JsonProperty("password")
	public String password;
}
