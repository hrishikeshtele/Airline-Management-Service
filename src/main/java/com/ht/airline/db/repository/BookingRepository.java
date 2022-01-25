package com.ht.airline.db.repository;

import com.ht.airline.db.entity.Booking;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {

  ArrayList<Booking> findAllByCustomerId(String customerId);

  ArrayList<Booking> findAllByFlightId(String flightId);

  Booking findByIdAndStatus(String id, String status);
}
