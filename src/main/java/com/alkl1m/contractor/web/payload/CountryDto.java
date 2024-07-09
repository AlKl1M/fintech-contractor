package com.alkl1m.contractor.web.payload;

import com.alkl1m.contractor.domain.entitiy.Country;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO для получения стран без вспомогательных полей.
 *
 * @param id   страны.
 * @param name страны.
 */
public record CountryDto(

        @Schema(description = "Уникальный идентификатор страны")
        String id,

        @Schema(description = "Название страны")
        String name
) {

    /**
     * Метод для преобразования из страны в DTO.
     *
     * @param country страна, из которой получаем DTO.
     * @return DTO страны без вспомогательных полей.
     */
    public static CountryDto from(Country country) {
        return new CountryDto(country.getId(),
                country.getName());
    }

}
