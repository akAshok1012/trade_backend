package com.tm.app.dto;

import com.tm.app.enums.UserRoles;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
	private String userName;
	private String updatedBy;
	private UserRoles userRole;
	private Long empCusId;
}
