package com.tm.app.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tm.app.dto.AuthenticationRequest;
import com.tm.app.dto.AuthenticationResponse;
import com.tm.app.dto.JwtToken;
import com.tm.app.entity.User;
import com.tm.app.repo.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	@Value("${server.env}")
	private String env;
	
    private final UserRepository repository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws JsonProcessingException {
	User user = repository.findByUserNameIgnoreCase(request.getUserName()).orElseThrow();
	authenticationManager
		.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), request.getPassword()));
	JwtToken jwtToken = new JwtToken();
	jwtToken.setUserName(user.getUsername());
	jwtToken.setRole(user.getUserRoles().name());
	jwtToken.setUserId(user.getId());
	jwtToken.setIsFirstLogin(user.getIsFirstLogin());
	jwtToken.setEnv(env);
	jwtToken.setUpdatedAt(user.getUpdatedAt());
	String token = jwtService.generateToken(jwtToken);
	return AuthenticationResponse.builder().token(token).build();
    }
}