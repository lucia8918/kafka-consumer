package com.example.kafkastudy.application.consumer.coupon;

import com.example.kafkastudy.application.request.coupon.CouponCreateRequest;
import com.example.kafkastudy.application.request.issue.CouponIssueDirectCreateRequest;
import com.example.kafkastudy.application.request.user.UserInfo;
import com.example.kafkastudy.application.response.CouponCreatedResponse;
import com.example.kafkastudy.constant.KafkaConstant;
import com.example.kafkastudy.domain.coupon.CouponDomainService;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaNull;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponConsumer {

  private final CouponDomainService couponDomainService;

  @Qualifier("KafkaTemplate") private final KafkaTemplate<String, CouponIssueDirectCreateRequest> template;

  public CouponCreatedResponse createCoupon(CouponCreateRequest couponCreateRequest) {
    return CouponCreatedResponse.of(couponDomainService.createOne(couponCreateRequest.toEntity()));
  }

  public List<UserInfo> dummyUsers(){

    // 사용자 100명 설정
    return Stream.iterate(1, n->n+1).limit(100).map(userId ->{
      return UserInfo.builder().userId(userId).build();
    }).collect(Collectors.toList());
  }

  @KafkaListener(groupId = "consumer-coupon-group", topics = KafkaConstant.COUPON_CREATE_TOPIC)
  public void listen(ConsumerRecord<?, ?> cr) {
    log.info(">>>> 쿠폰 정책 발급 시작 >>>>");
    log.info(">>>> Subscribe value :: {}", cr.value());

    // Step.1 - 쿠폰 정책 생성
    CouponCreatedResponse createdCoupon = createCoupon((CouponCreateRequest) cr.value());

    log.info(">>>> 생성 쿠폰 정보 :: {}", createdCoupon.getCouponId());

    // Step.2 - 즉시발급 쿠폰인경우, 'direct-coupon-issue' 토픽용 컨텐츠 생성

    // 더미 사용자 셋팅
    List<UserInfo> userInfos = dummyUsers();

    // 내용 셋팅
    CouponIssueDirectCreateRequest couponIssueDirectCreateRequest = CouponIssueDirectCreateRequest.builder()
        .couponId(createdCoupon.getCouponId()).users(userInfos).build();

    log.info(">>>> 즉시 발급 토픽 생성 :: {}", couponIssueDirectCreateRequest.toString());

    // Step.3 - 'direct-coupon-issue' 토픽 퍼블리싱
    this.template.send(KafkaConstant.COUPON_ISSUE_DIRECT_TOPIC, couponIssueDirectCreateRequest);

  }

  // @KafkaListener(topics = "kRequests")
  // @SendTo
  public Object listenAttackCoupon(ConsumerRecord<?, ?> cr) {
    log.info(">>>> Time Attack Coupon Listener.....");
    System.out.println("Server received: " + cr);
    String in = (String) cr.value();
    return in.equals("foo") ? in.toUpperCase() : KafkaNull.INSTANCE;
  }

}
