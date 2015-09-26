package com.jostens.ytoconduit.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonView;

public class ImageUploadResult implements Serializable {

	private static final long serialVersionUID = -6576508252616185432L;
	
	@JsonView(View.ImageUploadSummary.class)
	private String message;
	@JsonView(View.ImageUploadSummary.class)
	private Boolean status;
	@JsonView(View.ImageUploadSummary.class)
	private String url;
	@JsonView(View.ImageUploadSummary.class)
	private String cdnUrl;
	@JsonView(View.ImageUploadSummary.class)
	private String errorMessage;

	public ImageUploadResult() {
		// TODO Auto-generated constructor stub
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCdnUrl() {
		return cdnUrl;
	}

	public void setCdnUrl(String cdnUrl) {
		this.cdnUrl = cdnUrl;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
