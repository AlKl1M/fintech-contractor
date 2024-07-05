package com.alkl1m.contractor.web.payload;

import com.alkl1m.contractor.domain.entitiy.Industry;
import jakarta.validation.constraints.NotNull;

/**
 * DTO для передачи RequestBody нового или обновленного индустриального кода.
 *
 * @param id   индустриального кода.
 * @param name индустриального кода.
 * @author alkl1m
 */
public record NewIndustryPayload(
        Long id,
        @NotNull(message = "{contractor.industry.errors.name_is_null}")
        String name
) {

    /**
     * Маппер для преобразования DTO индустриального кода в индустриальный код.
     *
     * @param payload DTO нового или обновляемого индустриального кода.
     * @return объект индустриального кода.
     */
    public static Industry toIndustry(NewIndustryPayload payload) {
        return Industry.builder()
                .id(payload.id())
                .name(payload.name())
                .isActive(true)
                .build();
    }

}
