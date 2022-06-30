package com.thefreshlystore.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import play.data.validation.Constraints;

public class LoginOrCreateUserRequest {
	@Constraints.Required
	@JsonProperty("mobileNumber")
	public String mobileNumber;
	
	@Constraints.Required
	@JsonProperty("deviceTypeId")
	public Integer deviceTypeId;
	
	@Constraints.Required
	@JsonProperty("authCode")
	public String authCode;
}
