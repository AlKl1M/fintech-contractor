package com.alkl1m.contractor.repository;

import com.alkl1m.contractor.domain.entitiy.OrgForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrgFormRepository extends JpaRepository<OrgForm, Long> {

}
