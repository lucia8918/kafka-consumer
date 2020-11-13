package com.example.kafkastudy.domain.coupon;

import com.example.kafkastudy.domain.coupon.entity.Coupon;
import com.example.kafkastudy.domain.coupon.repository.CouponRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponDomainService {

  private final CouponRepository couponRepository;

  @Transactional
  public Coupon createOne(Coupon coupon) {
    return couponRepository.save(coupon);
  }

  @Transactional(readOnly = true)
  public Coupon getOne(Long id) {
    return getOptional(id).orElseThrow(() -> new RuntimeException("Data Not Exist"));
  }

  @Transactional(readOnly = true)
  public Optional<Coupon> getOptional(Long id) {
    return couponRepository.findById(id);
  }

  @Transactional(readOnly = true)
  public List<Coupon> getList() {
    return couponRepository.findAll();
  }

  @Transactional(readOnly = true)
  public long count() {
    return couponRepository.count();
  }
}
