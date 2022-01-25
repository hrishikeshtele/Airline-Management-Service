package com.ht.airline.db.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@MappedSuperclass
@Data
public abstract class BaseEntity implements Serializable {

  @CreationTimestamp
  @Column(name = "created_timestamp")
  private Timestamp createdTs;

  @UpdateTimestamp
  @Column(name = "updated_timestamp")
  private Timestamp updatedTs;
}
