package com.danidinchev.carmanagement.specifications;

import com.danidinchev.carmanagement.entity.Car;
import org.springframework.data.jpa.domain.Specification;


public class CarSpecification {

    public static Specification<Car> hasCarMake(String carMake) {
        return (root, query, criteriaBuilder) ->
                carMake != null ?
                        criteriaBuilder.like(root.get("make"), "%" + carMake + "%") :
                        criteriaBuilder.conjunction();
    }

    public static Specification<Car> hasProductionYearBetween(Integer fromYear, Integer toYear) {
        return (root, query, criteriaBuilder) -> {
            if (fromYear != null && toYear != null) {
                return criteriaBuilder.between(root.get("productionYear"), fromYear, toYear);
            } else if (fromYear != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("productionYear"), fromYear);
            } else if (toYear != null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("productionYear"), toYear);
            } else {
                return criteriaBuilder.conjunction();
            }
        };
    }
    public static Specification<Car> hasGarageId(Long garageId) {
        return (root, query, criteriaBuilder) ->
                garageId != null ?
                        criteriaBuilder.equal(root.join("garage").get("id"), garageId) :
                        criteriaBuilder.conjunction();
    }
}
