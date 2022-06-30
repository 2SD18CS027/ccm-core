package com.thefreshlystore.dtos.response;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.ALWAYS)
public class HomePageDetailsResponse {
	public boolean isUserLoggedIn;
	public String name;
	public String mobileNumber;
	public String emailId;
	public AllItemsResponse allItemDetails;
	public int totalCartItems;
	public List<MyAddressesResponse.Addresses> userAddresses;
	public List<ServiceableAreas> serviceableAreas;
	
	public HomePageDetailsResponse(boolean isUserLoggedIn, String name, String mobileNumber, String emailId,
			AllItemsResponse allItemDetails, int totalCartItems, MyAddressesResponse userAddresses,
			List<HomePageDetailsResponse.ServiceableAreas> serviceableAreas) {
		this.isUserLoggedIn = isUserLoggedIn;
		this.name = name;
		this.mobileNumber = mobileNumber;
		this.emailId = emailId;
		this.allItemDetails = allItemDetails;
		this.totalCartItems = totalCartItems;
		if(userAddresses != null && userAddresses.addresses != null) {
			this.userAddresses = userAddresses.addresses;
		} else {
			this.userAddresses = new ArrayList<>();
		}
		this.serviceableAreas = serviceableAreas;
	}
	
	@JsonInclude(JsonInclude.Include.ALWAYS)
	public static class ServiceableAreas {
		public String city;
		public List<Areas> areas;
		
		public ServiceableAreas(String city) {
			this.city = city;
			this.areas = new ArrayList<>();
		}
	}
	
	@JsonInclude(JsonInclude.Include.ALWAYS)
	public static class Areas {
		public String area;
		public String areaId;
		
		public Areas(String area, String areaId) {
			this.area = area;
			this.areaId = areaId;
		}
	}
}
