package com.alkl1m.contractor.service.impl;

import com.alkl1m.contractor.domain.entitiy.Industry;
import com.alkl1m.contractor.domain.exception.IndustryNotFoundException;
import com.alkl1m.contractor.repository.IndustryRepository;
import com.alkl1m.contractor.service.IndustryService;
import com.alkl1m.contractor.web.payload.IndustryDto;
import com.alkl1m.contractor.web.payload.NewIndustryPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Реализация сервиса для работы с индустриальными кодами.
 *
 * @author alkl1m
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class IndustryServiceImpl implements IndustryService {

    private final IndustryRepository industryRepository;

    /**
     * Метод для получения всех активных индустриальных кодов.
     *
     * @return список индустриальных кодов.
     */
    @Override
    public List<IndustryDto> getAllIndustries() {
        List<Industry> industries = industryRepository.findAllByIsActiveTrue();
        return industries.stream()
                .map(IndustryDto::from)
                .toList();
    }

    /**
     * Метод для получения индустриального кода по id.
     *
     * @param id индустриального кода.
     * @return объект индустриального кода.
     */
    @Override
    public IndustryDto getIndustryById(Long id) {
        Industry industry = industryRepository.findById(id).orElseThrow(
                () -> new IndustryNotFoundException(String.format("Industry with id %d not found!", id))
        );
        return IndustryDto.from(industry);
    }

    /**
     * Метод для создания нового индустриального кода и обновления существующего,
     * если в dto передан id.
     *
     * @param payload dto нового индустриального кода.
     * @return созданный или обновленный индустриальный код.
     */
    @Override
    @Transactional
    public IndustryDto saveIndustry(NewIndustryPayload payload) {
        if (payload.id() == null) {
            return IndustryDto.from(industryRepository.save(NewIndustryPayload.toIndustry(payload)));
        } else {
            Optional<Industry> existingIndustry = industryRepository.findById(payload.id());
            if (existingIndustry.isPresent()) {
                Industry industry = existingIndustry.get();
                industry.setName(payload.name());
                return IndustryDto.from(industryRepository.save(industry));
            } else {
                throw new IndustryNotFoundException(String.format("Industry with id %d not found", payload.id()));
            }
        }
    }

    /**
     * Метод для удаления индустриального кода по id.
     *
     * @param id индустриального кода.
     */
    @Override
    @Transactional
    public void deleteIndustry(Long id) {
        Optional<Industry> optionalIndustry = industryRepository.findById(id);
        optionalIndustry.ifPresentOrElse(industry -> {
            industry.setActive(false);
            industryRepository.save(industry);
        }, () -> {
            throw new IndustryNotFoundException(String.format("Industry with id %d not found", id));
        });
    }

}
