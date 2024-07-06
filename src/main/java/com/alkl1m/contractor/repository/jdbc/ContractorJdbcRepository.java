package com.alkl1m.contractor.repository.jdbc;

import com.alkl1m.contractor.domain.entitiy.Contractor;
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
                   o.id AS org_form_id, o.name AS org_form_name, o.is_active AS org_form_is_active,
                   c.create_date, c.modify_date, c.create_user_id, c.modify_user_id
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
    public Page<Contractor> getContractorByParameters(ContractorFiltersPayload payload, Pageable pageable) {
        StringBuilder queryBuilder = new StringBuilder(FIND_BY_IS_ACTIVE);

        addEqualFilterCondition(queryBuilder, "c.id", payload.id());
        addEqualFilterCondition(queryBuilder, "c.parent_id", payload.parentId());
        addLikeFilterCondition(queryBuilder, "c.name", payload.name());
        addLikeFilterCondition(queryBuilder, "c.name_full", payload.nameFull());
        addLikeFilterCondition(queryBuilder, "c.inn", payload.inn());
        addLikeFilterCondition(queryBuilder, "c.ogrn", payload.ogrn());
        addLikeFilterCondition(queryBuilder, "co.name", payload.countryName());
        addEqualFilterCondition(queryBuilder, "i.id", payload.industry() != null ? payload.industry().id() : null);
        addEqualFilterCondition(queryBuilder, "i.name", payload.industry() != null ? payload.industry().name() : null);
        addLikeFilterCondition(queryBuilder, "o.name", payload.orgFormName());

        queryBuilder.append("LIMIT ").append(pageable.getPageSize()).append(" OFFSET ").append(pageable.getPageNumber() * pageable.getPageSize());

        List<Contractor> contractors = jdbcTemplate.query(queryBuilder.toString(), new ContractorRowMapper());

        return new PageImpl<>(contractors, pageable, contractors.size());
    }

    private void addLikeFilterCondition(StringBuilder queryBuilder, String field, String value) {
        if (value != null) {
            queryBuilder.append("AND ").append(field).append(" LIKE '%").append(value).append("%' ");
        }
    }

    private void addEqualFilterCondition(StringBuilder queryBuilder, String field, Object value) {
        if (value != null) {
            queryBuilder.append("AND ").append(field).append(" = '").append(value).append("' ");
        }
    }

}
