package com.danidinchev.carmanagement.specifications;

import com.danidinchev.carmanagement.entity.Maintenance;
import org.springframework.data.jpa.domain.Specification;

public class MaintenanceSpecification {
    public static Specification<Maintenance> hasGarageId(Long garageId) {
        return (root, query, criteriaBuilder) ->
                garageId != null ?
                        criteriaBuilder.equal(root.join("garage").get("id"), garageId) :
                        criteriaBuilder.conjunction();
    }

    public static Specification<Maintenance> hasCarId(Long carId) {
        return (root, query, criteriaBuilder) ->
                carId != null ?
                        criteriaBuilder.equal(root.join("car").get("id"), carId) :
                        criteriaBuilder.conjunction();
    }
}
