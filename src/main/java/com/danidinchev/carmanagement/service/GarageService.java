package com.danidinchev.carmanagement.service;

import com.danidinchev.carmanagement.dto.CreateGarageDTO;
import com.danidinchev.carmanagement.dto.ResponseGarageDTO;
import com.danidinchev.carmanagement.entity.Garage;
import com.danidinchev.carmanagement.repository.GarageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GarageService {

    private final GarageRepository garageRepository;

    @Autowired
    public GarageService(GarageRepository garageRepository) {
        this.garageRepository = garageRepository;
    }


    public List<ResponseGarageDTO> getAllGarages() {
        return garageRepository.findAll().stream()
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
}
