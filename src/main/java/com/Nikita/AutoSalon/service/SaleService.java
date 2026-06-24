package com.Nikita.AutoSalon.service;
import com.Nikita.AutoSalon.dto.CreateSaleRequest;
import com.Nikita.AutoSalon.entity.Car;
import com.Nikita.AutoSalon.entity.PurchaseRequest;
import com.Nikita.AutoSalon.entity.Sale;
import com.Nikita.AutoSalon.entity.User;
import com.Nikita.AutoSalon.enums.CarStatus;
import com.Nikita.AutoSalon.enums.PurchaseRequestStatus;
import com.Nikita.AutoSalon.enums.Roles;
import com.Nikita.AutoSalon.repository.CarRepository;
import com.Nikita.AutoSalon.repository.PurchaseRequestRepository;
import com.Nikita.AutoSalon.repository.SaleRepository;
import com.Nikita.AutoSalon.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class SaleService {
    private final SaleRepository saleRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final PurchaseRequestRepository purchaseRequestRepository;

    @Transactional
    public Sale createSale(CreateSaleRequest saleRequest) {

        //Поиск заявки
        PurchaseRequest purchaseRequestForSale = purchaseRequestRepository.findById(saleRequest.getPurchaseRequestId()).orElseThrow(() ->
                new RuntimeException("Такой заявки не существует!"));

        if (purchaseRequestForSale.getStatus() != PurchaseRequestStatus.APPROVED) {
            throw new RuntimeException("Данная заявка не одобрена!");
        }

        //Поиск машины
        Car carSale = purchaseRequestForSale.getCar();
        if (carSale.getStatus() != CarStatus.AVAILABLE) {
            throw new RuntimeException("Данный автомобиль недоступен!");
        }

        //Поиск сотрудника
        User manager = purchaseRequestForSale.getManager();
        if (manager == null) {
            throw new RuntimeException(
                    "Для заявки не назначен менеджер"
            );
        }
        if(manager.getRole()!=Roles.MANAGER){
            throw new RuntimeException("Данный пользователь не является сотрудником!");
        }

        //Поиск клиента
        User customer = purchaseRequestForSale.getCustomer();

        //Создание продажи
        Sale newSale = new Sale();

        //Заполнение данных
        newSale.setCar(carSale);
        newSale.setManager(manager);
        newSale.setCustomer(customer);
        newSale.setSaleDate(LocalDateTime.now());
        newSale.setFinalPrice(carSale.getPrice());

        //Изменение статуса автомобиля
        carSale.setStatus(CarStatus.SOLD);

        //Изменение статуса заявки
        purchaseRequestForSale.setStatus(PurchaseRequestStatus.COMPLETED);

        return  saleRepository.save(newSale);
    }


    //Проверка существования записи о продаже
    public boolean existsSale(Long saleId) {

        return saleRepository.existsById(saleId);
    }

    //Вывод списка всех покупок совершенных клиентом
    public List<Sale> findSalesByCustomer(Long customerId) {
        User customerSale = userRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Такого пользователя не существует!"));
        if (customerSale.getRole() != Roles.CUSTOMER) {
            throw new RuntimeException("Данный пользователь не является клиентом!");
        }

        return saleRepository.findAllByCustomerId(customerId);
    }

    //Список всех сделок проведенный сотрудником
    public List<Sale> findSalesByManager(Long managerId) {
        User managerSale = userRepository.findById(managerId).orElseThrow(() -> new RuntimeException("Такого пользователя не существует!"));
        if (managerSale.getRole() != Roles.MANAGER) {
            throw new RuntimeException("Данный пользователь не является сотрудником!");
        }
        return saleRepository.findAllByManagerId(managerId);
    }
}
