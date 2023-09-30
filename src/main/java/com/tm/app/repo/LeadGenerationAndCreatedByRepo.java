package com.tm.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

@Repository
@NoRepositoryBean
public interface LeadGenerationAndCreatedByRepo <T, ID> extends JpaRepository<T, ID>  {
	
	List<T> findAll();

}
