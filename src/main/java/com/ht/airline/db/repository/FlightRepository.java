package com.ht.airline.db.repository;

import com.ht.airline.db.entity.Flight;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends JpaRepository<Flight, String> {

  ArrayList<Flight> findBySourceAirportIdAndDestinationAirportIdAndDepartureDate(
      String sourceId, String targetId, Date date);

  ArrayList<Flight> findByStatusAndDepartureTsLessThan(String status, Timestamp now);

  Flight findByIdAndStatus(String flightId, String status);
}
