package com.danidinchev.carmanagement.service;

import com.danidinchev.carmanagement.dto.CreateCarDTO;
import com.danidinchev.carmanagement.dto.ResponseCarDTO;
import com.danidinchev.carmanagement.dto.ResponseGarageDTO;
import com.danidinchev.carmanagement.dto.UpdateCarDTO;
import com.danidinchev.carmanagement.entity.Car;
import com.danidinchev.carmanagement.entity.Garage;
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
    private final GarageRepository garageRepository;

    @Autowired
    public CarService(CarRepository carRepository, GarageRepository garageRepository) {
        this.carRepository = carRepository;
        this.garageRepository = garageRepository;
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

    public Car saveCar(CreateCarDTO createCarDTO) {
        Car car = new Car();
        car.setMake(createCarDTO.getMake());
        car.setModel(createCarDTO.getModel());
        car.setProductionYear(createCarDTO.getProductionYear());
        car.setLicensePlate(createCarDTO.getLicensePlate());

        Optional<Garage> garageOptional = garageRepository.findById(createCarDTO.getGarageIds().get(0));
        garageOptional.ifPresent(car::setGarage);

        return carRepository.save(car);
    }

    public void deleteById(Long id) {
        carRepository.deleteById(id);
    }

    public Car updateCar(Long id, UpdateCarDTO updateCarDTO) {
        Optional<Car> optionalCar = carRepository.findById(id);

        if (optionalCar.isPresent()) {
            Car car = optionalCar.get();

            if (updateCarDTO.getMake() != null) car.setMake(updateCarDTO.getMake());
            if (updateCarDTO.getModel() != null) car.setModel(updateCarDTO.getModel());
            if (updateCarDTO.getProductionYear() != 0) car.setProductionYear(updateCarDTO.getProductionYear());
            if (updateCarDTO.getLicensePlate() != null) car.setLicensePlate(updateCarDTO.getLicensePlate());

            if (updateCarDTO.getGarageIds() != null && !updateCarDTO.getGarageIds().isEmpty()) {
                Long garageId = updateCarDTO.getGarageIds().get(0);
                Optional<Garage> garageOptional = garageRepository.findById(garageId);
                garageOptional.ifPresent(car::setGarage);
            }

            return carRepository.save(car);
        } else {
            throw new RuntimeException("Car not found");
        }

    }

}
