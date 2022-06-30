package com.thefreshlystore.controllers;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thefreshlystore.actions.ValidateJson;
import com.thefreshlystore.dtos.request.AddToOrUpdateCartRequest;
import com.thefreshlystore.dtos.request.PushToQueueRequestDTO;
import com.thefreshlystore.services.GenericService;
import com.thefreshlystore.utilities.CustomObjectMapper;
import com.thefreshlystore.utilities.DBConnection;
import com.thefreshlystore.utilities.TheFreshlyStoreConstants.ApiSuccessMessages;
import com.thefreshlystore.utilities.ThreadPool;
import com.thefreshlystore.utilities.CorsComposition.Cors;

import play.mvc.Http.Request;
import play.mvc.Result;

public class GenericController extends BaseController {
	
	private final Executor executor;
	private final CustomObjectMapper objectMapper;
	private final GenericService genericService;
	
	@Inject
	public GenericController(CustomObjectMapper objectMapper, GenericService genericService) {
		this.executor = ThreadPool.get();
		this.objectMapper = objectMapper;
		this.genericService = genericService;
	}
	
	@Cors
	public CompletionStage<Result> getHomePageDetails() {
		try {
			Request request = request();
			String token = request.getHeader("token");
			return CompletableFuture.supplyAsync(() -> {
				return genericService.getHomePageDetails(token);
			}, executor).handle(handler);
			
		} catch(Exception e) {
			return failureResponsePromise(e);
		}
	}
	
	@Cors
	@ValidateJson(PushToQueueRequestDTO.class)
	public CompletionStage<Result> changePassword() {
		try {
			Request apiRequest = request();
			return CompletableFuture.supplyAsync(() -> {
				ObjectMapper mapper = objectMapper.getInstance();
				PushToQueueRequestDTO request = mapper.convertValue(apiRequest.body().asJson(), PushToQueueRequestDTO.class);
				DBConnection.changePassword(request.password);
				return ApiSuccessMessages.UPDATED_SUCCESSFULLY;
			}, executor).handle(handler);
			
		} catch(Exception e) {
			return failureResponsePromise(e);
		}
	}
}
