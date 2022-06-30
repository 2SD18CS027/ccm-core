package com.thefreshlystore.utilities;

import java.util.List;

import javax.inject.Inject;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.Message;

public class HandleSESBounces {
	private final Configurations configurations;
	private final String queueUrl = "https://sqs.eu-west-1.amazonaws.com/027454311804/ses-bounces-queue";
	
	@Inject
	public HandleSESBounces(Configurations configurations) {
		this.configurations = configurations;
	}
	
	public void readMessage() {
		BasicAWSCredentials awsCreds = new BasicAWSCredentials(configurations.AWS_SES_KEY_ID, configurations.AWS_SES_SECRET_KEY);
		AmazonSQS sqs = AmazonSQSClientBuilder.standard()
				.withRegion(Regions.EU_WEST_1)
	            .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
	            .build();
		List<Message> messages = sqs.receiveMessage(queueUrl).getMessages();

        // delete messages from the queue
        for (Message m : messages) {
        	System.out.println(m.getBody());
            //sqs.deleteMessage(queueUrl, m.getReceiptHandle());
        }
	}
}
