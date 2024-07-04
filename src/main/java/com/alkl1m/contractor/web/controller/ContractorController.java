package com.alkl1m.contractor.web.controller;

import com.alkl1m.auditlogspringbootautoconfigure.annotation.AuditLog;
import com.alkl1m.contractor.service.ContractorService;
import com.alkl1m.contractor.web.payload.ContractorDto;
import com.alkl1m.contractor.web.payload.ContractorFiltersPayload;
import com.alkl1m.contractor.web.payload.NewContractorPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * RestController для работы с контрагентами.
 *
 * @author alkl1m
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/contractor")
public class ContractorController {

    private final ContractorService contractorService;

    /**
     * Метод для поиска исполнителей контрагентов по заданным параметрам.
     *
     * @param payload объект, содержащий фильтры для поиска контрагентов.
     * @param page    номер страницы результатов (по умолчанию 0).
     * @param size    количество элементов на странице (по умолчанию 10).
     * @return страница с найденными контрагентами.
     */
    @AuditLog
    @PostMapping("/search")
    public Page<ContractorDto> getContractorsByParameters(
            @RequestBody ContractorFiltersPayload payload,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return contractorService.getContractorsByParameters(payload, page, size);
    }

    /**
     * Метод для сохранения или обновления контрагента с использованием аннотации @AuditLog.
     *
     * @param payload объект с данными нового контрагента.
     * @return сохраненный или обновленный контрагент.
     */
    @AuditLog
    @PutMapping("/save")
    public ContractorDto saveOrUpdateContractor(@Validated @RequestBody NewContractorPayload payload) {
        return contractorService.saveOrUpdate(payload);
    }

    /**
     * Метод для получения страницы с контрагентами по их идентификатору с использованием аннотации @AuditLog.
     *
     * @param id       идентификатор контрагента.
     * @param pageable параметры страницы.
     * @return страница с найденными контрагентами.
     */
    @AuditLog
    @GetMapping("/{id}")
    public Page<ContractorDto> getContractorPageableById(@PathVariable String id, Pageable pageable) {
        return contractorService.getContractorPageableById(id, pageable);
    }

    /**
     * Метод для поиска контрагента с деталями по идентификатору с использованием аннотации @AuditLog.
     *
     * @param id идентификатор контрагента.
     * @return найденный контрагент с деталями.
     */
    @AuditLog
    @GetMapping("/crud/{id}")
    public ContractorDto findContractorWithDetailsById(@PathVariable String id) {
        return contractorService.findContractorWithDetailsById(id);
    }

    /**
     * Метод для удаления контрагента по идентификатору с использованием аннотации @AuditLog.
     *
     * @param id идентификатор контрагента.
     */
    @AuditLog
    @DeleteMapping("/delete/{id}")
    public void deleteContractorById(@PathVariable String id) {
        contractorService.deleteContractorById(id);
    }

}
