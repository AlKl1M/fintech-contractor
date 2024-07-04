package com.alkl1m.contractor.service;

import com.alkl1m.contractor.web.payload.NewOrgFormPayload;
import com.alkl1m.contractor.web.payload.OrgFormDto;

import java.util.List;

/**
 * @author alkl1m
 */
public interface OrgFormService {

    List<OrgFormDto> getAllOrgForms();

    OrgFormDto getOrgFormById(Long id);

    OrgFormDto saveOrgForm(NewOrgFormPayload payload);

    void deleteOrgForm(Long id);

}
