package com.thefreshlystore.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.ALWAYS)
public class AdministratorLoginResponse {
	public String token;
	
	public AdministratorLoginResponse(String token) {
		this.token = token;
	}
}
