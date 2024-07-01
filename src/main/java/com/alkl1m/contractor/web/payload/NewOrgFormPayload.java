package com.alkl1m.contractor.web.payload;

import com.alkl1m.contractor.domain.entitiy.OrgForm;
import jakarta.validation.constraints.NotNull;

public record NewOrgFormPayload(
        Long id,
        @NotNull(message = "{contractor.org_form.errors.name_is_null}")
        String name
) {

        public static OrgForm toOrgForm(NewOrgFormPayload payload) {
                return OrgForm.builder()
                        .id(payload.id())
                        .name(payload.name())
                        .isActive(true)
                        .build();
        }

}
