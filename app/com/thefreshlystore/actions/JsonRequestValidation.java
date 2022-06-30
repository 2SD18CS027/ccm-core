package com.thefreshlystore.actions;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;
import com.thefreshlystore.controllers.BaseController;
import com.thefreshlystore.exceptions.TheFreshlyStoreException;
import com.thefreshlystore.utilities.TheFreshlyStoreConstants.ApiFailureMessages;

import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.data.validation.ValidationError;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

public class JsonRequestValidation extends Action<ValidateJson> {
	@Inject
	FormFactory formFactory;

    @Inject
    BaseController baseController;

	@Override
	public CompletionStage<Result> call(Http.Context context) {
		CompletionStage<Result> result = validateRequest();
		if (result == null) {
			result = delegate.call(context);
		}
		return result;
	}

	public CompletionStage<Result> validateRequest() {
		Class<?> value = configuration.value();
		JsonNode data = Http.Context.current().request().body().asJson();
		Form<?> form = formFactory.form(value).bind(data);

		if (form.hasErrors()) {
            Logger.info(form.errors().toString());
            Map<String, List<ValidationError>> errorMap = form.errors();
            String errorKey = errorMap.keySet().iterator().next();
            ValidationError validationError = errorMap.get(errorKey).iterator().next();
            String message = validationError.message();
            Long arguments = 0L;

            if (!validationError.arguments().isEmpty()) {
                arguments = (Long) validationError.arguments().get(0);
            }

            String errorMessage;
            TheFreshlyStoreException HubballiEatsException;
            errorKey = capitalizeFirstLetter(errorKey);

            switch (message) {
                case "error.required":
                    errorMessage = ApiFailureMessages.FIELD_MISSING;
                    HubballiEatsException = new TheFreshlyStoreException(errorKey + " " + errorMessage);
					break;
				case "error.email":
					errorMessage = ApiFailureMessages.INVALID_EMAIL;
                    HubballiEatsException = new TheFreshlyStoreException(errorKey + " " + errorMessage);
					break;
                case "error.minLength":
                    errorMessage = ApiFailureMessages.MIN_LENGTH_VALIDATION;
                    HubballiEatsException = new TheFreshlyStoreException(errorKey + " " + errorMessage);
                    break;
                case "error.maxLength":
                    errorMessage = ApiFailureMessages.MAX_LENGTH_VALIDATION;
                    HubballiEatsException = new TheFreshlyStoreException(errorKey + " " + errorMessage);
                    break;
                case "error.pattern":
                    errorMessage = ApiFailureMessages.PATTERN_VALIDATION;
                    HubballiEatsException = new TheFreshlyStoreException(errorKey + " " + errorMessage);
                    break;
                default:
                    errorMessage = ApiFailureMessages.INVALID_INPUT;
                    HubballiEatsException = new TheFreshlyStoreException(errorKey + " " + errorMessage);
                    break;
            }

            return baseController.failureResponsePromise(HubballiEatsException);
		}

		return null;
	}

	private String capitalizeFirstLetter(String str) {
	    return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
