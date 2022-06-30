package com.thefreshlystore.controllers;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thefreshlystore.actions.AdminAuthentication;
import com.thefreshlystore.actions.DeliveryAssociateAuthentocation;
import com.thefreshlystore.actions.ValidateJson;
import com.thefreshlystore.dtos.request.AddDeliveryAssociateRequest;
import com.thefreshlystore.dtos.request.AdministratorLoginRequest;
import com.thefreshlystore.dtos.request.ChangeOrderStatusRequest;
import com.thefreshlystore.services.AdministratorService;
import com.thefreshlystore.utilities.CustomObjectMapper;
import com.thefreshlystore.utilities.PasswordUtility;
import com.thefreshlystore.utilities.ThreadPool;
import com.thefreshlystore.utilities.CorsComposition.Cors;
import com.thefreshlystore.utilities.TheFreshlyStoreConstants.ApiSuccessMessages;

import play.mvc.Http.Request;
import play.mvc.Result;

public class AdministratorController extends BaseController {
	private final Executor executor;
	private final CustomObjectMapper objectMapper;
	private final AdministratorService administratorService;
	private final PasswordUtility passwordUtility;
	
	@Inject
	public AdministratorController(CustomObjectMapper objectMapper, AdministratorService administratorService,
			PasswordUtility passwordUtility) {
		this.executor = ThreadPool.get();
		this.objectMapper = objectMapper;
		this.administratorService = administratorService;
		this.passwordUtility = passwordUtility;
	}
	
	@Cors
	@ValidateJson(AdministratorLoginRequest.class)
	public CompletionStage<Result> administratorLogin() {
		try {
			Request apiRequest = request();
			return CompletableFuture.supplyAsync(() -> {
				ObjectMapper mapper = objectMapper.getInstance();
				AdministratorLoginRequest request = mapper.convertValue(apiRequest.body().asJson(), AdministratorLoginRequest.class);
				return administratorService.login(request, passwordUtility);
			}, executor).handle(handler);
			
		} catch(Exception e) {
			return failureResponsePromise(e);
		}
	}
	
	@Cors
	@AdminAuthentication
	public CompletionStage<Result> getTodaysReport() {
		try {
			return CompletableFuture.supplyAsync(() -> {
				return administratorService.getTodaysReport();
			}, executor).handle(handler);
			
		} catch(Exception e) {
			return failureResponsePromise(e);
		}
	}
	
	@Cors
	@AdminAuthentication
	@ValidateJson(AddDeliveryAssociateRequest.class)
	public CompletionStage<Result> addDeliveryAssociate() {
		try {
			Request apiRequest = request();
			return CompletableFuture.supplyAsync(() -> {
				ObjectMapper mapper = objectMapper.getInstance();
				AddDeliveryAssociateRequest request = mapper.convertValue(apiRequest.body().asJson(), AddDeliveryAssociateRequest.class);
				administratorService.addDeliveryAssociate(request);
				return ApiSuccessMessages.UPDATED_SUCCESSFULLY;
			}, executor).handle(handler);
			
		} catch(Exception e) {
			return failureResponsePromise(e);
		}
	}
	
	@Cors
	@AdminAuthentication
	@ValidateJson(ChangeOrderStatusRequest.class)
	public CompletionStage<Result> updateOrderStatus() {
		try {
			Request apiRequest = request();
			return CompletableFuture.supplyAsync(() -> {
				ObjectMapper mapper = objectMapper.getInstance();
				ChangeOrderStatusRequest request = mapper.convertValue(apiRequest.body().asJson(), ChangeOrderStatusRequest.class);
				administratorService.updateOrderStatus(request);
				return ApiSuccessMessages.UPDATED_SUCCESSFULLY;
			}, executor).handle(handler);
			
		} catch(Exception e) {
			return failureResponsePromise(e);
		}
	}
}
