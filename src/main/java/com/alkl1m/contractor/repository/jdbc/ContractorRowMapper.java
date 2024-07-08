package com.alkl1m.contractor.repository.jdbc;

import com.alkl1m.contractor.domain.entitiy.Contractor;
import com.alkl1m.contractor.domain.entitiy.Country;
import com.alkl1m.contractor.domain.entitiy.Industry;
import com.alkl1m.contractor.domain.entitiy.OrgForm;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Маппер для преобразования ResultSet в ContractorDto.
 *
 * @author alkl1m
 */
public class ContractorRowMapper implements RowMapper<Contractor> {

    /**
     * @param resultSet     ResultSet, который содержит информацию для маппера.
     * @param rowNum чисто текущей строки.
     * @return DTO контрагента.
     * @throws SQLException возможное исключения во время выполнения SQL.
     */
    @Override
    public Contractor mapRow(ResultSet resultSet, int rowNum) throws SQLException {

        Country country = Country.builder()
                .id(resultSet.getString("country_id"))
                .name(resultSet.getString("country_name"))
                .isActive(resultSet.getBoolean("country_is_active"))
                .build();

        Industry industry = Industry.builder()
                .id(resultSet.getLong("industry_id"))
                .name(resultSet.getString("industry_name"))
                .isActive(resultSet.getBoolean("industry_is_active"))
                .build();

        OrgForm orgForm = OrgForm.builder()
                .id(resultSet.getLong("org_form_id"))
                .name(resultSet.getString("org_form_name"))
                .isActive(resultSet.getBoolean("org_form_is_active"))
                .build();

        return Contractor.builder()
                .id(resultSet.getString("contractor_id"))
                .name(resultSet.getString("contractor_name"))
                .nameFull(resultSet.getString("contractor_full_name"))
                .inn(resultSet.getString("inn"))
                .ogrn(resultSet.getString("ogrn"))
                .createDate(resultSet.getDate("create_date"))
                .modifyDate(resultSet.getDate("modify_date"))
                .createUserId(resultSet.getString("create_user_id"))
                .modifyUserId(resultSet.getString("modify_user_id"))
                .country(country)
                .industry(industry)
                .orgForm(orgForm)
                .isActive(true)
                .build();
    }

}
