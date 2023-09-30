package com.tm.app.utils;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class APIResponse<T> {

	private String message;

	private T data;

	private HttpStatus status;
}
