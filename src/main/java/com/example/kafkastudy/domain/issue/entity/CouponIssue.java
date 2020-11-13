package com.example.kafkastudy.domain.issue.entity;

import com.example.kafkastudy.domain.coupon.entity.Coupon;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CouponIssue {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "coupon_id")
  private Coupon couponId;

  private Long userId;

  private boolean isUsed;

  private LocalDateTime usableStartDate;

  private LocalDateTime usableEndDate;

  private boolean isDeleted;

  @PrePersist
  protected void onPersist() {
    isDeleted = false;
    isUsed = false;
    usableStartDate = LocalDateTime.now();
    usableStartDate = LocalDateTime.now().plusDays(1);
  }
}
