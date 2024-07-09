package com.alkl1m.contractor.web.payload;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO для передачи RequestBody списка фильтрации контрагентов.
 *
 * @param id          контрагента.
 * @param parentId    контрагента.
 * @param name        контрагента.
 * @param nameFull    контрагента.
 * @param inn         контрагента.
 * @param ogrn        контрагента.
 * @param countryName контрагента.
 * @param industry    контрагента.
 * @param orgFormName контрагента.
 */
public record ContractorFiltersPayload(
        @Schema(description = "Уникальный идентификатор")
        String id,

        @Schema(description = "Уникальный идентификатор родителя контрагента")
        String parentId,

        @Schema(description = "Имя контрагента")
        String name,

        @Schema(description = "Полное имя контрагента")
        String nameFull,

        @Schema(description = "Идентификационный номер налогоплательщика")
        String inn,

        @Schema(description = "Основной государственный регистрационный номер")
        String ogrn,

        @Schema(description = "Название страны")
        String countryName,

        @Schema(description = "DTO индустриального номера")
        IndustryDto industry,

        @Schema(description = "Название организационной формы")
        String orgFormName) {
}
