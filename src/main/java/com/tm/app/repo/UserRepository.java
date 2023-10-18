package com.tm.app.repo;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.User;
import com.tm.app.enums.UserRoles;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUserName(String userName);

	Optional<User> findByUserNameIgnoreCase(String userName);
	
	@Query("Select u from User u where u.userName =:userName and id !=:id")
	User getUserNameForUpdateUserName(String userName,Long id);

	Page<User> findByUserNameLikeIgnoreCase(String search, PageRequest of);

	boolean existsByUserRolesAndUserId(UserRoles userRole, Long empCusId);

	@Query("Select u from User u where LOWER(u.userName) like LOWER(:search)")
	Page<User> getUserList(String search, PageRequest of);

}