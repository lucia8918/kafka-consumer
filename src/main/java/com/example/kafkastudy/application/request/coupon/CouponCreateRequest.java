package com.example.kafkastudy.application.request.coupon;

import com.example.kafkastudy.domain.coupon.entity.Coupon;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CouponCreateRequest implements Serializable {

  private static final long serialVersionUID = 1L;
  
  private String couponName;

  public Coupon toEntity() {
    return Coupon.builder().name(this.couponName).build();
  }
}
