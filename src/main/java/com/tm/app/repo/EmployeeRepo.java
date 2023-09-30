package com.tm.app.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.dto.EmployeeIdNameDto;
import com.tm.app.entity.Employee;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {

    @Query("SELECT new com.tm.app.dto.EmployeeIdNameDto(id,name) FROM Employee")
    List<EmployeeIdNameDto> findByIdAndName();

    Employee findByName(String name);

    Page<Employee> findByNameLikeIgnoreCase(String search, PageRequest pageRequest);

    @Query("SELECT aadhaarNumber,phoneNumber,pfNumber,esiNumber,panNumber,uanNumber,email FROM Employee")
    List<String[]> getExistingList();

    boolean existsByPhoneNumber(Long phoneNumber);

    boolean existsByAadhaarNumber(Long aadhaarNumber);

    @Query("Select e from Employee e where e.aadhaarNumber in (:aadhaarNumberList) or e.phoneNumber in (:phoneNumberList) or e.email in (:emailList) or e.pfNumber in(:pfNumberList) or e.esiNumber in(:esiNumberList) or e.panNumber in(:panNumberList) or e.uanNumber in(:uanNumberList)")
    List<Employee> getExistingList(List<Long> phoneNumberList, List<String> emailList, List<String> pfNumberList,
	    List<String> panNumberList, List<String> esiNumberList, List<Long> uanNumberList,
	    List<Long> aadhaarNumberList);

    boolean existsByEsiNumber(String esiNumber);

    boolean existsByPanNumber(String panNumber);

    boolean existsByUanNumber(Long uanNumber);

    boolean existsByEmail(String email);

	boolean existsByPfNumber(String pfNumber);
}