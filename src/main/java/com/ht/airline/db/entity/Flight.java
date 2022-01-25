package com.ht.airline.db.entity;

import java.sql.Date;
import java.sql.Timestamp;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "flight")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Flight extends BaseEntity {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @Column(name = "id", length = 36)
  private String id;

  @Column(name = "air_carrier_id", columnDefinition = "text", nullable = false)
  private String airCarrierId;

  @Column(name = "source_airport_id", nullable = false)
  private String sourceAirportId;

  @Column(name = "destination_airport_id", nullable = false)
  private String destinationAirportId;

  @Column(name = "departure_date", nullable = false)
  private Date departureDate;

  @Column(name = "departure_timestamp", nullable = false)
  private Timestamp departureTs;

  @Column(name = "arrival_timestamp", nullable = false)
  private Timestamp arrivalTs;

  @Column(name = "economy_price", nullable = false)
  private Double economyPrice;

  @Column(name = "premium_economy_price", nullable = false)
  private Double premiumEconomyPrice;

  @Column(name = "business_price", nullable = false)
  private Double businessPrice;

  @Column(name = "economy_seats", nullable = false)
  private String economySeats;

  @Column(name = "premium_seats", nullable = false)
  private String premiumSeats;

  @Column(name = "business_seats", nullable = false)
  private String businessSeats;

  @Column(name = "status", nullable = false)
  private String status;
}
