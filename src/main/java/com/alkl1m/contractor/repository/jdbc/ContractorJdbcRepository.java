package com.alkl1m.contractor.repository.jdbc;

import com.alkl1m.contractor.web.payload.ContractorDto;
import com.alkl1m.contractor.web.payload.ContractorFiltersPayload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для работы с контрагентом.
 * Содержит реализацию запросов без использования ORM.
 *
 * @author alkl1m
 */
@Repository
public class ContractorJdbcRepository {

    private static final String FIND_BY_IS_ACTIVE = """
            SELECT c.id AS contractor_id, c.parent_id AS contractor_parent_id, c.name AS contractor_name, c.name_full AS contractor_full_name, c.inn, c.ogrn,
                   co.id AS country_id, co.name AS country_name, co.is_active AS country_is_active,
                   i.id AS industry_id, i.name AS industry_name, i.is_active AS industry_is_active,
                   o.id AS org_form_id, o.name AS org_form_name, o.is_active AS org_form_is_active
            FROM contractor.contractor c
                     LEFT JOIN contractor.country co ON c.country = co.id
                     LEFT JOIN contractor.industry i ON c.industry = i.id
                     LEFT JOIN contractor.org_form o ON c.org_form = o.id
            WHERE c.is_active = true
            """;

    private final JdbcTemplate jdbcTemplate;

    public ContractorJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * @param payload список фильтров для выборки.
     * @param pageable объект пагинации.
     * @return пагинированный ContractorDto.
     */
    public Page<ContractorDto> getContractorByParameters(ContractorFiltersPayload payload, Pageable pageable) {
        StringBuilder queryBuilder = new StringBuilder(FIND_BY_IS_ACTIVE);

        if (payload.id() != null) {
            queryBuilder.append("AND c.id = '").append(payload.id()).append("' ");
        }

        if (payload.parentId() != null) {
            queryBuilder.append("AND c.parent_id = '").append(payload.parentId()).append("' ");
        }

        if (payload.name() != null) {
            queryBuilder.append("AND c.name LIKE '%").append(payload.name()).append("%' ");
        }

        if (payload.nameFull() != null) {
            queryBuilder.append("AND c.name_full LIKE '%").append(payload.nameFull()).append("%' ");
        }

        if (payload.inn() != null) {
            queryBuilder.append("AND c.inn LIKE '%").append(payload.inn()).append("%' ");
        }

        if (payload.ogrn() != null) {
            queryBuilder.append("AND c.ogrn LIKE '%").append(payload.ogrn()).append("%' ");
        }

        if (payload.countryName() != null) {
            queryBuilder.append("AND co.name LIKE '%").append(payload.countryName()).append("%' ");
        }

        if (payload.industry() != null) {
            queryBuilder.append("AND i.id = '").append(payload.industry().id()).append("' ");
            queryBuilder.append("AND i.name = '").append(payload.industry().name()).append("' ");
        }

        if (payload.orgFormName() != null) {
            queryBuilder.append("AND o.name LIKE '%").append(payload.orgFormName()).append("%' ");
        }

        queryBuilder.append("LIMIT ").append(pageable.getPageSize()).append(" OFFSET ").append(pageable.getPageNumber() * pageable.getPageNumber());
        List<ContractorDto> contractors = jdbcTemplate.query(queryBuilder.toString(), new ContractorDtoRowMapper());
        return new PageImpl<>(contractors, pageable, contractors.size());
    }

}
