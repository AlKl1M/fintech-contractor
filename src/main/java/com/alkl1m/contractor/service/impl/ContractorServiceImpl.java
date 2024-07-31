package com.alkl1m.contractor.service.impl;

import com.alkl1m.authutilsspringbootautoconfigure.domain.enums.ERole;
import com.alkl1m.authutilsspringbootautoconfigure.service.impl.UserDetailsImpl;
import com.alkl1m.contractor.domain.entitiy.Contractor;
import com.alkl1m.contractor.domain.entitiy.Country;
import com.alkl1m.contractor.domain.entitiy.Industry;
import com.alkl1m.contractor.domain.entitiy.OrgForm;
import com.alkl1m.contractor.domain.exception.ContractorNotFoundException;
import com.alkl1m.contractor.domain.exception.CountryNotFoundException;
import com.alkl1m.contractor.domain.exception.IndustryNotFoundException;
import com.alkl1m.contractor.domain.exception.OrgFormNotFoundException;
import com.alkl1m.contractor.repository.jdbc.ContractorJdbcRepository;
import com.alkl1m.contractor.repository.ContractorRepository;
import com.alkl1m.contractor.repository.CountryRepository;
import com.alkl1m.contractor.repository.IndustryRepository;
import com.alkl1m.contractor.repository.OrgFormRepository;
import com.alkl1m.contractor.repository.spec.ContractorSpecifications;
import com.alkl1m.contractor.service.ContractorService;
import com.alkl1m.contractor.web.payload.ContractorDto;
import com.alkl1m.contractor.web.payload.ContractorFiltersPayload;
import com.alkl1m.contractor.web.payload.ContractorsDto;
import com.alkl1m.contractor.web.payload.NewContractorPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Реализация сервиса для работы с контрагентами.
 *
 * @author alkl1m
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ContractorServiceImpl implements ContractorService {

    private final ContractorRepository contractorRepository;
    private final ContractorJdbcRepository contractorJdbcRepository;
    private final CountryRepository countryRepository;
    private final IndustryRepository industryRepository;
    private final OrgFormRepository orgFormRepository;

    /**
     * Поиск контрагента по заданным параметрам.
     *
     * @param payload  список фильтров для выборки.
     * @param pageable объект пагинации.
     * @return DTO содержащее список пагинированных контрагентов.
     */
    public ContractorsDto getContractorsByParameters(ContractorFiltersPayload payload, Pageable pageable, UserDetailsImpl userDetails) {
        Specification<Contractor> spec = ContractorSpecifications.getContractorByParameters(payload);

        Set<GrantedAuthority> userRoles = getUserRoles(userDetails);

        validateUserRoles(payload, userRoles);

        Page<Contractor> contractorsPage = contractorRepository.findAll(spec, pageable);

        List<ContractorDto> contractorDtos = contractorsPage.getContent()
                .stream()
                .map(ContractorDto::from)
                .toList();

        return new ContractorsDto(new PageImpl<>(contractorDtos, contractorsPage.getPageable(), contractorsPage.getTotalElements()));
    }

    /**
     * Поиск контрагента по заданным параметрам.
     *
     * @param payload  список фильтров для выборки.
     * @param pageable объект пагинации.
     * @return DTO содержащее список пагинированных контрагентов.
     */
    @Override
    public ContractorsDto getContractorsWithCrudByParameters(ContractorFiltersPayload payload, Pageable pageable, UserDetailsImpl userDetails) {
        Set<GrantedAuthority> userRoles = getUserRoles(userDetails);

        validateUserRoles(payload, userRoles);

        Page<Contractor> contractorsPage = contractorJdbcRepository.getContractorByParameters(payload, pageable);

        List<ContractorDto> contractorDtos = contractorsPage.getContent()
                .stream()
                .map(ContractorDto::from)
                .toList();

        return new ContractorsDto(new PageImpl<>(contractorDtos, contractorsPage.getPageable(), contractorsPage.getTotalElements()));
    }


    /**
     * Создает контрагент, если в dto не передан id, и обновляет в противном случае.
     *
     * @param payload dto для нового контрагента.
     * @return созданный или обновленный контрагент.
     */
    @Override
    @Transactional
    public ContractorDto saveOrUpdate(NewContractorPayload payload, String userId) {

        return ContractorDto.from(contractorRepository.findById(payload.id())
                .map(existingContractor -> updateExistingContractor(payload, existingContractor, userId))
                .orElseGet(() -> createNewContractor(payload, userId)));
    }

    /**
     * Получение контрагента по ID.
     *
     * @param id ID по которому идет поиск контрагента.
     * @return контрагент, удовлетворяющий условию.
     */
    @Override
    public ContractorDto findById(String id) {
        Contractor contractor = contractorRepository.findById(id).orElseThrow(
                () -> new ContractorNotFoundException(String.format("Contractor with id %s not found!", id))
        );
        return ContractorDto.from(contractor);
    }

    /**
     * Удаление контрагента по id.
     *
     * @param id контрагента.
     */
    @Override
    @Transactional
    public void deleteContractorById(String id) {
        Optional<Contractor> optionalContractor = contractorRepository.findById(id);
        optionalContractor.ifPresentOrElse(contractor -> {
            contractor.setActive(false);
            contractorRepository.save(contractor);
        }, () -> {
            throw new ContractorNotFoundException(String.format("Contractor with id %s not found", id));
        });
    }

    /**
     * Изменение статуса основного заемщика у контрагента.
     *
     * @param contractorId уникальный идентификатор контрагента.
     * @param main         статус основного заемщика контрагента.
     */
    @Override
    @Transactional
    public void changeMainBorrower(String contractorId, Boolean main) {
        Optional<Contractor> optionalContractor = contractorRepository.findById(contractorId);
        optionalContractor.ifPresentOrElse(contractor -> {
            contractor.setActiveMainBorrower(main);
            contractorRepository.save(contractor);
        }, () -> {
            throw new ContractorNotFoundException(String.format("Contractor with id %s not found", contractorId));
        });
    }

    /**
     * Метод для создания нового контрагента если не передан id.
     *
     * @param payload dto контрагента.
     * @return созданный контрагент.
     */
    private Contractor createNewContractor(NewContractorPayload payload, String userId) {
        Country country = countryRepository.findById(payload.country_id()).orElseThrow(() -> new CountryNotFoundException("Country not found"));
        Industry industry = industryRepository.findById(payload.industry_id()).orElseThrow(() -> new IndustryNotFoundException("Industry not found"));
        OrgForm orgForm = orgFormRepository.findById(payload.orgForm_id()).orElseThrow(() -> new OrgFormNotFoundException("OrgForm not found"));

        return contractorRepository.save(
                NewContractorPayload.toContractor(payload, country, industry, orgForm, userId)
        );
    }

    /**
     * @param payload            информация для обновления контрагента.
     * @param existingContractor объект изменяемого контрагента.
     * @return объект обновленного контрагента.
     */
    private Contractor updateExistingContractor(NewContractorPayload payload, Contractor existingContractor, String userId) {
        Country country = countryRepository.findById(payload.country_id()).orElseThrow(() -> new CountryNotFoundException("Country not found"));
        Industry industry = industryRepository.findById(payload.industry_id()).orElseThrow(() -> new IndustryNotFoundException("Industry not found"));
        OrgForm orgForm = orgFormRepository.findById(payload.orgForm_id()).orElseThrow(() -> new OrgFormNotFoundException("OrgForm not found"));

        existingContractor.setParentId(payload.parentId());
        existingContractor.setName(payload.name());
        existingContractor.setNameFull(payload.nameFull());
        existingContractor.setInn(payload.inn());
        existingContractor.setOgrn(payload.ogrn());
        existingContractor.setCountry(country);
        existingContractor.setIndustry(industry);
        existingContractor.setOrgForm(orgForm);
        existingContractor.setModifyDate(new Date());
        existingContractor.setModifyUserId(userId);
        existingContractor.setActive(true);
        return contractorRepository.save(existingContractor);
    }

    private void validateUserRoles(ContractorFiltersPayload payload, Set<GrantedAuthority> userRoles) {
        if (hasRole(userRoles, ERole.CONTRACTOR_SUPERUSER) || hasRole(userRoles, ERole.SUPERUSER)) {
            return;
        }

        String country = Optional.ofNullable(payload.countryName()).orElse("Россия");

        if (hasRole(userRoles, ERole.CONTRACTOR_RUS) && !"Россия".equals(country)) {
            throw new AuthenticationServiceException("Пользователь с такой ролью не может просматривать эти данные.");
        }
    }

    private Set<GrantedAuthority> getUserRoles(UserDetailsImpl userDetails) {
        return new HashSet<>(userDetails.getAuthorities());
    }

    private boolean hasRole(Set<GrantedAuthority> userRoles, ERole role) {
        return userRoles.contains(new SimpleGrantedAuthority(role.name()));
    }

}
