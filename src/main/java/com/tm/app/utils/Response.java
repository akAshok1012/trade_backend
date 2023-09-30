package com.tm.app.utils;

import org.springframework.http.HttpStatus;

public class Response {

	/**
	 * 
	 * @param e
	 * @param status
	 * @return
	 */
	public static APIResponse<?> getFailureResponse(Exception e, HttpStatus status) {
		APIResponse<?> response = new APIResponse<>();
		response.setStatus(status);
		if (e.getMessage().contains("constraint")) {
			response.setMessage("Unable to delete the ID due to its association with other tables.");
		} else {
			response.setMessage(e.getMessage());
		}
		return response;
	}

	/**
	 * 
	 * @param <T>
	 * @param data
	 * @param message
	 * @param status
	 * @return
	 */
	public static <T> APIResponse<T> getSuccessResponse(T data, String message, HttpStatus status) {
		APIResponse<T> response = new APIResponse<>();
		response.setData(data);
		response.setStatus(status);
		response.setMessage(message);
		return response;
	}
}
