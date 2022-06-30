package com.thefreshlystore.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import play.data.validation.Constraints;

public class CreateItemRequest {
	@Constraints.Required
	@JsonProperty("name")
	public String name;
	
	@Constraints.Required
	@JsonProperty("price")
	public Double price;
	
	@Constraints.Required
	@JsonProperty("imageUrl")
	public String imageUrl;
	
	@Constraints.Required
	@JsonProperty("category")
	public String category;
	
	@Constraints.Required
	@JsonProperty("uom")
	public String uom;
}
