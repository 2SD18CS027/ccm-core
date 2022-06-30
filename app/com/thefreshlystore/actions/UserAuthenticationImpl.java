package com.thefreshlystore.actions;

import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import com.thefreshlystore.controllers.BaseController;
import com.thefreshlystore.models.UserSessions;
import com.thefreshlystore.services.UsersService;
import com.thefreshlystore.utilities.TheFreshlyStoreConstants.ApiKeys;
import com.thefreshlystore.utilities.TheFreshlyStoreConstants.ApiRequestHeaders;

import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

public class UserAuthenticationImpl extends Action<UserAuthentication> {
	private final BaseController baseController;
	private final UsersService usersService;
	
	@Inject
	public UserAuthenticationImpl(BaseController baseController, UsersService usersService) {
		this.baseController = baseController;
		this.usersService = usersService;
	}
	
	@Override
	public CompletionStage<Result> call(Http.Context context) {
		try {
			String token = context.request().getHeader(ApiRequestHeaders.SESSION_TOKEN_HEADER);
			if(token == null) {
				return baseController.sessionInvalidPromise();
			}
			
			if(!context.request().hasHeader(ApiRequestHeaders.DEVICE_TYPE_ID )) {
				return baseController.sessionInvalidPromise();
			}
			
			Integer deviceTypeId = 0;
			try {
				deviceTypeId = Integer.parseInt( context.request().getHeader(ApiRequestHeaders.DEVICE_TYPE_ID ) );
			} catch(Exception e) {
				return baseController.sessionInvalidPromise();
			}
			
			UserSessions session = usersService.getUserSessionByToken(token);
			if(session == null || (session != null && deviceTypeId != session.getDeviceTypeId())) {
				return baseController.sessionInvalidPromise();
			}
			
			context.args.put(ApiKeys.USER_ID, session.getUserId());
			context.args.put(ApiKeys.USER_SESSION, session);
			return delegate.call(context);
		} catch(Exception e) {
			return baseController.failureResponsePromise(e);
		}
	}
}
