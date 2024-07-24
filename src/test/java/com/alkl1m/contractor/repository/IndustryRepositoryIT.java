package com.alkl1m.contractor.repository;

import com.alkl1m.contractor.TestBeans;
import com.alkl1m.contractor.domain.entitiy.Industry;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(TestBeans.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class IndustryRepositoryIT {
    @Autowired
    IndustryRepository industryRepository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    void testSave_withValidPayload_returnsSameIndustry() {
        Industry newIndustry = new Industry(null, "TestIndustry", true);
        Industry insertedIndustry = industryRepository.save(newIndustry);
        assertThat(entityManager.find(Industry.class, insertedIndustry.getId())).isEqualTo(newIndustry);
    }

    @Test
    void testUpdate_withValidPayload_returnsUpdatedIndustry() {
        Industry newIndustry = new Industry(null, "TestIndustry", true);
        entityManager.persist(newIndustry);
        String newName = "New TestIndustry";
        newIndustry.setName(newName);
        industryRepository.save(newIndustry);
        assertThat(entityManager.find(Industry.class, newIndustry.getId()).getName()).isEqualTo(newName);
    }

    @Test
    void testFindById_withValidPayload_returnsValidIndustry() {
        Industry newIndustry = new Industry(null, "TestIndustry", true);
        entityManager.persist(newIndustry);
        Optional<Industry> retrievedIndustry = industryRepository.findById(newIndustry.getId());
        assertThat(retrievedIndustry).contains(newIndustry);
    }

    @Test
    void testDelete_withValidPayload_returnsNoIndustryInDB() {
        Industry newIndustry = new Industry(null, "TestIndustry", true);
        entityManager.persist(newIndustry);
        industryRepository.delete(newIndustry);
        assertThat(entityManager.find(Industry.class, newIndustry.getId())).isNull();
    }

}
