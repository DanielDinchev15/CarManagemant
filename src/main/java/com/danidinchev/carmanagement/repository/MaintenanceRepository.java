package com.danidinchev.carmanagement.repository;

import com.danidinchev.carmanagement.entity.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaintenanceRepository extends JpaRepository<Maintenance, Long>, JpaSpecificationExecutor<Maintenance> {

    List<Maintenance> findByGarage_Id(Long garageId);

    @Query("SELECT m FROM Maintenance m WHERE m.garage.id = :garageId AND m.scheduledDate = :scheduledDate")
    List<Maintenance> findByGarageIdAndScheduledDate(@Param("garageId") Long garageId, @Param("scheduledDate") String scheduledDate);
}
