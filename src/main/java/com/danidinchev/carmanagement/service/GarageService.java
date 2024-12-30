package com.danidinchev.carmanagement.service;

import com.danidinchev.carmanagement.dto.CreateGarageDTO;
import com.danidinchev.carmanagement.dto.GarageDailyAvailabilityReportDTO;
import com.danidinchev.carmanagement.dto.ResponseGarageDTO;
import com.danidinchev.carmanagement.dto.UpdateGarageDTO;
import com.danidinchev.carmanagement.entity.Garage;
import com.danidinchev.carmanagement.repository.GarageRepository;
import com.danidinchev.carmanagement.repository.MaintenanceRepository;
import com.danidinchev.carmanagement.specifications.GarageSpecification;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GarageService {

    private final GarageRepository garageRepository;
    private final MaintenanceRepository maintenanceRepository;

    @Autowired
    public GarageService(GarageRepository garageRepository, MaintenanceRepository maintenanceRepository) {
        this.garageRepository = garageRepository;
        this.maintenanceRepository = maintenanceRepository;
    }

    public List<ResponseGarageDTO> getAllGarages(String city) {

        Specification<Garage> spec = Specification.where(GarageSpecification.hasCity(city));

        return garageRepository.findAll(spec).stream()
                .map(garage -> new ResponseGarageDTO(
                        garage.getId(),
                        garage.getName(),
                        garage.getLocation(),
                        garage.getCity(),
                        garage.getCapacity()
                ))
                .collect(Collectors.toList());
    }

    public ResponseGarageDTO getGarageById(Long id) {
        Garage garage = garageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Garage not found with id: " + id));
        return new ResponseGarageDTO(
                garage.getId(),
                garage.getName(),
                garage.getLocation(),
                garage.getCity(),
                garage.getCapacity()
        );
    }

    public Garage createGarage(CreateGarageDTO createGarageDTO) {
        Garage garage = new Garage(null,
                createGarageDTO.getName(),
                createGarageDTO.getLocation(),
                createGarageDTO.getCapacity(),
                createGarageDTO.getCity());

        return garageRepository.save(garage);
    }

    public void deleteById(Long id){garageRepository.deleteById(id);}

    public Garage updateGarage(Long id, UpdateGarageDTO updateGarageDTO) {
        Optional<Garage> optionalGarage = garageRepository.findById(id);

        if (optionalGarage.isPresent()) {
            Garage garage = optionalGarage.get();

            if (updateGarageDTO.getName() != null) garage.setName(updateGarageDTO.getName());
            if (updateGarageDTO.getLocation() != null) garage.setLocation(updateGarageDTO.getLocation());
            if (updateGarageDTO.getCapacity() != 0) garage.setCapacity(updateGarageDTO.getCapacity());
            if (updateGarageDTO.getCity() != null) garage.setCity(updateGarageDTO.getCity());

            return garageRepository.save(garage);
        } else {
            throw new EntityNotFoundException("Not found Garage with id:" + id);
        }
    }

    public List<GarageDailyAvailabilityReportDTO> getGarageDailyAvailabilityReport(Long garageId, String startDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);
        List<GarageDailyAvailabilityReportDTO> report = new ArrayList<>();

        for (LocalDate currentDate = start; !currentDate.isAfter(end); currentDate = currentDate.plusDays(1)) {
            int requests = garageRepository.countByGarageIdAndScheduledDate(garageId, currentDate.toString());
            Garage garage = garageRepository.findById(garageId)
                    .orElseThrow(() -> new EntityNotFoundException("Garage not found with id: " + garageId));
            int availableCapacity = garage.getCapacity() - requests;
            GarageDailyAvailabilityReportDTO dailyReport = new GarageDailyAvailabilityReportDTO(
                    currentDate.toString(),
                    requests,
                    availableCapacity
            );
            report.add(dailyReport);
        }
        return report;
    }

}
