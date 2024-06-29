package com.alkl1m.contractor.web.payload;

import com.alkl1m.contractor.domain.entitiy.Industry;

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
