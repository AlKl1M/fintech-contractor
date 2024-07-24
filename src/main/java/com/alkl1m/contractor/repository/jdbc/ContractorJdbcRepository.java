package com.alkl1m.contractor.repository.jdbc;

import com.alkl1m.contractor.domain.entitiy.Contractor;
import com.alkl1m.contractor.web.payload.ContractorFiltersPayload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            FROM contractor c
                     LEFT JOIN country co ON c.country = co.id
                     LEFT JOIN industry i ON c.industry = i.id
                     LEFT JOIN org_form o ON c.org_form = o.id
            WHERE c.is_active = true
            """;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ContractorJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * @param payload список фильтров для выборки.
     * @param pageable объект пагинации.
     * @return пагинированный ContractorDto.
     */
    public Page<Contractor> getContractorByParameters(ContractorFiltersPayload payload, Pageable pageable) {
        Map<String, Object> paramMap = new HashMap<>();

        StringBuilder queryBuilder = new StringBuilder(FIND_BY_IS_ACTIVE);
        addEqualFilterCondition(queryBuilder, "c.id", "id", payload.id(), paramMap);
        addEqualFilterCondition(queryBuilder, "c.parent_id", "parentId", payload.parentId(), paramMap);
        addLikeFilterCondition(queryBuilder, "c.name", "name", payload.name(), paramMap);
        addLikeFilterCondition(queryBuilder, "c.name_full", "nameFull", payload.nameFull(), paramMap);
        addLikeFilterCondition(queryBuilder, "c.inn", "inn", payload.inn(), paramMap);
        addLikeFilterCondition(queryBuilder, "c.ogrn", "ogrn", payload.ogrn(), paramMap);
        addLikeFilterCondition(queryBuilder, "co.name", "countryName", payload.countryName(), paramMap);
        addEqualFilterCondition(queryBuilder, "i.id", "industryId", payload.industry() != null ? payload.industry().id() : null, paramMap);
        addEqualFilterCondition(queryBuilder, "i.name", "industryName", payload.industry() != null ? payload.industry().name() : null, paramMap);
        addLikeFilterCondition(queryBuilder, "o.name", "orgFormName", payload.orgFormName(), paramMap);

        queryBuilder.append(" LIMIT :limit OFFSET :offset");
        paramMap.put("limit", pageable.getPageSize());
        paramMap.put("offset", pageable.getPageNumber() * pageable.getPageSize());

        List<Contractor> contractors = jdbcTemplate.query(queryBuilder.toString(), paramMap, new ContractorRowMapper());

        return new PageImpl<>(contractors, pageable, contractors.size());
    }

    private void addLikeFilterCondition(StringBuilder queryBuilder, String field, String paramName, String value, Map<String, Object> paramMap) {
        if (value != null) {
            queryBuilder.append(" AND ").append(field).append(" LIKE :").append(paramName).append(" ");
            paramMap.put(paramName, "%" + value + "%");
        }
    }

    private void addEqualFilterCondition(StringBuilder queryBuilder, String field, String paramName, Object value, Map<String, Object> paramMap) {
        if (value != null) {
            queryBuilder.append(" AND ").append(field).append(" = :").append(paramName).append(" ");
            paramMap.put(paramName, value);
        }
    }

}