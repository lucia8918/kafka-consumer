package com.example.kafkastudy.application.consumer.issue;

import com.example.kafkastudy.application.request.issue.CouponIssueCancelRequest;
import com.example.kafkastudy.application.request.issue.CouponIssueDirectCreateRequest;
import com.example.kafkastudy.common.StatusCode;
import com.example.kafkastudy.constant.KafkaConstant;
import com.example.kafkastudy.domain.coupon.CouponDomainService;
import com.example.kafkastudy.domain.coupon.entity.Coupon;
import com.example.kafkastudy.domain.issue.CouponIssueDomainService;
import com.example.kafkastudy.domain.issue.entity.CouponIssue;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponIssueConsumer {

  private final CouponIssueDomainService couponIssueDomainService;
  private final CouponDomainService couponDomainService;

  public List<CouponIssue> createCouponIssues(List<CouponIssue> couponIssues) {
    return couponIssueDomainService.createAll(couponIssues);
  }

  @Transactional
  public long cancelCouponIssues(Coupon coupon) {
    // 삭제 가능 쿠폰 가져오기
    List<CouponIssue> availableList = couponIssueDomainService.getAvailableList(coupon);

    // 삭제 처리
    List<CouponIssue> cancelCouponIssues = availableList.stream().map(couponIssue -> {
      couponIssue.setDeleted(StatusCode.IS_TRUE); // 삭제 처리
      return couponIssue;
    }).collect(Collectors.toList());

    couponIssueDomainService.modifyAll(cancelCouponIssues);

    return cancelCouponIssues.size();
  }

  /**
   * 쿠폰 자동 지급 토픽 리스너
   *
   * @param cr
   */
  @KafkaListener(groupId = "consumer-issue-group", topics = KafkaConstant.COUPON_ISSUE_DIRECT_TOPIC)
  public void listenCouponIssueDirect(ConsumerRecord<?, ?> cr) {
    log.info(">>>> 쿠폰 자동 지급 시작 >>>>");
    log.info(">>>> Subscribe value :: {}", cr.value());
    CouponIssueDirectCreateRequest request = (CouponIssueDirectCreateRequest) cr.value();
    Coupon coupon = couponDomainService.getOne(request.getCouponId());

    // Setting coupon issues
    List<CouponIssue> couponIssues = request.getUsers().stream().map(user -> {
      return CouponIssue.builder().couponId(coupon).userId(user.getUserId()).build();
    }).collect(Collectors.toList());

    List<CouponIssue> createdCouponIssues = createCouponIssues(couponIssues);

    log.info(">>>> 쿠폰 자동 지급 개수 :: {}",
        createdCouponIssues.size());
  }

  /**
   * 쿠폰 자동 회수 토픽 리스너
   *
   * @param cr
   */
  @KafkaListener(groupId = "consumer-issue-cancel-group", topics = KafkaConstant.COUPON_CANCEL_ISSUED_TOPIC)
  public void listenCouponCancelIssued(ConsumerRecord<?, ?> cr) {
    log.info(">>>> 쿠폰 지급 회수 시작 >>>>");
    log.info(">>>> Subscribe value :: {}", cr.value());
    CouponIssueCancelRequest request = (CouponIssueCancelRequest) cr.value();
    Coupon coupon = couponDomainService.getOne(request.getCouponId());

    long cancelCouponIssuesCount = cancelCouponIssues(coupon);
    log.info(">>>> 쿠폰 자동 회수 개수 :: {}",
        cancelCouponIssuesCount);
  }

}
