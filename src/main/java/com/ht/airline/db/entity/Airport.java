package com.ht.airline.db.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "airport")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Airport extends BaseEntity {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @Column(name = "id", length = 36)
  private String id;

  @Column(name = "name", columnDefinition = "text")
  private String name;

  @Column(name = "city", length = 30, columnDefinition = "text", nullable = false)
  private String city;

  @Column(name = "city_code", length = 3, columnDefinition = "text", nullable = false)
  private String cityCode;

  @Column(name = "country", length = 30, columnDefinition = "text")
  private String country;

  @Column(name = "country_code", length = 3, columnDefinition = "text")
  private String countryCode;

  @Column(name = "latitude", length = 30, columnDefinition = "text")
  private String latitude;

  @Column(name = "longitude", length = 30, columnDefinition = "text")
  private String longitude;
}
