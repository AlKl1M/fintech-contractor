package com.alkl1m.contractor.service.impl;

import com.alkl1m.contractor.domain.entitiy.OrgForm;
import com.alkl1m.contractor.domain.exception.OrgFormNotFoundException;
import com.alkl1m.contractor.repository.OrgFormRepository;
import com.alkl1m.contractor.service.OrgFormService;
import com.alkl1m.contractor.web.payload.NewOrgFormPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrgFormServiceImpl implements OrgFormService {

    private final OrgFormRepository orgFormRepository;

    @Override
    public List<OrgForm> getAllOrgForms() {
        return orgFormRepository.findAll();
    }

    @Override
    public OrgForm getOrgFormById(Long id) {
        return orgFormRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public OrgForm saveOrgForm(NewOrgFormPayload payload) {
        return orgFormRepository.save(NewOrgFormPayload.toOrgForm(payload));
    }

    @Override
    @Transactional
    public void deleteOrgForm(Long id) {
        Optional<OrgForm> optionalOrgForm = orgFormRepository.findById(id);
        optionalOrgForm.ifPresentOrElse(orgForm -> {
            orgForm.setActive(false);
            orgFormRepository.save(orgForm);
        }, () -> {
            throw new OrgFormNotFoundException("OrgForm with id " + id + " not found");
        });
    }

}
