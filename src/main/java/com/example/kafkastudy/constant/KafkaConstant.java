package com.example.kafkastudy.constant;

public final class KafkaConstant {

  // 쿠폰 정책 생성
  public static final String COUPON_CREATE_TOPIC = "create-coupon";

  // 즉시 지급 용 토픽
  public static final String COUPON_ISSUE_DIRECT_TOPIC = "direct-coupon-issue";

  // 전체 회수 처리용 토픽
  public static final String COUPON_CANCEL_ISSUED_TOPIC = "cancel-coupon-issued";

  public static final String TIME_ATTACK_COUPON_TOPIC = "time-attack-coupon-1";

}
