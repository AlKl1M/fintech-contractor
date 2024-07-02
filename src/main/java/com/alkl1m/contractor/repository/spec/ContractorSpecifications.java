package com.alkl1m.contractor.repository.spec;

import com.alkl1m.contractor.domain.entitiy.Contractor;
import com.alkl1m.contractor.domain.entitiy.Country;
import com.alkl1m.contractor.domain.entitiy.Industry;
import com.alkl1m.contractor.domain.entitiy.OrgForm;
import com.alkl1m.contractor.web.payload.ContractorFiltersPayload;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

/**
 * Спецификация для фильтрации данных посредством получения
 * нужных параметров из тела запроса.
 *
 * @author alkl1m
 */
public final class ContractorSpecifications {

    private ContractorSpecifications() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * @param payload список фильтров.
     * @return контрагенты, подходящие под критерии фильтрации.
     */
    public static Specification<Contractor> getContractorByParameters(ContractorFiltersPayload payload) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(criteriaBuilder.isTrue(root.get("isActive")));

            if (payload.id() != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), payload.id()));
            }

            if (payload.parentId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("parentId"), payload.parentId()));
            }

            if (payload.name() != null) {
                predicates.add(criteriaBuilder.like(root.get("name"), payload.name()));
            }

            if (payload.nameFull() != null) {
                predicates.add(criteriaBuilder.like(root.get("nameFull"), payload.nameFull()));
            }

            if (payload.inn() != null) {
                predicates.add(criteriaBuilder.like(root.get("inn"), payload.inn()));
            }

            if (payload.ogrn() != null) {
                predicates.add(criteriaBuilder.like(root.get("ogrn"), payload.ogrn()));
            }

            if (payload.countryName() != null) {
                Join<Contractor, Country> countryJoin = root.join("country");
                predicates.add(criteriaBuilder.like(countryJoin.get("name"), payload.countryName()));
            }

            if (payload.industry() != null) {
                Join<Contractor, Industry> industryJoin = root.join("industry");
                predicates.add(criteriaBuilder.equal(industryJoin.get("id"), payload.industry().getId()));
                predicates.add(criteriaBuilder.equal(industryJoin.get("name"), payload.industry().getName()));
            }

            if (payload.orgFormName() != null) {
                Join<Contractor, OrgForm> orgFormJoin = root.join("orgForm");
                predicates.add(criteriaBuilder.like(orgFormJoin.get("name"), payload.orgFormName()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
