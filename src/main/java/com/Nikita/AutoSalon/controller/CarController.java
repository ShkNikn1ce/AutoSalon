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
            @ApiResponse(responseCode = "201", description = "Автомобиль успешно создан"),
            @ApiResponse(responseCode = "400", description = "Неверные данные запроса"),
            @ApiResponse(responseCode = "500", description = "Такой автомобиль уже существует")
    }

    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CarResponse createCar(@RequestBody CreateCarRequest request){
        return carService.createCar(request);
    }

    @Operation(
            summary = "Вывести список всех автомобилей",
            description = "Выводит список всех автомобилей"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный вывод списка всех автомобилей"),
            @ApiResponse(responseCode = "404", description = "Указанный путь не найден")
    }

    )
    @GetMapping
    public List<CarResponse> getAllCars(){
        return carService.findAllCars();
    }

    @Operation(
            summary = "Поиск автомобиля по VIN",
            description = "Возвращает информацию об автомобиле по его уникальному VIN-номеру"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Автомобиль найден"),
            @ApiResponse(responseCode = "404", description = "Автомобиль с таким VIN не найден")
    }
    )
    @GetMapping("/search")
    public CarResponse getCarByVin(@RequestParam String vin){
        return carService.findCarByVin(vin);
    }

    @Operation(
            summary = "Получить автомобиль по ID",
            description = "Возвращает информацию об автомобиле по его внутреннему ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Автомобиль найден"),
            @ApiResponse(responseCode = "404", description = "Автомобиль с таким ID не найден")
    }

    )
    @GetMapping("/{carId}")
    public CarResponse getCarById(@PathVariable Long carId){
        return carService.findCarById(carId);
    }

    @Operation(
            summary = "Удалить автомобиль",
            description = "Удаляет автомобиль из системы по VIN-номеру"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Автомобиль успешно удален"),
            @ApiResponse(responseCode = "404", description = "Автомобиль с таким VIN не найден")
    })
    @DeleteMapping("/{vin}")
    public void deleteCar(@PathVariable String vin){
        carService.deleteCar(vin);
    }

    @Operation(
            summary = "Обновить информацию об автомобиле",
            description = "Обновляет данные существующего автомобиля по его ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информация обновлена"),
            @ApiResponse(responseCode = "500", description = "Неверные данные запроса"),
            @ApiResponse(responseCode = "500", description = "Автомобиль с таким ID не найден")
    })
    @PutMapping("/{carId}")
    public CarResponse updateCar(@PathVariable Long carId, @RequestBody UpdateCarRequest carRequest){
        return carService.updateInfoCar(carId,carRequest);
    }

}
