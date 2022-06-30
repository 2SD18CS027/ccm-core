package com.thefreshlystore.services;

import java.io.File;
import java.util.Date;
import java.util.UUID;

import javax.inject.Inject;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.thefreshlystore.dtos.response.ImageUploadResponse;
import com.thefreshlystore.exceptions.TheFreshlyStoreException;
import com.thefreshlystore.utilities.Configurations;
import com.thefreshlystore.utilities.TheFreshlyStoreConstants;
import com.thefreshlystore.utilities.TheFreshlyStoreConstants.ApiFailureMessages;
import com.thefreshlystore.utilities.TheFreshlyStoreConstants.ApiResponseKeys.ImageUpload;

import play.mvc.Http.MultipartFormData.FilePart;

public class ImageUploadServiceImpl implements ImageUploadService {

	private final Configurations configurations;
	
	@Inject
	public ImageUploadServiceImpl(Configurations configurations) {
		this.configurations = configurations;
	}
	
	@Override
	public ImageUploadResponse uploadImage(FilePart<File> picture, String imageUploadClientSecret) {
		if(picture == null) {
	    	throw new TheFreshlyStoreException(ApiFailureMessages.ImageUpload.FILE_NOT_FOUND);
	    }
	    
		if(!(TheFreshlyStoreConstants.IMAGE_UPLOAD_CLIENT_SECRET.equals(imageUploadClientSecret))) {
			throw new TheFreshlyStoreException(ApiFailureMessages.CANNOT_PERFORM_OPERATION);
		}
		
		File image = picture.getFile();

		AWSCredentials awsCredentials = new BasicAWSCredentials(configurations.AWS_S3_ACCESS_KEY, configurations.AWS_S3_SECRET_KEY);
        AmazonS3 s3client = new AmazonS3Client(awsCredentials);
        
        Long date = new Date().getTime();
        String url = UUID.randomUUID().toString().concat(date.toString()).concat(".png");
        s3client.putObject(new PutObjectRequest("the-freshly-store-icons", url, image));
        
        return new ImageUploadResponse( ImageUpload.S3_ENDPOINT.concat(url) );
	}
}
