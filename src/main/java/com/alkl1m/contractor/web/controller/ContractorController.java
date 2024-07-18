package com.alkl1m.contractor.web.controller;

import com.alkl1m.auditlogspringbootautoconfigure.annotation.AuditLog;
import com.alkl1m.contractor.service.ContractorService;
import com.alkl1m.contractor.web.payload.ContractorDto;
import com.alkl1m.contractor.web.payload.ContractorFiltersPayload;
import com.alkl1m.contractor.web.payload.ContractorsDto;
import com.alkl1m.contractor.web.payload.MainBorrowerRequest;
import com.alkl1m.contractor.web.payload.NewContractorPayload;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * RestController для работы с контрагентами.
 *
 * @author alkl1m
 */

@Tag(name = "contractor", description = "The Contractor API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/contractor")
public class ContractorController {

    private final ContractorService contractorService;

    /**
     * Получение контрагентов по заданным параметрам.
     *
     * @param payload фильтры для поиска контрагента.
     * @param page    номер страницы.
     * @param size    размер страницы.
     * @return ReponseEntity с найденными пагинированными контрагентами.
     */
    @Operation(summary = "Получение контрагента по заданным параметрам", tags = "contractor")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Нашел контрагента по заданным параметрам",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ContractorsDto.class)))
                    })
    })
    @AuditLog
    @PostMapping("/search")
    public ResponseEntity<ContractorsDto> getContractorsByParameters(
            @RequestBody ContractorFiltersPayload payload,
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "10", required = false) Integer size
    ) {
        Pageable paging = PageRequest.of(page, size);
        ContractorsDto contractorPage = contractorService.getContractorsByParameters(payload, paging);
        return ResponseEntity.ok().body(contractorPage);
    }

    /**
     * Изменение статуса основного заемщика у контрагента.
     *
     * @param request содержит contractorId, у которого меняем статус основного заемщика на request.main().
     * @return Response.ok(), если изменение пройдет успешно.
     */
    @Operation(summary = "Изменение статуса основного заемщика", tags = "contractor")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Изменил статус основного заемщика у контрагента",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ContractorDto.class)))
                    })
    })
    @AuditLog
    @PatchMapping("/main-borrower")
    public ResponseEntity<Void> mainBorrower(@Validated @RequestBody MainBorrowerRequest request) {
        contractorService.changeMainBorrower(request.contractorId(), request.main());
        return ResponseEntity.ok().build();
    }

    /**
     * Получение контрагентов по заданным параметрам с помощью нативного запроса.
     *
     * @param payload фильтры для поиска контрагента.
     * @param page    номер страницы.
     * @param size    размер страницы.
     * @return ReponseEntity с найденными пагинированными контрагентами.
     */
    @Operation(summary = "Получение контрагента по заданным параметрам с помощью нативного запроса", tags = "contractor")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Нашел контрагента по заданным параметрам",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ContractorsDto.class)))
                    })
    })
    @AuditLog
    @PostMapping("/crud/search")
    public ResponseEntity<ContractorsDto> getContractorPageableByIdd(
            @RequestBody ContractorFiltersPayload payload,
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "10", required = false) Integer size
    ) {
        Pageable paging = PageRequest.of(page, size);
        ContractorsDto contractorsPage = contractorService.getContractorsWithCrudByParameters(payload, paging);
        return ResponseEntity.ok().body(contractorsPage);
    }

    /**
     * Получение контрагента по ID.
     *
     * @param id ID контрагента.
     * @return ResponseEntity с найденным контрагентом.
     */
    @Operation(summary = "Получение контрагента по ID", tags = "contractor")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Получил контрагента по ID",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ContractorDto.class)))
                    })
    })
    @AuditLog
    @GetMapping("/{id}")
    public ResponseEntity<ContractorDto> getContractorById(@PathVariable String id) {
        ContractorDto contractor = contractorService.findById(id);
        return ResponseEntity.ok(contractor);
    }

    /**
     * Сохранение или обновление контрагента в зависимости от ID.
     *
     * @param payload Данные нового контрагента.
     * @return ResponseEntity с сохраненным или обновленным контрагентом.
     */
    @Operation(summary = "Сохранение или обновление контрагента в зависимости от ID", tags = "contractor")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Сохранил/обновил контрагента",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ContractorDto.class)))
                    })
    })
    @AuditLog
    @PutMapping("/save")
    public ResponseEntity<ContractorDto> saveOrUpdateContractor(@Validated @RequestBody NewContractorPayload payload) {
        ContractorDto savedContractor = contractorService.saveOrUpdate(payload);
        return ResponseEntity.ok(savedContractor);
    }

    /**
     * @param id ID контрагента.
     * @return ResponseEntity с результатом удаления.
     */
    @Operation(summary = "Удаление контрагента по ID", tags = "contractor")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Удалил контрагента по ID",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ContractorDto.class)))
                    })
    })
    @AuditLog
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteContractorById(@PathVariable String id) {
        contractorService.deleteContractorById(id);
        return ResponseEntity.ok().build();
    }

}
