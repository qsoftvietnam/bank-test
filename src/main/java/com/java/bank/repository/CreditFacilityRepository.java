package com.java.bank.repository;

import com.java.bank.entity.CreditFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CreditFacilityRepository extends JpaRepository<CreditFacility, UUID> {
}
