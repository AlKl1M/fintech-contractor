package com.alkl1m.contractor.repository.spec;

import com.alkl1m.contractor.domain.entitiy.Contractor;
import com.alkl1m.contractor.domain.entitiy.Country;
import com.alkl1m.contractor.domain.entitiy.Industry;
import com.alkl1m.contractor.domain.entitiy.OrgForm;
import com.alkl1m.contractor.web.payload.ContractorFiltersPayload;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
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

            addEqualPredicate(predicates, root, criteriaBuilder, "id", payload.id());
            addEqualPredicate(predicates, root, criteriaBuilder, "parentId", payload.parentId());

            addLikePredicate(predicates, root, criteriaBuilder, "name", payload.name());
            addLikePredicate(predicates, root, criteriaBuilder, "nameFull", payload.nameFull());
            addLikePredicate(predicates, root, criteriaBuilder, "inn", payload.inn());
            addLikePredicate(predicates, root, criteriaBuilder, "ogrn", payload.ogrn());

            if (payload.countryName() != null) {
                Join<Contractor, Country> countryJoin = root.join("country");
                predicates.add(criteriaBuilder.like(countryJoin.get("name"), payload.countryName()));
            }

            if (payload.industry() != null) {
                Join<Contractor, Industry> industryJoin = root.join("industry");
                predicates.add(criteriaBuilder.equal(industryJoin.get("id"), payload.industry().id()));
                predicates.add(criteriaBuilder.equal(industryJoin.get("name"), payload.industry().name()));
            }

            if (payload.orgFormName() != null) {
                Join<Contractor, OrgForm> orgFormJoin = root.join("orgForm");
                predicates.add(criteriaBuilder.like(orgFormJoin.get("name"), payload.orgFormName()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static void addLikePredicate(List<Predicate> predicates, Root<Contractor> root, CriteriaBuilder criteriaBuilder, String field, String value) {
        if (value != null) {
            predicates.add(criteriaBuilder.like(root.get(field), value));
        }
    }

    private static void addEqualPredicate(List<Predicate> predicates, Root<Contractor> root, CriteriaBuilder criteriaBuilder, String field, String value) {
        if (value != null) {
            predicates.add(criteriaBuilder.equal(root.get(field), value));
        }
    }

}
