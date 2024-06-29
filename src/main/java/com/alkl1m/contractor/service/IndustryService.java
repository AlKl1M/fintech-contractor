package com.alkl1m.contractor.service;

import com.alkl1m.contractor.domain.entitiy.Industry;
import com.alkl1m.contractor.web.payload.NewIndustryPayload;

import java.util.List;

public interface IndustryService {

    List<Industry> getAllIndustries();

    Industry getIndustryById(Long id);

    Industry saveIndustry(NewIndustryPayload payload);

    void deleteIndustry(Long id);

}
