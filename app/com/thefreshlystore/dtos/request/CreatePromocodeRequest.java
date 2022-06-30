package com.thefreshlystore.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import play.data.validation.Constraints;

public class CreatePromocodeRequest {
	@Constraints.Required
	@JsonProperty("promocode")
	public String promocode;
	
	@Constraints.Required
	@JsonProperty("fromTime")
	public Long fromTime;
	
	@Constraints.Required
	@JsonProperty("toTime")
	public Long toTime;
	
	@Constraints.Required
	@JsonProperty("isPercentage")
	public Boolean isPercentage;
	
	@Constraints.Required
	@JsonProperty("value")
	public  Double value;
	
	@Constraints.Required
	@JsonProperty("maxValue")
	public Double maxValue;
	
	@Constraints.Required
	@JsonProperty("reuseCount")
	public Integer reuseCount;
}
