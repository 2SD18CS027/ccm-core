package com.thefreshlystore.controllers;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.BiFunction;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thefreshlystore.exceptions.TheFreshlyStoreException;
import com.thefreshlystore.utilities.Languages;
import com.thefreshlystore.utilities.CorsComposition.Cors;
import com.thefreshlystore.utilities.TheFreshlyStoreConstants.ApiFailureMessages;
import com.thefreshlystore.utilities.TheFreshlyStoreConstants.ApiResponseKeys;

import play.api.Play;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class BaseController extends Controller {
	public CompletionStage<Result> successResponsePromise(String object) {
		Languages languages = Play.current().injector().instanceOf(Languages.class);
		String message = languages.getLocalizedString(object);
		ObjectNode result = Json.newObject();
		result.put(ApiResponseKeys.MESSAGE, object);

		return promise(successResponse(result));
	}
	
	@Cors
	public  Result preflight(String path) {
		return ok("");
	}
	
	public CompletionStage<Result> successResponsePromise(Object result) {
		return promise(successResponse(result));
	}
	
	public CompletionStage<Result> failureResponsePromise(Exception e) {
		return promise(failureResponse(e));
	}
	
	public CompletionStage<Result> failureResponsePromise(Throwable e) {
		return promise(failureResponse(e));
	}
	
	public CompletionStage<Result> sessionInvalidPromise() {
		return promise(invalidSession());
	}
	
	private Result successResponse(Object object) {
		return ok(json(object));
	}
	
	private JsonNode json(Object data) {
		return Json.toJson(data);
	}
	
	private CompletionStage<Result> promise(Result result) {
		return CompletableFuture.supplyAsync(() -> result);
	}
	
	private Result failureResponse(Exception e) {
		e.printStackTrace();
		String message = null;
		String localizedErrorMessage = null;
		ObjectNode result = Json.newObject();
		
		if (e instanceof TheFreshlyStoreException) {
			message = ((TheFreshlyStoreException) e).getErrorMessage();
		} else {
			message = ApiFailureMessages.TECHNICAL_ERROR;
		}

		result.put(ApiResponseKeys.ERROR, message);
		return badRequest(result);
	}
	
	private Result failureResponse(Throwable e) {
		String message = null;
		String localizedErrorMessage = null;
		ObjectNode result = Json.newObject();
		Languages languages = Play.current().injector().instanceOf(Languages.class);
		
		if (e instanceof TheFreshlyStoreException) {
			message = ((TheFreshlyStoreException) e).getErrorMessage();
			localizedErrorMessage = languages.getLocalizedString(message);
		} else {
			message = ApiFailureMessages.TECHNICAL_ERROR;
			localizedErrorMessage = languages.getLocalizedString(message);
		}

		result.put(ApiResponseKeys.ERROR, localizedErrorMessage);
		return badRequest(result);
	}
	
	private Result invalidSession() {
		Languages languages = Play.current().injector().instanceOf(Languages.class);
		String message = "session.invalid";
		String localizedErrorMessage = languages.getLocalizedString(message);
		ObjectNode result = Json.newObject();
		result.put(ApiResponseKeys.ERROR, localizedErrorMessage);
		
		return unauthorized(result);
	}
	
	public Result successResponseObj(Object object) {
		if(object instanceof String) {
			Languages languages = Play.current().injector().instanceOf(Languages.class);
			String localizedErrorMessage = languages.getLocalizedString((String) object);
			return ok(json(
							Json.newObject().put(ApiResponseKeys.MESSAGE, localizedErrorMessage)
						));
		}
		return ok(json(object));
	}
	
	public Result failureResponseObj(Throwable e) {
		e.printStackTrace();
		String message = null;
		String localizedErrorMessage = null;
		ObjectNode result = Json.newObject();
		Languages languages = Play.current().injector().instanceOf(Languages.class);
		
		if (e instanceof TheFreshlyStoreException) {
			message = ((TheFreshlyStoreException) e).getErrorMessage();
			localizedErrorMessage = languages.getLocalizedString(message);
			result.put(ApiResponseKeys.ERROR, localizedErrorMessage);
		} else if(e.getCause() instanceof TheFreshlyStoreException) {
			message = ((TheFreshlyStoreException) e.getCause()).getErrorMessage();
			localizedErrorMessage = languages.getLocalizedString((message.toString()));
			result.put(ApiResponseKeys.ERROR, localizedErrorMessage);
		}	else {
			message = ApiFailureMessages.TECHNICAL_ERROR;
			result.put(ApiResponseKeys.ERROR, message);
		}

		return badRequest(result);
	}
	
	public BiFunction<Object, Throwable, Result> handler = (param1, param2) -> {
		if(param2 != null) {
			return failureResponseObj(param2);
		}
		
		return successResponseObj(param1);
	};
}
