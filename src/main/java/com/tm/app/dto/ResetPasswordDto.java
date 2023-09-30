package com.tm.app.dto;

import lombok.Data;

@Data
public class ResetPasswordDto {

	private String userName;
	private String newPassword;
}
