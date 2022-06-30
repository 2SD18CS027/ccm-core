package com.thefreshlystore.actions;

import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import com.thefreshlystore.controllers.BaseController;
import com.thefreshlystore.services.AdministratorService;
import com.thefreshlystore.utilities.Configurations;
import com.thefreshlystore.utilities.TheFreshlyStoreConstants.ApiRequestHeaders;

import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

public class AdminAuthenticationImpl extends Action<AdminAuthentication> {
	private final BaseController baseController;
	private final Configurations configurations;
	private final AdministratorService administratorService;
	
	@Inject
	public AdminAuthenticationImpl(BaseController baseController, Configurations configurations,
			AdministratorService administratorService) {
		this.baseController = baseController;
		this.configurations = configurations;
		this.administratorService = administratorService;
	}
	
	@Override
	public CompletionStage<Result> call(Http.Context context) {
		try {
			String token = context.request().getHeader(ApiRequestHeaders.SECRET_SESSION_TOKEN_HEADER);
			if(token == null) {
				return baseController.sessionInvalidPromise();
			}
			if(!token.equals(configurations.ADMIN_KEY)) {
				return baseController.sessionInvalidPromise();
			}
			token = context.request().getHeader(ApiRequestHeaders.SESSION_TOKEN_HEADER);
			administratorService.getByFieldName("token", token, true);
			return delegate.call(context);
		} catch(Exception e) {
			return baseController.failureResponsePromise(e);
		}
	}
}
