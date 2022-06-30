package com.thefreshlystore.controllers;

import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thefreshlystore.actions.UserAuthentication;
import com.thefreshlystore.actions.ValidateJson;
import com.thefreshlystore.dtos.request.AddToOrUpdateCartRequest;
import com.thefreshlystore.exceptions.TheFreshlyStoreException;
import com.thefreshlystore.models.Items;
import com.thefreshlystore.services.CartItemsService;
import com.thefreshlystore.services.ItemsService;
import com.thefreshlystore.utilities.CustomObjectMapper;
import com.thefreshlystore.utilities.ThreadPool;
import com.thefreshlystore.utilities.CorsComposition.Cors;
import com.thefreshlystore.utilities.TheFreshlyStoreConstants.ApiFailureMessages;
import com.thefreshlystore.utilities.TheFreshlyStoreConstants.ApiSuccessMessages;

import play.mvc.Http.Request;
import play.mvc.Result;

public class CartController extends BaseController {
	private final Executor executor;
	private final CustomObjectMapper objectMapper;
	private final CartItemsService cartService;
	private final ItemsService itemsService;
	
	@Inject
	public CartController(CustomObjectMapper objectMapper, CartItemsService cartService, ItemsService itemsService) {
		this.executor = ThreadPool.get();
		this.objectMapper = objectMapper;
		this.cartService = cartService;
		this.itemsService = itemsService;
	}
	
	@Cors
	@UserAuthentication
	@ValidateJson(AddToOrUpdateCartRequest.class)
	public CompletionStage<Result> addToOrUpdateCart() {
		try {
			Request apiRequest = request();
			BigInteger userId = UserController.getLoggedInUserId();
			return CompletableFuture.supplyAsync(() -> {
				ObjectMapper mapper = objectMapper.getInstance();
				AddToOrUpdateCartRequest request = mapper.convertValue(apiRequest.body().asJson(), AddToOrUpdateCartRequest.class);
				Items item = itemsService.getByFieldName("item_id", request.itemId, false);
				if(item == null) throw new TheFreshlyStoreException(ApiFailureMessages.Cart.ITEM_CURRENTLY_NOT_AVAILABLE);
				cartService.addToOrUpdateCart(request, userId, item.getId());
				return ApiSuccessMessages.UPDATED_SUCCESSFULLY;
			}, executor).handle(handler);
			
		} catch(Exception e) {
			return failureResponsePromise(e);
		}
	}
	
	@Cors
	@UserAuthentication
	public CompletionStage<Result> getMyCart() {
		try {
			BigInteger userId = UserController.getLoggedInUserId();
			return CompletableFuture.supplyAsync(() -> {
				return cartService.getMyCart(userId);
			}, executor).handle(handler);
			
		} catch(Exception e) {
			return failureResponsePromise(e);
		}
	}
}
