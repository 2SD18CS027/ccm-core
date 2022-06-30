package com.thefreshlystore.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.ALWAYS)
public class ImageUploadResponse {
	
	public String url;
	
	public ImageUploadResponse(String url) {
		this.url = url;
	}
}
