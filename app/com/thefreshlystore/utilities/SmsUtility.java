package com.thefreshlystore.utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.thefreshlystore.utilities.TheFreshlyStoreConstants.SmsEventIds;

public class SmsUtility {
	
	private final Configurations configurations;
	
	@Inject
	public SmsUtility(Configurations configurations) {
		this.configurations = configurations;
	}
	
	public void sendSms(String message, String mobileNumber) {
		try {
			if(configurations.IS_SEND_SMS) {
				HttpResponse<String> response = Unirest.post("http://api.msg91.com/api/v2/sendsms?campaign=&response=&afterminutes=&schtime=&unicode=1&flash=&message=&encrypt=&authkey=&mobiles=&route=&sender=&country=91")
				  .header("authkey", "172567AMbXw9pVS59a8f6c5")
				  .header("content-type", "application/json")
				  .body(("{  \"sender\": \"FRSHLY\",  \"route\": \"4\",  \"country\": \"91\",  \"sms\": [    {      \"message\": \"@param\",      \"to\": [        \"@mobileNumber\"      ]    }  ]}"
						  .replaceAll("@mobileNumber", mobileNumber)
						  .replaceAll("@param", message)))
				  .asString();
				System.out.println(response.getBody());
			}
			
		} catch(Exception e) {e.printStackTrace();}
	}
	
	public String getOrderPlacedMessage(int event, String orderId, Date fromDate, Date toDate) {
		String message = "";
		switch (event) {
			case SmsEventIds.ORDER_PLACED :
				DateFormat df = new SimpleDateFormat("EEE MMM dd,");
				String on = df.format(fromDate);
				df = new SimpleDateFormat("HH:mm");
				String from = df.format(fromDate);
				String to = df.format(toDate);
				message = "Thanks for shopping with us. Your order #"+orderId+" will be delivered on " + on + " between "+from+"-"+to + ". Regards, #TheFreshlyStore";
				break;
		}
		return message;
	}
	
	public String getOrderProcessedMessage(String orderId, String userName) {
		return "Hi " + userName + ", Your order #" + orderId + " has been successfully processed & packed by us. We will delivery it during your selected delivery time. Thanks, #TheFreshlyStore Team";
	}
	
	public String getOrderOutForDeliveryMessage(String orderId, String userName, Date fromDate, Date toDate) {
		DateFormat df = new SimpleDateFormat("EEE MMM dd,");
		df = new SimpleDateFormat("HH:mm");
		String from = df.format(fromDate);
		String to = df.format(toDate);
		return "Hi " + userName + ", Your order #" + orderId + " is out for delivery. Please be available at your shipping address between "+from+"-"+to + ". Thanks, #TheFreshlyStore Team";
	}
	
	public String getOrderDeliveredMessage(String orderId, String userName) {
		return "Hi " + userName + ", Your order #" + orderId + " has been delivered. We are very happy to serve you again. #TheFreshlyStore Team";
	}
}
