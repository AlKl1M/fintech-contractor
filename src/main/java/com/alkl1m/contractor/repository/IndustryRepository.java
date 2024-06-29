package com.alkl1m.contractor.repository;

import com.alkl1m.contractor.domain.entitiy.Industry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndustryRepository extends JpaRepository<Industry, Long> {

}