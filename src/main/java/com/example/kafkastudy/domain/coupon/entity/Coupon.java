package com.example.kafkastudy.domain.coupon.entity;

import com.example.kafkastudy.domain.common.BaseEntity;
import com.example.kafkastudy.domain.coupon.entity.embedded.CouponIssueCondition;
import com.example.kafkastudy.domain.coupon.entity.embedded.CouponUseCondition;
import com.example.kafkastudy.domain.coupon.enums.CouponBenefitType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Coupon extends BaseEntity {

  private String name;

  private String displayName;

  @Enumerated(EnumType.STRING)
  private CouponBenefitType benefitType;

  // TODO : 사용 필드 추가!

  @Embedded
  private CouponUseCondition couponUseCondition;

  @Embedded
  private CouponIssueCondition couponIssueCondition;

}
