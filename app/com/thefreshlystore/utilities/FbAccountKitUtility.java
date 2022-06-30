package com.thefreshlystore.utilities;

import java.net.URLEncoder;

import javax.inject.Inject;

import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.thefreshlystore.exceptions.TheFreshlyStoreException;
import com.thefreshlystore.utilities.TheFreshlyStoreConstants.ApiFailureMessages;

public class FbAccountKitUtility {
	
	private final Configurations configurations;
	
	@Inject
	public FbAccountKitUtility(Configurations configurations) {
		this.configurations = configurations;
	}
	
	public void authenticateUserWithFbAccountKit(String mobileNumber, String authCode) {
		String accessToken = getAccessTokenFromAuthCode(authCode);
		try {
			
			HttpResponse<String> response = Unirest.get("https://graph.accountkit.com/v1.3/me/?access_token=" + accessToken)
			  .header("Cache-Control", "no-cache")
			  .header("Postman-Token", "6bcabb3d-4358-07fa-97e8-00d0d812cf6b")
			  .asString();
			if(!(new JSONObject(response.getBody()).getJSONObject("phone").getString("national_number").equals(mobileNumber))) {
				throw new TheFreshlyStoreException(ApiFailureMessages.Users.INVALID_OTP);
			}
			
		} catch(Exception e) {}
	}
	
	private String getAccessTokenFromAuthCode(String authCode) {
		try {
			
			String accessToken = "AA|@appId|@appSecret";
			accessToken = accessToken.replaceAll("@appId", configurations.APP_ID).replaceAll("@appSecret", configurations.APP_SECRET);
			accessToken = URLEncoder.encode(accessToken, "UTF-8");
			String url = ("https://graph.accountkit.com/v1.3/access_token?grant_type=authorization_code&code=@authCode&access_token="+accessToken)
					.replaceAll("@authCode", authCode);
			HttpResponse<String> response = Unirest.get(url)
			  .header("Cache-Control", "no-cache")
			  .header("Postman-Token", "31ac8635-62e7-b579-9385-8ed0b0408500")
			  .asString();
			if(response.getStatus() != 200) {
				throw new TheFreshlyStoreException(ApiFailureMessages.Users.INVALID_OTP);
			}
			return new JSONObject(response.getBody()).getString("access_token");
			
		} catch(Exception e) {
			throw new TheFreshlyStoreException(ApiFailureMessages.Users.INVALID_OTP);
		}
	}
}
