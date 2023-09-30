package com.tm.app.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.dto.CustomerIdNameDto;
import com.tm.app.entity.Customer;
import com.tm.app.enums.CustomerType;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {

    @Query("SELECT new com.tm.app.dto.CustomerIdNameDto(id,name) from Customer")
    List<CustomerIdNameDto> findByCustomerIdName();

    Customer findByName(String name);

    Customer findByNameIgnoreCase(String name);

    Page<Customer> findByNameLikeIgnoreCase(String search, PageRequest of);

    @Query("SELECT c from Customer c where c.id = :id")
    Customer findByUserId(Long id);

    boolean existsByPhoneNumber(Long phoneNumber);

    @Query("select email,phoneNumber,gstNo,panNo from Customer")
    List<String[]> getExistingList();

    boolean existsByEmail(String email);

    @Query("select c from Customer c where c.email in(:emailList) or c.phoneNumber in (:phoneNumberList) or c.gstNo in (:gstNumberList) or c.panNo in (:panNumberList)")
    List<Customer> getExistingList(List<Long> phoneNumberList, List<String> emailList, List<String> gstNumberList,
	    List<String> panNumberList);

	boolean existsByPanNo(String panNo);

	boolean existsByGstNo(String gstNo);

	@Query("Select c from Customer c where c.customerType = :customerType and Lower(c.name) like LOWER(:search)")
	Page<Customer> getCustomerTypeList(CustomerType customerType, String search, PageRequest of);

}
