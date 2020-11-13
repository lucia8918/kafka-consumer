package com.example.kafkastudy.domain.common;

import com.example.kafkastudy.common.StatusCode;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import lombok.Getter;

@Getter
@MappedSuperclass
public class BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected Long id;

  @Column(name = "isDeleted")
  protected boolean isDeleted;

  @Column(name = "createdAt")
  protected LocalDateTime createdAt;

  @Column(name = "updatedAt")
  protected LocalDateTime updatedAt;

  @PrePersist
  protected void onPersist() {
    this.createdAt = this.updatedAt = LocalDateTime.now();
    isDeleted = StatusCode.IS_FALSE;
  }

  @PreUpdate
  protected void onUpdate() {
    this.updatedAt = LocalDateTime.now();
  }


}
