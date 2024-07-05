package com.alkl1m.contractor.web.payload;

import com.alkl1m.contractor.domain.entitiy.OrgForm;

/**
 * DTO для получения организационных форм без вспомогательных полей.
 *
 * @param id   организационной формы.
 * @param name организационной формы.
 */
public record OrgFormDto(
        Long id,
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
