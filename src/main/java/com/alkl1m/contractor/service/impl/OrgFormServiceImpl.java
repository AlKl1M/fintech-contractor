package com.alkl1m.contractor.service.impl;

import com.alkl1m.contractor.domain.entitiy.OrgForm;
import com.alkl1m.contractor.domain.exception.OrgFormNotFoundException;
import com.alkl1m.contractor.repository.OrgFormRepository;
import com.alkl1m.contractor.service.OrgFormService;
import com.alkl1m.contractor.web.payload.NewOrgFormPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Реализация сервиса для работы с организационными формами.
 *
 * @author alkl1m
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrgFormServiceImpl implements OrgFormService {

    private final OrgFormRepository orgFormRepository;

    /**
     * Метод для получения всех организационных форм.
     *
     * @return список организационных форм.
     */
    @Override
    public List<OrgForm> getAllOrgForms() {
        return orgFormRepository.findAll();
    }

    /**
     * Метод для получения организационной формы по id.
     *
     * @param id организационной формы.
     * @return объект организационной формы.
     */
    @Override
    public OrgForm getOrgFormById(Long id) {
        return orgFormRepository.findById(id).orElseThrow(
                () -> new OrgFormNotFoundException("OrgForm with id " + id + " not found")
        );
    }

    /**
     * Метод для создания новой организационной формы и обновления существующей,
     * если в dto передан id.
     *
     * @param payload dto новой организационной формы.
     * @return созданная или обновленная организационная форма.
     */
    @Override
    @Transactional
    public OrgForm saveOrgForm(NewOrgFormPayload payload) {
        if (payload.id() == null) {
            return orgFormRepository.save(NewOrgFormPayload.toOrgForm(payload));
        } else {
            Optional<OrgForm> existingOrgForm = orgFormRepository.findById(payload.id());
            if (existingOrgForm.isPresent()) {
                OrgForm orgForm = existingOrgForm.get();
                orgForm.setName(payload.name());
                return orgFormRepository.save(orgForm);
            } else {
                throw new OrgFormNotFoundException("OrgForm with id " + payload.id() + " not found");
            }
        }
    }

    /**
     * Метод для удаления организационной формы по id.
     *
     * @param id организационной формы.
     */
    @Override
    @Transactional
    public void deleteOrgForm(Long id) {
        Optional<OrgForm> optionalOrgForm = orgFormRepository.findById(id);
        optionalOrgForm.ifPresentOrElse(orgForm -> {
            orgForm.setActive(false);
            orgFormRepository.save(orgForm);
        }, () -> {
            throw new OrgFormNotFoundException("OrgForm with id " + id + " not found");
        });
    }

}
