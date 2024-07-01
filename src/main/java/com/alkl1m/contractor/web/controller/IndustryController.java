package com.alkl1m.contractor.web.controller;

import com.alkl1m.auditlogspringbootautoconfigure.annotation.AuditLog;
import com.alkl1m.contractor.domain.entitiy.Industry;
import com.alkl1m.contractor.service.IndustryService;
import com.alkl1m.contractor.web.payload.NewIndustryPayload;
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
 * RestController для работы с индустриальными кодами.
 *
 * @author alkl1m
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/industry")
public class IndustryController {

    private final IndustryService industryService;

    /**
     * Метод для получения списка всех индустриальных кодов с использованием аннотации @AuditLog.
     *
     * @return список всех индустриальных кодов.
     */
    @AuditLog
    @GetMapping("/all")
    public List<Industry> getAllIndustries() {
        return industryService.getAllIndustries();
    }

    /**
     * Метод для получения индустриального кода по идентификатору с использованием аннотации @AuditLog.
     *
     * @param id идентификатор индустриального кода.
     * @return найденный индустриального кода.
     */
    @AuditLog
    @GetMapping("/{id}")
    public Industry getIndustryById(@PathVariable Long id) {
        return industryService.getIndustryById(id);
    }

    /**
     * Метод для сохранения или обновления индустриального кода с использованием аннотации @AuditLog.
     *
     * @param payload объект с данными нового индустриального кода
     * @return сохраненный или обновленный индустриальный код.
     */
    @AuditLog
    @PutMapping("/save")
    public Industry saveIndustry(@Validated @RequestBody NewIndustryPayload payload) {
        return industryService.saveIndustry(payload);
    }

    /**
     * Метод для удаления индустриального кода по идентификатору с использованием аннотации @AuditLog.
     *
     * @param id идентификатор индустриального кода.
     */
    @AuditLog
    @DeleteMapping("/delete/{id}")
    public void deleteIndustry(@PathVariable Long id) {
        industryService.deleteIndustry(id);
    }

}
