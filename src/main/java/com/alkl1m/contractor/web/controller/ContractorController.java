package com.alkl1m.contractor.web.controller;

import com.alkl1m.auditlogspringbootautoconfigure.annotation.AuditLog;
import com.alkl1m.contractor.domain.entitiy.Contractor;
import com.alkl1m.contractor.service.ContractorService;
import com.alkl1m.contractor.web.payload.ContractorFiltersPayload;
import com.alkl1m.contractor.web.payload.NewContractorPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contractor")
public class ContractorController {

    private final ContractorService contractorService;

    @AuditLog
    @PostMapping("/search")
    public Page<Contractor> getContractorsByParameters(
            @RequestBody ContractorFiltersPayload payload,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return contractorService.getContractorsByParameters(payload, page, size);
    }

    @AuditLog
    @PutMapping("/save")
    public Contractor saveOrUpdateContractor(@Validated @RequestBody NewContractorPayload payload) {
        return contractorService.saveOrUpdate(payload);
    }

    @AuditLog
    @GetMapping("/{id}")
    public Page<Contractor> getContractorPageableById(@PathVariable String id, Pageable pageable) {
        return contractorService.getContractorPageableById(id, pageable);
    }

    @AuditLog
    @GetMapping("/crud/{id}")
    public Contractor findContractorWithDetailsById(@PathVariable String id) {
        return contractorService.findContractorWithDetailsById(id);
    }

    @AuditLog
    @DeleteMapping("/delete/{id}")
    public void deleteContractorById(@PathVariable String id) {
        contractorService.deleteContractorById(id);
    }

}
