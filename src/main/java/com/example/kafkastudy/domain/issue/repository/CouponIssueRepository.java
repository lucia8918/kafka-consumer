package com.example.kafkastudy.domain.issue.repository;

import com.example.kafkastudy.domain.coupon.entity.Coupon;
import com.example.kafkastudy.domain.issue.entity.CouponIssue;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponIssueRepository extends JpaRepository<CouponIssue, Long> {

  List<CouponIssue> findAllByCouponIdAndIsUsedFalseAndIsDeletedFalse(Coupon coupon);

  long countByCouponIdAndIsUsedFalseAndIsDeletedFalse(Coupon coupon);

}
