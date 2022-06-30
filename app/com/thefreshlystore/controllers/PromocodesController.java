package com.thefreshlystore.controllers;

import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thefreshlystore.actions.AdminAuthentication;
import com.thefreshlystore.actions.UserAuthentication;
import com.thefreshlystore.actions.ValidateJson;
import com.thefreshlystore.dtos.request.CreatePromocodeRequest;
import com.thefreshlystore.exceptions.TheFreshlyStoreException;
import com.thefreshlystore.services.CartItemsService;
import com.thefreshlystore.services.PromocodesService;
import com.thefreshlystore.utilities.CorsComposition.Cors;
import com.thefreshlystore.utilities.CustomObjectMapper;
import com.thefreshlystore.utilities.TheFreshlyStoreConstants.ApiFailureMessages;
import com.thefreshlystore.utilities.TheFreshlyStoreConstants.ApiSuccessMessages;
import com.thefreshlystore.utilities.ThreadPool;

import play.mvc.Http.Request;
import play.mvc.Result;

public class PromocodesController extends BaseController {
	private final Executor executor;
	private final CustomObjectMapper objectMapper;
	private final PromocodesService promocodesService;
	private final CartItemsService cartItemsService;
	
	@Inject
	public PromocodesController(CustomObjectMapper objectMapper, PromocodesService promocodesService,
			CartItemsService cartItemsService) {
		this.executor = ThreadPool.get();
		this.objectMapper = objectMapper;
		this.promocodesService = promocodesService;
		this.cartItemsService = cartItemsService;
	}
	
	@Cors
	@AdminAuthentication
	@ValidateJson(CreatePromocodeRequest.class)
	public CompletionStage<Result> createPromocode() {
		try {
			Request apiRequest = request();
			return CompletableFuture.supplyAsync(() -> {
				ObjectMapper mapper = objectMapper.getInstance();
				CreatePromocodeRequest request = mapper.convertValue(apiRequest.body().asJson(), CreatePromocodeRequest.class);
				request.promocode = request.promocode.toUpperCase();
				promocodesService.createPromocode(request);
				return ApiSuccessMessages.UPDATED_SUCCESSFULLY;
			}, executor).handle(handler);
			
		} catch(Exception e) {
			return failureResponsePromise(e);
		}
	}
	
	@Cors
	@UserAuthentication
	public CompletionStage<Result> checkPromocode(String promocode) {
		try {
			BigInteger userId = UserController.getLoggedInUserId();
			return CompletableFuture.supplyAsync(() -> {
				if(promocode == null) {
					throw new TheFreshlyStoreException(ApiFailureMessages.Promocodes.PROMOCODE_NOT_FOUND);
				}
				Double totalPrice = cartItemsService.getMyCart(userId).totalPrice;
				return promocodesService.checkPromocode(promocode.toUpperCase(), userId, totalPrice);
			}, executor).handle(handler);
			
		} catch(Exception e) {
			return failureResponsePromise(e);
		}
	}
}
