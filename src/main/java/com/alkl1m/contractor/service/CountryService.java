package com.alkl1m.contractor.service;

import com.alkl1m.contractor.web.payload.CountryDto;
import com.alkl1m.contractor.web.payload.NewCountryPayload;

import java.util.List;

/**
 * @author alkl1m
 */
public interface CountryService {

    List<CountryDto> getAllCountries();

    CountryDto getCountryById(String id);

    CountryDto saveCountry(NewCountryPayload payload);

    void deleteCountry(String id);

}
