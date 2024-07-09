package com.alkl1m.contractor.web.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;

/**
 * DTO для пагинированных контрагентов. Используется для корректного отображения в swagger.
 *
 * @param contractors список пагинированных DTO контрагентов
 */
public record ContractorsDto(

        @Schema(description = "DTO содержащая пагинированный список контрагентов")
        Page<ContractorDto> contractors

) {
    /**
     * @param contractors список пагинированных DTO контрагентов.
     * @return DTO для пагинированных контрагентов.
     */
    public static ContractorsDto from(Page<ContractorDto> contractors) {
        return new ContractorsDto(contractors);
    }

}

