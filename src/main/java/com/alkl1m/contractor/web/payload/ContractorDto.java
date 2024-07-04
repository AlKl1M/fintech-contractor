package com.alkl1m.contractor.web.payload;

import com.alkl1m.contractor.domain.entitiy.Contractor;
import com.alkl1m.contractor.domain.entitiy.Country;
import com.alkl1m.contractor.domain.entitiy.Industry;
import com.alkl1m.contractor.domain.entitiy.OrgForm;

import java.util.Date;

public record ContractorDto(
        String id,
        String parentId,
        String name,
        String nameFull,
        String inn,
        String ogrn,
        Country country,
        Industry industry,
        OrgForm orgForm,
        Date createDate,
        Date modifyDate,
        String createUserId,
        String modifyUserId,
        boolean isActive
) {

    public static ContractorDto from(Contractor contractor) {
        return new ContractorDto(
                contractor.getId(),
                contractor.getParentId(),
                contractor.getName(),
                contractor.getNameFull(),
                contractor.getInn(),
                contractor.getOgrn(),
                contractor.getCountry(),
                contractor.getIndustry(),
                contractor.getOrgForm(),
                contractor.getCreateDate(),
                contractor.getModifyDate(),
                contractor.getCreateUserId(),
                contractor.getModifyUserId(),
                contractor.isActive()
        );
    }

}
