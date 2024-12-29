package com.danidinchev.carmanagement.service;

import com.danidinchev.carmanagement.dto.ResponseCarDTO;
import com.danidinchev.carmanagement.dto.ResponseGarageDTO;
import com.danidinchev.carmanagement.entity.Car;
import com.danidinchev.carmanagement.repository.CarRepository;
import com.danidinchev.carmanagement.repository.GarageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarService {

    private final CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository, GarageRepository garageRepository) {
        this.carRepository = carRepository;
    }
    public List<ResponseCarDTO> getAllCars() {
        List<Car> cars = carRepository.findAll();
        return cars.stream()
                .map(this::convertToCarDTO)
                .collect(Collectors.toList());
    }

    private ResponseCarDTO convertToCarDTO(Car car) {
        ResponseGarageDTO garageDTO = new ResponseGarageDTO(
                car.getGarage().getId(),
                car.getGarage().getName(),
                car.getGarage().getLocation(),
                car.getGarage().getCity(),
                car.getGarage().getCapacity()
        );

        return new ResponseCarDTO(
                car.getId(),
                car.getMake(),
                car.getModel(),
                car.getProductionYear(),
                car.getLicensePlate(),
                Collections.singletonList(garageDTO)
        );
    }


    public List<ResponseCarDTO> findById(Long id) {
        Optional<Car> result = carRepository.findById(id);

        Car theCar = result.orElseThrow(() ->
                new RuntimeException("There is no car with this id: " + id)
        );

        return List.of(convertToCarDTO(theCar));
    }

}
