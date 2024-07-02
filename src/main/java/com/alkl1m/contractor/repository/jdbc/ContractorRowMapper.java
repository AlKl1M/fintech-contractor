package com.alkl1m.contractor.repository.jdbc;

import com.alkl1m.contractor.domain.entitiy.Contractor;
import com.alkl1m.contractor.domain.entitiy.Country;
import com.alkl1m.contractor.domain.entitiy.Industry;
import com.alkl1m.contractor.domain.entitiy.OrgForm;
import lombok.SneakyThrows;

import java.sql.ResultSet;

/**
 * Класс, который делает маппинг из результата запроса в бд в контрагент.
 *
 * @author alkl1m
 */
public class ContractorRowMapper {

    private ContractorRowMapper() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * @param resultSet результат запроса в бд.
     * @return контрагент, полученный из данных из запроса.
     * @author alkl1m
     */
    @SneakyThrows
    public static Contractor mapRow(ResultSet resultSet) {
        if (resultSet.next()) {
            Contractor contractor = Contractor.builder()
                    .id(resultSet.getString("contractor_id"))
                    .name(resultSet.getString("contractor_name"))
                    .nameFull(resultSet.getString("contractor_full_name"))
                    .inn(resultSet.getString("inn"))
                    .ogrn(resultSet.getString("ogrn"))
                    .createDate(resultSet.getDate("create_date"))
                    .modifyDate(resultSet.getDate("modify_date"))
                    .createUserId(resultSet.getString("create_user_id"))
                    .modifyUserId(resultSet.getString("modify_user_id"))
                    .isActive(true)
                    .build();

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

            contractor.setCountry(country);
            contractor.setIndustry(industry);
            contractor.setOrgForm(orgForm);

            return contractor;
        }

        return null;
    }

}
