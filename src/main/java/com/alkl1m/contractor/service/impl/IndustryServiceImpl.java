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

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class IndustryServiceImpl implements IndustryService {

    private final IndustryRepository industryRepository;

    @Override
    public List<Industry> getAllIndustries() {
        return industryRepository.findAll();
    }

    @Override
    public Industry getIndustryById(Long id) {
        return industryRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Industry saveIndustry(NewIndustryPayload payload) {
        return industryRepository.save(NewIndustryPayload.toIndustry(payload));
    }

    @Override
    @Transactional
    public void deleteIndustry(Long id) {
        Optional<Industry> optionalIndustry = industryRepository.findById(id);
        optionalIndustry.ifPresentOrElse(industry -> {
            industry.setActive(false);
            industryRepository.save(industry);
        }, () -> {
            throw new IndustryNotFoundException("Industry with id " + id + " not found");
        });
    }

}
