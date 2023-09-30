package com.tm.app.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.EmployeeType;

@Repository
public interface EmployeeTypeRepo extends JpaRepository<EmployeeType, Long> {

	Page<EmployeeType> findByEmployeeTypeLikeIgnoreCase(String search, PageRequest of);

}
