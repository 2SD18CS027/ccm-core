package com.thefreshlystore.controllers;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thefreshlystore.actions.AdminAuthentication;
import com.thefreshlystore.actions.ValidateJson;
import com.thefreshlystore.dtos.request.CreateItemRequest;
import com.thefreshlystore.dtos.request.EditItemRequest;
import com.thefreshlystore.services.ItemsService;
import com.thefreshlystore.utilities.CorsComposition.Cors;
import com.thefreshlystore.utilities.CustomObjectMapper;
import com.thefreshlystore.utilities.TheFreshlyStoreConstants.ApiSuccessMessages;
import com.thefreshlystore.utilities.ThreadPool;

import play.mvc.Http.Request;
import play.mvc.Result;

public class ItemsController extends BaseController {
	private final Executor executor;
	private final CustomObjectMapper objectMapper;
	private final ItemsService itemsService;
	
	@Inject
	public ItemsController(Executor executor, CustomObjectMapper objectMapper, ItemsService itemsService) {
		this.executor = ThreadPool.get();
		this.objectMapper = objectMapper;
		this.itemsService = itemsService;
	}
	
	@Cors
	@AdminAuthentication
	@ValidateJson(CreateItemRequest.class)
	public CompletionStage<Result> createItem() {
		try {
			Request apiRequest = request();
			return CompletableFuture.supplyAsync(() -> {
				ObjectMapper mapper = objectMapper.getInstance();
				CreateItemRequest request = mapper.convertValue(apiRequest.body().asJson(), CreateItemRequest.class);
				return itemsService.createItem(request);
			}, executor).handle(handler);
			
		} catch(Exception e) {
			return failureResponsePromise(e);
		}
	}
	
	@Cors
	public CompletionStage<Result> getAllItems(Boolean isAll) {
		try {
			return CompletableFuture.supplyAsync(() -> {
				return itemsService.getAllItems(isAll);
			}, executor).handle(handler);
			
		} catch(Exception e) {
			return failureResponsePromise(e);
		}
	}
	
	@Cors
	@AdminAuthentication
	@ValidateJson(EditItemRequest.class)
	public CompletionStage<Result> editItem() {
		try {
			Request apiRequest = request();
			return CompletableFuture.supplyAsync(() -> {
				ObjectMapper mapper = objectMapper.getInstance();
				EditItemRequest request = mapper.convertValue(apiRequest.body().asJson(), EditItemRequest.class);
				itemsService.editItem(request);
				return ApiSuccessMessages.UPDATED_SUCCESSFULLY;
			}, executor).handle(handler);
			
		} catch(Exception e) {
			return failureResponsePromise(e);
		}
	}
}
