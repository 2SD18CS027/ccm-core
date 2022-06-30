package com.thefreshlystore.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.ALWAYS)
public class AddAddressResponse {
	public String addressId;
	
	public AddAddressResponse(String addressId) {
		this.addressId = addressId;
	}
}
