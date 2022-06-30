package com.thefreshlystore.services;

import com.google.inject.ImplementedBy;
import com.thefreshlystore.dtos.response.ImageUploadResponse;

import java.io.File;
import play.mvc.Http.MultipartFormData.FilePart;

@ImplementedBy(ImageUploadServiceImpl.class)
public interface ImageUploadService {
	public ImageUploadResponse uploadImage(FilePart<File> picture, String imageUploadClientSecret);
}
