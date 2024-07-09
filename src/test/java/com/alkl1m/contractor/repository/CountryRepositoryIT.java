package com.alkl1m.contractor.repository;

import com.alkl1m.contractor.domain.entitiy.Country;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CountryRepositoryIT {

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    void testSave_withValidPayload_returnsSameCountry() {
        Country newCountry = new Country("TTT", "TestCountry", true);
        Country insertedCountry = countryRepository.save(newCountry);
        assertThat(entityManager.find(Country.class, insertedCountry.getId())).isEqualTo(newCountry);
    }

    @Test
    void testUpdate_withValidPayload_returnsUpdatedCountry() {
        Country newCountry = new Country("TTT", "TestCountry", true);
        entityManager.persist(newCountry);
        String newName = "New TestCountry";
        newCountry.setName(newName);
        countryRepository.save(newCountry);
        assertThat(entityManager.find(Country.class, newCountry.getId()).getName()).isEqualTo(newName);
    }

    @Test
    void testFindById_withValidPayload_returnsValidCountry() {
        Country newCountry = new Country("TTT", "TestCountry", true);
        entityManager.persist(newCountry);
        Optional<Country> retrievedCountry = countryRepository.findById(newCountry.getId());
        assertThat(retrievedCountry).contains(newCountry);
    }

    @Test
    void testDelete_withValidPayload_returnsNoCountryInDB() {
        Country newCountry = new Country("TTT", "TestCountry", true);
        entityManager.persist(newCountry);
        countryRepository.delete(newCountry);
        assertThat(entityManager.find(Country.class, newCountry.getId())).isNull();
    }

}
