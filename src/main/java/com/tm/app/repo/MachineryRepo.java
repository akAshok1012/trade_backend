package com.tm.app.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.Machinery;

@Repository
public interface MachineryRepo extends JpaRepository<Machinery, Long>{
	
	Machinery getMachineryById(Long id);

	@Query(value="SELECT m FROM Machinery m where LOWER(m.serialNumber) LIKE LOWER(:search) OR LOWER(m.modelNumber) LIKE LOWER(:search)")
	Page<Machinery> findBySerialNumberLikeIgnoreCase(String search, PageRequest of);

}
