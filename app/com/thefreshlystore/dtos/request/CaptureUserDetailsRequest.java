package com.thefreshlystore.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import play.data.validation.Constraints;

public class CaptureUserDetailsRequest {
	@Constraints.Required
	@Constraints.MaxLength(20)
	@Constraints.MinLength(3)
	@JsonProperty("name")
	public String name;
	
	@Constraints.Required
	@Constraints.MaxLength(40)
	@Constraints.MinLength(13)
	@Constraints.Email
	@JsonProperty("email")
	public String email;
	
	@Constraints.Required
	@JsonProperty("tempToken")
	public String tempToken;
	
	@Constraints.Required
	@Constraints.Min(1)
	@Constraints.Max(1)
	@JsonProperty("deviceTypeId")
	public Integer deviceTypeId;
}
