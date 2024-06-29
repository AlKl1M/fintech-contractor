package com.alkl1m.contractor.service;

import com.alkl1m.contractor.domain.entitiy.Country;
import com.alkl1m.contractor.web.payload.NewCountryPayload;

import java.util.List;

public interface CountryService {

    List<Country> getAllCountries();

    Country getCountryById(String id);

    Country saveCountry(NewCountryPayload payload);

    void deleteCountry(String id);

}
