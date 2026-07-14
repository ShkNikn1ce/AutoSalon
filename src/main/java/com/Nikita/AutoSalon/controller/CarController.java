package com.Nikita.AutoSalon.controller;

import com.Nikita.AutoSalon.dto.response.CarResponse;
import com.Nikita.AutoSalon.dto.request.CreateCarRequest;
import com.Nikita.AutoSalon.dto.request.UpdateCarRequest;
import com.Nikita.AutoSalon.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@Tag(name="Автомобили", description = "API для управления автомобилями")
@RequestMapping("/cars")
public class CarController {
    private final CarService carService;

    @Operation(
            summary = "Создать новый автомобиль",
            description = "Создает запись о новом автомобиле в системе"
    )
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Автомобиль успешно создан"),
            @ApiResponse(responseCode = "400", description = "Неверные данные запроса"),
            @ApiResponse(responseCode = "409", description = "Автомобиль с таким VIN уже существует")

    }

    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CarResponse createCar(@RequestBody CreateCarRequest request){
        return carService.createCar(request);
    }

    @GetMapping("/search")
    public CarResponse getCarByVin(@RequestParam String vin){
        return carService.findCarByVin(vin);
    }

    @GetMapping("/{carId}")
    public CarResponse getCarById(@PathVariable Long carId){
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
