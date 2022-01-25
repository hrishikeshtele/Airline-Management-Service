package com.ht.airline.db.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "booking")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Booking extends BaseEntity {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @Column(name = "id", length = 36)
  private String id;

  @Column(name = "flight_id", columnDefinition = "text", nullable = false)
  private String flightId;

  @Column(name = "customer_id", columnDefinition = "text", nullable = false)
  private String customerId;

  @Column(name = "passenger_ids", columnDefinition = "text", nullable = false)
  private String passengerIds;

  @Column(name = "amount", nullable = false)
  private Double amount;

  @Column(name = "status", nullable = false)
  private String status;

  @Column(name = "rewards_used", nullable = false)
  private Double rewardsUsed;
}
