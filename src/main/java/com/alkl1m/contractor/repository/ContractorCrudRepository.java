package com.alkl1m.contractor.repository;

import com.alkl1m.contractor.domain.entitiy.Contractor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ContractorCrudRepository extends CrudRepository<Contractor, String> {

    @Query("SELECT c FROM Contractor c WHERE c.id = :id")
    Page<Contractor> findContractorWithDetailsById(@Param("id") String id, Pageable pageable);

}
