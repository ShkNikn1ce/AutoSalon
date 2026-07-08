package com.Nikita.AutoSalon.service;

import com.Nikita.AutoSalon.dto.CarResponse;
import com.Nikita.AutoSalon.dto.CreateCarRequest;
import com.Nikita.AutoSalon.dto.UpdateCarRequest;
import com.Nikita.AutoSalon.entity.Brand;
import com.Nikita.AutoSalon.entity.Car;
import com.Nikita.AutoSalon.enums.CarStatus;
import com.Nikita.AutoSalon.mapper.CarMapper;
import com.Nikita.AutoSalon.repository.BrandRepository;
import com.Nikita.AutoSalon.repository.CarRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CarService {
    private final CarRepository carRepository;
    private final BrandRepository brandRepository;
    private final CarMapper carMapper;

    //Создание автомобиля
    @Transactional
    public CarResponse createCar(CreateCarRequest request) {
        Brand brand = brandRepository.findById(request.getBrandId())
                .orElseThrow(() -> new RuntimeException("Такого брэнда не существует!"));

        if (carRepository.existsByVin(request.getVin())) {
            throw new RuntimeException("Такой автомобиль уже существует!");
        }

        if (request.getMileage() < 0) {
            throw new RuntimeException("Пробег не может быть отрицательным");
        }

        if (request.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Цена должна быть больше 0");
        }

        int currentYear = LocalDate.now().getYear();
        if (request.getYear() < 1900 || request.getYear() > currentYear + 1) {
            throw new RuntimeException("Некорректный год выпуска автомобиля!");
        }

        Car car = new Car();
        car.setBrand(brand);
        car.setModel(request.getModel());
        car.setVin(request.getVin());
        car.setYear(request.getYear());
        car.setMileage(request.getMileage());
        car.setPrice(request.getPrice());
        car.setStatus(CarStatus.AVAILABLE);

        Car savedCar = carRepository.save(car);

        return carMapper.toResponse(savedCar);
    }

    //Поиск по VIN
    public CarResponse findCarByVin(String vin) {
        Car car = carRepository.findByVin(vin)
                .orElseThrow(() -> new RuntimeException("Такого автомобиля не существует!"));

        CarResponse response = new CarResponse();

        response.setBrand(car.getBrand().getName());
        response.setModel(car.getModel());
        response.setYear(car.getYear());
        response.setMileage(car.getMileage());
        response.setPrice(car.getPrice());
        response.setStatus(car.getStatus());

        return response;
    }

    //Поиск по ID
    public CarResponse findCarById(Long id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Такого автомобиля не существует!"));

        CarResponse response = new CarResponse();

        response.setBrand(car.getBrand().getName());
        response.setModel(car.getModel());
        response.setYear(car.getYear());
        response.setMileage(car.getMileage());
        response.setPrice(car.getPrice());
        response.setStatus(car.getStatus());

        return response;
    }

    //Удаление автомобиля
    public void deleteCar(String vin) {
        Car car = carRepository.findByVin(vin).orElseThrow(() ->
                new RuntimeException("Автомобиля с таким VIN номером не существует"));

        if (car.getStatus() == CarStatus.SOLD) {
            throw new RuntimeException("Нельзя удалить проданный автомобиль");
        }
        carRepository.delete(car);
    }

    //Обновление данных Автомобиля
    public CarResponse updateInfoCar(Long id, UpdateCarRequest request) {
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

        Car savedCar = carRepository.save(updateCar);

        CarResponse response = new CarResponse();

        response.setBrand(savedCar.getBrand().getName());
        response.setModel(savedCar.getModel());
        response.setYear(savedCar.getYear());
        response.setMileage(savedCar.getMileage());
        response.setPrice(savedCar.getPrice());
        response.setStatus(savedCar.getStatus());

        return response;
    }

    //Вывод списка всех автомобилей
    public List<CarResponse> findAllCars() {
        List<Car> cars = carRepository.findAll();
        List<CarResponse> responses = new ArrayList<>();

        for (Car car : cars) {
            CarResponse response = new CarResponse();

            response.setId(car.getId());
            response.setBrand(car.getBrand().getName());
            response.setModel(car.getModel());
            response.setYear(car.getYear());
            response.setMileage(car.getMileage());
            response.setPrice(car.getPrice());
            response.setStatus(car.getStatus());

            responses.add(response);
        }

        return responses;
    }

    //Проверка существования автомобиля по VIN номеру
    public boolean existsCarByVin(String vin) {
        return carRepository.existsByVin(vin);
    }
}
