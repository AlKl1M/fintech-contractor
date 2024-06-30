package com.alkl1m.contractor.service.impl;

import com.alkl1m.contractor.domain.entitiy.Contractor;
import com.alkl1m.contractor.domain.entitiy.Country;
import com.alkl1m.contractor.domain.entitiy.Industry;
import com.alkl1m.contractor.domain.entitiy.OrgForm;
import com.alkl1m.contractor.domain.exception.ContractorNotFoundException;
import com.alkl1m.contractor.domain.exception.CountryNotFoundException;
import com.alkl1m.contractor.domain.exception.IndustryNotFoundException;
import com.alkl1m.contractor.domain.exception.OrgFormNotFoundException;
import com.alkl1m.contractor.repository.jdbc.ContractorJdbcRepository;
import com.alkl1m.contractor.repository.ContractorRepository;
import com.alkl1m.contractor.repository.CountryRepository;
import com.alkl1m.contractor.repository.IndustryRepository;
import com.alkl1m.contractor.repository.OrgFormRepository;
import com.alkl1m.contractor.repository.spec.ContractorSpecifications;
import com.alkl1m.contractor.service.ContractorService;
import com.alkl1m.contractor.web.payload.ContractorFiltersPayload;
import com.alkl1m.contractor.web.payload.NewContractorPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ContractorServiceImpl implements ContractorService {

    private final ContractorRepository contractorRepository;
    private final ContractorJdbcRepository contractorJdbcRepository;
    private final CountryRepository countryRepository;
    private final IndustryRepository industryRepository;
    private final OrgFormRepository orgFormRepository;

    @Override
    public Page<Contractor> getContractorsByParameters(ContractorFiltersPayload payload, int page, int size) {
        Specification<Contractor> spec = ContractorSpecifications.getContractorByParameters(payload);
        PageRequest pageRequest = PageRequest.of(page, size);

        return contractorRepository.findAll(spec, pageRequest);
    }

    @Override
    @Transactional
    public Contractor saveOrUpdate(NewContractorPayload payload) {
        Country country = countryRepository.findById(payload.country_id()).orElseThrow(() -> new CountryNotFoundException("Country not found"));
        Industry industry = industryRepository.findById(payload.industry_id()).orElseThrow(() -> new IndustryNotFoundException("Industry not found"));
        OrgForm orgForm = orgFormRepository.findById(payload.orgForm_id()).orElseThrow(() -> new OrgFormNotFoundException("OrgForm not found"));

        return contractorRepository.findById(payload.id())
                .map(existingContractor -> updateExistingContractor(payload, existingContractor, country, industry, orgForm))
                .orElseGet(() -> createNewContractor(payload, country, industry, orgForm));
    }

    @Override
    public Page<Contractor> getContractorPageableById(String id, Pageable pageable) {
        return contractorRepository.findById(id, pageable);
    }

    @Override
    public Contractor findContractorWithDetailsById(String id) {
        Optional<Contractor> optionalContractor = contractorJdbcRepository.findById(id);
        return optionalContractor.orElseThrow(() -> new RuntimeException("Contractor not found for id: " + id));
    }

    @Override
    @Transactional
    public void deleteContractorById(String id) {
        Optional<Contractor> optionalContractor = contractorRepository.findById(id);
        optionalContractor.ifPresentOrElse(contractor -> {
            contractor.setActive(false);
            contractorRepository.save(contractor);
        }, () -> {
            throw new ContractorNotFoundException("Contractor with id " + id + " not found");
        });
    }

    private Contractor createNewContractor(NewContractorPayload payload, Country country, Industry industry, OrgForm orgForm) {
        return contractorRepository.save(
                NewContractorPayload.toContractor(payload, country, industry, orgForm, "1")
        );
    }

    private Contractor updateExistingContractor(NewContractorPayload payload, Contractor existingContractor, Country country, Industry industry, OrgForm orgForm) {
        existingContractor.setParentId(payload.parentId());
        existingContractor.setName(payload.name());
        existingContractor.setNameFull(payload.nameFull());
        existingContractor.setInn(payload.inn());
        existingContractor.setOgrn(payload.ogrn());
        existingContractor.setCountry(country);
        existingContractor.setIndustry(industry);
        existingContractor.setOrgForm(orgForm);
        existingContractor.setModifyDate(new Date());
        existingContractor.setModifyUserId("1");
        existingContractor.setActive(true);
        return contractorRepository.save(existingContractor);
    }

}
