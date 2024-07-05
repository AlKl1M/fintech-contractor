package com.alkl1m.contractor.repository;

import com.alkl1m.contractor.domain.entitiy.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author alkl1m
 */
@Repository
public interface CountryRepository extends JpaRepository<Country, String> {
    List<Country> findAllByIsActiveTrue();
}
