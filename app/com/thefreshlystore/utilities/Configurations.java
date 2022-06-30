package com.thefreshlystore.utilities;

import javax.inject.Inject;

import play.Configuration;

public class Configurations {
	public static String serverHostName;
	public static String isProduction;
	
	/* Razor Pay Credentials */
	public static String RAZOR_PAY_API_KEY;
	public static String RAZOR_PAY_API_SECRET;
	
	/* FCM Keys */
	public static String FCM_PRIVATE_KEY;
	public static String FCM_PUBIC_KEY;
	
	/* Admin Keys */
	public static String ADMIN_KEY;
	
	/* Generic Constants */
	public static String SLOT_TIMES;
	public static Integer NEXT_AVAILABLE_DATES_COUNT;
	public static Boolean IS_SEND_SMS;
	public static Integer DELIVERY_FEE;
	public static Integer MINIMUM_AMOUNT;
	public static String HOST;
	public static String MAIL_USERNAME;
	public static String MAIL_PASSWORD;
	public static String SENDGRID_EMAIL_API_KEY;
	
	/* Facebook Account Kit Constants */
	public static String APP_ID;
	public static String APP_SECRET;
	
	/* AWS Credentials */
	public static String AWS_SES_KEY_ID;
	public static String AWS_SES_SECRET_KEY;
	public static String AWS_S3_ACCESS_KEY;
	public static String AWS_S3_SECRET_KEY;
	
	@Inject
	public Configurations(final Configuration configuration) {
		Configurations.serverHostName = configuration.getString("server.host.name");
		Configurations.isProduction = configuration.getString("is.production");
		
		/* Razor Pay Credentials */
		Configurations.RAZOR_PAY_API_KEY = configuration.getString("razor.pay.api.key");
		Configurations.RAZOR_PAY_API_SECRET = configuration.getString("razor.pay.api.secret");
		
		/* FCM Keys */
		Configurations.FCM_PRIVATE_KEY = configuration.getString("fcm.private.key");
		Configurations.FCM_PUBIC_KEY = configuration.getString("fcm.public.key");
		
		/* Admin Keys */
		Configurations.ADMIN_KEY = configuration.getString("admin.key");
		
		/* Generic Constants */
		Configurations.SLOT_TIMES = configuration.getString("slot.times");
		Configurations.NEXT_AVAILABLE_DATES_COUNT = Integer.parseInt(configuration.getString("next.available.dates.count"));
		Configurations.IS_SEND_SMS = Boolean.valueOf( configuration.getString("is.send.sms") );
		Configurations.DELIVERY_FEE = Integer.valueOf( configuration.getString("delivery.fee") );
		Configurations.MINIMUM_AMOUNT = Integer.valueOf( configuration.getString("minimum.amount") );
		Configurations.HOST = configuration.getString("host");
		Configurations.MAIL_USERNAME = configuration.getString("javax.mail.username");
		Configurations.MAIL_PASSWORD = configuration.getString("javax.mail.password");
		Configurations.SENDGRID_EMAIL_API_KEY = configuration.getString("sendgrid.email.api.key");
		
		/* Facebook Account Kit Constants */
		Configurations.APP_ID = configuration.getString("app.id");
		Configurations.APP_SECRET = configuration.getString("app.secret");
		
		/* AWS Credentials */
		Configurations.AWS_SES_KEY_ID = configuration.getString("aws.ses.key.id");
		Configurations.AWS_SES_SECRET_KEY = configuration.getString("aws.ses.secret.key");
		Configurations.AWS_S3_ACCESS_KEY = configuration.getString("aws.s3.access.key");
		Configurations.AWS_S3_SECRET_KEY = configuration.getString("aws.s3.secret.key");
	};
}
