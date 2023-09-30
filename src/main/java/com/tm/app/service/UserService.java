package com.tm.app.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tm.app.dto.ChangePasswordDto;
import com.tm.app.dto.DataFilter;
import com.tm.app.dto.RegisterRequest;
import com.tm.app.dto.ResetPasswordDto;
import com.tm.app.dto.UserListDto;
import com.tm.app.dto.UserRoleDto;
import com.tm.app.dto.UserRoleIdNameDto;
import com.tm.app.entity.User;
import com.tm.app.entity.UserRole;

import jakarta.servlet.http.HttpServletRequest;

public interface UserService {

	UserRole saveUserRole(UserRoleDto userTypeDto);

	List<UserRole> getAllUserRole();

	UserRole getUserRoleById(Long id);

	void deleteUserRoleById(Long id);

	UserListDto getUserById(Long id);

	List<User> getAllUsers();

	void deleteUserById(Long id);

	UserRole updateUserRoleById(Long id, UserRoleDto userRoleDto);

	User updateUserById(Long id, RegisterRequest request);

	List<UserRoleIdNameDto> getUserRoleIdName();

	Page<UserRole> getUserRoleList(DataFilter dataFilter);

	Page<User> getUserList(DataFilter dataFilter);

	User changePassword(ChangePasswordDto changePasswordDto);

	String refreshToken(HttpServletRequest httpServletRequest) throws JsonProcessingException;

	User resetPasswordByUserName(ResetPasswordDto resetPasswordDto);

	User register(RegisterRequest request);
}