package com.alkl1m.contractor.web.payload;

import com.alkl1m.contractor.domain.entitiy.OrgForm;
import jakarta.validation.constraints.NotNull;

public record NewOrgFormPayload(
        @NotNull(message = "Id cannot be null")
        Long id,
        @NotNull(message = "Name cannot be null")
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
