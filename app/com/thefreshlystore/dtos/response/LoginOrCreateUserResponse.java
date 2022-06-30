package com.thefreshlystore.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.ALWAYS)
public class LoginOrCreateUserResponse {
	public String token;
	public boolean isUserDetailsCaptured;
	
	public LoginOrCreateUserResponse(String token, boolean isUserDetailsCaptured) {
		this.token = token; this.isUserDetailsCaptured = isUserDetailsCaptured;
	}
}
