package com.thefreshlystore.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import play.data.validation.Constraints;

public class DeleteItemRequest {
	@Constraints.Required
	@JsonProperty("itemId")
	public String itemId;
}
