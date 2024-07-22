package com.alkl1m.contractor.repository;

import com.alkl1m.contractor.TestBeans;
import com.alkl1m.contractor.domain.entitiy.OrgForm;
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
class OrgFormRepositoryIT {

    @Autowired
    OrgFormRepository orgFormRepository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    void testSave_withValidPayload_returnsSameOrgForm() {
        OrgForm newOrgForm = new OrgForm(null, "TestOrgForm", true);
        OrgForm insertedOrgForm = orgFormRepository.save(newOrgForm);
        assertThat(entityManager.find(OrgForm.class, insertedOrgForm.getId())).isEqualTo(newOrgForm);
    }

    @Test
    void testUpdate_withValidPayload_returnsUpdatedOrgForm() {
        OrgForm newOrgForm = new OrgForm(null, "TestOrgForm", true);
        entityManager.persist(newOrgForm);
        String newName = "New TestOrgForm";
        newOrgForm.setName(newName);
        orgFormRepository.save(newOrgForm);
        assertThat(entityManager.find(OrgForm.class, newOrgForm.getId()).getName()).isEqualTo(newName);
    }

    @Test
    void testFindById_withValidPayload_returnsValidOrgForm() {
        OrgForm newOrgForm = new OrgForm(null, "-", true);
        entityManager.persist(newOrgForm);
        Optional<OrgForm> retrievedOrgForm = orgFormRepository.findById(newOrgForm.getId());
        assertThat(retrievedOrgForm).contains(newOrgForm);
    }

    @Test
    void testDelete_withValidPayload_returnsNoOrgFormInDB() {
        OrgForm newOrgForm = new OrgForm(null, "-", true);
        entityManager.persist(newOrgForm);
        orgFormRepository.delete(newOrgForm);
        assertThat(entityManager.find(OrgForm.class, newOrgForm.getId())).isNull();
    }

}
