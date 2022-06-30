package com.thefreshlystore.dtos.response;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.ALWAYS)
public class MyAddressesResponse {
	public List<Addresses> addresses;
	
	public MyAddressesResponse() { this.addresses = new ArrayList<>(); }
	
	@JsonInclude(JsonInclude.Include.ALWAYS)
	public static class Addresses {
		public String addressId;
		public String address;
		public String area;
		public String areaId;
		public Boolean isActive;
		
		public Addresses () {}
		
		public Addresses(String addressId, String address) {
			this.addressId = addressId;
			this.address = address;
		}
		
	}
}
