package com.ht.airline.db.repository;

import com.ht.airline.db.entity.AirCarrier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirCarrierRepository extends JpaRepository<AirCarrier, String> {}
