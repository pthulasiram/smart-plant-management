package com.archius.cosmos.exception;
/**
 * @author Thulasiram
 *
 */
public class RestApiException extends Exception {
	private static final long serialVersionUID = 1L;
	private String url;
	private int statusCode;
	private String message;

	public RestApiException(String url, int statusCode, String message) {
		this.url = url;
		this.statusCode = statusCode;
		this.message = message;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
