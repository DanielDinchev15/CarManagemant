package com.danidinchev.carmanagement.specifications;

import com.danidinchev.carmanagement.entity.Garage;
import org.springframework.data.jpa.domain.Specification;

public class GarageSpecification {

    public static Specification<Garage> hasCity(String city) {
        return (root, query, criteriaBuilder) ->
                city != null ?
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("city")), "%" + city.toLowerCase() + "%") :
                        criteriaBuilder.conjunction();
    }
}
