package com.alkl1m.contractor.repository.jdbc;

import com.alkl1m.contractor.domain.entitiy.Contractor;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для работы с контрагентом.
 * Содержит реализацию запросов без использования ORM.
 *
 * @author alkl1m
 */
@Repository
public class ContractorJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    private final String FIND_BY_ID = """
            SELECT c.id AS contractor_id, c.name AS contractor_name, c.name_full AS contractor_full_name, c.inn, c.ogrn,
                   co.id AS country_id, co.name AS country_name, co.is_active AS country_is_active,
                   i.id AS industry_id, i.name AS industry_name, i.is_active AS industry_is_active,
                   o.id AS org_form_id, o.name AS org_form_name, o.is_active AS org_form_is_active,
                   c.create_date, c.modify_date, c.create_user_id, c.modify_user_id
            FROM contractor.contractor c
                     LEFT JOIN contractor.country co ON c.country = co.id
                     LEFT JOIN contractor.industry i ON c.industry = i.id
                     LEFT JOIN contractor.org_form o ON c.org_form = o.id
            WHERE c.id = ?
            """;

    public ContractorJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Contractor> findById(String id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID, new Object[]{id}, (rs, rowNum) -> ContractorRowMapper.mapRow(rs)));
    }

}
