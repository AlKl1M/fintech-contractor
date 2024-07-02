package com.alkl1m.contractor.service;

import com.alkl1m.contractor.domain.entitiy.OrgForm;
import com.alkl1m.contractor.web.payload.NewOrgFormPayload;

import java.util.List;

/**
 * @author alkl1m
 */
public interface OrgFormService {

    List<OrgForm> getAllOrgForms();

    OrgForm getOrgFormById(Long id);

    OrgForm saveOrgForm(NewOrgFormPayload payload);

    void deleteOrgForm(Long id);

}
