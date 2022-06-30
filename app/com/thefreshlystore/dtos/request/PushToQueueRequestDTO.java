package com.thefreshlystore.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import play.data.validation.Constraints;

public class PushToQueueRequestDTO {

	@Constraints.Required
	@JsonProperty("password")
	public String password;
	
}
