package com.alkl1m.contractor.service.impl;

import com.alkl1m.contractor.domain.entitiy.Country;
import com.alkl1m.contractor.domain.exception.CountryNotFoundException;
import com.alkl1m.contractor.repository.CountryRepository;
import com.alkl1m.contractor.service.CountryService;
import com.alkl1m.contractor.web.payload.NewCountryPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Реализация сервиса для работы со странами.
 *
 * @author alkl1m
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    /**
     * Метод для получения всех стран.
     *
     * @return список стран.
     */
    @Override
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    /**
     * Метод для поиска страны по id.
     *
     * @param id  страны.
     * @return объект страны.
     */
    @Override
    public Country getCountryById(String id) {
        return countryRepository.findById(id).orElseThrow(
                () -> new CountryNotFoundException("Country with id " + id + " not found!")
        );
    }

    /**
     * Метод для создания или обновления страны.
     *
     * @param payload dto для новой страны.
     * @return созданная или обновленная страна.
     */
    @Override
    @Transactional
    public Country saveCountry(NewCountryPayload payload) {
        return countryRepository.save(NewCountryPayload.toCountry(payload));
    }

    /**
     * Удаление страны по id
     *
     * @param id страны.
     */
    @Override
    @Transactional
    public void deleteCountry(String id) {
        Optional<Country> optionalCountry = countryRepository.findById(id);
        optionalCountry.ifPresentOrElse(country -> {
            country.setActive(false);
            countryRepository.save(country);
        }, () -> {
            throw new CountryNotFoundException("Country with id " + id + " not found");
        });
    }

}
