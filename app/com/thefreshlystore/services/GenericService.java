package com.thefreshlystore.services;

import com.google.inject.ImplementedBy;
import com.thefreshlystore.dtos.response.HomePageDetailsResponse;

@ImplementedBy(GenericServiceImpl.class)
public interface GenericService {
	public HomePageDetailsResponse getHomePageDetails(String token);
}
