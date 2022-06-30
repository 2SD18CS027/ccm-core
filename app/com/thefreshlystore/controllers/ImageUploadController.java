package com.thefreshlystore.controllers;

import java.io.File;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;
import play.mvc.Result;

import com.thefreshlystore.actions.AdminAuthentication;
import com.thefreshlystore.services.ImageUploadService;
import com.thefreshlystore.utilities.CorsComposition.Cors;
import com.thefreshlystore.utilities.TheFreshlyStoreConstants.ApiRequestKeys;

import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;

public class ImageUploadController extends BaseController {
	private final ImageUploadService imageUploadService;
	
	@Inject
	public ImageUploadController(ImageUploadService imageUploadService) {
		this.imageUploadService = imageUploadService;
	}
	
	@Cors
	@AdminAuthentication
	public CompletionStage<Result> uploadImage() {
		try {
			MultipartFormData<File> body = request().body().asMultipartFormData();
		    FilePart<File> picture = body.getFile(ApiRequestKeys.ImageUpload.IMAGE);
		    String imageUploadClientSecret = request().getHeader(ApiRequestKeys.ImageUpload.CLIENT_SECRET);
		    
            return successResponsePromise(imageUploadService.uploadImage(picture, imageUploadClientSecret));
		} catch(Exception e) {
			return failureResponsePromise(e);
		}
	}
}
