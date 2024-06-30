package com.alkl1m.contractor.repository.jdbc;

import com.alkl1m.contractor.domain.entitiy.Contractor;
import com.alkl1m.contractor.domain.exception.ContractorNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ContractorJdbcRepository {

    private final DataSourceConfig dataSourceConfig;

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

    public Optional<Contractor> findById(String id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            statement.setString(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                return Optional.ofNullable(ContractorRowMapper.mapRow(rs));
            }
        } catch (SQLException throwable) {
            throw new ContractorNotFoundException("Exception while trying to get contractor with provided id: " + id);
        }
    }

}
