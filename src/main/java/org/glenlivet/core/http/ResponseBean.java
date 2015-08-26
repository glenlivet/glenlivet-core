package org.glenlivet.core.http;

public class ResponseBean<T> {
	private T content;

	/**
	 * operation success.
	 */
	private Boolean success;

	/**
	 * error message.
	 */
	private String message;

	public ResponseBean(T content) {
		this.content = content;
		this.success = true;
	}

	public ResponseBean(String message) {
		this.success = false;
		this.message = message;
	}

	public T getContent() {
		return content;
	}

	public void setContent(T content) {
		this.content = content;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
