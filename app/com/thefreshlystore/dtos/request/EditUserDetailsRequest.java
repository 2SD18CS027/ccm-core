package com.thefreshlystore.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EditUserDetailsRequest {
	@JsonProperty("name")
	public String name;
	
	@JsonProperty("email")
	public String email;
}
