package com.thefreshlystore.actions;

import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import com.thefreshlystore.controllers.BaseController;
import com.thefreshlystore.services.DeliveryAssociateService;
import com.thefreshlystore.utilities.Configurations;
import com.thefreshlystore.utilities.TheFreshlyStoreConstants.ApiRequestHeaders;

import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

public class DeliveryAssociateAuthentocationImpl extends Action<DeliveryAssociateAuthentocation> {
	private final BaseController baseController;
	private final Configurations configurations;
	private final DeliveryAssociateService deliveryAssociateService;
	
	@Inject
	public DeliveryAssociateAuthentocationImpl(BaseController baseController, Configurations configurations,
			DeliveryAssociateService deliveryAssociateService) {
		this.baseController = baseController;
		this.configurations = configurations;
		this.deliveryAssociateService = deliveryAssociateService;
	}
	
	@Override
	public CompletionStage<Result> call(Http.Context context) {
		try {
			String token = context.request().getHeader(ApiRequestHeaders.SESSION_TOKEN_HEADER);
			if(token == null) {
				return baseController.sessionInvalidPromise();
			}
			token = context.request().getHeader(ApiRequestHeaders.SESSION_TOKEN_HEADER);
			if(deliveryAssociateService.getByFieldName("token", token, false) == null) {
				return baseController.sessionInvalidPromise();
			}
			return delegate.call(context);
		} catch(Exception e) {
			return baseController.failureResponsePromise(e);
		}
	}
}
