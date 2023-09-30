package com.tm.app.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.dto.UserRoleIdNameDto;
import com.tm.app.entity.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long>{

	@Query("SELECT new com.tm.app.dto.UserRoleIdNameDto(id,name) FROM UserRole")
	List<UserRoleIdNameDto> findByIdAndName();

	Object findByName(String name);

	Page<UserRole> findByNameLikeIgnoreCase(String search, PageRequest of);

}
