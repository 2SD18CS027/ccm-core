package com.thefreshlystore.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.ImplementedBy;

@ImplementedBy(CustomObjectMapperImpl.class)
public interface CustomObjectMapper {
	ObjectMapper getInstance() ;
}