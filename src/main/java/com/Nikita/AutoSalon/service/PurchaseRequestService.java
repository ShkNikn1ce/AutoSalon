package com.Nikita.AutoSalon.service;

import com.Nikita.AutoSalon.dto.CreatePurchaseRequestRequest;
import com.Nikita.AutoSalon.dto.PurchaseRequestResponse;
import com.Nikita.AutoSalon.entity.Car;
import com.Nikita.AutoSalon.entity.PurchaseRequest;
import com.Nikita.AutoSalon.entity.User;
import com.Nikita.AutoSalon.enums.CarStatus;
import com.Nikita.AutoSalon.enums.PurchaseRequestStatus;
import com.Nikita.AutoSalon.enums.Roles;
import com.Nikita.AutoSalon.mapper.PurchaseRequestMapper;
import com.Nikita.AutoSalon.repository.CarRepository;
import com.Nikita.AutoSalon.repository.PurchaseRequestRepository;
import com.Nikita.AutoSalon.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor
public class PurchaseRequestService {
    private final PurchaseRequestRepository purchaseRequestRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final PurchaseRequestMapper purchaseRequestMapper;

    //Создание заявки
    @Transactional
    public PurchaseRequestResponse createRequest(CreatePurchaseRequestRequest request) {

        PurchaseRequest purchaseRequest = new PurchaseRequest();

        if (purchaseRequestRepository.existsByCarIdAndStatusIn(request.getCarId(),
                List.of(PurchaseRequestStatus.NEW,
                        PurchaseRequestStatus.IN_PROGRESS,
                        PurchaseRequestStatus.APPROVED))) {
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

        if (request.getComment() == null || request.getComment().isBlank()) {
            throw new RuntimeException("Комментарий обязателен!");
        }

        if(request.getComment().length()>100){
            throw  new RuntimeException("Длина комментария должна быть не более 100 символов!");
        }

        purchaseRequest.setCustomer(customer);
        purchaseRequest.setCar(requestCar);
        requestCar.setStatus(CarStatus.RESERVED);
        purchaseRequest.setComment(request.getComment());
        purchaseRequest.setCreatedAt(LocalDateTime.now());
        purchaseRequest.setStatus(PurchaseRequestStatus.NEW);

        PurchaseRequest savedRequest = purchaseRequestRepository.save(purchaseRequest);

        PurchaseRequestResponse response = new PurchaseRequestResponse();

        User customerResponse = savedRequest.getCustomer();

        response.setId(savedRequest.getId());
        response.setCustomerId(customerResponse.getId());
        response.setCustomerFirstName(customerResponse.getFirstName());
        response.setCustomerLastName(customerResponse.getLastName());

        if (savedRequest.getManager() != null) {
            response.setManagerId(savedRequest.getManager().getId());
            response.setManagerFirstName(savedRequest.getManager().getFirstName());
            response.setManagerLastName(savedRequest.getManager().getLastName());
        }

        Car carResponse = savedRequest.getCar();

        response.setCarId(carResponse.getId());
        response.setBrand(carResponse.getBrand().getName());
        response.setModel(carResponse.getModel());
        response.setYear(carResponse.getYear());
        response.setMileage(carResponse.getMileage());

        response.setComment(savedRequest.getComment());

        response.setStatus(savedRequest.getStatus());

        response.setRejectReason(savedRequest.getRejectReason());

        response.setCreatedAt(savedRequest.getCreatedAt());

        return response;
    }

    //Вывод списка заявок по указанному статусу
    public List<PurchaseRequestResponse> findAllByStatus(PurchaseRequestStatus status) {
        List<PurchaseRequest> requests = purchaseRequestRepository.findAllByStatus(status);
        List<PurchaseRequestResponse> responses = new ArrayList<>();

        for(PurchaseRequest request :requests) {

            PurchaseRequestResponse response = new PurchaseRequestResponse();
            User customerResponse = request.getCustomer();

            response.setId(request.getId());
            response.setCustomerId(customerResponse.getId());
            response.setCustomerFirstName(customerResponse.getFirstName());
            response.setCustomerLastName(customerResponse.getLastName());

            if (request.getManager() != null) {
                response.setManagerId(request.getManager().getId());
                response.setManagerFirstName(request.getManager().getFirstName());
                response.setManagerLastName(request.getManager().getLastName());
            }

            Car carResponse = request.getCar();

            response.setCarId(carResponse.getId());
            response.setBrand(carResponse.getBrand().getName());
            response.setModel(carResponse.getModel());
            response.setYear(carResponse.getYear());
            response.setMileage(carResponse.getMileage());

            response.setComment(request.getComment());

            response.setStatus(request.getStatus());

            response.setRejectReason(request.getRejectReason());

            response.setCreatedAt(request.getCreatedAt());

            responses.add(response);
        }

        return responses;
    }

    //Вывод истории покупок конкретным пользователем
    public List<PurchaseRequestResponse> findCustomerRequests(Long id) {
        User customer = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Пользователя с таким ID не существует!"));
        if (customer.getRole() != Roles.CUSTOMER){
            throw new RuntimeException("Данный пользователь не является клиентом!");
        }
        List<PurchaseRequest>requests = purchaseRequestRepository.findByCustomerId(id);
        List<PurchaseRequestResponse> responses = new ArrayList<>();
        for(PurchaseRequest request : requests){
            responses.add(purchaseRequestMapper.toResponse(request));
        }
        return responses;
    }

    //Вывод всех заявок закрепленных за конкретным менеджером
    public List<PurchaseRequestResponse> findManagerRequests(Long id) {
        User manager = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Пользователя с таким ID не существует!"));
        if (manager.getRole() != Roles.MANAGER) {
            throw new RuntimeException("Данный пользователь не является менеджером!");
        }
        List<PurchaseRequest> requests = purchaseRequestRepository.findByManagerId(id);
        List<PurchaseRequestResponse> responses = new ArrayList<>();
        for (PurchaseRequest request : requests) {
            responses.add(purchaseRequestMapper.toResponse(request));
        }
        return responses;
    }

    //Назначение сотрудника на заявку о продаже автомобиля
    public PurchaseRequestResponse assignManager(Long requestId, Long managerId) {

        PurchaseRequest request = purchaseRequestRepository.findById(requestId).orElseThrow(() ->
                new RuntimeException("Такой заявки не существует!"));

        if (request.getStatus() != PurchaseRequestStatus.NEW) {
            throw new RuntimeException("Данная заявка уже находится в работе!");
        }

        User manager = userRepository.findById(managerId).orElseThrow(() -> new RuntimeException("Такого пользователя не существует!"));

        if (manager.getRole() != Roles.MANAGER) {
            throw new RuntimeException("Данный пользователь не является сотрудником!");
        }
        request.setManager(manager);
        request.setStatus(PurchaseRequestStatus.IN_PROGRESS);

        PurchaseRequest savedRequest = purchaseRequestRepository.save(request);

        return purchaseRequestMapper.toResponse(savedRequest);
    }

    //Изменение статуса. Одобрение заявки
    public PurchaseRequestResponse approveRequest(Long requestId) {
        PurchaseRequest request = purchaseRequestRepository.findById(requestId).orElseThrow(() ->
                new RuntimeException("Такой заявки не существует!"));
        if (request.getStatus() != PurchaseRequestStatus.IN_PROGRESS) {
            throw new RuntimeException("Данная заявка не взята в работу сотрудником!");
        }
        if (request.getManager() == null) {
            throw new RuntimeException("У заявки не назначен менеджер");
        }
        request.setStatus(PurchaseRequestStatus.APPROVED);
        PurchaseRequest savedRequest = purchaseRequestRepository.save(request);
        return purchaseRequestMapper.toResponse(savedRequest);
    }

    //Изменение статуса. Отклонение заявки
    public PurchaseRequestResponse rejectRequest(Long requestId, String reason) {
        PurchaseRequest request = purchaseRequestRepository.findById(requestId).orElseThrow(() ->
                new RuntimeException("Такой заявки не существует!"));
        if (request.getStatus() != PurchaseRequestStatus.IN_PROGRESS) {
            throw new RuntimeException("Данная заявка не взята в работу сотрудником!");
        }
        request.setStatus(PurchaseRequestStatus.REJECTED);
        request.setRejectReason(reason);

        PurchaseRequest savedRequest = purchaseRequestRepository.save(request);
        return purchaseRequestMapper.toResponse(savedRequest);
    }

    //Проверка существования конкретного автомобиля и его статуса
    public boolean existsActiveRequestForCar(Long carId, Collection<PurchaseRequestStatus> statuses) {
        return purchaseRequestRepository.existsByCarIdAndStatusIn(carId, statuses);
    }
}
