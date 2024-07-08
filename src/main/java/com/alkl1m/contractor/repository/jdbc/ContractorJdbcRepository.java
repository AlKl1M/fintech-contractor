package com.alkl1m.contractor.repository.jdbc;

import com.alkl1m.contractor.domain.entitiy.Contractor;
import com.alkl1m.contractor.web.payload.ContractorFiltersPayload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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
        List<Object> params = new ArrayList<>();

        addEqualFilterCondition(queryBuilder, "c.id", payload.id(), params);
        addEqualFilterCondition(queryBuilder, "c.parent_id", payload.parentId(), params);
        addLikeFilterCondition(queryBuilder, "c.name", payload.name(), params);
        addLikeFilterCondition(queryBuilder, "c.name_full", payload.nameFull(), params);
        addLikeFilterCondition(queryBuilder, "c.inn", payload.inn(), params);
        addLikeFilterCondition(queryBuilder, "c.ogrn", payload.ogrn(), params);
        addLikeFilterCondition(queryBuilder, "co.name", payload.countryName(), params);
        addEqualFilterCondition(queryBuilder, "i.id", payload.industry() != null ? payload.industry().id() : null, params);
        addEqualFilterCondition(queryBuilder, "i.name", payload.industry() != null ? payload.industry().name() : null, params);
        addLikeFilterCondition(queryBuilder, "o.name", payload.orgFormName(), params);

        queryBuilder.append("LIMIT ? OFFSET ?");

        params.add(pageable.getPageSize());
        params.add(pageable.getPageNumber() * pageable.getPageSize());
        List<Contractor> contractors = jdbcTemplate.query(queryBuilder.toString(), new ContractorRowMapper(), params.toArray());

        return new PageImpl<>(contractors, pageable, contractors.size());
    }

    private void addLikeFilterCondition(StringBuilder queryBuilder, String field, String value, List<Object> params) {
        if (value != null) {
            queryBuilder.append("AND ").append(field).append(" LIKE ? ");
            params.add("%" + value + "%");
        }
    }

    private void addEqualFilterCondition(StringBuilder queryBuilder, String field, Object value, List<Object> params) {
        if (value != null) {
            queryBuilder.append("AND ").append(field).append(" = ? ");
            params.add(value);
        }
    }

}