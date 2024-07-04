package com.alkl1m.contractor.service;

import com.alkl1m.contractor.web.payload.IndustryDto;
import com.alkl1m.contractor.web.payload.NewIndustryPayload;

import java.util.List;

/**
 * @author alkl1m
 */
public interface IndustryService {

    List<IndustryDto> getAllIndustries();

    IndustryDto getIndustryById(Long id);

    IndustryDto saveIndustry(NewIndustryPayload payload);

    void deleteIndustry(Long id);

}
