package com.alkl1m.contractor.web.payload;

import com.alkl1m.contractor.domain.entitiy.Industry;

public record IndustryDto(
        Long id,
        String name,
        boolean isActive
) {

    public static IndustryDto from(Industry industry) {
        return new IndustryDto(industry.getId(),
                industry.getName(),
                industry.isActive());
    }

}
