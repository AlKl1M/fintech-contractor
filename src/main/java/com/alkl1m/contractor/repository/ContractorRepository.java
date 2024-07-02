package com.alkl1m.contractor.repository;

import com.alkl1m.contractor.domain.entitiy.Contractor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author alkl1m
 */
@Repository
public interface ContractorRepository extends JpaRepository<Contractor, String>, JpaSpecificationExecutor<Contractor> {

    Page<Contractor> findById(String id, Pageable pageable);

}
