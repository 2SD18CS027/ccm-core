package com.thefreshlystore.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import play.data.validation.Constraints;

public class EditItemRequest {
	@Constraints.Required
	@JsonProperty("itemId")
	public String itemId;
	
	@JsonProperty("name")
	public String name;
	
	@JsonProperty("price")
	public Double price;
	
	@JsonProperty("imageUrl")
	public String imageUrl;
	
	@JsonProperty("category")
	public String category;
	
	@JsonProperty("uom")
	public String uom;
	
	@JsonProperty("isAvailable")
	public Boolean isAvailable;
}
