package com.Nikita.AutoSalon.controller;

import com.Nikita.AutoSalon.dto.CarResponse;
import com.Nikita.AutoSalon.dto.CreateCarRequest;
import com.Nikita.AutoSalon.dto.UpdateCarRequest;
import com.Nikita.AutoSalon.service.CarService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/cars")
public class CarController {
    private final CarService carService;

    @PostMapping
    public CarResponse createCar(@RequestBody CreateCarRequest request){
        return carService.createCar(request);
    }

    @GetMapping("/search")
    public CarResponse getCarByVin(@RequestParam String vin){
        return carService.findCarByVin(vin);
    }

    @GetMapping("/{carId}")
    public CarResponse getCarById(@RequestParam Long carId){
        return carService.findCarById(carId);
    }

    @GetMapping
    public List<CarResponse> getAllCars(){
        return carService.findAllCars();
    }

    @DeleteMapping("/{vin}")
    public void deleteCar(@PathVariable String vin){
        carService.deleteCar(vin);
    }

    @PutMapping("/{carId}")
    public CarResponse updateCar(@PathVariable Long carId, @RequestBody UpdateCarRequest carRequest){
        return carService.updateInfoCar(carId,carRequest);
    }

}
