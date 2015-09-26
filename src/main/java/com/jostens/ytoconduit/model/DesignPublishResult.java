package com.jostens.ytoconduit.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonView;

public class DesignPublishResult implements Serializable {

	private static final long serialVersionUID = 464151865032764565L;
	
	@JsonView(View.DesignPublishSummary.class)
	private String message;
	@JsonView(View.DesignPublishSummary.class)
	private Boolean status;
	@JsonView(View.DesignPublishSummary.class)
	private String errorMessage;

	public DesignPublishResult() {
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
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
