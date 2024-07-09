package com.alkl1m.contractor.web.controller;

import com.alkl1m.auditlogspringbootautoconfigure.annotation.AuditLog;
import com.alkl1m.contractor.service.CountryService;
import com.alkl1m.contractor.web.payload.CountryDto;
import com.alkl1m.contractor.web.payload.NewCountryPayload;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
@Tag(name = "country", description = "The Country API")
@RestController
@RequestMapping("/country")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;

    /**
     * Метод для получения списка всех стран с использованием аннотации @AuditLog.
     *
     * @return ResponseEntity список всех пагинированных стран.
     */
    @Operation(summary = "Получение списка всех стран", tags = "country")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Получил список всех стран",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = CountryDto.class)))
                    })
    })
    @AuditLog
    @GetMapping("/all")
    public ResponseEntity<List<CountryDto>> getAllCountries() {
        List<CountryDto> countries = countryService.getAllCountries();
        return ResponseEntity.ok(countries);
    }

    /**
     * Метод для получения страны по идентификатору с использованием аннотации @AuditLog.
     *
     * @param id идентификатор страны.
     * @return ResponseEntity с найденной страной.
     */
    @Operation(summary = "Получение страны по ID", tags = "country")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Нашел страну по переданному ID",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = CountryDto.class)))
                    })
    })
    @AuditLog
    @GetMapping("/{id}")
    public ResponseEntity<CountryDto> getCountryById(@PathVariable String id) {
        CountryDto country = countryService.getCountryById(id);
        return ResponseEntity.ok(country);
    }

    /**
     * Метод для сохранения или обновления страны с использованием аннотации @AuditLog.
     *
     * @param payload объект с данными новой страны.
     * @return ResponseEntity с сохраненной или обновленной страной.
     */
    @Operation(summary = "Сохранение/обновление страны", tags = "country")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Создал/обновил страну ",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = CountryDto.class)))
                    })
    })
    @AuditLog
    @PutMapping("/save")
    public ResponseEntity<CountryDto> saveCountry(@Validated @RequestBody NewCountryPayload payload) {
        CountryDto savedCountry = countryService.saveCountry(payload);
        return ResponseEntity.ok(savedCountry);
    }

    /**
     * Метод для удаления страны по идентификатору с использованием аннотации @AuditLog.
     *
     * @param id идентификатор страны.
     * @return ResponseEntity с результатом удаления.
     */
    @Operation(summary = "Удаление страны по ID", tags = "country")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Удалил страну по ID",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = CountryDto.class)))
                    })
    })
    @AuditLog
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCountry(@PathVariable String id) {
        countryService.deleteCountry(id);
        return ResponseEntity.ok().build();
    }

}
