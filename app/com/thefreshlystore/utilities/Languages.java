package com.thefreshlystore.utilities;

import play.api.Play;
import play.i18n.Lang;
import play.i18n.MessagesApi;
import play.mvc.Http;

public class Languages {
	private MessagesApi messagesApi;

    public Languages() {
        this.messagesApi = Play.current().injector().instanceOf(MessagesApi.class);
    }

    public String getLocalizedString(String message) {
    	Lang lang = Lang.forCode("en");
        return messagesApi.get(lang, message);
    }

    public String getLocalizedString(String message, Object... args) {
        return messagesApi.get(Http.Context.current().lang(), message, args);
    }
    
    public static String getCurrentLanguage() {
		String currentLanguage = Http.Context.current().request().getHeader(TheFreshlyStoreConstants.ACCEPT_LANGUAGE.toString());
		currentLanguage = (currentLanguage == null) ? TheFreshlyStoreConstants.DEFAULT_LANGUAGE.toString() : currentLanguage;
		return currentLanguage;
	}
}
