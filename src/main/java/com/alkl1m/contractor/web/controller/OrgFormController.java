package com.alkl1m.contractor.web.controller;

import com.alkl1m.auditlogspringbootautoconfigure.annotation.AuditLog;
import com.alkl1m.contractor.domain.entitiy.OrgForm;
import com.alkl1m.contractor.service.OrgFormService;
import com.alkl1m.contractor.web.payload.NewOrgFormPayload;
import com.alkl1m.contractor.web.payload.OrgFormDto;
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
 * RestController для работы с организационными формами.
 *
 * @author alkl1m
 */
@Tag(name = "orgform", description = "The OrgForm API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/orgform")
public class OrgFormController {

    private final OrgFormService orgFormService;

    /**
     * Метод для получения списка всех организационных форм с использованием аннотации @AuditLog.
     *
     * @return ResponseEntity список всех организационных форм.
     */
    @Operation(summary = "Получение списка всех организационных форм", tags = "orgform")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Получил список всех организационных форм",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = OrgForm.class)))
                    })
    })
    @AuditLog
    @GetMapping("/all")
    public ResponseEntity<List<OrgFormDto>> getAllOrgForms() {
        List<OrgFormDto> orgForms = orgFormService.getAllOrgForms();
        return ResponseEntity.ok(orgForms);
    }

    /**
     * Метод для получения организационной формы по идентификатору с использованием аннотации @AuditLog.
     *
     * @param id идентификатор организационной формы.
     * @return ResponseEntity с найденной организационная форма.
     */
    @Operation(summary = "Получение организационной формы по ID", tags = "orgform")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Нашел организационную форму по переданному ID",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = OrgForm.class)))
                    })
    })
    @AuditLog
    @GetMapping("/{id}")
    public ResponseEntity<OrgFormDto> getOrgFormById(@PathVariable Long id) {
        OrgFormDto orgForms = orgFormService.getOrgFormById(id);
        return ResponseEntity.ok(orgForms);
    }

    /**
     * Метод для сохранения или обновления формы организации с использованием аннотации @AuditLog.
     *
     * @param payload объект с данными новой организационной формы.
     * @return ResponseEntity с сохраненной или обновленной организационной формой.
     */

    @Operation(summary = "Сохранение/обновление организационной формы", tags = "orgform")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Создал/обновил организационную форму",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = OrgForm.class)))
                    })
    })
    @AuditLog
    @PutMapping("/save")
    public ResponseEntity<OrgFormDto> saveOrgForm(@Validated @RequestBody NewOrgFormPayload payload) {
        OrgFormDto savedOrgForm = orgFormService.saveOrgForm(payload);
        return ResponseEntity.ok(savedOrgForm);
    }

    /**
     * Метод для удаления организационной формы по идентификатору с использованием аннотации @AuditLog.
     *
     * @param id идентификатор организационной формы.
     * @return ResponseEntity с результатом удаления.
     */
    @Operation(summary = "Удаление организационной формы по ID", tags = "orgform")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Удалил организационную форму по ID",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = OrgForm.class)))
                    })
    })
    @AuditLog
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteOrgForm(@PathVariable Long id) {
        orgFormService.deleteOrgForm(id);
        return ResponseEntity.ok().build();
    }

}
