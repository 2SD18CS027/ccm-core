package com.thefreshlystore.controllers;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thefreshlystore.actions.AdminAuthentication;
import com.thefreshlystore.actions.ValidateJson;
import com.thefreshlystore.dtos.request.CreateAreaRequest;
import com.thefreshlystore.services.ServiceableAreasService;
import com.thefreshlystore.utilities.CorsComposition.Cors;
import com.thefreshlystore.utilities.CustomObjectMapper;
import com.thefreshlystore.utilities.TheFreshlyStoreConstants.ApiSuccessMessages;
import com.thefreshlystore.utilities.ThreadPool;

import play.mvc.Http.Request;
import play.mvc.Result;

public class ServiceableAreasController extends BaseController {
	
	private final Executor executor;
	private final CustomObjectMapper objectMapper;
	private final ServiceableAreasService serviceableAreasService;
	
	@Inject
	public ServiceableAreasController(CustomObjectMapper objectMapper, ServiceableAreasService serviceableAreasService) {
		this.executor = ThreadPool.get();
		this.objectMapper = objectMapper;
		this.serviceableAreasService = serviceableAreasService;
	}
	
	@Cors
	@AdminAuthentication
	@ValidateJson(CreateAreaRequest.class)
	public CompletionStage<Result> createArea() {
		try {
			Request apiRequest = request();
			return CompletableFuture.supplyAsync(() -> {
				ObjectMapper mapper = objectMapper.getInstance();
				CreateAreaRequest request = mapper.convertValue(apiRequest.body().asJson(), CreateAreaRequest.class);
				serviceableAreasService.createArea(request);
				return ApiSuccessMessages.UPDATED_SUCCESSFULLY;
			}, executor).handle(handler);
			
		} catch(Exception e) {
			return failureResponsePromise(e);
		}
	}
}
