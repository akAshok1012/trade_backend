package com.tm.app.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tm.app.dto.ChangePasswordDto;
import com.tm.app.dto.DataFilter;
import com.tm.app.dto.RegisterRequest;
import com.tm.app.dto.ResetPasswordDto;
import com.tm.app.dto.UserListDto;
import com.tm.app.dto.UserRoleDto;
import com.tm.app.dto.UserRoleIdNameDto;
import com.tm.app.entity.User;
import com.tm.app.entity.UserRole;
import com.tm.app.security.annotations.IsSuperAdmin;
import com.tm.app.security.annotations.IsSuperAdminOrAdmin;
import com.tm.app.security.annotations.IsSuperAdminOrAdminOrCustomerOrEmployee;
import com.tm.app.service.UserService;
import com.tm.app.utils.APIResponse;
import com.tm.app.utils.APIResponseConstants;
import com.tm.app.utils.Response;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
	@Autowired
	private UserService userService;

	@PostMapping("/user-role")
	@IsSuperAdmin
	public APIResponse<?> saveUserRole(@RequestBody UserRoleDto userRoleDto) {
		try {
			UserRole userRole = userService.saveUserRole(userRoleDto);
			return Response.getSuccessResponse(userRole,
					String.format(APIResponseConstants.CREATION_SUCCESS_MESSAGE, userRole.getName()), HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/user-roles")
	@IsSuperAdmin
	public APIResponse<?> getAllUserRole() {
		try {
			List<UserRole> userRoles = userService.getAllUserRole();
			return Response.getSuccessResponse(userRoles, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE, HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/user-role-list")
	@IsSuperAdmin
	public APIResponse<?> getUserRoleList(@ModelAttribute DataFilter dataFilter) {
		try {
			Page<UserRole> userRoles = userService.getUserRoleList(dataFilter);
			return Response.getSuccessResponse(userRoles, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE, HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/user-role/{id}")
	@IsSuperAdmin
	public APIResponse<?> getUserRoleById(@PathVariable("id") Long id) {
		try {
			UserRole userRole = userService.getUserRoleById(id);
			return Response.getSuccessResponse(userRole, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE, HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/user-role/{id}")
	@IsSuperAdmin
	public APIResponse<?> updateUserRoleById(@PathVariable("id") Long id, @RequestBody UserRoleDto userRoleDto) {
		try {
			UserRole userRole = userService.updateUserRoleById(id, userRoleDto);
			return Response.getSuccessResponse(userRole,
					String.format(APIResponseConstants.UPDATED_SUCCESS_MESSAGE, userRole.getName()), HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/user-role/{id}")
	@IsSuperAdmin
	public APIResponse<?> deleteUserRoleById(@PathVariable("id") Long id) {
		try {
			userService.deleteUserRoleById(id);
			return Response.getSuccessResponse(null, APIResponseConstants.DELETE_RESPONSE_MESSAGE,
					HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/users")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getAllUsers() {
		try {
			List<User> users = userService.getAllUsers();
			return Response.getSuccessResponse(users, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE, HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/user/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getUserById(@PathVariable("id") Long id) {
		try {
			UserListDto userListDto = userService.getUserById(id);
			return Response.getSuccessResponse(userListDto, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE,
					HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/user/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> updateUserById(@PathVariable("id") Long id, @RequestBody RegisterRequest request) {
		try {
			User user = userService.updateUserById(id, request);
			return Response.getSuccessResponse(user,
					String.format(APIResponseConstants.UPDATED_SUCCESS_MESSAGE, user.getUsername()), HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/user/{id}")
	@IsSuperAdminOrAdmin
	public APIResponse<?> deleteUserById(@PathVariable("id") Long id) {
		try {
			userService.deleteUserById(id);
			return Response.getSuccessResponse(null, APIResponseConstants.DELETE_RESPONSE_MESSAGE,
					HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/user")
	@IsSuperAdminOrAdmin
	public APIResponse<?> register(@RequestBody RegisterRequest request) {
		try {
			User user = userService.register(request);
			return Response.getSuccessResponse(user,
					String.format(APIResponseConstants.CREATION_SUCCESS_MESSAGE, user.getUsername()), HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/user-name")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getUserRoleIdName() {
		try {
			List<UserRoleIdNameDto> userRoleIdNameDto = userService.getUserRoleIdName();
			return Response.getSuccessResponse(userRoleIdNameDto, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE,
					HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/user-list")
	@IsSuperAdminOrAdmin
	public APIResponse<?> getUserList(@ModelAttribute DataFilter dataFilter) {
		try {
			Page<User> userList = userService.getUserList(dataFilter);
			return Response.getSuccessResponse(userList, APIResponseConstants.SUCCESS_RESPONSE_MESSAGE, HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/change-password")
	@IsSuperAdminOrAdminOrCustomerOrEmployee
	public APIResponse<?> changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
		try {
			User updatePassword = userService.changePassword(changePasswordDto);
			return Response.getSuccessResponse(updatePassword, "Password Updated Successfully", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/refresh-token")
	@IsSuperAdminOrAdminOrCustomerOrEmployee
	public APIResponse<?> refreshToken(HttpServletRequest httpServletRequest) {
		try {
			String refreshToken = userService.refreshToken(httpServletRequest);
			return Response.getSuccessResponse(refreshToken, "Refresh Token", HttpStatus.OK);
		} catch (Exception e) {
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/reset-password")
	@IsSuperAdminOrAdminOrCustomerOrEmployee
	public APIResponse<?> resetPassword(@RequestBody ResetPasswordDto resetPasswordDto) {
		try {
			User user = userService.resetPasswordByUserName(resetPasswordDto);
			return Response.getSuccessResponse(user, "Password reset successfully", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getFailureResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}