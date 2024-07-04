package com.alkl1m.contractor.service;

import com.alkl1m.contractor.web.payload.ContractorDto;
import com.alkl1m.contractor.web.payload.ContractorFiltersPayload;
import com.alkl1m.contractor.web.payload.NewContractorPayload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author alkl1m
 */
public interface ContractorService {

    Page<ContractorDto> getContractorsByParameters(ContractorFiltersPayload payload, int page, int size);

    ContractorDto saveOrUpdate(NewContractorPayload payload);

    Page<ContractorDto> getContractorPageableById(String id, Pageable pageable);

    ContractorDto findContractorWithDetailsById(String id);

    void deleteContractorById(String id);

}
