package com.tm.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.EmployeePayConfiguration;

@Repository
public interface EmployeePayConfigurationRepo extends JpaRepository<EmployeePayConfiguration, Long> {

}
