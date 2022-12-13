package com.codehelper.nomorebugs.payload;

public class ErrorResponse {
	private int status;
	private String message;
	private String timeStamp;
	private String details;
	
	public ErrorResponse() {}
	
	public ErrorResponse(int status, String message, String timeStamp, String details) {
		this.status = status;
		this.message = message;
		this.timeStamp = timeStamp;
		this.details = details;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
	
	
	
	
}
