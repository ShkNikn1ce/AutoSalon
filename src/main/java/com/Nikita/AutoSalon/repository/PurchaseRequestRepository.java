package com.Nikita.AutoSalon.repository;

import com.Nikita.AutoSalon.dto.PurchaseRequestResponse;
import com.Nikita.AutoSalon.entity.PurchaseRequest;
import com.Nikita.AutoSalon.enums.PurchaseRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface PurchaseRequestRepository extends JpaRepository<PurchaseRequest, Long> {
    boolean existsByCarIdAndStatusIn(Long carId, Collection<PurchaseRequestStatus> statuses);
    List<PurchaseRequest> findAllByStatus(PurchaseRequestStatus status);
    List<PurchaseRequest> findByCustomerId(Long customerId);
    List<PurchaseRequest> findByManagerId(Long managerId);
}
