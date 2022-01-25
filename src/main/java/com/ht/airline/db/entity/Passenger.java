package com.ht.airline.db.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "passenger")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Passenger extends BaseEntity {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @Column(name = "id", length = 36)
  private String id;

  @Column(name = "first_name", columnDefinition = "text", nullable = false)
  private String firstName;

  @Column(name = "last_name", columnDefinition = "text", nullable = false)
  private String lastName;

  @Column(name = "mobile_number", columnDefinition = "text")
  private String mobileNumber;

  @Column(name = "email", columnDefinition = "text", nullable = false)
  private String email;

  @Column(name = "pnr", columnDefinition = "text", nullable = false)
  private String pnr;

  @Column(name = "seat_number", columnDefinition = "text", nullable = false)
  private String seatNumber;

  @Column(name = "seat_class", columnDefinition = "text", length = 1, nullable = false)
  private String seatClass;
}
