package com.tm.app.dto;

import lombok.Data;

@Data
public class ChangePasswordDto {
	private Long id;
	private String userName;
	private String oldPassword;
	private String newPassword;
	private String updatedBy;
}
