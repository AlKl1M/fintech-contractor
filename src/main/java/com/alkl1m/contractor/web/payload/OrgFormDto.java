package com.alkl1m.contractor.web.payload;

import com.alkl1m.contractor.domain.entitiy.OrgForm;

public record OrgFormDto(
        Long id,
        String name,
        boolean isActive
) {

    public static OrgFormDto from(OrgForm orgForm) {
        return new OrgFormDto(orgForm.getId(),
                orgForm.getName(),
                orgForm.isActive());
    }

}
