package com.Nikita.AutoSalon.service;

import com.Nikita.AutoSalon.dto.UpdateCarRequest;
import com.Nikita.AutoSalon.entity.Car;
import com.Nikita.AutoSalon.enums.CarStatus;
import com.Nikita.AutoSalon.repository.CarRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    //Проверка существования автомобиля по VIN номеру
    public boolean existsCarByVin(String vin) {
        return carRepository.existsByVin(vin);
    }

    //Создание автомобиля
    public Car createCar(Car car) {
        if (carRepository.existsByVin(car.getVin())) {
            throw new RuntimeException("Автомобиль с таким VIN номером уже существует");

        }
        return carRepository.save(car);
    }

    //Удаление автомобиля
    public void deleteCar(String vin) {
        Car car = carRepository.findByVin(vin).orElseThrow(() ->
                new RuntimeException("Автомобиля с таким VIN номером не существует"));

        if (car.getStatus() == CarStatus.SOLD) {
            throw new RuntimeException("Это проданный автомобиль");
        }
        carRepository.delete(car);
    }

    //Поиск по VIN
    public Car findCarByVin(String vin) {
        return carRepository.findByVin(vin).orElseThrow(() ->
                new RuntimeException("Автомобиль с таким VIN не найден!"));
    }

    //Поиск по ID
    public Car findCarById(Long id) {
        return carRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Автомобиль с таким ID не найден!"));

    }

    //Обновление данных Автомобиля
    public Car updateInfoCar(Long id, UpdateCarRequest request) {
        Car updateCar = carRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Автомобиль с таким ID не найден!"));

        int currentYear = LocalDate.now().getYear();

        //Проверка статуса автомобиля
        if (updateCar.getStatus() == CarStatus.SOLD) {
            throw new RuntimeException("Нельзя редактировать данные проданного автомобиля!");
        }

        //Проверка пробега автомобиля
        if (request.getMileage() < 0) {
            throw new RuntimeException("Пробег у авто не может быть отрицательным!");

        }

        //Проверка года выпуска автомобиля
        if (request.getYear() < 1900 || request.getYear() > currentYear + 1) {
            throw new RuntimeException("Некорректный год выпуска автомобиля!");
        }

        //Проверка цены автомобиля
        if (request.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Цена должна быть больше 0!");
        }

        updateCar.setModel(request.getModel());
        updateCar.setYear(request.getYear());
        updateCar.setMileage(request.getMileage());
        updateCar.setPrice(request.getPrice());

        return carRepository.save(updateCar);
    }
}
