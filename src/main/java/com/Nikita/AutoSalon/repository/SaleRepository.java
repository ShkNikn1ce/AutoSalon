package com.Nikita.AutoSalon.repository;

import com.Nikita.AutoSalon.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findAllByCustomerId(Long customerId);
    List<Sale> findAllByManagerId(Long managerId);
}
