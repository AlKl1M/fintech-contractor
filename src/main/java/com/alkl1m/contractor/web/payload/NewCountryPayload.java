package com.alkl1m.contractor.web.payload;

import com.alkl1m.contractor.domain.entitiy.Country;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO для передачи RequestBody новой или обновляемой страны. ID известен.
 *
 * @param id   страны.
 * @param name страны.
 * @author alkl1m
 */
public record NewCountryPayload(
        @NotNull(message = "{contractor.country.errors.id_is_null}")
        @Size(min = 1, max = 3, message = "{contractor.country.errors.id_size_is_invalid}")
        String id,
        @NotNull(message = "{contractor.country.errors.name_is_null}")
        String name
) {

    /**
     * Маппер для преобразования DTO страны в страну.
     *
     * @param payload DTO новой или обновляемой страны.
     * @return объект страны.
     */
    public static Country toCountry(NewCountryPayload payload) {
        return Country.builder()
                .id(payload.id())
                .name(payload.name())
                .isActive(true)
                .build();
    }

}
