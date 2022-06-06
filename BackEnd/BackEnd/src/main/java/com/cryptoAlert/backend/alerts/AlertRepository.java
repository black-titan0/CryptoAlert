package com.cryptoAlert.backend.alerts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertRepository extends
        JpaRepository<Alert, Long> {
}
