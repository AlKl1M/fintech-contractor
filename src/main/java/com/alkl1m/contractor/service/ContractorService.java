package com.alkl1m.contractor.service;

import com.alkl1m.contractor.domain.entitiy.Contractor;
import com.alkl1m.contractor.web.payload.ContractorFiltersPayload;
import com.alkl1m.contractor.web.payload.NewContractorPayload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContractorService {

    Page<Contractor> getContractorsByParameters(ContractorFiltersPayload payload, int page, int size);

    Contractor saveOrUpdate(NewContractorPayload payload);

    Page<Contractor> getContractorPageableById(String id, Pageable pageable);

    Page<Contractor> findContractorWithDetailsById(String id, Pageable pageable);

    void deleteContractorById(String id);

}
