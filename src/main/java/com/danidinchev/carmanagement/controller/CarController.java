package com.danidinchev.carmanagement.controller;

import com.danidinchev.carmanagement.dto.CreateCarDTO;
import com.danidinchev.carmanagement.dto.ResponseCarDTO;
import com.danidinchev.carmanagement.dto.UpdateCarDTO;
import com.danidinchev.carmanagement.entity.Car;
import com.danidinchev.carmanagement.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/{id}")
    public List<ResponseCarDTO> getCarById(@PathVariable Long id){

        return carService.findById(id);
    }

    @GetMapping
    public List<ResponseCarDTO> getCars(
            @RequestParam(required = false, name = "carMake") String carMake,
            @RequestParam(required = false) Long garageId,
            @RequestParam(required = false) Integer fromYear,
            @RequestParam(required = false) Integer toYear
    ) {
        return carService.getAll(carMake, garageId, fromYear, toYear);
    }

    @PostMapping
    public Car createCar(@RequestBody CreateCarDTO carDTO) {

        return carService.saveCar(carDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteCar(@PathVariable Long id){carService.deleteById(id);}

    @PutMapping("/{id}")
    public Car updateCar(@PathVariable Long id, @RequestBody UpdateCarDTO updateCarDTO) {
        return carService.updateCar(id, updateCarDTO);
    }
}
