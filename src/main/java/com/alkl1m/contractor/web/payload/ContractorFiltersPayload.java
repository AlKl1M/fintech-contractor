package com.alkl1m.contractor.web.payload;

import com.alkl1m.contractor.domain.entitiy.Industry;

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
public record ContractorFiltersPayload(String id,
                                       String parentId,
                                       String name,
                                       String nameFull,
                                       String inn,
                                       String ogrn,
                                       String countryName,
                                       Industry industry,
                                       String orgFormName) {
}
