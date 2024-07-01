package com.alkl1m.contractor.web.payload;

import com.alkl1m.contractor.domain.entitiy.Industry;
import jakarta.validation.constraints.NotNull;

public record NewIndustryPayload(
        Long id,
        @NotNull(message = "{contractor.industry.errors.name_is_null}")
        String name
) {

        public static Industry toIndustry(NewIndustryPayload payload) {
                return Industry.builder()
                        .id(payload.id())
                        .name(payload.name())
                        .isActive(true)
                        .build();
        }

}
