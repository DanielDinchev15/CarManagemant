package com.danidinchev.carmanagement.controller;

import com.danidinchev.carmanagement.dto.ResponseCarDTO;
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
    public List<ResponseCarDTO> getCars() {
        return carService.getAllCars();
    }
}
