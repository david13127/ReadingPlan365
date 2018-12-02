package com.thirdleave.readingplan.controller.po;

import java.util.List;

public class ResponseJson {
	private String status;
	private String message;
	private List<? extends Object> results;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<? extends Object> getResults() {
		return results;
	}

	public void setResults(List<? extends Object> results) {
		this.results = results;
	}

	@Override
	public String toString() {
		return "{" + "status:" + status + ", message:" + message + "}";
	}
}
