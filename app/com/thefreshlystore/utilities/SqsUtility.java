package com.thefreshlystore.utilities;



import java.util.List;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.regions.Region;

public class SqsUtility {

	public static void push(String password) {
		SqsClient sqsClient = SqsClient.builder()
                .region(Region.AP_SOUTH_1)
                .build();
	}
}
