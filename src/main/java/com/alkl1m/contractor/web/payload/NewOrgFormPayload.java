package com.alkl1m.contractor.web.payload;

import com.alkl1m.contractor.domain.entitiy.OrgForm;
import jakarta.validation.constraints.NotNull;

/**
 * DTO для передачи RequestBody новой или обновленной организационной формы.
 *
 * @param id организационной формы.
 * @param name организационной формы.
 * @author alkl1m
 */
public record NewOrgFormPayload(
        Long id,
        @NotNull(message = "{contractor.org_form.errors.name_is_null}")
        String name
) {

        /**
         * Маппер для преобразования DTO организационной формы в организационную форму.
         *
         * @param payload DTO новой или обновляемой организационной формы.
         * @return объект организационной формы.
         */
        public static OrgForm toOrgForm(NewOrgFormPayload payload) {
                return OrgForm.builder()
                        .id(payload.id())
                        .name(payload.name())
                        .isActive(true)
                        .build();
        }

}
