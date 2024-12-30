package com.danidinchev.carmanagement.service;

import com.danidinchev.carmanagement.dto.CreateMaintenanceDTO;
import com.danidinchev.carmanagement.dto.MonthlyRequestsReportDTO;
import com.danidinchev.carmanagement.dto.ResponseMaintenanceDTO;
import com.danidinchev.carmanagement.dto.UpdateMaintenanceDTO;
import com.danidinchev.carmanagement.entity.Car;
import com.danidinchev.carmanagement.entity.Garage;
import com.danidinchev.carmanagement.entity.Maintenance;
import com.danidinchev.carmanagement.repository.CarRepository;
import com.danidinchev.carmanagement.repository.GarageRepository;
import com.danidinchev.carmanagement.repository.MaintenanceRepository;
import com.danidinchev.carmanagement.specifications.MaintenanceSpecification;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;
    private final GarageRepository garageRepository;
    private final CarRepository carRepository;

    @Autowired
    public MaintenanceService(MaintenanceRepository maintenanceRepository, GarageRepository garageRepository, CarRepository carRepository) {
        this.maintenanceRepository = maintenanceRepository;
        this.garageRepository = garageRepository;
        this.carRepository = carRepository;
    }

    public List<ResponseMaintenanceDTO> findMaintenances(Long garageId, Long carId) {
        Specification<Maintenance> spec = Specification
                .where(MaintenanceSpecification.hasGarageId(garageId))
                .and(MaintenanceSpecification.hasCarId(carId));

        return maintenanceRepository.findAll(spec).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ResponseMaintenanceDTO convertToDTO(Maintenance maintenance) {
        return new ResponseMaintenanceDTO(
                maintenance.getId(),
                maintenance.getCar().getId(),
                maintenance.getCarName(),
                maintenance.getServiceType(),
                maintenance.getScheduledDate(),
                maintenance.getGarage().getId(),
                maintenance.getGarageName()
        );
    }

    public Maintenance createMaintenance(CreateMaintenanceDTO createMaintenanceDTO) {
        Optional<Garage> optionalGarage = garageRepository.findById(createMaintenanceDTO.getGarageId());
        if (optionalGarage.isEmpty()) {
            throw new EntityNotFoundException("Garage with ID " + createMaintenanceDTO.getGarageId() + " not found");
        }

        Optional<Car> optionalCar = carRepository.findById(createMaintenanceDTO.getCarId());
        if (optionalCar.isEmpty()) {
            throw new EntityNotFoundException("Car with ID " + createMaintenanceDTO.getCarId() + " not found");
        }
        Maintenance maintenance = new Maintenance();
        maintenance.setGarage(optionalGarage.get());
        maintenance.setCar(optionalCar.get());
        maintenance.setServiceType(createMaintenanceDTO.getServiceType());
        maintenance.setScheduledDate(createMaintenanceDTO.getScheduledDate());

        return maintenanceRepository.save(maintenance);
    }

    public Maintenance updateMaintenance(Long id, UpdateMaintenanceDTO updateMaintenanceDTO) {
        Optional<Maintenance> optionalMaintenance = maintenanceRepository.findById(id);

        if (optionalMaintenance.isPresent()) {
            Maintenance maintenance = optionalMaintenance.get();

            if (updateMaintenanceDTO.getServiceType() != null) maintenance.setServiceType(updateMaintenanceDTO.getServiceType());
            if (updateMaintenanceDTO.getScheduledDate() != null) maintenance.setScheduledDate(updateMaintenanceDTO.getScheduledDate());

            if (updateMaintenanceDTO.getGarageId() != 0) {
                Optional<Garage> garageOpt = garageRepository.findById(updateMaintenanceDTO.getGarageId());
                garageOpt.ifPresent(maintenance::setGarage);
            }

            if (updateMaintenanceDTO.getCarId() != 0) {
                Optional<Car> carOpt = carRepository.findById(updateMaintenanceDTO.getCarId());
                carOpt.ifPresent(maintenance::setCar);
            }

            return maintenanceRepository.save(maintenance);
        } else {
            throw new RuntimeException("There is not maintenance with id: " + id);
        }
    }

    public void deleteById(Long id) {
        maintenanceRepository.deleteById(id);
    }

    public List<ResponseMaintenanceDTO> findById(Long id) {

        Optional<Maintenance> result = maintenanceRepository.findById(id);

        Maintenance theMaintenance = result.orElseThrow(() ->
                new RuntimeException("There is not maintenance with id: " + id)
        );
        return List.of(convertToDTO(theMaintenance));
    }

    public List<MonthlyRequestsReportDTO> getMonthlyRequestReport(Long garageId, String startMonth, String endMonth) {
        List<MonthlyRequestsReportDTO> monthlyRequestsReportDTO = new ArrayList<>();
        List<Maintenance> maintenances = maintenanceRepository.findByGarage_Id(garageId);

        YearMonth currentYearMonth = YearMonth.parse(startMonth);
        YearMonth yearMonthEnd = YearMonth.parse(endMonth);

        Map<YearMonth, Integer> yearMonthRequestsMap = new HashMap<>();

        do {
            yearMonthRequestsMap.put(currentYearMonth, 0);
            currentYearMonth = currentYearMonth.plusMonths(1);
        } while (currentYearMonth.isBefore(yearMonthEnd.plusMonths(1)));

        for (Maintenance maintenance : maintenances) {
            YearMonth maintenanceMonth = YearMonth.parse(maintenance.getScheduledDate(), DateTimeFormatter.ISO_LOCAL_DATE);
            if (yearMonthRequestsMap.containsKey(maintenanceMonth)) {
                yearMonthRequestsMap.put(maintenanceMonth, yearMonthRequestsMap.get(maintenanceMonth) + 1);
            }
        }

        for (YearMonth yearMonth : yearMonthRequestsMap.keySet()) {
            MonthlyRequestsReportDTO requestsReportDTO = new MonthlyRequestsReportDTO();
            requestsReportDTO.setYearMonth(yearMonth);
            requestsReportDTO.setRequests(yearMonthRequestsMap.get(yearMonth));
            monthlyRequestsReportDTO.add(requestsReportDTO);
        }
        return monthlyRequestsReportDTO;
    }
}
