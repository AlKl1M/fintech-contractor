package com.alkl1m.contractor.web.payload;

import com.alkl1m.contractor.domain.entitiy.Contractor;
import com.alkl1m.contractor.domain.entitiy.Country;
import com.alkl1m.contractor.domain.entitiy.Industry;
import com.alkl1m.contractor.domain.entitiy.OrgForm;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

/**
 * DTO для передачи RequestBody нового или обновляемого контрагента. ID известен.
 *
 * @param id          контрагента.
 * @param parentId    контрагента.
 * @param name        контрагента.
 * @param nameFull    контрагента.
 * @param inn         контрагента.
 * @param ogrn        контрагента.
 * @param country_id  контрагента.
 * @param industry_id контрагента.
 * @param orgForm_id  контрагента.
 * @author alkl1m
 */
public record NewContractorPayload(
        @NotNull(message = "{contractor.contractor.errors.id_is_null}")
        String id,
        String parentId,
        @NotNull(message = "{contractor.contractor.errors.name_is_null}")
        String name,
        String nameFull,
        String inn,
        String ogrn,
        @NotNull(message = "{contractor.contractor.errors.country_id_is_null}")
        String country_id,
        @NotNull(message = "{contractor.contractor.errors.industry_id_is_null}")
        Long industry_id,
        @NotNull(message = "{contractor.contractor.errors.org_form_id_is_null}")
        Long orgForm_id) {

    /**
     * Маппер для преобразования DTO контрагента в объект контрагента.
     *
     * @param payload  DTO контрагента.
     * @param country  контрагента.
     * @param industry контрагента.
     * @param orgForm  контрагента.
     * @param userId   контрагента.
     * @return
     */
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
