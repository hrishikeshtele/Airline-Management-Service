package com.ht.airline.db.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "air_carrier")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AirCarrier extends BaseEntity {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @Column(name = "id", length = 36)
  private String id;

  @Column(name = "name", columnDefinition = "text", nullable = false)
  private String name;

  @Column(name = "time", nullable = false)
  private Double time;

  @Column(name = "base_price", nullable = false)
  private Double basePrice;

  @Column(name = "premium_multiplier", nullable = false)
  private Double premiumMultiplier;

  @Column(name = "business_multiplier", nullable = false)
  private Double businessMultiplier;
}
