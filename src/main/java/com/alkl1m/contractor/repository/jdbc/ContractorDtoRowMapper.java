package com.alkl1m.contractor.repository.jdbc;

import com.alkl1m.contractor.web.payload.ContractorDto;
import com.alkl1m.contractor.web.payload.CountryDto;
import com.alkl1m.contractor.web.payload.IndustryDto;
import com.alkl1m.contractor.web.payload.OrgFormDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Маппер для преобразования ResultSet в ContractorDto.
 *
 * @author alkl1m
 */
public class ContractorDtoRowMapper implements RowMapper<ContractorDto> {

    /**
     * @param rs ResultSet, который содержит информацию для маппера.
     * @param rowNum чисто текущей строки.
     * @return DTO контрагента.
     * @throws SQLException возможное исключения во время выполнения SQL.
     */
    @Override
    public ContractorDto mapRow(ResultSet rs, int rowNum) throws SQLException {

        CountryDto country = new CountryDto(rs.getString("country_id"),
                rs.getString("country_name"));

        IndustryDto industry = new IndustryDto(rs.getLong("industry_id"),
                rs.getString("industry_name"));

        OrgFormDto orgForm = new OrgFormDto(rs.getLong("org_form_id"),
                rs.getString("org_form_name"));

        return new ContractorDto(
                rs.getString("contractor_id"),
                rs.getString("contractor_parent_id"),
                rs.getString("contractor_name"),
                rs.getString("contractor_full_name"),
                rs.getString("inn"),
                rs.getString("ogrn"),
                country,
                industry,
                orgForm
        );
    }

}
