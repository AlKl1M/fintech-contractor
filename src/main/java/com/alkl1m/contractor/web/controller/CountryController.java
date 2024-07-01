package com.alkl1m.contractor.web.controller;

import com.alkl1m.auditlogspringbootautoconfigure.annotation.AuditLog;
import com.alkl1m.contractor.domain.entitiy.Country;
import com.alkl1m.contractor.service.CountryService;
import com.alkl1m.contractor.web.payload.NewCountryPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * RestController для работы со странами.
 *
 * @author alkl1m
 */
@RestController
@RequestMapping("/country")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;

    /**
     * Метод для получения списка всех стран с использованием аннотации @AuditLog.
     *
     * @return список всех стран.
     */
    @AuditLog
    @GetMapping("/all")
    public List<Country> getAllCountries() {
        return countryService.getAllCountries();
    }

    /**
     * Метод для получения страны по идентификатору с использованием аннотации @AuditLog.
     *
     * @param id идентификатор страны.
     * @return найденная страна.
     */
    @AuditLog
    @GetMapping("/{id}")
    public Country getCountryById(@PathVariable String id) {
        return countryService.getCountryById(id);
    }

    /**
     * Метод для сохранения или обновления страны с использованием аннотации @AuditLog.
     *
     * @param payload объект с данными новой страны.
     * @return сохраненная или обновленная страна.
     */
    @AuditLog
    @PutMapping("/save")
    public Country saveCountry(@Validated @RequestBody NewCountryPayload payload) {
        return countryService.saveCountry(payload);
    }

    /**
     * Метод для удаления страны по идентификатору с использованием аннотации @AuditLog.
     *
     * @param id идентификатор страны.
     */
    @AuditLog
    @DeleteMapping("/delete/{id}")
    public void deleteCountry(@PathVariable String id) {
        countryService.deleteCountry(id);
    }

}
