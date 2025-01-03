package com.danidinchev.carmanagement.controller;

import com.danidinchev.carmanagement.dto.CreateGarageDTO;
import com.danidinchev.carmanagement.dto.GarageDailyAvailabilityReportDTO;
import com.danidinchev.carmanagement.dto.ResponseGarageDTO;
import com.danidinchev.carmanagement.dto.UpdateGarageDTO;
import com.danidinchev.carmanagement.entity.Garage;
import com.danidinchev.carmanagement.service.GarageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/garages")
public class GarageController {

    private final GarageService garageService;

    @Autowired
    public GarageController(GarageService garageService) {
        this.garageService = garageService;
    }

    @GetMapping("/{id}")
    public ResponseGarageDTO getGarageById(@PathVariable Long id) {
        return garageService.getGarageById(id);
    }

    @GetMapping
    public List<ResponseGarageDTO> getAllGarages(@RequestParam(required = false, name = "city") String city) {
        return garageService.getAllGarages(city);
    }

    @PostMapping
    public ResponseEntity<Garage> createGarage(@RequestBody CreateGarageDTO createGarageDTO) {
        Garage createdGarage = garageService.createGarage(createGarageDTO);
        return ResponseEntity.ok(createdGarage);
    }

    @PutMapping ("/{id}")
    public Garage updateGarage(@PathVariable Long id, @RequestBody UpdateGarageDTO updateGarageDTO){
        return garageService.updateGarage(id, updateGarageDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteGarage(@PathVariable Long id){garageService.deleteById(id);}

    @GetMapping("/dailyAvailabilityReport")
    public List<GarageDailyAvailabilityReportDTO> getGarageDailyAvailabilityReport(
            @RequestParam Long garageId,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        return garageService.getGarageDailyAvailabilityReport(garageId, startDate, endDate);
    }

}

