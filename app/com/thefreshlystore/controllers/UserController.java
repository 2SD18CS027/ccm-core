package com.thefreshlystore.controllers;

import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thefreshlystore.actions.UserAuthentication;
import com.thefreshlystore.actions.ValidateJson;
import com.thefreshlystore.dtos.request.AddAddressRequestDTO;
import com.thefreshlystore.dtos.request.CaptureUserDetailsRequest;
import com.thefreshlystore.dtos.request.DeleteUserAddressRequest;
import com.thefreshlystore.dtos.request.EditUserAddressRequest;
import com.thefreshlystore.dtos.request.EditUserDetailsRequest;
import com.thefreshlystore.dtos.request.LoginOrCreateUserRequest;
import com.thefreshlystore.services.UsersService;
import com.thefreshlystore.utilities.CorsComposition.Cors;
import com.thefreshlystore.utilities.CustomObjectMapper;
import com.thefreshlystore.utilities.HandleSESBounces;
import com.thefreshlystore.utilities.TheFreshlyStoreConstants.ApiKeys;
import com.thefreshlystore.utilities.TheFreshlyStoreConstants.ApiSuccessMessages;
import com.thefreshlystore.utilities.ThreadPool;

import play.mvc.Http.Request;
import play.mvc.Result;

public class UserController extends BaseController {
	private final Executor executor;
	private final CustomObjectMapper objectMapper;
	private final UsersService usersService;
	private final HandleSESBounces handleSESBounces;
	
	@Inject
	public UserController(CustomObjectMapper objectMapper, UsersService usersService, 
			HandleSESBounces handleSESBounces) {
		this.executor = ThreadPool.get();
		this.objectMapper = objectMapper;
		this.usersService = usersService;
		this.handleSESBounces = handleSESBounces;
	}
	
	@Cors
	@ValidateJson(LoginOrCreateUserRequest.class)
	public CompletionStage<Result> loginOrCreateUser() {
		try {
			Request apiRequest = request();
			return CompletableFuture.supplyAsync(() -> {
				ObjectMapper mapper = objectMapper.getInstance();
				LoginOrCreateUserRequest request = mapper.convertValue(apiRequest.body().asJson(), LoginOrCreateUserRequest.class);
				return usersService.loginOrCreateUser(request);
			}, executor).handle(handler);
			
		} catch(Exception e) {
			return failureResponsePromise(e);
		}
	}
	
	@Cors
	@ValidateJson(CaptureUserDetailsRequest.class)
	public CompletionStage<Result> captureUserDetails() {
		try {
			Request apiRequest = request();
			return CompletableFuture.supplyAsync(() -> {
				ObjectMapper mapper = objectMapper.getInstance();
				CaptureUserDetailsRequest request = mapper.convertValue(apiRequest.body().asJson(), CaptureUserDetailsRequest.class);
				return usersService.captureUserDetails(request);
			}, executor).handle(handler);
			
		} catch(Exception e) {
			return failureResponsePromise(e);
		}
	}
	
	@Cors
	public CompletionStage<Result> logout(String token) {
		try {
			return CompletableFuture.supplyAsync(() -> {
				usersService.deleteUserSessionByToken(token);
				return ApiSuccessMessages.UPDATED_SUCCESSFULLY;
			}, executor).handle(handler);
			
		} catch(Exception e) {
			return successResponsePromise(ApiSuccessMessages.UPDATED_SUCCESSFULLY);
		}
	}
	
	@Cors
	@UserAuthentication
	@ValidateJson(AddAddressRequestDTO.class)
	public CompletionStage<Result> addAddress() {
		try {
			Request apiRequest = request();
			BigInteger userId = getLoggedInUserId();
			return CompletableFuture.supplyAsync(() -> {
				ObjectMapper mapper = objectMapper.getInstance();
				AddAddressRequestDTO request = mapper.convertValue(apiRequest.body().asJson(), AddAddressRequestDTO.class);
				return usersService.addAddress(request, userId);
			}, executor).handle(handler);
			
		} catch(Exception e) {
			return successResponsePromise(ApiSuccessMessages.UPDATED_SUCCESSFULLY);
		}
	}
	
	@Cors
	@UserAuthentication
	public CompletionStage<Result> getMyAddresses() {
		try {
			BigInteger userId = getLoggedInUserId();
			return CompletableFuture.supplyAsync(() -> {
				return usersService.getMyAddresses(userId);
			}, executor).handle(handler);
			
		} catch(Exception e) {
			return successResponsePromise(ApiSuccessMessages.UPDATED_SUCCESSFULLY);
		}
	}
	
	@Cors
	@UserAuthentication
	@ValidateJson(EditUserAddressRequest.class)
	public CompletionStage<Result> editUserAddress() {
		try {
			BigInteger userId = getLoggedInUserId();
			Request apiRequest = request();
			return CompletableFuture.supplyAsync(() -> {
				ObjectMapper mapper = objectMapper.getInstance();
				EditUserAddressRequest request = mapper.convertValue(apiRequest.body().asJson(), EditUserAddressRequest.class);
				usersService.editUserAddress(userId, request);
				return ApiSuccessMessages.UPDATED_SUCCESSFULLY;
			}, executor).handle(handler);
			
		} catch(Exception e) {
			return successResponsePromise(ApiSuccessMessages.UPDATED_SUCCESSFULLY);
		}
	}
	
	@Cors
	@UserAuthentication
	@ValidateJson(DeleteUserAddressRequest.class)
	public CompletionStage<Result> deleteUserAddress() {
		try {
			BigInteger userId = getLoggedInUserId();
			Request apiRequest = request();
			return CompletableFuture.supplyAsync(() -> {
				ObjectMapper mapper = objectMapper.getInstance();
				DeleteUserAddressRequest request = mapper.convertValue(apiRequest.body().asJson(), DeleteUserAddressRequest.class);
				usersService.deleteUserAddress(userId, request);
				return ApiSuccessMessages.UPDATED_SUCCESSFULLY;
			}, executor).handle(handler);
			
		} catch(Exception e) {
			return successResponsePromise(ApiSuccessMessages.UPDATED_SUCCESSFULLY);
		}
	}
	
	@Cors
	@UserAuthentication
	@ValidateJson(EditUserDetailsRequest.class)
	public CompletionStage<Result> editUserDetails() {
		try {
			BigInteger userId = getLoggedInUserId();
			Request apiRequest = request();
			return CompletableFuture.supplyAsync(() -> {
				ObjectMapper mapper = objectMapper.getInstance();
				EditUserDetailsRequest request = mapper.convertValue(apiRequest.body().asJson(), EditUserDetailsRequest.class);
				usersService.editUserDetails(userId, request);
				return ApiSuccessMessages.UPDATED_SUCCESSFULLY;
			}, executor).handle(handler);
			
		} catch(Exception e) {
			return successResponsePromise(ApiSuccessMessages.UPDATED_SUCCESSFULLY);
		}
	}
	
	@Cors
	public CompletionStage<Result> dummy() {
		try {
			return CompletableFuture.supplyAsync(() -> {
				//handleSESBounces.readMessage();
				return ApiSuccessMessages.UPDATED_SUCCESSFULLY;
			}, executor).handle(handler);
			
		} catch(Exception e) {
			return successResponsePromise(ApiSuccessMessages.UPDATED_SUCCESSFULLY);
		}
	}
	
	public static BigInteger getLoggedInUserId() {
		return (BigInteger) ctx().args.get(ApiKeys.USER_ID);
	}
}
