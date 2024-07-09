package com.alkl1m.contractor.service;

import com.alkl1m.contractor.web.payload.ContractorDto;
import com.alkl1m.contractor.web.payload.ContractorFiltersPayload;
import com.alkl1m.contractor.web.payload.ContractorsDto;
import com.alkl1m.contractor.web.payload.NewContractorPayload;
import org.springframework.data.domain.Pageable;

/**
 * @author alkl1m
 */
public interface ContractorService {

    ContractorsDto getContractorsByParameters(ContractorFiltersPayload payload, Pageable pageable);

    ContractorsDto getContractorsWithCrudByParameters(ContractorFiltersPayload payload, Pageable pageable);

    ContractorDto saveOrUpdate(NewContractorPayload payload);

    ContractorDto findById(String id);

    void deleteContractorById(String id);

}
