package com.alkl1m.contractor.repository.jdbc;

import com.alkl1m.contractor.domain.entitiy.Contractor;
import com.alkl1m.contractor.domain.entitiy.Country;
import com.alkl1m.contractor.domain.entitiy.Industry;
import com.alkl1m.contractor.domain.entitiy.OrgForm;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Класс, который делает маппинг из результата запроса в бд в контрагент.
 *
 * @author alkl1m
 */
public class ContractorRowMapper implements RowMapper<Contractor> {

    @Override
    public Contractor mapRow(ResultSet rs, int rowNum) throws SQLException {
        Contractor contractor = new Contractor();
        contractor.setId(rs.getString("contractor_id"));
        contractor.setName(rs.getString("contractor_name"));
        contractor.setNameFull(rs.getString("contractor_full_name"));
        contractor.setInn(rs.getString("inn"));
        contractor.setOgrn(rs.getString("ogrn"));

        Country country = new Country();
        country.setId(rs.getString("country_id"));
        country.setName(rs.getString("country_name"));
        country.setActive(rs.getBoolean("country_is_active"));
        contractor.setCountry(country);

        Industry industry = new Industry();
        industry.setId(rs.getLong("industry_id"));
        industry.setName(rs.getString("industry_name"));
        industry.setActive(rs.getBoolean("industry_is_active"));
        contractor.setIndustry(industry);

        OrgForm orgForm = new OrgForm();
        orgForm.setId(rs.getLong("org_form_id"));
        orgForm.setName(rs.getString("org_form_name"));
        orgForm.setActive(rs.getBoolean("org_form_is_active"));
        contractor.setOrgForm(orgForm);

        contractor.setCreateDate(rs.getDate("create_date"));
        contractor.setModifyDate(rs.getDate("modify_date"));
        contractor.setCreateUserId(rs.getString("create_user_id"));
        contractor.setModifyUserId(rs.getString("modify_user_id"));

        return contractor;
    }

}
