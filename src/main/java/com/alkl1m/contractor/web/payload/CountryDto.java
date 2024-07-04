package com.alkl1m.contractor.web.payload;

import com.alkl1m.contractor.domain.entitiy.Country;

public record CountryDto(
        String id,
        String name,
        boolean isActive
) {

    public static CountryDto from(Country country) {
        return new CountryDto(country.getId(),
                country.getName(),
                country.isActive());
    }

}
