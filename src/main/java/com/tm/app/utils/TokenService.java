package com.tm.app.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tm.app.service.impl.JwtService;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class TokenService {

	@Autowired
	public JwtService jwtService;

	public String getUserName(HttpServletRequest request) throws JsonProcessingException {
		String authorizationHeaderValue = request.getHeader("Authorization");
		if (authorizationHeaderValue != null && authorizationHeaderValue.startsWith("Bearer ")) {
			authorizationHeaderValue = authorizationHeaderValue.substring(7, authorizationHeaderValue.length());
		}
		return jwtService.extractJwtObject(authorizationHeaderValue).getUserName();
	}
}