package com.alkl1m.contractor.web.payload;

import com.alkl1m.contractor.domain.entitiy.Contractor;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO для контрагентов без вспомогательных полей.
 *
 * @param id       контрагента.
 * @param parentId контрагента.
 * @param name     контрагента.
 * @param nameFull контрагента.
 * @param inn      контрагента.
 * @param ogrn     контрагента.
 * @param country  контрагента.
 * @param industry контрагента.
 * @param orgForm  контрагента.
 * @author alkl1m
 */
public record ContractorDto(

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

        @Schema(description = "DTO страны")
        CountryDto country,

        @Schema(description = "DTO индустриального номера")
        IndustryDto industry,

        @Schema(description = "DTO организационной формы")
        OrgFormDto orgForm
) {

    /**
     * DTO для получения контрагента без вспомогательных полей.
     *
     * @param contractor контрагент, из которого собираем DTO.
     * @return DTO контрагента.
     */
    public static ContractorDto from(Contractor contractor) {
        return new ContractorDto(
                contractor.getId(),
                contractor.getParentId(),
                contractor.getName(),
                contractor.getNameFull(),
                contractor.getInn(),
                contractor.getOgrn(),
                CountryDto.from(contractor.getCountry()),
                IndustryDto.from(contractor.getIndustry()),
                OrgFormDto.from(contractor.getOrgForm())
        );
    }

}
