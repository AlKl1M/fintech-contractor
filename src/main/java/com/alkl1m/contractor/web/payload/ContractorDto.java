package com.alkl1m.contractor.web.payload;

import com.alkl1m.contractor.domain.entitiy.Contractor;

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
        String id,
        String parentId,
        String name,
        String nameFull,
        String inn,
        String ogrn,
        CountryDto country,
        IndustryDto industry,
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
