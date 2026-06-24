package com.Nikita.AutoSalon.service;
import com.Nikita.AutoSalon.dto.CreatePurchaseRequestRequest;
import com.Nikita.AutoSalon.entity.Car;
import com.Nikita.AutoSalon.entity.PurchaseRequest;
import com.Nikita.AutoSalon.entity.User;
import com.Nikita.AutoSalon.enums.CarStatus;
import com.Nikita.AutoSalon.enums.PurchaseRequestStatus;
import com.Nikita.AutoSalon.enums.Roles;
import com.Nikita.AutoSalon.repository.CarRepository;
import com.Nikita.AutoSalon.repository.PurchaseRequestRepository;
import com.Nikita.AutoSalon.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor
public class PurchaseRequestService {
    private final PurchaseRequestRepository purchaseRequestRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;

    //Создание заявки
    @Transactional
    public PurchaseRequest createRequest(CreatePurchaseRequestRequest request) {
        PurchaseRequest purchaseRequest = new PurchaseRequest();

        if(purchaseRequestRepository.existsByCarIdAndStatusIn(request.getCarId(),
                List.of(PurchaseRequestStatus.NEW,
                PurchaseRequestStatus.IN_PROGRESS,
                PurchaseRequestStatus.APPROVED))){
            throw new RuntimeException("На данный автомобиль уже существует активная заявка!");
        }

        User customer = userRepository.findById(request.getCustomerId()).orElseThrow(() -> new RuntimeException("Пользователя с таким ID не существует"));
        if (customer.getRole() != Roles.CUSTOMER) {
            throw new RuntimeException("Пользователь не является клиентом!");
        }

        Car requestCar = carRepository.findById(request.getCarId()).orElseThrow(() -> new RuntimeException("Автомобиля с таким ID не существует!"));
        if (requestCar.getStatus() != CarStatus.AVAILABLE) {
            throw new RuntimeException("Данный автомобиль куплен или зарезервирован!");
        }

        if(request.getComment() == null || request.getComment().isBlank()){
            throw new RuntimeException("Комментарий обязателен!");
        }

        purchaseRequest.setCustomer(customer);
        purchaseRequest.setCar(requestCar);
        requestCar.setStatus(CarStatus.RESERVED);
        purchaseRequest.setComment(request.getComment());
        purchaseRequest.setCreatedAt(LocalDateTime.now());
        purchaseRequest.setStatus(PurchaseRequestStatus.NEW);

        return purchaseRequestRepository.save(purchaseRequest);
    }

    //Проверка существования конкретного автомобиля и его статуса
    public boolean existsActiveRequestForCar(Long carId, Collection<PurchaseRequestStatus> statuses) {
        return purchaseRequestRepository.existsByCarIdAndStatusIn(carId, statuses);
    }

    //Вывод списка заявок по указанному статусу
    public List<PurchaseRequest> findAllByStatus(PurchaseRequestStatus status) {
        return purchaseRequestRepository.findAllByStatus(status);
    }

    //Вывод истории покупок конкретным пользователем
    public List<PurchaseRequest> findCustomerRequests(Long id) {
        User customer = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Пользователя с таким ID не существует!"));
        if (customer.getRole() != Roles.CUSTOMER) {
            throw new RuntimeException("Данный пользователь не является клиентом!");
        }
        return purchaseRequestRepository.findByCustomerId(id);
    }

    //Вывод всех заявок закрепленных за конкретным менеджером
    public List<PurchaseRequest> findManagerRequests(Long id) {
        User manager = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Пользователя с таким ID не существует!"));
        if (manager.getRole() != Roles.MANAGER) {
            throw new RuntimeException("Данный пользователь не является менеджером!");
        }
        return purchaseRequestRepository.findByManagerId(id);
    }

    //Назначение сотрудника на заявку о продаже автомобиля
    public PurchaseRequest assignManager(Long requestId, Long managerId){
        PurchaseRequest request = purchaseRequestRepository.findById(requestId).orElseThrow(()->
                new RuntimeException("Такой заявки не существует!"));
        if(request.getStatus()!=PurchaseRequestStatus.NEW){
            throw new RuntimeException("Данная заявка уже существует и находится в работе!");
        }

        User manager = userRepository.findById(managerId).orElseThrow(()-> new RuntimeException("Такого пользователя не существует!"));
        if(manager.getRole()!=Roles.MANAGER){
            throw new RuntimeException("Данный пользователь не является сотрудником!");
        }
        request.setManager(manager);
        request.setStatus(PurchaseRequestStatus.IN_PROGRESS);
        return purchaseRequestRepository.save(request);
    }

    //Изменение статуса. Одобрение заявки
    public PurchaseRequest approveRequest(Long requestId){
        PurchaseRequest request = purchaseRequestRepository.findById(requestId).orElseThrow(()->
                new RuntimeException("Такой заявки не существует!"));
        if(request.getStatus()!=PurchaseRequestStatus.IN_PROGRESS){
            throw new RuntimeException("Данная заявка не взята в работу сотрудником!");
        }
        if(request.getManager()==null){
            throw new RuntimeException("У заявки не назначен менеджер");
        }
        request.setStatus(PurchaseRequestStatus.APPROVED);
        return purchaseRequestRepository.save(request);
    }

    //Изменение статуса. Отклонение заявки
    public PurchaseRequest rejectRequest(Long requestId, String reason){
        PurchaseRequest request = purchaseRequestRepository.findById(requestId).orElseThrow(()->
                new RuntimeException("Такой заявки не существует!"));
        if(request.getStatus()!=PurchaseRequestStatus.IN_PROGRESS){
            throw new RuntimeException("Данная заявка не взята в работу сотрудником!");
        }
        request.setStatus(PurchaseRequestStatus.REJECTED);
        request.setRejectReason(reason);
        return purchaseRequestRepository.save(request);
    }

}
