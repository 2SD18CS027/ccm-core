package com.thefreshlystore.utilities;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Singleton
public class CustomObjectMapperImpl implements CustomObjectMapper {
	private ObjectMapper mapper;
	
	@Inject
	public CustomObjectMapperImpl(ObjectMapper mapper) {
		this.mapper = mapper;
		this.mapper.setSerializationInclusion(Include.NON_NULL);
		this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public ObjectMapper getInstance() {
		return mapper;
	}
}
