package com.tm.app.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tm.app.dto.ChangePasswordDto;
import com.tm.app.dto.DataFilter;
import com.tm.app.dto.JwtToken;
import com.tm.app.dto.RegisterRequest;
import com.tm.app.dto.ResetPasswordDto;
import com.tm.app.dto.UserListDto;
import com.tm.app.dto.UserRoleDto;
import com.tm.app.dto.UserRoleIdNameDto;
import com.tm.app.entity.Customer;
import com.tm.app.entity.Employee;
import com.tm.app.entity.Notification;
import com.tm.app.entity.User;
import com.tm.app.entity.UserRole;
import com.tm.app.enums.NotificationStatus;
import com.tm.app.enums.UserRoles;
import com.tm.app.repo.CustomerRepo;
import com.tm.app.repo.EmployeeRepo;
import com.tm.app.repo.NotificationRepo;
import com.tm.app.repo.UserRepository;
import com.tm.app.repo.UserRoleRepository;
import com.tm.app.service.UserService;
import com.tm.app.utils.RandomUtils;
import com.tm.app.utils.TokenService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private EmployeeRepo employeeRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private NotificationRepo notificationRepo;

	@Value("${server.env}")
	private String env;

	@Override
	@Transactional
	public UserRole saveUserRole(UserRoleDto userRoleDto) {
		UserRole userRole = new UserRole();
		try {
			if (Objects.nonNull(userRoleRepository.findByName(userRoleDto.getName()))) {
				throw new RuntimeException("UserRole  already exist");
			}
			BeanUtils.copyProperties(userRoleDto, userRole);
			userRole = userRoleRepository.save(userRole);
		} catch (BeansException e) {
			log.error("[USER_ROLE] adding userRole failed", e);
			throw new RuntimeException("Adding userRole failed");
		}
		return userRole;
	}

	@Override
	public List<UserRole> getAllUserRole() {
		return userRoleRepository.findAll();
	}

	@Override
	public UserRole getUserRoleById(Long id) {
		return userRoleRepository.findById(id).orElseThrow();
	}

	@Override
	@Transactional
	public UserRole updateUserRoleById(Long id, UserRoleDto userRoleDto) {
		UserRole userRole = userRoleRepository.findById(id).orElseThrow();
		try {
			userRole.setName(userRoleDto.getName());
			userRole.setDescription(userRoleDto.getDescription());
			userRole.setUpdatedBy(userRoleDto.getUpdatedBy());
			userRole = userRoleRepository.save(userRole);
		} catch (Exception e) {
			log.error("[USER_ROLE] updating userRole failed", e);
			throw new RuntimeException("Updating userRole failed");
		}
		return userRole;
	}

	@Override
	@Transactional
	public void deleteUserRoleById(Long id) {
		try {
			userRoleRepository.deleteById(id);
		} catch (Exception e) {
			log.error("[USER_ROLE] deleting userRole failed", e);
			throw new RuntimeException("Deleting userRole failed");
		}
	}

	@Override
	public UserListDto getUserById(Long id) {
		UserListDto userListDto = new UserListDto();
		User user = userRepository.findById(id).orElseThrow();
		if (user.getUserRoles().name().equals(UserRoles.CUSTOMER.name())) {
			Customer customer = customerRepo.findById(user.getUserId()).orElseThrow();
			customerRepo.save(customer);
			userListDto.setCustomer(customer);
		}
		if (user.getUserRoles().name().equals(UserRoles.EMPLOYEE.toString())) {
			Employee employee = employeeRepo.findById(user.getUserId()).orElseThrow();
			employeeRepo.save(employee);
			userListDto.setEmployee(employee);
		}
		userListDto.setUser(user);
		return userListDto;
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	@Transactional
	public User updateUserById(Long id, RegisterRequest request) {
		User user = userRepository.findById(id).orElseThrow();
		try {
			user.setUserName(request.getUserName());
			user.setUpdatedBy(request.getUpdatedBy());
			user.setUserRoles(request.getUserRole());
			user = userRepository.save(user);
			log.info("[USER] updating user [{}]", user.getId());
		} catch (Exception e) {
			log.error("[USER] updating user failed", e);
			throw new RuntimeException("Updating user failed");
		}
		return user;
	}

	@Override
	public void deleteUserById(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	public List<UserRoleIdNameDto> getUserRoleIdName() {
		return userRoleRepository.findByIdAndName();
	}

	@Override
	public Page<UserRole> getUserRoleList(DataFilter dataFilter) {
		return userRoleRepository.findByNameLikeIgnoreCase(dataFilter.getSearch(), PageRequest.of(dataFilter.getPage(),
				dataFilter.getSize(), Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
	}

	@Override
	public Page<User> getUserList(DataFilter dataFilter) {
		Page<User> userList = userRepository.getUserList(dataFilter.getSearch(), PageRequest.of(dataFilter.getPage(),
				dataFilter.getSize(), Sort.by(dataFilter.getSortBy(), dataFilter.getSortByField())));
		userList.getContent().stream().forEach(System.out::println);
		Map<Long, String> employeeMap = employeeRepo.findAll().stream()
				.collect(Collectors.toMap(Employee::getId, Employee::getName));
		Map<Long, String> customerMap = customerRepo.findAll().stream()
				.collect(Collectors.toMap(Customer::getId, Customer::getName));
		userList.getContent().stream().forEach(user -> {
			if (user.getUserRoles().name().equals(UserRoles.EMPLOYEE.name())
					|| user.getUserRoles().name().equals(UserRoles.ADMIN.name())) {
				if (employeeMap.containsKey(user.getUserId())) {
					user.setName(employeeMap.get(user.getUserId()));
				} else {
					user.setName(null);
				}
			}
			if (user.getUserRoles().name().equals(UserRoles.CUSTOMER.name())) {
				if (customerMap.containsKey(user.getUserId())) {
					user.setName(customerMap.get(user.getUserId()));
				} else {
					user.setName(null);
				}
			}
		});
		return userList;
	}

	@Override
	@Transactional
	public User changePassword(ChangePasswordDto changePasswordDto) {
		User user = userRepository.findById(changePasswordDto.getId()).orElseThrow();
		if (!passwordEncoder.matches(changePasswordDto.getOldPassword(), user.getPassword())) {
			throw new RuntimeException("Old Password Incorrect");
		} else {
			user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
		}
		user.setUpdatedBy(changePasswordDto.getUpdatedBy());
		user.setIsFirstLogin(true);
		user = userRepository.save(user);
		return user;
	}

	@Override
	public String refreshToken(HttpServletRequest httpServletRequest) throws JsonProcessingException {
		String username = tokenService.getUserName(httpServletRequest);
		User user = userRepository.findByUserName(username).orElseThrow();
		JwtToken jwtToken = new JwtToken();
		jwtToken.setUserName(user.getUsername());
		jwtToken.setRole(user.getUserRoles().name());
		jwtToken.setUserId(user.getId());
		jwtToken.setIsFirstLogin(user.getIsFirstLogin());
		jwtToken.setEnv(env);
		return jwtService.generateToken(jwtToken);
	}

	@Override
	public User resetPasswordByUserName(ResetPasswordDto resetPasswordDto) {
		User user = userRepository.findByUserName(resetPasswordDto.getUserName()).orElseThrow();
		user.setPassword(passwordEncoder.encode(resetPasswordDto.getNewPassword()));
		return userRepository.save(user);
	}

	@Override
	public User register(RegisterRequest request) {
		if (userRepository.findByUserNameIgnoreCase(request.getUserName()).isPresent()) {
			throw new RuntimeException("User Already Exist");
		}
		if (request.getEmpCusId() != null
				&& (userRepository.existsByUserRolesAndUserId(request.getUserRole(), request.getEmpCusId()))) {
			throw new RuntimeException("User Already Exist for " + request.getUserRole().name());
		}
		String password = RandomUtils.generatePassword();
		User user = User.builder().userName(request.getUserName()).password(passwordEncoder.encode(password))
				.userRoles(request.getUserRole()).userId(request.getEmpCusId()).updatedBy(request.getUpdatedBy())
				.isFirstLogin(false).build();
		User savedUser = userRepository.save(user);

		// insert in Notification table
		updateUserNotification(user);
		savedUser.setPassword(password);
		return savedUser;
	}

	/**
	 * Insert in Notification Table
	 * 
	 * @param savedUser
	 */
	private void updateUserNotification(User user) {
		Notification notification = new Notification();
		notification.setMessage("Hello " + user.getUsername()
				+ ", Your Account has been Created Successfully. Your Login Credentials are Username: "
				+ user.getUsername() + " and Password: " + user.getPassword()
				+ ". Please keep them confidential.Thanks, [KPR & Team]");
		notification.setTitle("New User Created");
		notification.setUpdatedBy(user.getUpdatedBy());
		try {
			notification.setIsSend(true);
			notification.setNotificationStatus(NotificationStatus.PENDING);
		} catch (Exception e) {
			notification.setIsSend(false);
			notification.setNotificationStatus(NotificationStatus.FAILED);
		}
		notification = notificationRepo.save(notification);
	}
}