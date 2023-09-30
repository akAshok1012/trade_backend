package com.tm.app.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.Production;

@Repository
public interface ProductionRepository extends JpaRepository<Production, Long> {

	@Query("SELECT p FROM Production p join ItemMaster im on(p.itemMaster=im.id) where LOWER(im.itemName) like LOWER(:search)")
	Page<Production> findByItemName(String search, PageRequest of);

}
