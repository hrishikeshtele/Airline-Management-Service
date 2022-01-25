package com.ht.airline.db.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(
    name = "customer",
    indexes = {@Index(name = "email_index", columnList = "email", unique = true)})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer extends BaseEntity {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @Column(name = "id", length = 36)
  private String id;

  @Column(name = "first_name", columnDefinition = "text", nullable = false)
  private String firstName;

  @Column(name = "last_name", columnDefinition = "text", nullable = false)
  private String lastName;

  @Column(name = "mobile_number", columnDefinition = "text", nullable = false)
  private String mobileNumber;

  @Column(name = "email", columnDefinition = "text", nullable = false)
  private String email;

  @Column(name = "pwd_hash", columnDefinition = "text", nullable = false)
  private String pwdHash;

  @Column(name = "rewards")
  private Double rewards;

  @Column(name = "admin", nullable = false, columnDefinition = "BOOLEAN default '0'")
  private Boolean admin;
}
