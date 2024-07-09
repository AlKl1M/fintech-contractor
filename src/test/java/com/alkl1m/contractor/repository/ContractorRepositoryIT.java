package com.alkl1m.contractor.repository;

import com.alkl1m.contractor.domain.entitiy.Contractor;
import com.alkl1m.contractor.domain.entitiy.Country;
import com.alkl1m.contractor.domain.entitiy.Industry;
import com.alkl1m.contractor.domain.entitiy.OrgForm;
import com.alkl1m.contractor.repository.spec.ContractorSpecifications;
import com.alkl1m.contractor.web.payload.ContractorFiltersPayload;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.jdbc.Sql;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ContractorRepositoryIT {

    @Autowired
    ContractorRepository contractorRepository;

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    IndustryRepository industryRepository;

    @Autowired
    OrgFormRepository orgFormRepository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    void testSave_withValidPayload_returnsSameContractor() {
        Optional<Country> country = countryRepository.findById("ABH");
        Optional<Industry> industry = industryRepository.findById(1L);
        Optional<OrgForm> orgForm = orgFormRepository.findById(1L);
        Date date = new Date();
        Contractor newContractor = Contractor.builder()
                .id("fasfs-2r214-sffasf")
                .parentId("parent_1")
                .name("Contractor Name")
                .nameFull("Full Contractor Name")
                .inn("1234567890")
                .ogrn("9876543210")
                .country(country.get())
                .industry(industry.get())
                .orgForm(orgForm.get())
                .createDate(date)
                .modifyDate(date)
                .createUserId("user1")
                .modifyUserId("user2")
                .isActive(true)
                .build();
        Contractor insertedContractor = contractorRepository.save(newContractor);
        assertEquals(insertedContractor.getId(), newContractor.getId());
        assertEquals(insertedContractor.getParentId(), newContractor.getParentId());
        assertEquals(insertedContractor.getName(), newContractor.getName());
        assertEquals(insertedContractor.getNameFull(), newContractor.getNameFull());
        assertEquals(insertedContractor.getInn(), newContractor.getInn());
        assertEquals(insertedContractor.getOgrn(), newContractor.getOgrn());
        assertNotNull(entityManager.find(Contractor.class, insertedContractor.getId()).getCreateDate());
        assertNotNull(entityManager.find(Contractor.class, insertedContractor.getId()).getModifyDate());
    }

    @Test
    void testUpdate_withValidPayload_returnsUpdatedContractor() {
        Optional<Country> country = countryRepository.findById("ABH");
        Optional<Industry> industry = industryRepository.findById(1L);
        Optional<OrgForm> orgForm = orgFormRepository.findById(1L);
        Date date = new Date();
        Contractor newContractor = Contractor.builder()
                .id("fasfs-2r214-sffasf")
                .parentId("parent_1")
                .name("Contractor Name")
                .nameFull("Full Contractor Name")
                .inn("1234567890")
                .ogrn("9876543210")
                .country(country.get())
                .industry(industry.get())
                .orgForm(orgForm.get())
                .createDate(date)
                .modifyDate(date)
                .createUserId("user1")
                .modifyUserId("user2")
                .isActive(true)
                .build();
        entityManager.persist(newContractor);
        String newName = "New TestContractor";
        newContractor.setName(newName);
        contractorRepository.save(newContractor);
        assertThat(entityManager.find(Contractor.class, newContractor.getId()).getName()).isEqualTo(newName);
    }

    @Test
    void testFindById_withValidPayload_returnsValidContractor() {
        Optional<Country> country = countryRepository.findById("ABH");
        Optional<Industry> industry = industryRepository.findById(1L);
        Optional<OrgForm> orgForm = orgFormRepository.findById(1L);
        Date date = new Date();
        Contractor newContractor = Contractor.builder()
                .id("fasfs-2r214-sffasf")
                .parentId("parent_1")
                .name("Contractor Name")
                .nameFull("Full Contractor Name")
                .inn("1234567890")
                .ogrn("9876543210")
                .country(country.get())
                .industry(industry.get())
                .orgForm(orgForm.get())
                .createDate(date)
                .modifyDate(date)
                .createUserId("user1")
                .modifyUserId("user2")
                .isActive(true)
                .build();
        entityManager.persist(newContractor);
        Optional<Contractor> retrievedContractor = contractorRepository.findById(newContractor.getId());
        assertThat(retrievedContractor).contains(newContractor);
    }

    @Test
    @Sql("/sql/contractors.sql")
    void testFindAll_withValidPayload_returnsValidContractor() {
        ContractorFiltersPayload payload = new ContractorFiltersPayload(
                "1",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
        Specification<Contractor> spec = ContractorSpecifications.getContractorByParameters(payload);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Contractor> retrievedContractor = contractorRepository.findAll(spec, pageable);
        Contractor contractor = retrievedContractor.getContent().get(0);

        assertEquals(payload.id(), contractor.getId());
        assertNotNull(contractor.getName());
        assertNotNull(contractor.getNameFull());
        assertNotNull(contractor.getInn());
        assertNotNull(contractor.getOgrn());

    }

    @Test
    void testDelete_withValidPayload_returnsNoContractorInDB() {
        Optional<Country> country = countryRepository.findById("ABH");
        Optional<Industry> industry = industryRepository.findById(1L);
        Optional<OrgForm> orgForm = orgFormRepository.findById(1L);
        Date date = new Date();
        Contractor newContractor = Contractor.builder()
                .id("fasfs-2r214-sffasf")
                .parentId("parent_1")
                .name("Contractor Name")
                .nameFull("Full Contractor Name")
                .inn("1234567890")
                .ogrn("9876543210")
                .country(country.get())
                .industry(industry.get())
                .orgForm(orgForm.get())
                .createDate(date)
                .modifyDate(date)
                .createUserId("user1")
                .modifyUserId("user2")
                .isActive(true)
                .build();
        entityManager.persist(newContractor);
        contractorRepository.delete(newContractor);
        assertThat(entityManager.find(Contractor.class, newContractor.getId())).isNull();
    }

}
