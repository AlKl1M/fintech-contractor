package com.alkl1m.contractor.web.payload;

import com.alkl1m.contractor.domain.entitiy.Country;
import jakarta.validation.constraints.NotNull;

public record NewCountryPayload(
        @NotNull(message = "Id cannot be null")
        String id,
        @NotNull(message = "Name cannot be null")
        String name
) {

        public static Country toCountry(NewCountryPayload payload) {
                return Country.builder()
                        .id(payload.id())
                        .name(payload.name())
                        .isActive(true)
                        .build();
        }

}
