package com.alkl1m.contractor.web.payload;

import com.alkl1m.contractor.domain.entitiy.Industry;
import jakarta.validation.constraints.NotNull;

public record NewIndustryPayload(
        @NotNull(message = "Id cannot be null")
        Long id,
        @NotNull(message = "Name cannot be null")
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
