package com.thefreshlystore.utilities;

import javax.inject.Inject;

import org.json.JSONObject;

import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

public class RazorPayUtility {
	private final Configurations configurations;
	
	@Inject
	public RazorPayUtility(Configurations configurations) {
		this.configurations = configurations;
	}
	
	public boolean capture(Double totalPrice, String razorPaymentId) {
		try {
			RazorpayClient razorpay = getClient();
			JSONObject captureRequest = new JSONObject();
			captureRequest.put("amount", (totalPrice * 100));
			razorpay.Payments.capture(razorPaymentId, captureRequest);
			return true;
		} catch(Exception e) {}
		return false;
	}
	
	public Integer getCapturedAmountForOrder(String paymentId) {
		Integer result = 0;
		try {
			RazorpayClient razorpay = getClient();
			Payment payment = razorpay.Payments.fetch(paymentId);
			result = payment.get("amount");
			result = (result/100);
		} catch(Exception e) { e.printStackTrace(); }
		return result;
	}
	
	private RazorpayClient getClient() throws RazorpayException {
		return new RazorpayClient(configurations.RAZOR_PAY_API_KEY, configurations.RAZOR_PAY_API_SECRET);
	}
}
