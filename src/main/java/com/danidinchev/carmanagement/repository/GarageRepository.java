package com.danidinchev.carmanagement.repository;

import com.danidinchev.carmanagement.entity.Garage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface GarageRepository extends JpaRepository<Garage, Long>, JpaSpecificationExecutor<Garage> {
}
