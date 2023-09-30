package com.tm.app.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tm.app.entity.MachinerySpares;

@Repository
public interface MachinerySparesRepo extends JpaRepository<MachinerySpares, Long> {

    MachinerySpares getMachinerySparesById(Long id);

    Page<MachinerySpares> findByTechnicianNameLikeIgnoreCase(String search, PageRequest of);

    @Query(value = "select tmsd.machinery_spares_id ,tmsd.spare_item_name,tmsd.estimated_date from t_machinery_spare_details tmsd  where tmsd.estimated_date <=current_date-2 and tmsd.service_return_date is null", nativeQuery = true)
    List<Object[]> getSparesNotification();

}
