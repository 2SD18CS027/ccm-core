package com.thefreshlystore.services;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.thefreshlystore.dtos.response.AllItemsResponse;
import com.thefreshlystore.dtos.response.HomePageDetailsResponse;
import com.thefreshlystore.dtos.response.MyAddressesResponse;
import com.thefreshlystore.models.CartItems;

public class GenericServiceImpl implements GenericService {
	private final CartItemsService cartItemsService;
	private final UsersService usersService;
	private final ItemsService itemsService;
	private final ServiceableAreasService serviceableAreasService;
	
	@Inject
	public GenericServiceImpl(ServiceableAreasService serviceableAreasService, CartItemsService cartItemsService, 
			UsersService usersService, ItemsService itemsService) {
		this.serviceableAreasService = serviceableAreasService;
		this.cartItemsService = cartItemsService;
		this.usersService = usersService;
		this.itemsService = itemsService;
	}

	@Override
	public HomePageDetailsResponse getHomePageDetails(String token) {
//		UserSessions session = null;
//		if(token != null) {
//			session = usersService.getUserSessionByToken(token);
//		}
		boolean isUserLoggedIn = true;
		String name = "Bharat Tatti";
		String mobileNumber = "8904125737";
		String emailId = "2sd18cs027@gmail.com";
		AllItemsResponse allItemDetails = itemsService.getAllItems(false);
		MyAddressesResponse userAddresses = null;
		List<CartItems> cartItems = new ArrayList<>();
		
		List<HomePageDetailsResponse.ServiceableAreas> serviceableAreas = new ArrayList<HomePageDetailsResponse.ServiceableAreas>();
		List<HomePageDetailsResponse.Areas> areas = new ArrayList<HomePageDetailsResponse.Areas>();
		areas.add( new HomePageDetailsResponse.Areas("Vidyanagar", "123") );
		areas.add( new HomePageDetailsResponse.Areas("Navanagar", "1234") );
		HomePageDetailsResponse.ServiceableAreas serviceableArea = new HomePageDetailsResponse.ServiceableAreas("Hubli");
		serviceableArea.areas.addAll(areas);
		serviceableAreas.add( serviceableArea );
		return new HomePageDetailsResponse(isUserLoggedIn, name, mobileNumber, emailId, allItemDetails, cartItems.size(), userAddresses, serviceableAreas);
	}
}
