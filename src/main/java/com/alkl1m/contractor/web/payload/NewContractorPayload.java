package com.alkl1m.contractor.web.payload;

import com.alkl1m.contractor.domain.entitiy.Contractor;
import com.alkl1m.contractor.domain.entitiy.Country;
import com.alkl1m.contractor.domain.entitiy.Industry;
import com.alkl1m.contractor.domain.entitiy.OrgForm;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record NewContractorPayload(
        @NotNull(message = "Id cannot be null")
        String id,
        String parentId,
        @NotNull(message = "Name cannot be null")
        String name,
        String nameFull,
        String inn,
        String ogrn,
        @NotNull(message = "Country ID cannot be null")
        String country_id,
        @NotNull(message = "Industry ID cannot be null")
        Long industry_id,
        @NotNull(message = "Organization Form ID cannot be null")
        Long orgForm_id) {

        public static Contractor toContractor(NewContractorPayload payload,
                                      Country country,
                                      Industry industry,
                                      OrgForm orgForm,
                                      String userId) {
                Date date = new Date();
                return Contractor.builder()
                        .id(payload.id)
                        .parentId(payload.parentId())
                        .name(payload.name)
                        .nameFull(payload.nameFull)
                        .inn(payload.inn)
                        .ogrn(payload.ogrn())
                        .country(country)
                        .industry(industry)
                        .orgForm(orgForm)
                        .createDate(date)
                        .modifyDate(date)
                        .createUserId(userId)
                        .modifyUserId(userId)
                        .isActive(true)
                        .build();
        }

}
