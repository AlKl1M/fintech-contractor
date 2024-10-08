package com.alkl1m.contractor.web.controller;

import com.alkl1m.auditlogspringbootautoconfigure.annotation.AuditLog;
import com.alkl1m.contractor.service.IndustryService;
import com.alkl1m.contractor.web.payload.IndustryDto;
import com.alkl1m.contractor.web.payload.NewIndustryPayload;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
 * RestController для работы с индустриальными кодами.
 *
 * @author alkl1m
 */
@Tag(name = "industry", description = "The Industry API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/industry")
public class IndustryController {

    private final IndustryService industryService;

    /**
     * Метод для получения списка всех индустриальных кодов с использованием аннотации @AuditLog.
     *
     * @return ResponseEntity список всех индустриальных кодов.
     */
    @Operation(summary = "Получение списка всех индустриальных кодов", tags = "industry")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Получил список всех индустриальных кодов",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = IndustryDto.class)))
                    })
    })
    @AuditLog
    @GetMapping("/all")
    public ResponseEntity<List<IndustryDto>> getAllIndustries() {
        List<IndustryDto> industries = industryService.getAllIndustries();
        return ResponseEntity.ok(industries);
    }

    /**
     * Метод для получения индустриального кода по идентификатору с использованием аннотации @AuditLog.
     *
     * @param id идентификатор индустриального кода.
     * @return ResponseEntity с найденным индустриальным кодом.
     */
    @Operation(summary = "Получение индустриального кода по ID", tags = "industry")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Нашел индустриальный код по переданному ID",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = IndustryDto.class)))
                    })
    })
    @AuditLog
    @GetMapping("/{id}")
    public ResponseEntity<IndustryDto> getIndustryById(@PathVariable Long id) {
        IndustryDto industry = industryService.getIndustryById(id);
        return ResponseEntity.ok(industry);
    }

    /**
     * Метод для сохранения или обновления индустриального кода с использованием аннотации @AuditLog.
     *
     * @param payload объект с данными нового индустриального кода.
     * @return @return ResponseEntity с сохраненным или обновленным индустриальным кодом.
     */
    @Operation(summary = "Сохранение/обновление индустриального кода", tags = "industry")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Создал/обновил индустриальный код",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = IndustryDto.class)))
                    })
    })
    @AuditLog
    @PutMapping("/save")
    @PreAuthorize("hasAnyAuthority('CONTRACTOR_SUPERUSER', 'SUPERUSER')")
    public ResponseEntity<IndustryDto> saveIndustry(@Validated @RequestBody NewIndustryPayload payload) {
        IndustryDto savedIndustry = industryService.saveIndustry(payload);
        return ResponseEntity.ok(savedIndustry);
    }

    /**
     * Метод для удаления индустриального кода по идентификатору с использованием аннотации @AuditLog.
     *
     * @param id идентификатор индустриального кода.
     * @return ResponseEntity с результатом удаления.
     */
    @Operation(summary = "Удаление индустриального кода по ID", tags = "industry")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Удалил индустриальный код по ID",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = IndustryDto.class)))
                    })
    })
    @AuditLog
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('CONTRACTOR_SUPERUSER', 'SUPERUSER')")
    public ResponseEntity<Void> deleteIndustry(@PathVariable Long id) {
        industryService.deleteIndustry(id);
        return ResponseEntity.ok().build();
    }

}
