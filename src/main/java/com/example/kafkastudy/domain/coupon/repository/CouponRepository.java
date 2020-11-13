package com.example.kafkastudy.domain.coupon.repository;

import com.example.kafkastudy.domain.coupon.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

}
