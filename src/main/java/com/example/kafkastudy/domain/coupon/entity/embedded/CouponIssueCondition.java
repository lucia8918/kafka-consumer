package com.example.kafkastudy.domain.coupon.entity.embedded;

import java.time.LocalDateTime;
import javax.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class CouponIssueCondition {

  private LocalDateTime issueStartDate;
  private LocalDateTime issueEndDate;

  // TODO : 사용 필드 추가!

}
