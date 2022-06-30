package com.thefreshlystore.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import play.data.validation.Constraints;

public class AdministratorLoginRequest {
	@Constraints.Required
	@JsonProperty("username")
	public String username;
	
	@Constraints.Required
	@JsonProperty("password")
	public String password;
}
