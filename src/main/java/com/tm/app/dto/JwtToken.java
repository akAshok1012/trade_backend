package com.tm.app.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class JwtToken implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userName;
	private String role;
	private Long userId;
	private Boolean isFirstLogin;
}
