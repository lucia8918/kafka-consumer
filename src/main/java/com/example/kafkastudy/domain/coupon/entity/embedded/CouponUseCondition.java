package com.example.kafkastudy.domain.coupon.entity.embedded;

import java.time.LocalDateTime;
import javax.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class CouponUseCondition {

  private LocalDateTime useStartDate;
  private LocalDateTime userEndDate;

  // TODO : 사용 필드 추가!

}
