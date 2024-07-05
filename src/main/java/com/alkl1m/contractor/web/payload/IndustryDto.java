package com.alkl1m.contractor.web.payload;

import com.alkl1m.contractor.domain.entitiy.Industry;

/**
 * DTO для получения индустриальных кодов без вспомогательных полей.
 *
 * @param id   индустриального кода.
 * @param name индустриального кода.
 */
public record IndustryDto(
        Long id,
        String name
) {

    /**
     * Метод для преобразования из индустриального кода в DTO.
     *
     * @param industry индустриальный код, из которого получаем DTO.
     * @return DTO индустриального кода без вспомогательных полей.
     */
    public static IndustryDto from(Industry industry) {
        return new IndustryDto(industry.getId(),
                industry.getName());
    }

}
