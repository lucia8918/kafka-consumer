package com.example.kafkastudy.application.response;

import com.example.kafkastudy.domain.coupon.entity.Coupon;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CouponCreatedResponse {

  private long couponId;
  private String couponName;

  public static CouponCreatedResponse of(Coupon coupon) {
    return CouponCreatedResponse.builder().couponId(coupon.getId()).couponName(coupon.getName())
        .build();
  }
}
