package com.Nikita.AutoSalon.service;
import com.Nikita.AutoSalon.dto.request.CreateSaleRequest;
import com.Nikita.AutoSalon.dto.response.SaleDetailResponse;
import com.Nikita.AutoSalon.dto.response.SaleResponse;
import com.Nikita.AutoSalon.entity.Car;
import com.Nikita.AutoSalon.entity.PurchaseRequest;
import com.Nikita.AutoSalon.entity.Sale;
import com.Nikita.AutoSalon.entity.User;
import com.Nikita.AutoSalon.enums.CarStatus;
import com.Nikita.AutoSalon.enums.PurchaseRequestStatus;
import com.Nikita.AutoSalon.enums.Roles;
import com.Nikita.AutoSalon.mapper.SaleMapper;
import com.Nikita.AutoSalon.repository.CarRepository;
import com.Nikita.AutoSalon.repository.PurchaseRequestRepository;
import com.Nikita.AutoSalon.repository.SaleRepository;
import com.Nikita.AutoSalon.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class SaleService {
    private final SaleRepository saleRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final SaleMapper saleMapper;
    private final PurchaseRequestRepository purchaseRequestRepository;

    @Transactional
    public SaleDetailResponse createSale(CreateSaleRequest saleRequest) {

        //Поиск заявки
        PurchaseRequest purchaseRequestForSale = purchaseRequestRepository.findById(saleRequest.getPurchaseRequestId()).orElseThrow(() ->
                new RuntimeException("Такой заявки не существует!"));

        if (purchaseRequestForSale.getStatus() != PurchaseRequestStatus.APPROVED) {
            throw new RuntimeException("Данная заявка не одобрена!");
        }

        //Поиск машины
        Car carSale = purchaseRequestForSale.getCar();
        if (carSale.getStatus() != CarStatus.RESERVED){
            throw new RuntimeException("На данный автомобиль не существует заявки!");
        }

        //Поиск сотрудника
        User manager = purchaseRequestForSale.getManager();
        if (manager == null) {
            throw new RuntimeException(
                    "Для заявки не назначен менеджер"
            );
        }
        if (manager.getRole() != Roles.MANAGER) {
            throw new RuntimeException("Данный пользователь не является сотрудником!");
        }

        //Поиск клиента
        User customer = purchaseRequestForSale.getCustomer();
        if (customer.getRole() != Roles.CUSTOMER) {
            throw new RuntimeException("Данный пользователь не является клиентом!");
        }

        //Создание продажи
        Sale newSale = new Sale();

        //Заполнение данных
        newSale.setCar(carSale);
        newSale.setManager(manager);
        newSale.setCustomer(customer);
        newSale.setPurchaseRequest(purchaseRequestForSale);
        newSale.setSaleDate(LocalDateTime.now());
        newSale.setFinalPrice(carSale.getPrice());

        //Изменение статуса автомобиля
        carSale.setStatus(CarStatus.SOLD);

        //Изменение статуса заявки
        purchaseRequestForSale.setStatus(PurchaseRequestStatus.COMPLETED);

        carRepository.save(carSale);
        purchaseRequestRepository.save(purchaseRequestForSale);

        Sale savedSale = saleRepository.save(newSale);


        return saleMapper.toDetailResponse(savedSale);

    }

    //Вывод списка всех покупок совершенных клиентом
    public List<SaleDetailResponse> findSalesByCustomer(Long id) {
        User customerSale = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Такого пользователя не существует!"));
        if (customerSale.getRole() != Roles.CUSTOMER) {
            throw new RuntimeException("Данный пользователь не является клиентом!");
        }
        List<Sale> salesCustomer = saleRepository.findAllByCustomerId(id);
        List<SaleDetailResponse> responsesCustomer = new ArrayList<>();

        for (Sale sale : salesCustomer) {
            responsesCustomer.add(saleMapper.toDetailResponse(sale));
        }
        return responsesCustomer;
    }

    //Список всех сделок проведенный сотрудником
    public List<SaleResponse> findSalesByManager(Long id) {
        User managerSale = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Такого пользователя не существует!"));
        if (managerSale.getRole() != Roles.MANAGER) {
            throw new RuntimeException("Данный пользователь не является сотрудником!");
        }
        List<Sale> salesManager = saleRepository.findAllByManagerId(id);
        List<SaleResponse> responsesManager = new ArrayList<>();

        for (Sale sale : salesManager) {
            responsesManager.add(saleMapper.toResponse(sale));
        }

        return responsesManager;

    }

    //Проверка существования записи о продаже
    public boolean existsSale(Long saleId) {
        return saleRepository.existsById(saleId);
    }
}
