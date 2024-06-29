package com.alkl1m.contractor.web.controller;

import com.alkl1m.auditlogspringbootautoconfigure.annotation.AuditLog;
import com.alkl1m.contractor.domain.entitiy.Industry;
import com.alkl1m.contractor.service.IndustryService;
import com.alkl1m.contractor.web.payload.NewIndustryPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/industry")
public class IndustryController {

    private final IndustryService industryService;

    @AuditLog
    @GetMapping("/all")
    public List<Industry> getAllIndustries() {
        return industryService.getAllIndustries();
    }

    @AuditLog
    @GetMapping("/{id}")
    public Industry getIndustryById(@PathVariable Long id) {
        return industryService.getIndustryById(id);
    }

    @AuditLog
    @PutMapping("/save")
    public Industry saveIndustry(@Validated @RequestBody NewIndustryPayload payload) {
        return industryService.saveIndustry(payload);
    }

    @AuditLog
    @DeleteMapping("/delete/{id}")
    public void deleteIndustry(@PathVariable Long id) {
        industryService.deleteIndustry(id);
    }

}
