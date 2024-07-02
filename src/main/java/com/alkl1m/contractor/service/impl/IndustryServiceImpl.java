package com.alkl1m.contractor.service.impl;

import com.alkl1m.contractor.domain.entitiy.Industry;
import com.alkl1m.contractor.domain.exception.IndustryNotFoundException;
import com.alkl1m.contractor.repository.IndustryRepository;
import com.alkl1m.contractor.service.IndustryService;
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
     * Метод для получения всех индустриальных кодов.
     *
     * @return список индустриальных кодов.
     */
    @Override
    public List<Industry> getAllIndustries() {
        return industryRepository.findAll();
    }

    /**
     * Метод для получения индустриального кода по id.
     *
     * @param id индустриального кода.
     * @return объект индустриального кода.
     */
    @Override
    public Industry getIndustryById(Long id) {
        return industryRepository.findById(id).orElseThrow(
                () -> new IndustryNotFoundException(String.format(String.format("Industry with id %d not found", id)))
        );
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
    public Industry saveIndustry(NewIndustryPayload payload) {
        if (payload.id() == null) {
            return industryRepository.save(NewIndustryPayload.toIndustry(payload));
        } else {
            Optional<Industry> existingIndustry = industryRepository.findById(payload.id());
            if (existingIndustry.isPresent()) {
                Industry industry = existingIndustry.get();
                industry.setName(payload.name());
                return industryRepository.save(industry);
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
