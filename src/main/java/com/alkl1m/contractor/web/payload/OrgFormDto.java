package com.alkl1m.contractor.web.payload;

import com.alkl1m.contractor.domain.entitiy.OrgForm;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO для получения организационных форм без вспомогательных полей.
 *
 * @param id   организационной формы.
 * @param name организационной формы.
 */
public record OrgFormDto(

        @Schema(description = "Уникальный идентификатор")
        Long id,

        @Schema(description = "Название организационной формы")
        String name
) {

    /**
     * Метод для преобразования из организационной формы в DTO.
     *
     * @param orgForm организационная форма, из которой получаем DTO.
     * @return DTO организационной формы без вспомогательных полей.
     */
    public static OrgFormDto from(OrgForm orgForm) {
        return new OrgFormDto(orgForm.getId(),
                orgForm.getName());
    }

}
