package com.alkl1m.contractor.repository.jdbc;

import com.alkl1m.contractor.TestBeans;
import com.alkl1m.contractor.domain.entitiy.Contractor;
import com.alkl1m.contractor.web.payload.ContractorFiltersPayload;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest(classes = TestBeans.class)
class ContractorJdbcRepositoryIT {

    @Autowired
    ContractorJdbcRepository contractorJdbcRepository;

    @Test
    @Sql("/sql/contractors.sql")
    void testGetContractorByParameters_withAllParameters_ReturnsValidContractor() {
        ContractorFiltersPayload payload = new ContractorFiltersPayload(
                "1",
                null,
                "Contractor 1",
                "Full Name 1",
                "111111111",
                "111111111",
                null,
                null,
                null
        );

        Pageable pageable = PageRequest.of(0, 10);

        Page<Contractor> result = contractorJdbcRepository.getContractorByParameters(payload, pageable);

        Contractor contractor = result.getContent().get(0);

        assertEquals(payload.id(), contractor.getId());
        assertEquals(payload.parentId(), contractor.getParentId());
        assertEquals(payload.name(), contractor.getName());
        assertEquals(payload.nameFull(), contractor.getNameFull());
        assertEquals(payload.inn(), contractor.getInn());
        assertEquals(payload.ogrn(), contractor.getOgrn());
        assertNotNull(contractor.getCreateDate());
        assertNotNull(contractor.getCreateUserId());
        assertTrue(contractor.isActive());
    }

    @Test
    @Sql("/sql/contractors.sql")
    void testGetContractorByParameters_withOneParameter_returnsCoupleContractors() {
        ContractorFiltersPayload payload = new ContractorFiltersPayload(
                null,
                null,
                "C",
                null,
                null,
                null,
                null,
                null,
                null
        );

        Pageable pageable = PageRequest.of(0, 10);

        Page<Contractor> result = contractorJdbcRepository.getContractorByParameters(payload, pageable);

        assertEquals(9, result.getContent().size());
    }

}
