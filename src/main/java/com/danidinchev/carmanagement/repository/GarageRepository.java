package com.danidinchev.carmanagement.repository;

import com.danidinchev.carmanagement.entity.Garage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GarageRepository extends JpaRepository<Garage, Long>, JpaSpecificationExecutor<Garage> {
    @Query("SELECT COUNT(m) FROM Maintenance m WHERE m.garage.id = :garageId AND m.scheduledDate = :scheduledDate")
    int countByGarageIdAndScheduledDate(@Param("garageId") Long garageId, @Param("scheduledDate") String scheduledDate);
}
