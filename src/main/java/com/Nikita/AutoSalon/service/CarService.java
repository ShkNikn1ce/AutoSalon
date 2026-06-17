package com.Nikita.AutoSalon.service;

import com.Nikita.AutoSalon.entity.Car;
import com.Nikita.AutoSalon.enums.CarStatus;
import com.Nikita.AutoSalon.repository.CarRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CarService {

    private final CarRepository carRepository;

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

    //Проверка существования автомобиля по VIN номеру
    public boolean existsCarByVin(String vin) {
        return carRepository.existsByVin(vin);
    }
}
