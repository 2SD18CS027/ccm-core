package com.thefreshlystore.controllers;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thefreshlystore.actions.DeliveryAssociateAuthentocation;
import com.thefreshlystore.actions.ValidateJson;
import com.thefreshlystore.dtos.request.AssociateLoginRequest;
import com.thefreshlystore.dtos.request.ChangeOrderStatusRequest;
import com.thefreshlystore.services.AdministratorService;
import com.thefreshlystore.services.DeliveryAssociateService;
import com.thefreshlystore.utilities.CorsComposition.Cors;
import com.thefreshlystore.utilities.CustomObjectMapper;
import com.thefreshlystore.utilities.TheFreshlyStoreConstants.ApiSuccessMessages;
import com.thefreshlystore.utilities.ThreadPool;

import play.mvc.Http.Request;
import play.mvc.Result;

public class DeliveryAssociateController extends BaseController {
	private final Executor executor;
	private final CustomObjectMapper objectMapper;
	private final DeliveryAssociateService deliveryAssociateService;
	private final AdministratorService administratorService;
	
	@Inject
	public DeliveryAssociateController(CustomObjectMapper objectMapper, DeliveryAssociateService deliveryAssociateService,
			AdministratorService administratorService) {
		this.executor = ThreadPool.get();
		this.objectMapper = objectMapper;
		this.deliveryAssociateService = deliveryAssociateService;
		this.administratorService = administratorService;
	}
	
	@Cors
	@ValidateJson(AssociateLoginRequest.class)
	public CompletionStage<Result> login() {
		try {
			Request apiRequest = request();
			return CompletableFuture.supplyAsync(() -> {
				ObjectMapper mapper = objectMapper.getInstance();
				AssociateLoginRequest request = mapper.convertValue(apiRequest.body().asJson(), AssociateLoginRequest.class);
				return deliveryAssociateService.login(request);
			}, executor).handle(handler);
			
		} catch(Exception e) {
			return failureResponsePromise(e);
		}
	}
	
	@Cors
	@DeliveryAssociateAuthentocation
	public CompletionStage<Result> getOrdersToDeliver() {
		try {
			return CompletableFuture.supplyAsync(() -> {
				return administratorService.getTodaysReport();
			}, executor).handle(handler);
			
		} catch(Exception e) {
			return failureResponsePromise(e);
		}
	}
	
	@Cors
	@DeliveryAssociateAuthentocation
	@ValidateJson(ChangeOrderStatusRequest.class)
	public CompletionStage<Result> updateOrderStatus() {
		try {
			Request apiRequest = request();
			return CompletableFuture.supplyAsync(() -> {
				ObjectMapper mapper = objectMapper.getInstance();
				ChangeOrderStatusRequest request = mapper.convertValue(apiRequest.body().asJson(), ChangeOrderStatusRequest.class);
				deliveryAssociateService.updateOrderStatus(request);
				return ApiSuccessMessages.UPDATED_SUCCESSFULLY;
			}, executor).handle(handler);
			
		} catch(Exception e) {
			return failureResponsePromise(e);
		}
	}
}
