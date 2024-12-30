package com.danidinchev.carmanagement.repository;

import com.danidinchev.carmanagement.entity.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MaintenanceRepository extends JpaRepository<Maintenance, Long>, JpaSpecificationExecutor<Maintenance> {
}
